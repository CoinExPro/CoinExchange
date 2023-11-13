package org.springblade.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradePlateItem {
    private BigDecimal price;
    private BigDecimal amount;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(price).append(":").append(amount);
        return sb.toString();
    }
}
