package com.bkap.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bkap.DTO.PaymentRestDTO;
import com.bkap.entities.Account;
import com.bkap.service.CartService;
import com.bkap.vnpConfig.VNpayConfig;

import jakarta.servlet.http.HttpServletRequest;



@Controller
@RequestMapping("/api/payment")
public class PaymentController {
	@Autowired
	private CartService cartService;

	@GetMapping("/create_payment/{orderId}/{totalAmount}")
	public ResponseEntity<PaymentRestDTO> createPayment(jakarta.servlet.http.HttpServletRequest req,
			@PathVariable Integer orderId,
			Model model,
			@PathVariable String totalAmount) throws UnsupportedEncodingException {
		
		 totalAmount = totalAmount.replace(",", ".");
	    // Chuyển đổi totalAmount từ String sang double
	    double totalAmountDouble = Double.parseDouble(totalAmount);
		long amount = (long) (totalAmountDouble*100000);
		
		String bankCode = req.getParameter("bankCode");
		String vnp_TxnRef = String.valueOf(orderId);
		String vnp_IpAddr = VNpayConfig.getIpAddress(req);
		String vnp_TmnCode = VNpayConfig.vnp_TmnCode;
		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", VNpayConfig.vnp_Version);
		vnp_Params.put("vnp_Command", VNpayConfig.vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");

		if (bankCode != null && !bankCode.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bankCode);
		}
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", VNpayConfig.orderType);

		String locate = req.getParameter("language");
		if (locate != null && !locate.isEmpty()) {
			vnp_Params.put("vnp_Locale", locate);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", VNpayConfig.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

		Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String vnp_CreateDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		cld.add(Calendar.MINUTE, 15);
		String vnp_ExpireDate = formatter.format(cld.getTime());
		vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = VNpayConfig.hmacSHA512(VNpayConfig.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = VNpayConfig.vnp_PayUrl + "?" + queryUrl ;
		PaymentRestDTO dto = new PaymentRestDTO();
		dto.setStatus("OK");
		dto.setMessage("succesful");
		dto.setURL(paymentUrl);
//		return ResponseEntity.status(HttpStatus.OK).body(dto);

//		return "redirect:/" + paymentUrl;
		 return ResponseEntity.status(HttpStatus.FOUND)
             .location(URI.create(paymentUrl))  
                //Chuyển hướng đến paymentUrl
                .build();

	}
	@GetMapping("/vn-pay-callback")
    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        com.bkap.DTO.PaymentDTO dot = new com.bkap.DTO.PaymentDTO();
        if (status.equals("00")) {
            dot.setCode("00");
            dot.setMessage("succerss");
            dot.setPaymentUrl("");
            return ResponseEntity.status(HttpStatus.OK).body(dot);
        } else {
        	dot.setCode("00");
            dot.setMessage("fail");
            dot.setPaymentUrl("");
            return ResponseEntity.status(HttpStatus.OK).body(dot);
        }
    }

}
