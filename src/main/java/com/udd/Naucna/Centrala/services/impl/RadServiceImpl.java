package com.udd.Naucna.Centrala.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udd.Naucna.Centrala.repository.elasticSearch.ElasticSearchRepository;
import com.udd.Naucna.Centrala.services.RadService;

@Service
public class RadServiceImpl implements RadService {

	@Autowired
	private ElasticSearchRepository elasticSearchRepository;
	
	@Override
	public boolean exists(Long id) {
		if(elasticSearchRepository.findById(id)!=null)
			return true;
		return false;
	}

	@Override
	public String saveMultipartFile(MultipartFile file) {
		final Path rootLocation = Paths.get("src/main/resources/static/assets/pdf/");
		String fileName = null;
		try {
			fileName = file.getOriginalFilename();
			if(!(fileName.endsWith(".PDF")|| fileName.endsWith(".pdf"))) {
				return fileName;
			}
			Path filePath = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
            	fileName = fileName.substring(0, fileName.length() - 4);
            		fileName = fileName + "_"  + System.currentTimeMillis() + ".pdf";
            }
            Files.copy(file.getInputStream(), rootLocation.resolve(fileName));
            System.out.println("fileName: "+fileName);
            System.out.println("rootLocation: "+rootLocation.toString());
        } catch (Exception e) {
        	throw new RuntimeException();
        }
		return "assets/pdf/"+fileName;
	}

}
