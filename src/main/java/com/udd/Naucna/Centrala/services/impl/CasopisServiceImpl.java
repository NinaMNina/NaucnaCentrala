package com.udd.Naucna.Centrala.services.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.CasopisDTO;
import com.udd.Naucna.Centrala.model.Casopis;
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
}
