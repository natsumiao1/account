package org.plw.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "splits")
public class Splits {
    @Id
    private String guid;

    private String txGuid;

    private String accountGuid;

    private String memo;

    private String action;

    private String reconcileState;

    private LocalDateTime reconcileDate;

    private Long valueNum;

    private Long valueDenom;

    private Long quantityNum;

    private Long quantityDenom;

    private String lotGuid;

}
