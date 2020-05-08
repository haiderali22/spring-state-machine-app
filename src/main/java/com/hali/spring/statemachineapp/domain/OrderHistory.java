package com.hali.spring.statemachineapp.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "order_history")
@Table(name = "order_history")
@Setter
@Getter
public class OrderHistory 
{
	@Id
    @GeneratedValue
    private Long id;
	
	@ManyToOne
	@JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "fk_order_history_id"))
	Order order;


	@Enumerated(EnumType.STRING)
	private OrderEvent event;

	@Enumerated(EnumType.STRING)
	private OrderState previousState;
	
	@Enumerated(EnumType.STRING)
	private OrderState currentState;
}
