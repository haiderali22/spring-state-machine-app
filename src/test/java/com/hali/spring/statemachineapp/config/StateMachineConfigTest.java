package com.hali.spring.statemachineapp.config;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import com.hali.spring.statemachineapp.domain.OrderEvent;
import com.hali.spring.statemachineapp.domain.OrderState;
import com.hali.spring.statemachineapp.services.OrderService;

@SpringBootTest
class StateMachineConfigTest 
{
	@Autowired
	StateMachineFactory<OrderState, OrderEvent> factory ;
	
	@Test
	public void testNewStateMachine()
	{
		StateMachine<OrderState, OrderEvent> sm = factory.getStateMachine(UUID.randomUUID());
		
		sm.start();
		
		System.out.println(sm.getState().toString());
		
		sm.sendEvent(OrderEvent.ORDER_PLACED);
		
		System.out.println(sm.getState().toString());
		

		sm.sendEvent(OrderEvent.PAYMENT_RECEIVED);
		
		System.out.println(sm.getState().toString());
	}

}
