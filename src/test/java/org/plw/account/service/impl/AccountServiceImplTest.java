package org.plw.account.service.impl;

import org.junit.jupiter.api.Test;
import org.plw.account.dto.AccountTreeDTO;
import org.plw.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class AccountServiceImplTest {

    @Autowired
    private AccountService accountService;

    @Test
    void getAccountTree() {
        List<AccountTreeDTO> accountTree = accountService.getAccountTree();
        System.out.println(accountTree);
    }
}