package com.udd.Naucna.Centrala.services;

import java.util.ArrayList;

import com.udd.Naucna.Centrala.dto.HighlightedRadDTO;
import com.udd.Naucna.Centrala.dto.ParametriDTO;
import com.udd.Naucna.Centrala.dto.RadDTO;
import com.udd.Naucna.Centrala.dto.RecenzentDTO;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Rad;

public interface ElasticSearchService {

	RadDTO uploadRad(RadDTO rad);

	ArrayList<HighlightedRadDTO> searchObican(String tekst);

	ArrayList<RadDTO> moreLikeThis(Long id);

	ArrayList<HighlightedRadDTO> searchParams(ParametriDTO p);

	ArrayList<RecenzentDTO> findUdaljeniRecenzenti(Rad rad, Casopis casopis);

}
