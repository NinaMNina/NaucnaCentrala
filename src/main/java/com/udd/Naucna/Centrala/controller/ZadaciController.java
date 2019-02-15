package com.udd.Naucna.Centrala.controller;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udd.Naucna.Centrala.dto.TaskDto;
import com.udd.Naucna.Centrala.dto.ZadaciCamunda;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.token.TokenUtils;

@Controller
@RequestMapping("/zadaci")
public class ZadaciController {
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
	
	@GetMapping(path = "/get/{token}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<ZadaciCamunda>> getZadaci(@PathVariable String token) {
		if(token.isEmpty() || token==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		Korisnik k = korisnikService.findByKorisnickoIme(username);
		ArrayList<ZadaciCamunda> retVal = new ArrayList<ZadaciCamunda>();
		List<Task> zadaci = taskService.createTaskQuery().taskAssignee(username).list();
		for(Task z : zadaci){
			ZadaciCamunda novi = new ZadaciCamunda(z.getName(), z.getId());
			retVal.add(novi);
		}
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
}
