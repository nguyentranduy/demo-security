package com.m2m.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentResponse {

	String orderId;
    String totalPrice;
    String paymentTime;
    String transactionId;
}
