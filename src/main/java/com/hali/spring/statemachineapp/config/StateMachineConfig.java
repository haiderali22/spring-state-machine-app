package com.hali.spring.statemachineapp.config;

import java.util.EnumSet;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.hali.spring.statemachineapp.domain.OrderEvent;
import com.hali.spring.statemachineapp.domain.OrderState;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderState, OrderEvent> 
{

    private final Action<OrderState, OrderEvent> orderPlacedAction;
	
	@Override
	public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception 
	{		
		states.withStates().initial(OrderState.NEW)
		.states(EnumSet.allOf(OrderState.class))
		.end(OrderState.COMPLETED)
		.end(OrderState.CANCELED)
//		.state(OrderState.PLACED, orderPlacedAction)
		.stateDo(OrderState.PLACED, orderPlacedAction);
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {

		transitions
			.withExternal()
				.source(OrderState.NEW)
				.event(OrderEvent.ORDER_PLACED)
				.target(OrderState.PLACED)			
				.action(orderPlacedAction)
		.and()
			.withExternal()
				.source(OrderState.PLACED)
				.target(OrderState.READY_FOR_DELIVERY)
				.event(OrderEvent.UNLOCK_DELIVERY)
		.and()
			.withExternal()
				.source(OrderState.PLACED)
				.target(OrderState.READY_FOR_DELIVERY)
				.event(OrderEvent.PAYMENT_RECEIVED);
		
	}

	@Override
	public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config) throws Exception {

		StateMachineListenerAdapter<OrderState, OrderEvent> adaptor = new StateMachineListenerAdapter<OrderState, OrderEvent>() {

			@Override
			public void stateChanged(State<OrderState, OrderEvent> from, State<OrderState, OrderEvent> to) {
				log.info(String.format("state changed from %s to %s", from.toString(),to.toString()));
			}

		};

		config.withConfiguration().listener(adaptor);
	}

}
