package org.plw.account.repository;

import org.plw.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface SplitsRepository extends JpaRepository<Account, String> {
    @Query(value = "SELECT SUM(s.value_num::numeric / s.value_denom) FROM splits s WHERE s.account_guid = :accountGuid", nativeQuery = true)
    BigDecimal sumValueNumByAccountGuid(String accountGuid);
}
