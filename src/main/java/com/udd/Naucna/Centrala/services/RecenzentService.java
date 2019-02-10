package com.udd.Naucna.Centrala.services;

import java.util.ArrayList;

import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Recenzent;

public interface RecenzentService {

	ArrayList<Recenzent> getRecenzenti(ZadaciDTO zad);
	

}
