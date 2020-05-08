package com.hali.spring.statemachineapp.domain;

public enum OrderState 
{
	NEW,PLACED,AWAITING_PAYMENT,READY_FOR_DELIVERY,SENT_FOR_DELIVERY,COMPLETED,CANCELED, REFUND;
}
