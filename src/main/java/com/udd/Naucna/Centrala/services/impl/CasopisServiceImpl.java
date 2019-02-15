package com.udd.Naucna.Centrala.services.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.CasopisDTO;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.NaucnaOblast;
import com.udd.Naucna.Centrala.repository.CasopisRepository;
import com.udd.Naucna.Centrala.services.CasopisService;

@Service
public class CasopisServiceImpl implements CasopisService{
	@Autowired
	private CasopisRepository casopisRepository;
	@Override
	public ArrayList<CasopisDTO> getAll() {
		ArrayList<Casopis> casopisi = (ArrayList<Casopis>)casopisRepository.findAll();
		ArrayList<CasopisDTO> retVal = new ArrayList<CasopisDTO>();
		for(Casopis c0 : casopisi){
			CasopisDTO cDTO = c0.convertToDTO();
			retVal.add(cDTO);
		}
		return retVal;
	}
	@Override
	public Casopis findOne(Long id) {		
		return casopisRepository.findById(id).get();
	}
	@Override
	public ArrayList<String> stringNO(Long casopisId) {
		ArrayList<String> retVal = new ArrayList<String>();
		Optional<Casopis> cTry = casopisRepository.findById(casopisId);
		Casopis c = null;
		if(!cTry.isPresent()){
			return null;
		}
		c = cTry.get();
		for(NaucnaOblast no : c.getNaucneOblasti()){
			String str = no.getNazivOblasti() + " - "+no.getNazivPodOblasti();
			retVal.add(str);
		}
		return retVal;
	}
}
