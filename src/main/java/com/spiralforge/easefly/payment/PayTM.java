package com.spiralforge.easefly.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("PayTM")
public class PayTM implements Payment {

	Logger logger = LoggerFactory.getLogger(PayTM.class);

	@Override
	public Boolean pay() {
		logger.info("Payment done through PayTM");
		return true;
	}

}
