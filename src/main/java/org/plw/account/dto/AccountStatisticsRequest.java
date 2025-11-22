package org.plw.account.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.plw.account.enums.GroupByType;
import org.plw.account.enums.TransactionType;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatisticsRequest {
    @NotNull(message = "账户ID列表不能为空")
    private List<String> accountIds;

    @NotNull(message = "开始日期不能为空")
    private LocalDate startDate;

    @NotNull(message = "结束日期不能为空")
    private LocalDate endDate;

    @NotNull(message = "分组方式不能为空")
    private GroupByType groupBy;

    private TransactionType transactionType;
}
