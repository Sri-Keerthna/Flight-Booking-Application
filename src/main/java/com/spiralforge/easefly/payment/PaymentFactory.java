package com.spiralforge.easefly.payment;

public interface PaymentFactory {
	
	Payment getPaymentMethod(String paymentType);

}
