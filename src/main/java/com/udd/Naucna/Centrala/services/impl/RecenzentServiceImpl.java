package com.udd.Naucna.Centrala.services.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Izdanje;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.repository.CasopisRepository;
import com.udd.Naucna.Centrala.repository.IzdanjeRepository;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.repository.RecenzentRepository;
import com.udd.Naucna.Centrala.services.RecenzentService;


@Service
public class RecenzentServiceImpl implements RecenzentService {
	@Autowired
	private RecenzentRepository recenzentRepository;
	@Autowired
	private RadRepository radRepository;
	@Autowired
	private IzdanjeRepository izdanjeRepository;
	@Autowired
	private CasopisRepository casopisRepository;
/*	@Autowired
	private ElasticSearchService elasticsearchService;*/
	
	@Override
	public ArrayList<RecenzentDTO> getRecenzenti(ZadaciDTO zad) {
		ArrayList<Recenzent> retVal = new ArrayList<Recenzent>();
		Optional<Rad> radOpt = radRepository.findById(zad.getRad());
		if(!radOpt.isPresent())
			return null;
		Rad rad = radOpt.get();
		Casopis c = getCasopis(rad);
		if(c==null)
			return null;
		ArrayList<Recenzent> rec = recenzentRepository.findAllByAngazovanjeId(c.getId());
		return convertToDTO(rec);
	}
	
	private ArrayList<RecenzentDTO> convertToDTO(ArrayList<Recenzent> rec) {
		ArrayList<RecenzentDTO> retVal = new ArrayList<RecenzentDTO>();
		for(Recenzent r : rec){
		//	RecenzentDTO rDTO = new RecenzentDTO(r.getId(), r.getIme(), r.getPrezime(), r.getEmail(), "", setGeoPointLokacija(r.getLokacija()), "");
			RecenzentDTO rDTO = new RecenzentDTO(r.getId(), r.getIme(), r.getPrezime(), r.getEmail(), "", "");
			retVal.add(rDTO);
		}
		return retVal;
	}
/*
	private GeoPoint setGeoPointLokacija(Point lokacija) {
		return new GeoPoint(lokacija.getX(), lokacija.getY());
	}

*/	private Casopis getCasopis(Rad rad) {
		ArrayList<Casopis> c = (ArrayList<Casopis>) casopisRepository.findAll();
		for(Casopis c0 : c){
			for(Izdanje i0 : izdanjeRepository.findByIzCasopisaId(c0.getId())){
				for(Rad r0 : i0.getRadovi())
					if(r0.getId()==rad.getId())
						return c0;
			}
		}
		return null;
	}
/*
	@Override
	public ArrayList<RecenzentDTO> getStrucniRecenzenti(ZadaciDTO zad) {
		ArrayList<Recenzent> retVal = new ArrayList<Recenzent>();
		Optional<Rad> radOpt = radRepository.findById(zad.getRad());
		if(!radOpt.isPresent())
			return null;
		Rad rad = radOpt.get();
		Casopis c = getCasopis(rad);
		if(c==null)
			return null;
		ArrayList<Recenzent> rec = recenzentRepository.findAllByAngazovanjeId(c.getId());
		ArrayList<Long> ubaceni = new ArrayList<>();
		for(Recenzent rec0 : rec){
			List<NaucnaOblast> newList = (List<NaucnaOblast>) rec0.getPokrivaNaucneOblasti();
			for(NaucnaOblast no : newList){
				if(no.getId()==rad.getNaucnaOblast().getId())
					if(!ubaceni.contains(rec0.getId())){
						retVal.add(rec0);
						ubaceni.add(rec0.getId());						
					}
							
			}
		}
		return convertToDTO(retVal);
	}

	@Override
	public ArrayList<RecenzentDTO> getUdaljeniRecenzenti(ZadaciDTO zad) {
		Optional<Rad> radOpt = radRepository.findById(zad.getRad());
		if(!radOpt.isPresent())
			return null;
		Rad rad = radOpt.get();
		Casopis casopis = getCasopis(rad);
		return elasticsearchService.findUdaljeniRecenzenti(rad, casopis);
	}

	@Override
	public ArrayList<RecenzentDTO> getSlicniRecenzenti(ZadaciDTO zad) {
		Optional<Rad> radOpt = radRepository.findById(zad.getRad());
		if(!radOpt.isPresent())
			return null;
		Rad rad = radOpt.get();
		Casopis casopis = getCasopis(rad);
		return elasticsearchService.findSlicniRecenzenti(rad, casopis);
	}

	@Override
	public ArrayList<RecenzentDTO> getRecenzenti(ZadaciDTO zad) {
		// TODO Auto-generated method stub
		return null;
	}*/
	@Override
	public ArrayList<RecenzentDTO> getStrucniRecenzenti(ZadaciDTO zad) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<RecenzentDTO> getUdaljeniRecenzenti(ZadaciDTO zad) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<RecenzentDTO> getSlicniRecenzenti(ZadaciDTO zad) {
		// TODO Auto-generated method stub
		return null;
	}
}
