package com.udd.Naucna.Centrala.services;

import java.util.ArrayList;

import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Recenzent;

public interface RecenzentService {

	ArrayList<RecenzentDTO> getRecenzenti(ZadaciDTO zad);

	ArrayList<RecenzentDTO> getStrucniRecenzenti(ZadaciDTO zad);

	ArrayList<RecenzentDTO> getUdaljeniRecenzenti(ZadaciDTO zad);
	

}
