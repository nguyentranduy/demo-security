package com.m2m.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.m2m.dto.PaymentResponse;
import com.m2m.service.VNPayService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/vnpay")
public class VNPayApi {

	@Autowired
	VNPayService vnPayService;

	@PostMapping("/submit")
	@RolesAllowed({"user", "admin"})
	public String doGetSubmit(@RequestParam("amount") int orderTotal,
			@RequestParam("orderInfo") String orderInfo,
			HttpServletRequest request) {

		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
		return vnpayUrl;
	}

	@GetMapping("/vnpay-payment")
	public ResponseEntity<?> GetMapping(HttpServletRequest request) {
		int paymentStatus = vnPayService.orderReturn(request);

		String orderInfo = request.getParameter("vnp_OrderInfo");
		String paymentTime = request.getParameter("vnp_PayDate");
		String transactionId = request.getParameter("vnp_TransactionNo");
		String totalPrice = request.getParameter("vnp_Amount");
		
		if (paymentStatus == 1) {
			return ResponseEntity.ok(new PaymentResponse(orderInfo, totalPrice, paymentTime, transactionId));
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
