package com.udd.Naucna.Centrala.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udd.Naucna.Centrala.dto.CasopisDTO;
import com.udd.Naucna.Centrala.dto.FormFieldsCamunda;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.services.CasopisService;
import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.token.TokenUtils;

@Controller
@RequestMapping("/casopis")
public class CasopisController {
	@Autowired
	private CasopisService casopisService;
	@Autowired
	private TokenUtils tokenUtils;
	@Autowired
	private KorisnikService korisnikService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private RuntimeService runtimeService;
	
	@GetMapping(path = "/getAll", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<CasopisDTO>> getAll(){		
        return new ResponseEntity(casopisService.getAll(), HttpStatus.OK);
    }
	
	@PostMapping(path = "/odaberi/{taskId}/{casopisId}/{token}", produces = "application/json")
    public @ResponseBody ResponseEntity<Korisnik> odaberiCasopis(@PathVariable String token, @PathVariable String taskId, @PathVariable Long casopisId){	
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null || casopisId==null){
			return new ResponseEntity(null, HttpStatus.OK);
		}
		Korisnik k = korisnikService.findByKorisnickoIme(username);
		if(k==null){
			return new ResponseEntity(null, HttpStatus.OK);			
		}
		Casopis c = casopisService.findOne(casopisId);
		if(c==null){
			return new ResponseEntity(null, HttpStatus.OK);				
		}
		if(taskId.equals("nemaga")){
			ProcessInstance pi = runtimeService.startProcessInstanceByKey("upravljanje_poslovnim_procesima");
			Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
			taskId = task.getId();
		}
		taskService.claim(taskId, username);
		Map<String, Object> retVal = new HashMap<>();
		retVal.put("retVal", "USPESNO");
		retVal.put("ps_korisnickoIme", username);
		retVal.put("ps_lozinka", k.getLokacija());
		retVal.put("ps_odabranCasopisId", c.getId());
		retVal.put("isOpenAccess", c.isOpenAccess());
		retVal.put("ps_odgovorniUrednik", c.getUrednik().getKorisnickoIme());
		taskService.complete(taskId, retVal);
		
        return new ResponseEntity(k, HttpStatus.OK);
    }
	@GetMapping(path = "/getForma/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<FormFieldsCamunda> getForma(@PathVariable String taskId){		
		TaskFormData tfd = formService.getTaskFormData(taskId);
		ArrayList<FormField> properties = (ArrayList<FormField>) tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new ResponseEntity(new FormFieldsCamunda(taskId,properties, ""),HttpStatus.OK);
    }
}
