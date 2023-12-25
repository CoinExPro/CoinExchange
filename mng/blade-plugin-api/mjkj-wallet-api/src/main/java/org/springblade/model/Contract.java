package org.springblade.model;

import org.springblade.util.EthConvert;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;

@Data
public class Contract {
    //合约精度
    private String decimals;
    //合约地址
    private String address;
    private BigInteger gasLimit;
    private String eventTopic0;
    public EthConvert.Unit getUnit(){
        if(StringUtils.isEmpty(decimals))return EthConvert.Unit.ETHER;
        else return EthConvert.Unit.fromString(decimals);
    }
}
