package com.udd.Naucna.Centrala.services;

import java.util.ArrayList;

import com.udd.Naucna.Centrala.dto.ParametriDTO;
import com.udd.Naucna.Centrala.dto.RadDTO;

public interface ElasticSearchService {

	RadDTO uploadRad(RadDTO rad);

	ArrayList<RadDTO> search(ParametriDTO p);

}
