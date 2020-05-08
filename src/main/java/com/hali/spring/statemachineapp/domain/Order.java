package com.hali.spring.statemachineapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_order")
@Table(name = "tbl_order")
@Setter
@Getter
public class Order
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id; 

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "current_state")
	private OrderState currentState;

	@Column(name = "prepaid")
	private boolean prePaid;
}
