package com.udd.Naucna.Centrala.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.repository.elasticSearch.ElasticSearchRepository;
import com.udd.Naucna.Centrala.services.RadService;

@Service
public class RadServiceImpl implements RadService {

	@Autowired
	private ElasticSearchRepository elasticSearchRepository;
	
	@Override
	public boolean exists(Long id) {
		if(elasticSearchRepository.findById(id)!=null)
			return true;
		return false;
	}

}
