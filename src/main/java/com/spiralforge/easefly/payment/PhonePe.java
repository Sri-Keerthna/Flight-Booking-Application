package com.spiralforge.easefly.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("PhonePe")
public class PhonePe implements Payment {

	Logger logger = LoggerFactory.getLogger(PhonePe.class);

	@Override
	public Boolean pay() {
		logger.info("Payment done through PhonePe");
		return true;
	}
}
