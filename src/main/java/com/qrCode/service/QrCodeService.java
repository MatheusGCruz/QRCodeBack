package com.qrCode.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.qrCode.dto.QrCode;
import com.qrCode.repository.QrCodeRepository;
import com.qrCode.utils.StringUtils;

@Service
public class QrCodeService {
	
	@Autowired 	public QrCodeRepository qrCodeRepository;
	@Autowired	public StringUtils 		stringUtils;
	
    public QrCode upsertQrCode(String desiredUrl) {
    	List<QrCode> qrCodeList = qrCodeRepository.findAllByDesiredUrl(desiredUrl);
    	if(qrCodeList.size()>0) {
    		return qrCodeList.get(0);
    	}
    	return generateQRCode(desiredUrl, 256, 256);    	
    }
    

       
    public String getUrl(String pinCode) {
    	List<QrCode> qrCodeList = qrCodeRepository.findAllByPinCode(pinCode);
    	
    	if(qrCodeList.size()==0) {
    		return "";
    	}
    	
    	return qrCodeList.get(0).getDesiredUrl();
    }
    
    private QrCode generateQRCode(String desiredUrl, int width, int height) {
        try {
        	QrCode newQrCode = generateQrCode();
        	
        	
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(newQrCode.getPinCode(), BarcodeFormat.QR_CODE, width, height);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "jpeg", outputStream);
            
            String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            
            return saveQrCode(newQrCode, desiredUrl, base64);
        } catch (WriterException | java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    
    private QrCode saveQrCode(QrCode newQrCode, String desiredUrl, String base64) {
    	newQrCode.setBase64(base64);
    	newQrCode.setDesiredUrl(desiredUrl);
    	
    	return qrCodeRepository.save(newQrCode);
    }
    
    private QrCode generateQrCode() {
    	QrCode newQrCode = new QrCode();
    	//newQrCode.setDesiredUrl(desiredUrl);
    	//newQrCode.setBase64(base64);
    	newQrCode.setUserId(1);
    	newQrCode.setPinCode(generatePinCode());
    	newQrCode.setCreatedAt(LocalDateTime.now());    	
    	return qrCodeRepository.save(newQrCode);
    	
    } 
    
    private String generatePinCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder pinCode = new StringBuilder();
        pinCode.append(stringUtils.getBaseUrl());
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            pinCode.append(chars.charAt(random.nextInt(chars.length())));
        }
        return pinCode.toString();
    }
}
