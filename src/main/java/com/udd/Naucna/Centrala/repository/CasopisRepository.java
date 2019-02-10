package com.udd.Naucna.Centrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udd.Naucna.Centrala.model.Casopis;

public interface CasopisRepository extends JpaRepository<Casopis, Long>{

	Casopis findByUrednikId(Long id);

}
