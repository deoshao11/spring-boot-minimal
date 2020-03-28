package com.spring.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class AccountBalance {
    String accountName;

    Map<String, BigDecimal> instruments;

    String type;
}
