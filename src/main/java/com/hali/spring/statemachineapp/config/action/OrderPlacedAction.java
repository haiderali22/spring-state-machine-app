package com.hali.spring.statemachineapp.config.action;

import java.util.Optional;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.hali.spring.statemachineapp.domain.OrderEvent;
import com.hali.spring.statemachineapp.domain.OrderState;
import com.hali.spring.statemachineapp.services.OrderService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderPlacedAction implements Action<OrderState, OrderEvent> 
{
	@Override
	public void execute(StateContext<OrderState, OrderEvent> context) 
	{

		Optional.ofNullable(context.getMessage()).ifPresent( msg -> {
			Optional.ofNullable(Boolean.class.cast( msg.getHeaders().get(OrderService.ORDER_PREPAID_HEADER)))
			.ifPresent( isPrePaid -> {
					
				if(!isPrePaid)
				{
					context.getStateMachine().sendEvent(OrderEvent.UNLOCK_DELIVERY);
				}

			});
		});
		
//		context.getStateMachine().sendEvent(OrderEvent.UNLOCK_DELIVERY);
		
	}
}
