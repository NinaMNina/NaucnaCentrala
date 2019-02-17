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
import org.springframework.web.multipart.MultipartFile;

import com.udd.Naucna.Centrala.dto.CasopisDTO;
import com.udd.Naucna.Centrala.dto.FormFieldDTO;
import com.udd.Naucna.Centrala.dto.FormFieldsCamunda;
import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.NaucnaOblast;
import com.udd.Naucna.Centrala.model.Proces;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.UrednikNO;
import com.udd.Naucna.Centrala.model.enums.StatusRada;
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.repository.NaucnaOblastRepository;
import com.udd.Naucna.Centrala.repository.ProcessRepository;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.repository.UrednikNORepository;
import com.udd.Naucna.Centrala.services.CasopisService;
import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.services.RadService;
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
	@Autowired
	private RadService radService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private ProcessRepository procesRepository;
	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private RadRepository radRepository;
	@Autowired
	private NaucnaOblastRepository naucnaOblastRepository;
	@Autowired
	private UrednikNORepository urednikNORepository;
	
	
	
	@GetMapping(path = "/getAll", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<CasopisDTO>> getAll(){		
        return new ResponseEntity(casopisService.getAll(), HttpStatus.OK);
    }

	@PostMapping(path = "/odaberi/{taskId}/{casopisId}/{processInstanceId}/{token}", produces = "application/json")
    public @ResponseBody ResponseEntity<Korisnik> odaberiCasopisProcess(@PathVariable String token, @PathVariable String taskId, @PathVariable Long casopisId, @PathVariable String  processInstanceId){	
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
		Proces proces = procesRepository.findByAutor(username);
		if(proces==null)
			proces = new Proces();
		if(taskId.equals("nemaga")){
			ProcessInstance pi = runtimeService.startProcessInstanceByKey("upravljanje_poslovnim_procesima");
			Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
			taskId = task.getId();
			processInstanceId = pi.getId();
		}
		proces.setProcessInstanceId(processInstanceId);
		proces.setTaskId(taskId);
		proces.setAutor(username);	
		proces.setUrednik(c.getUrednik().getKorisnickoIme());	
		proces.setCasopisId(c.getId());
		proces = procesRepository.save(proces);
		
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
		Proces proces = procesRepository.findByAutor(username);
		if(proces==null)
			proces = new Proces();
		if(taskId.equals("nemaga")){
			ProcessInstance pi = runtimeService.startProcessInstanceByKey("upravljanje_poslovnim_procesima");
			Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
			taskId = task.getId();
			proces.setProcessInstanceId(pi.getId());
		}
		proces.setTaskId(taskId);
		proces.setAutor(username);	
		proces.setUrednik(c.getUrednik().getKorisnickoIme());	
		proces.setCasopisId(c.getId());
		proces = procesRepository.save(proces);
		
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
        return new ResponseEntity(new FormFieldsCamunda(taskId,properties, ""),HttpStatus.OK);
    }
	@PostMapping(path = "/proba/upload/{token}", produces = "text/html")
    public @ResponseBody ResponseEntity<String> uploadPDFa(@PathVariable String token, @RequestBody MultipartFile file) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		String retVal = radService.saveProbaMultipartFile(file);
		if(retVal==""){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);			
		}
		return new ResponseEntity(retVal, HttpStatus.OK);	
    }
	@GetMapping(path = "/naucneOblasti/{token}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<String>> getNaucneOblasti(@PathVariable String token){		
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		if(!(korisnikService.findByKorisnickoIme(username) instanceof Autor)){
			return new ResponseEntity(new ArrayList<String>(),HttpStatus.OK);			
		}
		Proces p = procesRepository.findByAutor(username);
		if(p==null)
			return new ResponseEntity(HttpStatus.BAD_REQUEST);			
		Long casopisId = p.getCasopisId();
		ArrayList<String> retVal = casopisService.stringNO(casopisId);
		return new ResponseEntity(retVal,HttpStatus.OK);
    }
	@PostMapping(path = "/submitPodaci/{taskId}/{token}", produces = "application/json", consumes="application/json")
    public @ResponseBody ResponseEntity<Boolean> submitPodaci(@PathVariable String token, @RequestBody ArrayList<FormFieldDTO> podaci,@PathVariable String taskId) {
		String username = tokenUtils.getUsernameFromToken(token);
		if(username==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Proces p = procesRepository.findByAutor(username);
		if(p==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);			
		}
		HashMap<String, Object> retVal = new HashMap<>();
		for(FormFieldDTO ff : podaci){
			if(!(ff.getKey().equals("up_odgovorniUrednikNO"))){
				retVal.put(ff.getKey(), ff.getValue());
			}
			if(ff.getKey().equals("up_oblast")){
				String sssss = getUrednikNO(ff.getValue(), p.getCasopisId());
				retVal.put("up_odgovorniUrednikNO", sssss);
			}
		}
		p.setUrednikNO(retVal.get("up_odgovorniUrednikNO").toString());
		Rad rad = new Rad(null, retVal.get("up_naziv").toString(),
				retVal.get("up_koautori").toString(), 
				autorRepository.findByKorisnickoIme(username), 
				retVal.get("up_kljucneReci").toString(), 
				retVal.get("up_pdf").toString(), 
				"", 
				getNaucneOblastiFromString(retVal.get("up_oblast").toString()),
				StatusRada.PRIJAVLJEN, 
				new ArrayList<>());
		rad = radRepository.save(rad);
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);	
    }

	private NaucnaOblast getNaucneOblastiFromString(String object) {
		ArrayList<NaucnaOblast> oblasti = (ArrayList<NaucnaOblast>) naucnaOblastRepository.findAll();
		for(NaucnaOblast no : oblasti){
			if(object.contains(no.getNazivOblasti()) && object.contains(no.getNazivPodOblasti()))
				return no;
		}
		return null;
	}

	private String getUrednikNO(String key, Long casopisId) {
		Casopis c = casopisService.findOne(casopisId);
		if(c==null)
			return "";
		NaucnaOblast noDef = null;
		for(NaucnaOblast no : c.getNaucneOblasti()){
			if(key.contains(no.getNazivOblasti()) &&  key.contains(no.getNazivPodOblasti()))
				noDef = no;
		}
		if(noDef==null)
			return c.getUrednik().getKorisnickoIme();
		List<UrednikNO> nos = urednikNORepository.findAll();
		for(UrednikNO n0 : nos){
			if(n0.getAngazovanKaoUradnikZaCasopis().getId()==c.getId() && noDef.getId()==n0.getOdgovoranZaNaucnuOblast().getId())
				return n0.getKorisnickoIme();
		}
		return c.getUrednik().getKorisnickoIme();
	}
}
