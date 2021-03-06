package com.udd.Naucna.Centrala.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udd.Naucna.Centrala.model.Recenzent;

public interface RecenzentRepository extends JpaRepository<Recenzent, Long>{

	ArrayList<Recenzent> findAllByAngazovanjeId(Long id);

}
