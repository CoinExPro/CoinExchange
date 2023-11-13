package org.springblade.web.model.param;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 参加市值维护
 */
@Data
public class MarketJoinParam {
    /**
     * 市值维护id
     */
    private String marketSymbolId;
    /**
     * 参与币种id
     */
    private String coinId;
    /**
     * 参与金额
     */
    private BigDecimal amount;
    /**
     * 是否续签
     */
    private String autoFlag;
}
