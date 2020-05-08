package com.hali.spring.statemachineapp.domain;

public enum OrderEvent 
{
	ORDER_PLACED,
	UNLOCK_DELIVERY,
    PAYMENT_RECEIVED,
    REFUND,
    DELIVER,
    CANCEL, UNDELIVER;
}
