package org.plw.account.service;

import org.plw.account.dto.AccountTreeDTO;

import java.util.List;

public interface AccountService {
    List<AccountTreeDTO> getAccountTree();
}
