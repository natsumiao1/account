package org.plw.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    private String guid;

    private String name;

    private String accountType;

    private String commodityGuid;

    private Integer commodityScu;

    private Integer nonStdScu;

    private String parentGuid;

    private String code;

    private String description;

    private Integer hidden;

    private Integer placeholder;

}
