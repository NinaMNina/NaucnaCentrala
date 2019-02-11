package com.udd.Naucna.Centrala.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udd.Naucna.Centrala.dto.RadDTO;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Izdanje;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.repository.CasopisRepository;
import com.udd.Naucna.Centrala.repository.IzdanjeRepository;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.repository.RecenzentRepository;
import com.udd.Naucna.Centrala.repository.elasticSearch.ElasticSearchRepository;
import com.udd.Naucna.Centrala.services.RadService;

@Service
public class RadServiceImpl implements RadService {

	@Autowired
	private ElasticSearchRepository elasticSearchRepository;
	@Autowired
	private RadRepository radRepository;
	@Autowired
	private CasopisRepository casopisRepository;
	@Autowired
	private IzdanjeRepository izdanjeRepository;
	@Autowired
	private RecenzentRepository recenzentRepository;
	
	@Override
	public boolean exists(Long id) {
		if(elasticSearchRepository.findById(id)!=null)
			return true;
		return false;
	}

	@Override
	public String saveMultipartFile(MultipartFile file) {
		final Path rootLocation = Paths.get("src/main/resources/static/assets/pdf/");
		String fileName = null;
		try {
			fileName = file.getOriginalFilename();
			if(!(fileName.endsWith(".PDF")|| fileName.endsWith(".pdf"))) {
				return fileName;
			}
			Path filePath = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
            	fileName = fileName.substring(0, fileName.length() - 4);
            		fileName = fileName + "_"  + System.currentTimeMillis() + ".pdf";
            }
            Files.copy(file.getInputStream(), rootLocation.resolve(fileName));
            System.out.println("fileName: "+fileName);
            System.out.println("rootLocation: "+rootLocation.toString());
        } catch (Exception e) {
        	throw new RuntimeException();
        }
		return "assets/pdf/"+fileName;
	}

	@Override
	public RadDTO getRadDTO(Long rad) {
		Optional<Rad> rTry = radRepository.findById(rad);
		Rad r0;
		if(rTry.isPresent()){
			r0 = rTry.get();
			RadDTO retVal = new RadDTO(r0.getId(), getCasopis(r0), r0.getNaslov(), getAutori(r0), setLokacija(r0.getOdgovorniAutor().getLokacija()), r0.getKljucniPojmovi(), "", r0.getNaucnaOblast().getNazivOblasti()+" - "+r0.getNaucnaOblast().getNazivPodOblasti(), "", true);
			return retVal;
		}
		return null;	
	}

	private String setLokacija(Point lokacija) {
		String lon = Double.toString(lokacija.getX());
		String lat = Double.toString(lokacija.getY());
		return lon+","+lat;
	}

	private String getAutori(Rad r0) {
		if(r0.getKoautoriRada().equals(""))
			return r0.getOdgovorniAutor().getIme()+" "+r0.getOdgovorniAutor().getPrezime()+", "+r0.getOdgovorniAutor().getEmail();
		String retVal = r0.getKoautoriRada()+"; "+r0.getOdgovorniAutor().getIme()+" "+r0.getOdgovorniAutor().getPrezime()+", "+r0.getOdgovorniAutor().getEmail();
		return retVal;
	}
	

	private String getCasopis(Rad rad) {
		ArrayList<Casopis> c = (ArrayList<Casopis>) casopisRepository.findAll();
		for(Casopis c0 : c){
			for(Izdanje i0 : izdanjeRepository.findByIzCasopisaId(c0.getId())){
				for(Rad r0 : i0.getRadovi())
					if(r0.getId()==rad.getId())
						return c0.getNaziv();
			}
		}
		return "";
	}

	@Override
	public Boolean dodajRecenzente(Long id, ArrayList<Long> odabrani) {
		Optional<Rad> rTry = radRepository.findById(id);
		if(rTry.isPresent()){
			Rad r = rTry.get();
			List<Korisnik> lista = r.getRecenzenti();			
			for(Long index : odabrani){
				Optional<Recenzent> recTry = recenzentRepository.findById(index);
				if(recTry.isPresent()){
					Recenzent rec = recTry.get();
					lista.add(rec);
				}
				else{
					return false;
				}
			}
			r.setRecenzenti(lista);
			radRepository.save(r);
			return true;
		}
		return false;
	}

}
