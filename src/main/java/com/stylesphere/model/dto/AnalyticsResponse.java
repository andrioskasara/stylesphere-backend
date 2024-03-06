package com.stylesphere.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsResponse {
    private Long placed;
    private Long shipped;
    private Long delivered;
    private Long currentMonthOrders;
    private Long currentMonthEarnings;
    private Long previousMonthOrders;
    private Long previousMonthEarnings;
}
