package org.plw.account.service.impl;

import org.plw.account.dto.AccountTreeDTO;
import org.plw.account.entity.Account;
import org.plw.account.repository.AccountRepository;
import org.plw.account.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountTreeDTO> getAccountTree() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts == null || accounts.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, AccountTreeDTO> nodeMap = accounts.stream()
                .collect(Collectors.toMap(
                        Account::getGuid,
                        AccountTreeDTO::new, // 使用你的构造函数
                        (existing, replacement) -> {
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
        return roots;
    }

    private static boolean isRoot(String parentGuid) {
        return parentGuid == null || parentGuid.trim().isEmpty();
    }
}
