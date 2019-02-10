package com.udd.Naucna.Centrala.services;

import org.springframework.web.multipart.MultipartFile;

public interface RadService {

	boolean exists(Long id);

	String saveMultipartFile(MultipartFile file);
	

}
