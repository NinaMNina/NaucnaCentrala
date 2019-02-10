package com.udd.Naucna.Centrala.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udd.Naucna.Centrala.model.Izdanje;

public interface IzdanjeRepository extends JpaRepository<Izdanje, Long>{

	ArrayList<Izdanje> findByIzCasopisaId(Long id);

}
