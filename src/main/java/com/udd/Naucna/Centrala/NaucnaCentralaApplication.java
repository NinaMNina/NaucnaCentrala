package com.udd.Naucna.Centrala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.udd.Naucna.Centrala.cofig.FileStorageProps;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProps.class})
public class NaucnaCentralaApplication {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\Java\\jdk-11.0.1\\lib\\security\\cacerts");
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
		SpringApplication.run(NaucnaCentralaApplication.class, args);
	}

}

