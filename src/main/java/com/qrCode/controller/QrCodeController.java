package com.qrCode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qrCode.dto.QrCode;
import com.qrCode.service.QrCodeService;

@RestController
@RequestMapping("/")
public class QrCodeController {
	
	@Autowired 
	private QrCodeService qrCodeService;
	
	@PostMapping("/generateQrCode")
	public String generateQrCode(@RequestBody String desiredUrl) {
		QrCode generatedQrCode;
		try {
			generatedQrCode = qrCodeService.upsertQrCode(desiredUrl);
			return generatedQrCode.getBase64();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Not Found";
		}
		
	}
	
	@PostMapping("/getUrl")
	public String getUrl(@RequestBody String pinCode) {
		try {
			return qrCodeService.getUrl(pinCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Not Found";
		}
	}

}
