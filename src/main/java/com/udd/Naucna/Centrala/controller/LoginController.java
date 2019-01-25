package com.udd.Naucna.Centrala.controller;

import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udd.Naucna.Centrala.dto.FormFieldsCamunda;
import com.udd.Naucna.Centrala.dto.KorisnikDTO;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.security.CustomUserDetailsFactory;
import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.token.TokenUtils;


@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody FormFieldsCamunda get() {
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("upravljanje_poslovnim_procesima");

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsCamunda(task.getId(),properties, pi.getId());
    }
	
	@PostMapping(path = "/do", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<String> get(KorisnikDTO korisnik) {
		Korisnik k = korisnikService.uloguj(korisnik);
		if(k==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		String token = tokenUtils.generateToken(CustomUserDetailsFactory.createCustomUserDetails(k));
		return new ResponseEntity(token, HttpStatus.OK);		
    }
}
