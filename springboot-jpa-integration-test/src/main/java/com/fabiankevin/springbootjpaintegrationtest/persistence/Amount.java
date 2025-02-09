package com.fabiankevin.springbootjpaintegrationtest.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {
    private BigDecimal value;
    private String currency;

    public static Amount ofPhp(Double value) {
        return new Amount(BigDecimal.valueOf(value), "PHP");
    }
}
