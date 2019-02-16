package com.udd.Naucna.Centrala.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Rad;

public interface AutorRepository extends JpaRepository<Autor, Long>{

	Autor findByKorisnickoIme(String korisnickoIme);


}
