package com.udd.Naucna.Centrala.services.camunda;

import java.util.ArrayList;

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
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.services.KorisnikService;

@Service
public class ProcesiranjePodataka implements JavaDelegate{
	@Autowired
	private KorisnikService korisnikService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private IdentityService identityService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
//		Map<String, Object> input = runtimeService.getVariables("out");
		
		//Map<String, Object> input = execution.getVariables();		
		//execution.getVariables();
		String ime = execution.getVariable("ar_ime").toString();
		String prezime = execution.getVariable("ar_prezime").toString();
		String korisnickoIme = execution.getVariable("ps_korisnickoIme").toString();
		String lozinka = execution.getVariable("ps_lozinka").toString();
		String email = execution.getVariable("ar_mail").toString();
		Double geografskaDuzina = new Double(execution.getVariable("ar_geografskaDuzina").toString());
		Double geografskaSirina = new Double(execution.getVariable("ar_geografskaSirina").toString());
	//	System.out.println(input.toString());
		Autor a = new Autor(null, korisnickoIme, lozinka, ime, prezime, email, createPoint(geografskaDuzina, geografskaSirina), new ArrayList<>(), new ArrayList<>());
		a = autorRepository.save(a);
//		System.out.println("registrovao:" + korisnickoIme + " --- "+lozinka);
		Boolean retVal = true;
		if(a==null){
			retVal =  false;
		}
		execution.setVariable("success", retVal);
		
		User user = identityService.newUser(a.getKorisnickoIme());
		user.setFirstName(a.getIme());
		user.setLastName(a.getPrezime());
		user.setEmail(a.getEmail());
		user.setPassword(a.getLozinka());
		identityService.saveUser(user);	
		identityService.createMembership(a.getKorisnickoIme(), "autor");
		
		execution.setVariable("isOpenAccess", true);
		
	}

	private Point createPoint(Double geografskaDuzina, Double geografskaSirina) {
		Point retVal = new Point(geografskaSirina, geografskaDuzina);
		return retVal;
	}
	
}
