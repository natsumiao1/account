package org.plw.account.controller;

import org.plw.account.dto.AccountStatisticsRequest;
import org.plw.account.dto.AccountStatisticsResponse;
import org.plw.account.dto.AccountTreeDTO;
import org.plw.account.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/statistics")
    public ResponseEntity<AccountStatisticsResponse> getAccountStatistics(
            @RequestBody AccountStatisticsRequest request) {
        AccountStatisticsResponse response = accountService.getStatistics(request);
        return ResponseEntity.ok(response);
    }
}
