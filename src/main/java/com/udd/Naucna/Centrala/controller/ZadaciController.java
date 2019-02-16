package com.udd.Naucna.Centrala.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udd.Naucna.Centrala.dto.PregledRadDTO;
import com.udd.Naucna.Centrala.dto.RedirekcijaDTO;
import com.udd.Naucna.Centrala.dto.ZadaciCamunda;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.repository.RadRepository;
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
	private RadRepository radRepository;
	
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
	@GetMapping(path = "/redirekt/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<RedirekcijaDTO> getRedirekcija(@PathVariable String taskId) {
		List<Task> zadaci = taskService.createTaskQuery().list();
		RedirekcijaDTO retVal = new RedirekcijaDTO();
		for(Task t : zadaci){
			if(t.getId().equals(taskId)){
				retVal = new RedirekcijaDTO(t.getId(), naStranicu(t.getName()));
				break;
			}
		}
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	private String naStranicu(String name) {
		switch(name){
			case "pregled naslova, apstrakta i ključnih pojmova": 
				return "pregledPodataka";
			case "pregled pdf-a":
				return "pregledPDFa";
		}
		return "ostani";
	}
	@GetMapping(path = "/pregledPodataka/podaci/{taskId}/{token}", produces = "application/json")
    public @ResponseBody ResponseEntity<PregledRadDTO> getRadZaPregled(@PathVariable String taskId, @PathVariable String token) {
		String username = tokenUtils.getUsernameFromToken(token);
		taskService.claim(taskId, username);
		List<Task> zadaci = taskService.createTaskQuery().list();
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		String naziv = runtimeService.getVariable(processInstanceId, "up_naziv").toString();
		String apstrakt = runtimeService.getVariable(processInstanceId, "up_apstrakt").toString();
		String kljucneReci = runtimeService.getVariable(processInstanceId, "up_kljucneReci").toString();
		System.out.println("naziv"+naziv);
		System.out.println("apstrakt"+apstrakt);
		System.out.println("kljucneReci"+kljucneReci);
		PregledRadDTO retVal = new PregledRadDTO(naziv, apstrakt, kljucneReci);
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	@PostMapping(path = "/pregledPodataka/reseno/{taskId}/{result}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resiPregledPodataka(@PathVariable String taskId, @PathVariable String result) {
		HashMap<String, Object> retVal = new HashMap<>();
		if(result.equals("prihvaceno"))
			retVal.put("isRelevantan", true);
		else
			retVal.put("isRelevantan", false);
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }
}
