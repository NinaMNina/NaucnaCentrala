package com.udd.Naucna.Centrala.cofig;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProps {
    private String uploadDir = "C:\\Users\\nina.miladinovic\\git\\NaucnaCentrala\\src\\main\\resources\\static\\assets\\pdf_proba";

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
