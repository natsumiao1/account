package org.plw.account.repository;

import org.plw.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SplitsRepository extends JpaRepository<Account, String> {
    @Query(value = "SELECT SUM(s.value_num::numeric / s.value_denom) FROM splits s WHERE s.account_guid = :accountGuid", nativeQuery = true)
    BigDecimal sumValueNumByAccountGuid(String accountGuid);

    @Query(value = "SELECT " + "TO_CHAR(t.post_date, ?1) as date, " + "SUM(s.value_num) as amount " + "FROM transactions t " + "JOIN splits s ON t.guid = s.tx_guid " + "WHERE s.account_guid IN (?2) " + "AND t.post_date BETWEEN ?3 AND ?4 " + "GROUP BY date " + "ORDER BY date", nativeQuery = true)
    List<Object[]> findExpenseStatisticsByAccountsAndDateRangeNative(String dateFormat, List<String> accountIds, LocalDate startDate, LocalDate endDate);

}
