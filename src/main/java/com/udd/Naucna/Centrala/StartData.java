package com.udd.Naucna.Centrala;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Izdanje;
import com.udd.Naucna.Centrala.model.Kupljeno;
import com.udd.Naucna.Centrala.model.NaucnaOblast;
import com.udd.Naucna.Centrala.model.PretplataNaCasopis;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.model.Urednik;
import com.udd.Naucna.Centrala.model.UrednikNO;
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.repository.CasopisRepository;
import com.udd.Naucna.Centrala.repository.IzdanjeRepository;
import com.udd.Naucna.Centrala.repository.KorisnikRepository;
import com.udd.Naucna.Centrala.repository.UrednikRepository;

@Component
public class StartData {
	@Autowired
    private AutorRepository autorRepository;
	@Autowired
    private CasopisRepository casopisRepository;
	@Autowired
    private UrednikRepository urednikRepository;
	@Autowired
    private IzdanjeRepository izdanjeRepository;
	
	@PostConstruct
    private void init(){
//AUTORI
		Autor autor0 = new Autor(null, "nina.m", "nina", "Nina", "Miladinovic", "n@n.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<>(),new ArrayList<>());
		autor0 = autorRepository.save(autor0);
		
//CASOPISI
		Casopis casNG = new Casopis(null, "Nacionalna Geografija", true, "12345678", new ArrayList<NaucnaOblast>(), null, new ArrayList<UrednikNO>(),
				new ArrayList<Recenzent>(), new ArrayList<Izdanje>());
		casNG = casopisRepository.save(casNG);
		Casopis casMU = new Casopis(null, "Mašinsko Učenje 101", false, "87654321", new ArrayList<NaucnaOblast>(), null, new ArrayList<UrednikNO>(),
				new ArrayList<Recenzent>(), new ArrayList<Izdanje>());
		casMU = casopisRepository.save(casMU);
		
//UREDNICI
		Urednik urednikNG = new Urednik(null, "urednikNG", "urednikNG", "Mika", "Mikić", "mika@gmail.com", new Point(45.25167, 19.83694), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casNG, "dr");
		urednikNG = urednikRepository.save(urednikNG);
		casNG.setUrednik(urednikNG);
		
		Urednik urednikMU = new Urednik(null, "urednikMU", "urednikMU", "Pera", "peric", "pera@gmail.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casMU, "dr");
		urednikMU = urednikRepository.save(urednikMU);
		casMU.setUrednik(urednikMU);
		casMU = casopisRepository.save(casMU);
		
//IZDANJA
		String string4 = "January 1, 2018";
		String string5 = "February 1, 2018";
		String string6 = "March 1, 2018";
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		Date date4 = new Date();
		Date date5 = new Date();
		Date date6 = new Date();
		try {
			date4 = format.parse(string4);
			date5 = format.parse(string5);
			date6 = format.parse(string6);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Izdanje izd1NG = new Izdanje(null, date4, date5, new ArrayList<Rad>());	
		Izdanje izd2NG = new Izdanje(null, date5, date6, new ArrayList<Rad>());
		izdanjeRepository.save(izd1NG);
		izdanjeRepository.save(izd2NG);
		ArrayList<Izdanje> izdanjaNG = new ArrayList<Izdanje>();
		izdanjaNG.add(izd1NG);
		izdanjaNG.add(izd2NG);
		casNG.setIzdanja(izdanjaNG);
		casNG = casopisRepository.save(casNG);
		
		
		Izdanje izd1MU = new Izdanje(null, date4, date5, new ArrayList<Rad>());	
		Izdanje izd2MU = new Izdanje(null, date5, date6, new ArrayList<Rad>());
		izdanjeRepository.save(izd1MU);
		izdanjeRepository.save(izd2MU);
		ArrayList<Izdanje> izdanjaMU = new ArrayList<Izdanje>();
		izdanjaNG.add(izd1MU);
		izdanjaNG.add(izd2MU);
		casNG.setIzdanja(izdanjaMU);
		casMU = casopisRepository.save(casMU);
		
//RADOVI
		
		

		
	}
}
