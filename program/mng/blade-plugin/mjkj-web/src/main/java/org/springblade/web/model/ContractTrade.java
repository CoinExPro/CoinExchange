package org.springblade.web.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springblade.core.tool.utils.Func;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ContractTrade implements Serializable {
	private String topic;//主题

    private String symbolName;//BTC/USDT
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal buyTurnover;
    private BigDecimal sellTurnover;
    private String direction;
    private String buyOrderId;
    private String sellOrderId;
    private Long time;
    private Date createTime;
    @Override
    public String toString() {
        return  JSON.toJSONString(this);
    }

	public void setCreateTime(Date createTime) {
    	if(Func.isNotEmpty(createTime)){
    		time=createTime.getTime();
		}
		this.createTime = createTime;
	}
}
