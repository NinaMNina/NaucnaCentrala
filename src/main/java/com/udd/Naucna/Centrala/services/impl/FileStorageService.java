package com.udd.Naucna.Centrala.services.impl;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.cofig.FileStorageProps;

@Service
public class FileStorageService {
	
	private final Path fileStorageLocation;
	
	 @Autowired
	    public FileStorageService(FileStorageProps fileStorageProperties) {

	        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
	                .toAbsolutePath().normalize();

	        try {
	            Files.createDirectories(this.fileStorageLocation);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	 
	 public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) 
                return resource;
            System.out.println("nije uspeo u servisu");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
		return null;	        
    }
}
