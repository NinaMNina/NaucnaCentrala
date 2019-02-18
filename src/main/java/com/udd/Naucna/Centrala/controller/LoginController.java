package com.udd.Naucna.Centrala.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udd.Naucna.Centrala.dto.FormFieldsCamunda;
import com.udd.Naucna.Centrala.dto.KorisnikDTO;
import com.udd.Naucna.Centrala.dto.TaskDto;
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
	IdentityService identityService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@PostMapping(path = "/do", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<KorisnikDTO> getDo(@RequestBody KorisnikDTO korisnik) {
		Korisnik k = korisnikService.uloguj(korisnik);
		if(k==null)
			return new ResponseEntity(null, HttpStatus.OK);
		String token = tokenUtils.generateToken(CustomUserDetailsFactory.createCustomUserDetails(k));
		korisnik.setLozinka(token);
		return new ResponseEntity(korisnik, HttpStatus.OK);		
    }
	@GetMapping(path = "/get", produces = "application/json")
    public @ResponseBody ResponseEntity<FormFieldsCamunda> getFormFields(){
		//provera da li korisnik sa id-jem pera postoji
//		List<User> users = identityService.createUserQuery().userId("pera").list();
/*		String username = tokenUtils.getUsernameFromToken(token);
		boolean jedinstven = true;*/
		String piId = "";
/*		if(!taskService.createTaskQuery().taskAssignee(username).list().isEmpty()){
			List<Task> zadaci = taskService.createTaskQuery().taskAssignee(username).list();
			for(Task z : zadaci){
				if(z.getName().equals("")){
					jedinstven = false;
					piId = z.getProcessInstanceId();
				}
			}
		}
		if(jedinstven)*/
			piId = runtimeService.startProcessInstanceByKey("upravljanje_poslovnim_procesima").getId();
		Task task = taskService.createTaskQuery().processInstanceId(piId).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		ArrayList<FormField> properties = (ArrayList<FormField>) tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new ResponseEntity(new FormFieldsCamunda(task.getId(),properties, piId), HttpStatus.OK);
    }
	
	@GetMapping(path = "/registracija/{id}")
    public @ResponseBody ResponseEntity<String> registracija(@PathVariable String id) {
		Task task = taskService.createTaskQuery().taskId(id).singleResult();
	/*	String processInstanceId = task.getProcessInstanceId();
		String user = (String) runtimeService.getVariable(processInstanceId, "username");
		taskService.claim(id, user);*/
		Map<String, Object> retVal = new HashMap<>();
		retVal.put("retVal", "REGISTRACIJA");
		taskService.complete(id, retVal);
		ArrayList<Task> tasks = (ArrayList<Task>) taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task0 : tasks) {
			TaskDto t = new TaskDto(task0.getId(), task0.getName(), task0.getAssignee());
			dtos.add(t);
		}
		
        return null;
    }
	@GetMapping(path = "/checkValidity/{token}")
    public @ResponseBody ResponseEntity<Boolean> checkValidity(@PathVariable String token) {
		if(token.isEmpty() || token==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		if(tokenUtils.getUsernameFromToken(token)==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		Boolean retVal = !tokenUtils.isTokenExpired(token);
		if(retVal==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		return new ResponseEntity(retVal, HttpStatus.OK);
		}
}
