package org.plw.account.service.impl;

import org.plw.account.dto.AccountTreeDTO;
import org.plw.account.entity.Account;
import org.plw.account.repository.AccountRepository;
import org.plw.account.repository.SplitsRepository;
import org.plw.account.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
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

    private static boolean isRoot(String parentGuid) {
        return parentGuid == null || parentGuid.trim().isEmpty();
    }
}
