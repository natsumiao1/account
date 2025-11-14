package org.plw.account.service;

import org.plw.account.dto.AccountTreeDTO;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    List<AccountTreeDTO> getAccountTree();

    BigDecimal getAccountBalanceByGuid(String accountGuid);
}
