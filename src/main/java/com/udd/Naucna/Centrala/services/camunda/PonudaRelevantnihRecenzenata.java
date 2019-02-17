package com.udd.Naucna.Centrala.services.camunda;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.enums.ZadatakTip;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.services.RecenzentService;

@Service
public class PonudaRelevantnihRecenzenata implements JavaDelegate{
	@Autowired
	private RecenzentService recenzentService;
	@Autowired
	private RadRepository radRepository;
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String naslov = execution.getVariable("up_naziv").toString();
		Rad rad = radRepository.findByNaslov(naslov);
		ZadaciDTO zad = new ZadaciDTO("", rad.getId(), ZadatakTip.DODAJ_RECENZENTA);
		ArrayList<RecenzentDTO> retVal = recenzentService.getRecenzenti(zad);
		execution.setVariable("odabrani_recenzenti", retVal);
	}

}