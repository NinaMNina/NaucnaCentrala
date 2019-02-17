package com.udd.Naucna.Centrala.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udd.Naucna.Centrala.dto.FormFieldDTO;
import com.udd.Naucna.Centrala.dto.PregledRadDTO;
import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.dto.RedirekcijaDTO;
import com.udd.Naucna.Centrala.dto.ZadaciCamunda;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.model.enums.TipPolja;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.repository.RecenzentRepository;
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
	private RecenzentRepository recenzentRepository;
	
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
			case "upload novog pdf-a":
				return "uploadPDFa";
			case "izbor recenzenta i vremenskog roka":
				return "izborRecenzenata";
				
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
	@GetMapping(path = "/pregledPDFa/getPDF/{taskId}")
    public ResponseEntity<Resource> get(@PathVariable String taskId, HttpServletRequest request) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		String file = runtimeService.getVariable(processInstanceId, "up_pdf").toString();
		Resource resource;
		Path filePath = Paths.get(file);
        try {
			resource = new UrlResource(filePath.toUri());
			String contentType = null;
			try {
				contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			} catch (IOException ex) {
				System.out.println("Could not determine file type.");
			}

			// Fallback to the default content type if type could not be determined
			if(contentType == null) {
				contentType = "application/octet-stream";
			}

			try {
				return ResponseEntity.ok()
						.contentType(MediaType.parseMediaType(contentType))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				        .header( HttpHeaders.LOCATION, resource.getURI().toString())
						.body(resource);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
    }

	@PostMapping(path = "/pregledPDFa/reseno/{taskId}/{result}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resiPregledPDFa(@PathVariable String taskId, @PathVariable String result, @RequestBody FormFieldDTO komentar) {
		HashMap<String, Object> retVal = new HashMap<>();
		if(result.equals("dobro")){
			retVal.put("isDobroFormatiran", true);
			retVal.put("pp_komentarUradnika", "");
		}
		else{
			retVal.put("isDobroFormatiran", false);
			retVal.put("pp_komentarUradnika", komentar.getValue());
		}
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }

	@GetMapping(path = "/uploadPDFa/komentar/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<FormFieldDTO> komentarUploadPDFa(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		String komentar = runtimeService.getVariable(processInstanceId, "pp_komentarUradnika").toString();
		FormFieldDTO retVal = new FormFieldDTO("pp_komentarUradnika", komentar, TipPolja.STRING);
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	@PostMapping(path = "/uploadPDFa/reseno/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resiUploadPDFa(@PathVariable String taskId, @RequestBody FormFieldDTO novaLokacija) {
		HashMap<String, Object> retVal = new HashMap<>();
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		String naslov = runtimeService.getVariable(processInstanceId, "up_naziv").toString();
		Rad r = radRepository.findByNaslov(naslov);
		r.setLokacijaProbnogRada(novaLokacija.getValue());
		radRepository.save(r);
		retVal.put(novaLokacija.getKey(), novaLokacija.getValue());
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }

	@GetMapping(path = "/izborRecenzenta/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<RecenzentDTO>> izborRecenzentaForma(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		Object rec = runtimeService.getVariable(processInstanceId, "odabrani_recenzenti");
		ArrayList<RecenzentDTO> retVal = (ArrayList<RecenzentDTO>) rec;
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	@PostMapping(path = "/izborRecenzenta/{taskId}/{rok}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resenoIzborRecenzenta(@PathVariable String taskId, @PathVariable String rok, @RequestBody ArrayList<RecenzentDTO> odabrani) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		HashMap<String, Object> retVal = new HashMap<>();
		Recenzent rec0 = recenzentRepository.findById(odabrani.get(0).getId()).get();
		Recenzent rec1 = recenzentRepository.findById(odabrani.get(1).getId()).get();
		if(rec1==null || rec0==null){
			return new ResponseEntity(false, HttpStatus.BAD_REQUEST);			
		}
		retVal.put("durationRecenzija", rok);
		retVal.put("ir_recenzent1", rec0.getKorisnickoIme());
		retVal.put("ir_recenzent2", rec1.getKorisnickoIme());
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }
}
