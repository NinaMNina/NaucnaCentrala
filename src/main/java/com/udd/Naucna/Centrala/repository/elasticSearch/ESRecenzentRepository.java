package com.udd.Naucna.Centrala.repository.elasticSearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.udd.Naucna.Centrala.dto.RecenzentDTO;

public interface ESRecenzentRepository extends ElasticsearchRepository<RecenzentDTO, Long>{

}
