package com.udd.Naucna.Centrala.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Izdanje;
import com.udd.Naucna.Centrala.model.NaucnaOblast;
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
	
	@Override
	public ArrayList<Recenzent> getRecenzenti(ZadaciDTO zad) {
		ArrayList<Recenzent> retVal = new ArrayList<Recenzent>();
		Optional<Rad> radOpt = radRepository.findById(zad.getRad());
		if(!radOpt.isPresent())
			return null;
		Rad rad = radOpt.get();
		Casopis c = getCasopis(rad);
		if(c==null)
			return null;
		ArrayList<Recenzent> rec = recenzentRepository.findAllByAngazovanjeId(c.getId());
		for(Recenzent rec0 : rec){
			List<NaucnaOblast> newList = (List<NaucnaOblast>) rec0.getPokrivaNaucneOblasti();
			ArrayList<NaucnaOblast> al = new ArrayList<>(newList);
		//	nooo.addAll(noo);
			for(NaucnaOblast no : newList){
				if(no.getId()==rad.getNaucnaOblast().getId())
					retVal.add(rec0);
			}
		}
		return retVal;
	}
	
	private Casopis getCasopis(Rad rad) {
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

}
