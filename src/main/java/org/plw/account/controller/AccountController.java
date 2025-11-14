package org.plw.account.controller;

import org.plw.account.dto.AccountTreeDTO;
import org.plw.account.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/tree")
    public List<AccountTreeDTO> getAccountTree() {
        return accountService.getAccountTree();
    }

    @GetMapping("/balance/{accountGuid}")
    public BigDecimal getAccountBalanceByGuid(@PathVariable String accountGuid) {
        return accountService.getAccountBalanceByGuid(accountGuid);
    }
}
