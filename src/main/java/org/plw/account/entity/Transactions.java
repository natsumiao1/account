package org.plw.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transactions {
    @Id
    @Size(max = 32)
    @Column(name = "guid", nullable = false, length = 32)
    private String guid;

    @Size(max = 32)
    @NotNull
    @Column(name = "currency_guid", nullable = false, length = 32)
    private String currencyGuid;

    @Size(max = 2048)
    @NotNull
    @Column(name = "num", nullable = false, length = 2048)
    private String num;

    @Column(name = "post_date")
    private Instant postDate;

    @Column(name = "enter_date")
    private Instant enterDate;

    @Size(max = 2048)
    @Column(name = "description", length = 2048)
    private String description;

}