package com.udd.Naucna.Centrala.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.token.TokenUtils;

@Controller
@RequestMapping("/registracija")
public class RegistracijaController {
	//CAMUNDA
/*	@Autowired
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
/*	@GetMapping(path = "/get/{id}", produces = "application/json")
    public @ResponseBody FormFieldsCamunda get(@PathVariable String id) {
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();

		Task task = taskService.createTaskQuery().processInstanceId(id).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		for(FormField fp : properties) {
			System.out.println(fp.getId() + fp.getType());
		}
		
        return new FormFieldsCamunda(task.getId(),properties, id);
    }
	
	@PostMapping(path = "/do", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<String> register(RegistracijaDTO reg) {
		FormFieldsCamunda ffc = reg.getFormFieldsCamunda();
		KorisnikDTO korisnik = reg.getKorisnikDTO();
		Korisnik korisnik0 = korisnikService.registruj(ffc, korisnik);
		if(korisnik0==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);			
		}
		Korisnik k = korisnikService.uloguj(korisnik);
		if(k==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		String token = tokenUtils.generateToken(CustomUserDetailsFactory.createCustomUserDetails(k));
		String id = ffc.getTaskId();
		Task task = taskService.createTaskQuery().taskId(id).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		String user = (String) runtimeService.getVariable(processInstanceId, "username");
		taskService.claim(id, user);
		taskService.complete(id);
		return new ResponseEntity(token, HttpStatus.OK);	
		//ovo nije dobro, jer postoji service task koji resava klasa "com.udd.Naucna.Centrala.services.ProcesiranjePodataka"
    }
*/
}
