package com.udd.Naucna.Centrala.services;

import java.util.ArrayList;

import com.udd.Naucna.Centrala.dto.CasopisDTO;
import com.udd.Naucna.Centrala.model.Casopis;

public interface CasopisService {

	ArrayList<CasopisDTO> getAll();

	Casopis findOne(Long id);
	
}
