package com.udd.Naucna.Centrala.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udd.Naucna.Centrala.model.Rad;

public interface RadRepository extends JpaRepository<Rad, Long>{

	ArrayList<Rad> findByOdgovorniAutorId(Long id);
}
