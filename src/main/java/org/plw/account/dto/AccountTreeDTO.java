package org.plw.account.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.plw.account.entity.Account;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "children")
public class AccountTreeDTO {
    private String guid;
    private String name;
    private String accountType;
    private List<AccountTreeDTO> children = new ArrayList<>();

    public AccountTreeDTO(Account account) {
        this.guid = account.getGuid();
        this.name = account.getName();
        this.accountType = account.getAccountType();
    }

    public void addChild(AccountTreeDTO child) {
        this.children.add(child);
    }

}
