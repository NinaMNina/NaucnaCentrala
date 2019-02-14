package com.udd.Naucna.Centrala.services.camunda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.services.KorisnikService;

@Service
public class ProcesiranjePodataka implements JavaDelegate{
	@Autowired
	private KorisnikService korisnikService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
//		Map<String, Object> input = runtimeService.getVariables("out");
		
		//Map<String, Object> input = execution.getVariables();		
		//execution.getVariables();
		String ime = execution.getVariable("ime").toString();
		String prezime = execution.getVariable("prezime").toString();
		String korisnickoIme = execution.getVariable("korisnickoIme").toString();
		String lozinka = execution.getVariable("lozinka").toString();
		String email = execution.getVariable("email").toString();
		Double geografskaDuzina = new Double(execution.getVariable("geografskaDuzina").toString());
		Double geografskaSirina = new Double(execution.getVariable("geografskaSirina").toString());
	//	System.out.println(input.toString());
		Autor autor = new Autor(null, korisnickoIme, lozinka, ime, prezime, email, createPoint(geografskaDuzina, geografskaSirina), new ArrayList<>(), new ArrayList<>());
		Korisnik korisnik0 = korisnikService.registruj(autor);
		Boolean retVal = true;
		if(korisnik0==null){
			retVal =  false;
		}
		else
		execution.setVariable("success", retVal);
		execution.setVariable("isOpenAccess", true);
		
	}

	private Point createPoint(Double geografskaDuzina, Double geografskaSirina) {
		Point retVal = new Point(geografskaSirina, geografskaDuzina);
		return retVal;
	}
	
}
