package com.udd.Naucna.Centrala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udd.Naucna.Centrala.model.Proces;

public interface ProcessRepository extends JpaRepository<Proces, Long> {

	Proces findByAutor(String username);

}
