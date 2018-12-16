package com.udd.Naucna.Centrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udd.Naucna.Centrala.model.EntitetPlacanja;

public interface ElasticSearchRepository extends JpaRepository<EntitetPlacanja, Long>{

}
