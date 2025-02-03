package com.qrCode.dto;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class QrCode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String desiredUrl;
	
	@Column
	private String pinCode;
	
	@Column(columnDefinition="nvarchar(max)")
	private String base64;	
	
	@Column
	private Integer userId;
	
	@Column
	private LocalDateTime createdAt;
	
}
