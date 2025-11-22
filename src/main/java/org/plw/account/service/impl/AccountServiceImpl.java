package org.plw.account.service.impl;

import org.plw.account.dto.AccountStatisticsData;
import org.plw.account.dto.AccountStatisticsRequest;
import org.plw.account.dto.AccountStatisticsResponse;
import org.plw.account.dto.AccountTreeDTO;
import org.plw.account.entity.Account;
import org.plw.account.repository.AccountRepository;
import org.plw.account.repository.SplitsRepository;
import org.plw.account.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final SplitsRepository splitsRepository;

    public AccountServiceImpl(AccountRepository accountRepository, SplitsRepository splitsRepository) {
        this.accountRepository = accountRepository;
        this.splitsRepository = splitsRepository;
    }

    @Override
    public List<AccountTreeDTO> getAccountTree() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, AccountTreeDTO> nodeMap = accounts.stream()
                .collect(Collectors.toMap(
                        Account::getGuid,
                        AccountTreeDTO::new, // 使用你的构造函数
                        (existing, _) -> {
                            throw new IllegalStateException("Duplicate guid: " + existing.getGuid());
                        }
                ));

        List<AccountTreeDTO> roots = new ArrayList<>();
        for (Account account : accounts) {
            AccountTreeDTO node = nodeMap.get(account.getGuid());
            String parentGuid = account.getParentGuid();

            if (isRoot(parentGuid) || !nodeMap.containsKey(parentGuid)) {
                roots.add(node);
            } else {
                AccountTreeDTO parent = nodeMap.get(parentGuid);
                parent.addChild(node);
            }
        }

        roots.removeIf(account -> account.getAccountType().equals("Template Root"));
        return roots.getFirst().getChildren();
    }

    @Override
    public BigDecimal getAccountBalanceByGuid(String accountGuid) {
        return splitsRepository.sumValueNumByAccountGuid(accountGuid).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public AccountStatisticsResponse getStatistics(AccountStatisticsRequest request) {
        List<Object[]> results = splitsRepository.findExpenseStatisticsByAccountsAndDateRangeNative(request.getGroupBy().getDateFormat(), request.getAccountIds(), request.getStartDate(), request.getEndDate());
        List<AccountStatisticsData> statisticsData = results.stream()
                .map(row -> new AccountStatisticsData(
                        (String) row[0],
                        ((BigDecimal) row[1]).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN)
                ))
                .collect(Collectors.toList());

        return AccountStatisticsResponse.builder()
                .data(statisticsData)
                .build();
    }

    private static boolean isRoot(String parentGuid) {
        return parentGuid == null || parentGuid.trim().isEmpty();
    }
}
