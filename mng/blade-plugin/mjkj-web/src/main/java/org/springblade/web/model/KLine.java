package org.springblade.web.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KLine {
    public KLine() {

    }

    public KLine(String period) {
        this.period = period;
    }

    private BigDecimal openPrice = BigDecimal.ZERO;
    private BigDecimal highestPrice = BigDecimal.ZERO;
    private BigDecimal lowestPrice = BigDecimal.ZERO;
    private BigDecimal closePrice = BigDecimal.ZERO;
    private long time;
    private String period;

    /**
     * 成交笔数
     */
    private int count;
    /**
     * 成交量
     */
    private BigDecimal volume = BigDecimal.ZERO;
    /**
     * 成交额
     */
    private BigDecimal turnover = BigDecimal.ZERO;

    /**
     * 0：默认行情
     * 1：自定义行情
     */
    private Integer type;
}
