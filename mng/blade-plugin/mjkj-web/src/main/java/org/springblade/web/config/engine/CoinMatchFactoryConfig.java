package org.springblade.web.config.engine;

import lombok.extern.slf4j.Slf4j;
import org.springblade.web.config.engine.contract.CoinMatchContractFactory;
import org.springblade.web.config.engine.contract.CoinMatchContractFactoryAll;
import org.springblade.web.config.engine.exchange.CoinMatchFactoryExchange;
import org.springblade.web.config.engine.exchange.CoinMatchFactoryExchangeAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CoinMatchFactoryConfig {

	//获取现货
    @Bean
    public CoinMatchFactoryExchange getExchangeMatchFactory() {
        CoinMatchFactoryExchange factory = new CoinMatchFactoryExchange();
        return factory;
    }

	//获取现货-全仓
	@Bean
	public CoinMatchFactoryExchangeAll getExchangeAllMatchFactory() {
		CoinMatchFactoryExchangeAll factory = new CoinMatchFactoryExchangeAll();
		return factory;
	}

    //获取合约
	@Bean
	public CoinMatchContractFactory getCoinMatchContractFactory() {
		CoinMatchContractFactory factory = new CoinMatchContractFactory();
		return factory;
	}

	//获取合约-全仓
	@Bean
	public CoinMatchContractFactoryAll getCoinMatchContractFactoryAll() {
		CoinMatchContractFactoryAll factory = new CoinMatchContractFactoryAll();
		return factory;
	}



}
