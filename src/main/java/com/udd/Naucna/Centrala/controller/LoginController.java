package com.udd.Naucna.Centrala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.token.TokenUtils;


@Controller
@RequestMapping("/login")
public class LoginController {
	//CAMUNDA
	/*@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;*/
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private TokenUtils tokenUtils;
	//CAMUNDA
/*	@GetMapping(path = "/get", produces = "application/json")
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
	@GetMapping(path = "/registracija/{id}")
    public @ResponseBody ResponseEntity<String> registracija(@PathVariable String id) {
		Task task = taskService.createTaskQuery().taskId(id).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String user = (String) runtimeService.getVariable(processInstanceId, "username");
		taskService.claim(id, user);
		Map<String, Object> retVal = new HashMap<>();
		retVal.put("retVal", "REGISTRACIJA");
		taskService.complete(id, retVal);
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
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
		Boolean retVal = tokenUtils.isTokenExpired(token);
		if(retVal==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		return new ResponseEntity(retVal, HttpStatus.OK);
		}*/
}
