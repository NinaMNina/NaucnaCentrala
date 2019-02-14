package com.udd.Naucna.Centrala.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.udd.Naucna.Centrala.dto.RadDTO;
import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Izdanje;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.repository.CasopisRepository;
import com.udd.Naucna.Centrala.repository.IzdanjeRepository;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.repository.RecenzentRepository;
import com.udd.Naucna.Centrala.repository.elasticSearch.ESRecenzentRepository;
import com.udd.Naucna.Centrala.repository.elasticSearch.ElasticSearchRepository;
import com.udd.Naucna.Centrala.services.ElasticSearchService;
import com.udd.Naucna.Centrala.services.RadService;

@Service
public class RadServiceImpl implements RadService {

	/*@Autowired
	private ElasticSearchRepository elasticSearchRepository;
	@Autowired
	private ESRecenzentRepository esRecenzentRepository;
	@Autowired
	private ElasticSearchService elasticSearchService;*/
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Boolean saveMultipartFile(MultipartFile file, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public RadDTO getRadDTO(Long rad) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean dodajRecenzente(Long id, ArrayList<Long> odabrani) {
		// TODO Auto-generated method stub
		return null;
	}
	
/*	@Override
	public boolean exists(Long id) {
		if(elasticSearchRepository.findById(id)!=null)
			return true;
		return false;
	}

	@Override
	public Boolean saveMultipartFile(MultipartFile file, Long id) {
		Optional<Rad> radTry = radRepository.findById(id);
		Rad rad = null;
		if(!radTry.isPresent()){
			return false;
		}
		rad = radTry.get();
		final Path rootLocation = Paths.get("C:/Users/nina.miladinovic/git/NaucnaCentrala/src/main/resources/static/assets/pdf/");
		String fileName = null;
		try {
			fileName = file.getOriginalFilename();
			if(!(fileName.endsWith(".PDF")|| fileName.endsWith(".pdf"))) {
				return false;
			}
			Path filePath = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
            	fileName = fileName.substring(0, fileName.length() - 4);
            		fileName = fileName + "_"  + System.currentTimeMillis() + ".pdf";
            }
            Files.copy(file.getInputStream(), rootLocation.resolve(fileName));
            rad.setLokacijaRada("assets/pdf/"+fileName);
            radRepository.save(rad);
            HashMap<String, String> podaci = getPodaciORadu(rad);
            System.out.println(filePath.toString());
            RadDTO newRad = new RadDTO(rad.getId(), podaci.get("naslov"), rad.getNaslov(), podaci.get("autori"), "", rad.getKljucniPojmovi(), "", podaci.get("naucnaOblast"),filePath.toString(), getIfCasopisFree(rad));
            RadDTO ret = elasticSearchService.uploadRad(newRad);
            if(ret==null)
            	return false;
        } catch (Exception e) {
        	throw new RuntimeException();
        }
		return true;
	}

	private Boolean getIfCasopisFree(Rad rad) {
		ArrayList<Casopis> c = (ArrayList<Casopis>) casopisRepository.findAll();
		for(Casopis c0 : c){
			for(Izdanje i0 : izdanjeRepository.findByIzCasopisaId(c0.getId())){
				for(Rad r0 : i0.getRadovi())
					if(r0.getId()==rad.getId())
						return c0.isOpenAccess();
			}
		}
		return false;
	}

	private HashMap<String, String> getPodaciORadu(Rad rad) {
		ArrayList<Casopis> c = (ArrayList<Casopis>) casopisRepository.findAll();
		HashMap<String, String> retVal = new HashMap<String, String>();
		for(Casopis c0 : c){
			for(Izdanje i0 : izdanjeRepository.findByIzCasopisaId(c0.getId())){
				for(Rad r0 : i0.getRadovi())
					if(r0.getId()==rad.getId())
						retVal.put("naslov", c0.getNaziv());
			}
		}
		retVal.put("naucnaOblast", rad.getNaucnaOblast().getNazivOblasti()+" - "+rad.getNaucnaOblast().getNazivPodOblasti());
		retVal.put("autori", getAutori(rad));
		return retVal;
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
					Optional<RecenzentDTO> recDTOtry = esRecenzentRepository.findById(rec.getId());
					if(recDTOtry.isPresent()){
						RecenzentDTO recDTO = recDTOtry.get();
						recDTO.setTekstovi(recDTO.getTekstovi()+getProbniTekst(r.getLokacijaProbnogRada()));
						esRecenzentRepository.save(recDTO);
					}
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

	private String getProbniTekst(String lokacijaProbnogRada) {
		String retVal = "";
		File pdf = new File(lokacijaProbnogRada);
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ParseContext pcontext = new ParseContext();
		PDFParser pdfparser = new PDFParser(); 
		try {
			FileInputStream inputstream = new FileInputStream(pdf);
			try {
				pdfparser.parse(inputstream, handler, metadata,pcontext);
			//	System.out.println("Contents of the PDF :" + handler.toString());
				retVal = handler.toString();
			} catch (IOException | SAXException | TikaException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return retVal;
	}
*/
}
