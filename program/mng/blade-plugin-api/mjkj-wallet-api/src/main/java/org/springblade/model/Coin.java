package org.springblade.model;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@Component
public class Coin {
    private String name;
    private String unit;
    private String rpc;
    private String keystorePath;
    private BigDecimal defaultMinerFee;
    private String withdrawAddress;
    private String withdrawWallet;
    private String withdrawWalletPassword;
    private BigDecimal minCollectAmount;
    private BigInteger gasLimit;
    private BigDecimal gasSpeedUp = BigDecimal.ONE;
    private BigDecimal rechargeMinerFee;
    private String ignoreFromAddress;
    private String masterAddress;
}
