package com.hali.spring.statemachineapp.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.hali.spring.statemachineapp.domain.Order;
import com.hali.spring.statemachineapp.domain.OrderHistory;
import com.hali.spring.statemachineapp.domain.OrderState;
import com.hali.spring.statemachineapp.repositories.OrderHistoryRepository;
import com.hali.spring.statemachineapp.repositories.OrderRepository;

@SpringBootTest
class OrderServiceTest 
{

	@Autowired
	OrderRepository orderRepo;

	@Autowired
	OrderHistoryRepository orderHRepo;

	@Autowired
	OrderService service;

	Order order;

	@BeforeEach
	void setUp() throws Exception 
	{

		order = new Order();
		order.setCurrentState(OrderState.NEW);

	}

	@Test
	@Transactional
	void testNewOrder() throws Exception {
		Order saved = service.newOrder(order);  // postpay

		Order fetchOrder =  orderRepo.getOne(saved.getId());

		Assertions.assertEquals( OrderState.READY_FOR_DELIVERY,fetchOrder.getCurrentState());
	}

	@Test
	@Transactional
	void testReceivePayment() throws Exception {
		order.setPrePaid(true);
		Order saved = service.newOrder(order);

		service.paymentReceived(saved.getId());

		Order fetchOrder =  orderRepo.getOne(saved.getId());

		Assertions.assertEquals( OrderState.READY_FOR_DELIVERY ,fetchOrder.getCurrentState());
	}

	@Test
	@Transactional
	void testPostPaid() throws Exception {
		Order saved = service.newOrder(order);

		
		Order fetchOrder =  orderRepo.getOne(saved.getId());

		Assertions.assertEquals( OrderState.READY_FOR_DELIVERY ,fetchOrder.getCurrentState());
	}

	@Test
	@Transactional
	void testPrePaid() throws Exception {
		order.setPrePaid(true);
		Order saved = service.newOrder(order);

		Order fetchOrder =  orderRepo.getOne(saved.getId());

		Assertions.assertEquals( OrderState.PLACED ,fetchOrder.getCurrentState());
	}


}
