package com.qrCode.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qrCode.dto.QrCode;



@Repository
public interface QrCodeRepository extends JpaRepository<QrCode,Long>{

	//List<CheckedItem> findAllByChecklistId(Integer id);
	List<QrCode> findAllByPinCode(String pinCode);
	List<QrCode> findAllByDesiredUrl(String desiredUrl);
}
