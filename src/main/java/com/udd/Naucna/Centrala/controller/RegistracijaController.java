package com.udd.Naucna.Centrala.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
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
import com.udd.Naucna.Centrala.dto.FormFieldsCamundaDTO;
import com.udd.Naucna.Centrala.dto.KorisnikDTO;
import com.udd.Naucna.Centrala.dto.RegistracijaDTO;
import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Kupljeno;
import com.udd.Naucna.Centrala.model.PretplataNaCasopis;
import com.udd.Naucna.Centrala.model.Proces;
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.repository.ProcessRepository;
import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.token.TokenUtils;

@Controller
@RequestMapping("/registracija")
public class RegistracijaController {
 
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
	
	@Autowired
	private ProcessRepository procesRepository;
	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private IdentityService identityService;

	@GetMapping(path = "/get/{id}", produces = "application/json")
    public @ResponseBody FormFieldsCamunda get(@PathVariable String id) {
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();

		Task task = taskService.createTaskQuery().processInstanceId(id).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		ArrayList<FormField> properties = (ArrayList<FormField>) tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsCamunda(task.getId(),properties, id);
    }
	
	@PostMapping(path = "/do", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<String> register(@RequestBody RegistracijaDTO reg) {		
		FormFieldsCamundaDTO ffc = reg.getFormFieldsCamunda();
		KorisnikDTO korisnik = reg.getKorisnikDTO();
		
		Task task = taskService.createTaskQuery().taskId(ffc.getTaskId()).singleResult();
		String processInstanceId = task.getProcessInstanceId();
	//	String user = (String) runtimeService.getVariable(processInstanceId, "username");
	//	taskService.claim(ffc.getTaskId(), user);
		Map<String, Object> retVal = new HashMap<>();
		retVal.put("ar_ime", ffc.findImeValue());
		retVal.put("ar_prezime", ffc.findPrezimeValue());
		retVal.put("ar_mail", ffc.findEmailValue());
		retVal.put("ar_geografskaSirina", ffc.findSirinaValue());
		retVal.put("ar_geografskaDuzina", ffc.findDuzinaValue());
		retVal.put("ps_korisnickoIme", ffc.findDuzinaValue());
		retVal.put("ps_lozinka", korisnik.getLozinka());
		taskService.complete(ffc.getTaskId(), retVal);
		Proces proces = new Proces(null, korisnik.getIme(), null, null, null, null, processInstanceId, ffc.getTaskId(),null);
		
		return new ResponseEntity("done", HttpStatus.OK);	
	}
}
