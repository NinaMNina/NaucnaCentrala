package com.udd.Naucna.Centrala.services;

import org.springframework.web.multipart.MultipartFile;

import com.udd.Naucna.Centrala.dto.RadDTO;

public interface RadService {

	boolean exists(Long id);

	String saveMultipartFile(MultipartFile file);

	RadDTO getRadDTO(Long rad);
	

}
