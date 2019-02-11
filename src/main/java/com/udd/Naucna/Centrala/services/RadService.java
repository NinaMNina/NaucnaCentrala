package com.udd.Naucna.Centrala.services;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import com.udd.Naucna.Centrala.dto.RadDTO;

public interface RadService {

	boolean exists(Long id);

	Boolean saveMultipartFile(MultipartFile file, Long id);

	RadDTO getRadDTO(Long rad);

	Boolean dodajRecenzente(Long id, ArrayList<Long> odabrani);
	

}
