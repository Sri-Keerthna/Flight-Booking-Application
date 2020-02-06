package com.spiralforge.easefly.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("GooglePay")
public class GooglePay implements Payment {

	Logger logger = LoggerFactory.getLogger(GooglePay.class);

	@Override
	public Boolean pay() {
		logger.info("Payment done through GooglePay");
		return true;
	}

}
