package com.hali.spring.statemachineapp.services;

import java.util.Optional;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hali.spring.statemachineapp.domain.Order;
import com.hali.spring.statemachineapp.domain.OrderEvent;
import com.hali.spring.statemachineapp.domain.OrderHistory;
import com.hali.spring.statemachineapp.domain.OrderState;
import com.hali.spring.statemachineapp.repositories.OrderHistoryRepository;
import com.hali.spring.statemachineapp.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderState, OrderEvent> 
{
	private final OrderRepository orderRepository;
	private final OrderHistoryRepository orderHistoryRepository;
	
	@Override
	@Transactional
	public void preStateChange(State<OrderState, OrderEvent> state, Message<OrderEvent> message,
			Transition<OrderState, OrderEvent> transition, StateMachine<OrderState, OrderEvent> stateMachine) {
		
		Optional.ofNullable(message).ifPresent( msg -> {
				Optional.ofNullable(Long.class.cast( msg.getHeaders().get(OrderService.ORDER_ID_HEADER)))
				.ifPresent( orderID -> {
							Order order = orderRepository.getOne(orderID);	
							
							OrderState previousState = order.getCurrentState();
							
							order.setCurrentState(state.getId());
							
							orderRepository.save(order);
							
							OrderHistory oHistory = new OrderHistory();
							
							oHistory.setCurrentState(state.getId());
							oHistory.setPreviousState(previousState);
							oHistory.setEvent(transition.getTrigger().getEvent());
							
							oHistory.setOrder(order);
							
							orderHistoryRepository.save(oHistory);
							
					});
		});
		
	}
}