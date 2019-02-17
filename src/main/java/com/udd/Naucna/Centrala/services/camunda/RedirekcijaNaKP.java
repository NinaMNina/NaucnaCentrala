package com.udd.Naucna.Centrala.services.camunda;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class RedirekcijaNaKP implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.setVariable("uspesnoPlacanje", true);	
	}

}