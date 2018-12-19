package com.udd.Naucna.Centrala.repository.elasticSearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.udd.Naucna.Centrala.dto.RadDTO;

public interface ElasticSearchRepository extends ElasticsearchRepository<RadDTO, Long>{
	
}
