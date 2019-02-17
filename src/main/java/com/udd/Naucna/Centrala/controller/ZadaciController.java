package com.udd.Naucna.Centrala.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import com.udd.Naucna.Centrala.dto.IzmeneDTO;
import com.udd.Naucna.Centrala.dto.OceneDTO;
import com.udd.Naucna.Centrala.dto.PregledRadDTO;
import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.dto.RedirekcijaDTO;
import com.udd.Naucna.Centrala.dto.ZadaciCamunda;
import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.model.enums.TipPolja;
import com.udd.Naucna.Centrala.model.enums.ZadatakTip;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.repository.RecenzentRepository;
import com.udd.Naucna.Centrala.services.KorisnikService;
import com.udd.Naucna.Centrala.services.RecenzentService;
import com.udd.Naucna.Centrala.services.impl.FileStorageService;
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
	private FileStorageService fileStorageService;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private RecenzentService recenzentService;
	
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
			case "upload novog pdf-a i podataka":
				return "uploadPDFa";
			case "izbor recenzenta i vremenskog roka":
				return "izborRecenzenata";
			case "Citanje rada, komentari autoru, uredniku i unos procene":
				return "aktivnostRecenzenta";
			case "pregled ocena":
				return "pregledOcena";	
			case "dorada rada i upload novog pdf-a":
				return "doradaRada";
			case "Pregled izmena":
				return "pregledIzmena";
			case "Upload rada":
				return "konacniUpload";
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
        resource = fileStorageService.loadFileAsResource(file);;
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			System.out.println("Could not determine file type.");
		}
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
    public @ResponseBody ResponseEntity<ArrayList<FormFieldDTO>> komentarUploadPDFa(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		String komentar = runtimeService.getVariable(processInstanceId, "pp_komentarUradnika").toString();
		FormFieldDTO kom = new FormFieldDTO("pp_komentarUradnika", komentar, TipPolja.STRING);
		ArrayList<FormFieldDTO> retVal = new ArrayList<FormFieldDTO>();
		retVal.add(kom);
		FormFieldDTO polje1 = new FormFieldDTO("up_naziv", runtimeService.getVariable(processInstanceId, "up_naziv").toString(), TipPolja.STRING);
		FormFieldDTO polje2 = new FormFieldDTO("up_koautori", runtimeService.getVariable(processInstanceId, "up_koautori").toString(), TipPolja.STRING);
		FormFieldDTO polje3 = new FormFieldDTO("up_kljucneReci", runtimeService.getVariable(processInstanceId, "up_kljucneReci").toString(), TipPolja.STRING);
		FormFieldDTO polje4 = new FormFieldDTO("up_apstrakt", runtimeService.getVariable(processInstanceId, "up_apstrakt").toString(), TipPolja.STRING);
		FormFieldDTO polje5 = new FormFieldDTO("up_pdf", runtimeService.getVariable(processInstanceId, "up_pdf").toString(), TipPolja.STRING);
		retVal.add(polje1);
		retVal.add(polje2);
		retVal.add(polje3);
		retVal.add(polje4);
		retVal.add(polje5);
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	@PostMapping(path = "/uploadPDFa/reseno/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resiUploadPDFa(@PathVariable String taskId, @RequestBody ArrayList<FormFieldDTO> noveVrednosti) {
		HashMap<String, Object> retVal = new HashMap<>();
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		String naslov = runtimeService.getVariable(processInstanceId, "up_naziv").toString();
		Rad r = radRepository.findByNaslov(naslov);
		for(FormFieldDTO ff : noveVrednosti){
			retVal.put(ff.getKey(), ff.getValue());
			if(ff.getKey().equals("up_pfd")){
				r.setLokacijaProbnogRada(ff.getValue());
				radRepository.save(r);
			}
		}
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }

	@GetMapping(path = "/izborRecenzenta/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<RecenzentDTO>> izborRecenzentaForma(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		String naslov = runtimeService.getVariable(processInstanceId, "up_naziv").toString();
		Rad rad = radRepository.findByNaslov(naslov);
		ZadaciDTO zad = new ZadaciDTO("", rad.getId(), ZadatakTip.DODAJ_RECENZENTA);
		ArrayList<RecenzentDTO> retVal = recenzentService.getRecenzenti(zad);
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	@PostMapping(path = "/izborRecenzenta/{taskId}/{rok}", consumes="application/json")
    public @ResponseBody ResponseEntity<Boolean> resenoIzborRecenzenta(@PathVariable String taskId, @PathVariable String rok, @RequestBody ArrayList<RecenzentDTO> odabrani) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		HashMap<String, Object> retVal = new HashMap<>();
		List<String> odabraniRec = new ArrayList();
		List<String> prethodni = (List<String>) runtimeService.getVariable(processInstanceId, "listaRecenzenata");
		boolean dodatnaRec = false;
		if(prethodni==null && odabrani.size()<2)
			return new ResponseEntity(false, HttpStatus.OK);	
		else if(prethodni!=null){
			dodatnaRec = true;
			for(String sss : prethodni)
				odabraniRec.add(sss);
		}
		for(RecenzentDTO rrr : odabrani){
			Recenzent rec0 = recenzentRepository.findById(rrr.getId()).get();
			odabraniRec.add(rec0.getKorisnickoIme());
		}
		retVal.put("durationRecenzija", rok);
		retVal.put("listaRecenzenata", odabraniRec);
		taskService.complete(taskId, retVal);		
		if(dodatnaRec){
			for(String p : prethodni){
				List<Task> zadaci = taskService.createTaskQuery().taskAssignee(p).list();
				for(Task t0 : zadaci){
					if(t0.getName().equals("Citanje rada, komentari autoru, uredniku i unos procene"))
						taskService.complete(t0.getId());
				}
			}
			
		}
		return new ResponseEntity(true, HttpStatus.OK);
    }
	@GetMapping(path = "/aktivnostiRecenzenta/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<FormFieldDTO>> aktivnostRecenzentaForma(@PathVariable String taskId) {
		
		TaskFormData tfd = formService.getTaskFormData(taskId);
		ArrayList<FormField> properties = (ArrayList<FormField>) tfd.getFormFields();
		ArrayList<FormFieldDTO> retVal = new ArrayList<FormFieldDTO>();
		for(FormField fp : properties) {
			FormFieldDTO field = new FormFieldDTO(fp.getId(), "", TipPolja.STRING);
			retVal.add(field);
		}
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	@PostMapping(path = "/aktivnostiRecenzenta/{taskId}/{token}", produces = "application/json", consumes = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resenoAtivnostRecenzenta(@PathVariable String taskId, @PathVariable String token, @RequestBody ArrayList<FormFieldDTO> polja) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		String username = tokenUtils.getUsernameFromToken(token);
		
		HashMap<String, Object> retVal = new HashMap<>();
		for(FormFieldDTO fp : polja) {
			retVal.put(fp.getKey()+"_"+username, fp.getValue());
		}
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }
	@GetMapping(path = "/pregledOcena/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<OceneDTO>> pregledOcenaPodaci(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		ArrayList<OceneDTO> retVal = new ArrayList<OceneDTO>();
		List<String> recenzenti = (List<String>) runtimeService.getVariable(processInstanceId, "listaRecenzenata");
		for(String fp : recenzenti) {
			OceneDTO ooo = new OceneDTO(runtimeService.getVariable(processInstanceId, "cr_komentarUredniku"+"_"+fp).toString(), 
					runtimeService.getVariable(processInstanceId, "cr__ocena"+"_"+fp).toString());
			retVal.add(ooo);
		}
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	@PostMapping(path = "/pregledOcena/{taskId}/{ocena}/{rok}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resenjePregledOcena(@PathVariable String taskId, @PathVariable String ocena, @PathVariable String rok) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 		
		HashMap<String, Object> retVal = new HashMap<>();
		if(!rok.equals("P"))
			retVal.put("durationDorada", rok);
		retVal.put("ocena", ocena);
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }
	@GetMapping(path = "/doradaRada/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<ArrayList<OceneDTO>> doradaRadaPodaci(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		ArrayList<OceneDTO> retVal = new ArrayList<OceneDTO>();
		List<String> recenzenti = (List<String>) runtimeService.getVariable(processInstanceId, "listaRecenzenata");
		for(String fp : recenzenti) {
			OceneDTO ooo = new OceneDTO(runtimeService.getVariable(processInstanceId, "cr_komentarAutoru"+"_"+fp).toString(), "");
			retVal.add(ooo);
		}
		return new ResponseEntity(retVal, HttpStatus.OK);
    }
	@PostMapping(path = "/doradaRada/{taskId}", produces = "application/json", consumes = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resenjeDoradaRada(@PathVariable String taskId, @RequestBody FormFieldDTO pdf) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 		
		HashMap<String, Object> retVal = new HashMap<>();
		
		String naslov = runtimeService.getVariable(processInstanceId, "up_naziv").toString();
		Rad r = radRepository.findByNaslov(naslov);
		retVal.put(pdf.getKey(), pdf.getValue());
		if(pdf.getKey().equals("up_pfd")){
			r.setLokacijaProbnogRada(pdf.getValue());
			radRepository.save(r);
		}
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }
	@GetMapping(path = "/pregledIzmena/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<IzmeneDTO> pregledIzmenaPodaci(@PathVariable String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 
		
		ArrayList<OceneDTO> ocene = new ArrayList<OceneDTO>();
		ArrayList<FormFieldDTO> rad = new ArrayList<FormFieldDTO>();
		List<String> recenzenti = (List<String>) runtimeService.getVariable(processInstanceId, "listaRecenzenata");
		for(String fp : recenzenti) {
			OceneDTO ooo = new OceneDTO(runtimeService.getVariable(processInstanceId, "cr_komentarAutoru"+"_"+fp).toString(), "");
			ocene.add(ooo);
		}
		String naslov = runtimeService.getVariable(processInstanceId, "up_naziv").toString();
		Rad r = radRepository.findByNaslov(naslov);
		FormFieldDTO ff0 = new FormFieldDTO("naslov", r.getNaslov(), TipPolja.STRING);
		FormFieldDTO ff1 = new FormFieldDTO("ključni pojmovi", r.getKljucniPojmovi(), TipPolja.STRING);
		FormFieldDTO ff2 = new FormFieldDTO("apstrakt", runtimeService.getVariable(processInstanceId, "up_apstrakt").toString(), TipPolja.STRING);
		rad.add(ff0);
		rad.add(ff1);
		rad.add(ff2);
		IzmeneDTO retVal = new IzmeneDTO(ocene, rad);
		return new ResponseEntity(retVal, HttpStatus.OK);
    }

	@PostMapping(path = "/pregledIzmena/{taskId}/{odluka}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resenjeDoradaRada(@PathVariable String taskId, @PathVariable String odluka) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 		
		HashMap<String, Object> retVal = new HashMap<>();
		if(odluka.equals("zadovoljan"))
			retVal.put("zadovoljan", true);
		else
			retVal.put("zadovoljan", false);			
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }
	@PostMapping(path = "/konacniUpload/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity<Boolean> resenjeKonacniUpload(@PathVariable String taskId, @RequestBody FormFieldDTO pdf) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult(); 
		String processInstanceId = task.getProcessInstanceId(); 		
		HashMap<String, Object> retVal = new HashMap<>();
		
		String naslov = runtimeService.getVariable(processInstanceId, "up_naziv").toString();
		Rad r = radRepository.findByNaslov(naslov);
		retVal.put("konacnaLokacija", pdf.getValue());
		r.setLokacijaRada(pdf.getValue());
		radRepository.save(r);
		
		taskService.complete(taskId, retVal);
		return new ResponseEntity(true, HttpStatus.OK);
    }
}
