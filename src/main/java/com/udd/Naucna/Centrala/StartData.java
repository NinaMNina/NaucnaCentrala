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
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Kupljeno;
import com.udd.Naucna.Centrala.model.NaucnaOblast;
import com.udd.Naucna.Centrala.model.PretplataNaCasopis;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Recenzent;
import com.udd.Naucna.Centrala.model.Urednik;
import com.udd.Naucna.Centrala.model.UrednikNO;
import com.udd.Naucna.Centrala.model.enums.StatusRada;
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.repository.CasopisRepository;
import com.udd.Naucna.Centrala.repository.IzdanjeRepository;
import com.udd.Naucna.Centrala.repository.NaucnaOblastRepository;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.repository.RecenzentRepository;
import com.udd.Naucna.Centrala.repository.UrednikNORepository;
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
	@Autowired
    private NaucnaOblastRepository naucnaOblastRepository;
	@Autowired
    private RadRepository radRepository;
	@Autowired
    private RecenzentRepository recenzentRepository;
	@Autowired
    private UrednikNORepository urednikNORepository;
	
	@PostConstruct
    private void init(){
//AUTORI
		Autor autor0 = new Autor(null, "nina", "nina", "Nina", "Miladinovic", "n@n.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<>(),new ArrayList<>());
		autor0 = autorRepository.save(autor0);
		Autor autorrad1MU = new Autor(null, "radovan", "radovan", "Radovan", "Turović", "ispravi!!!radovan.turovic@uns.ac.rs", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<>(),new ArrayList<>());
		autorrad1MU = autorRepository.save(autorrad1MU);		
		Autor autorrad2MU = new Autor(null, "aleksandara", "aleksandra", "Aleksandra", "Mitrović", "ispravi!!!aleksandramitrovic@uns.ac.rs", new Point(new Double(43.84864), new Double(18.35644)), new ArrayList<>(),new ArrayList<>());
		autorrad2MU = autorRepository.save(autorrad2MU);
		Autor autorrad3MU = new Autor(null, "nemanja", "nemanja", "Nemanja", "Miladinović", "ispravi!!!nemanja.miladinovic@live.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<>(),new ArrayList<>());
		autorrad3MU = autorRepository.save(autorrad3MU);
		Autor autorrad1NG = new Autor(null, "lazar", "lazar", "Lazar", "Milin", "ispravi!!!lazar.milin@live.com", new Point(new Double(44.787197), new Double(20.457273)), new ArrayList<>(),new ArrayList<>());
		autorrad1NG = autorRepository.save(autorrad1NG);
		Autor autorrad2NG = new Autor(null, "dragutin", "dragutin", "Dragutin", "Mataruga", "ispravi!!!dragutin.mataruga@live.com", new Point(new Double(44.787197), new Double(20.457273)), new ArrayList<>(),new ArrayList<>());
		autorrad2NG = autorRepository.save(autorrad2NG);
		Autor autorrad3NG = new Autor(null, "tijana", "tijana", "Tijana", "Ivanović", "ispravi!!!tijana.ivanovic@live.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<>(),new ArrayList<>());
		autorrad3NG = autorRepository.save(autorrad3NG);
//CASOPISI
		Casopis casNG = new Casopis(null, "Nacionalna Geografija", true, "12345678", new ArrayList<NaucnaOblast>(), null, new ArrayList<UrednikNO>(),
				new ArrayList<Recenzent>(), new ArrayList<Izdanje>());
		casNG = casopisRepository.save(casNG);
		Casopis casMU = new Casopis(null, "Mašinsko Učenje 101", false, "87654321", new ArrayList<NaucnaOblast>(), null, new ArrayList<UrednikNO>(),
				new ArrayList<Recenzent>(), new ArrayList<Izdanje>());
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
		
		Izdanje izd1NG = new Izdanje(null, casNG, date4, date5, new ArrayList<Rad>());	
		Izdanje izd2NG = new Izdanje(null, casNG, date5, date6, new ArrayList<Rad>());
		izd1NG = izdanjeRepository.save(izd1NG);
		izd2NG = izdanjeRepository.save(izd2NG);
		ArrayList<Izdanje> izdanjaNG = new ArrayList<Izdanje>();
		izdanjaNG.add(izd1NG);
		izdanjaNG.add(izd2NG);
		
		
		Izdanje izd1MU = new Izdanje(null, casMU, date4, date5, new ArrayList<Rad>());	
		Izdanje izd2MU = new Izdanje(null, casMU, date5, date6, new ArrayList<Rad>());
		izd1MU = izdanjeRepository.save(izd1MU);
		izd2MU = izdanjeRepository.save(izd2MU);
		ArrayList<Izdanje> izdanjaMU = new ArrayList<Izdanje>();
		izdanjaMU.add(izd1MU);
		izdanjaMU.add(izd2MU);	

//RECENZENTI
		ArrayList<Casopis> cas1 = new ArrayList<Casopis>();
		cas1.add(casNG);
		ArrayList<Casopis> cas2 = new ArrayList<Casopis>();
		cas2.add(casMU);
		ArrayList<Casopis> casOba = new ArrayList<Casopis>();
		casOba.add(casNG);
		casOba.add(casMU);
		Recenzent rec1 = new Recenzent(null, "rec1", "rec1", "Pera", "Perić", "ispravi!!!pera.p@live.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), cas1, "mr", new ArrayList<NaucnaOblast>());
		rec1 = recenzentRepository.save(rec1);
		Recenzent rec2 = new Recenzent(null, "rec2", "rec2", "Mika", "Mikić", "ispravi!!!mika.m@live.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), cas2, "ms", new ArrayList<NaucnaOblast>());
		rec2 = recenzentRepository.save(rec2);			
		Recenzent rec3 = new Recenzent(null, "rec3", "rec3", "Jose", "Muchaco", "ispravi!!!ho.mu@live.com", new Point(new Double(40.4165), new Double(-3.70256)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casOba, "dr", new ArrayList<NaucnaOblast>());
		rec3 = recenzentRepository.save(rec3);			
		Recenzent rec4 = new Recenzent(null, "rec4", "rec4", "Koja", "Kojić", "ispravi!!!koja.kojic@live.com", new Point(new Double(46.05108), new Double(14.50513)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casOba, "dr", new ArrayList<NaucnaOblast>());
		rec4 = recenzentRepository.save(rec4);			
		Recenzent rec5 = new Recenzent(null, "rec5", "rec5", "Mujo", "Mujić", "ispravi!!!m.mujic@live.com", new Point(new Double(44.53842), new Double(18.66709)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casOba, "ms", new ArrayList<NaucnaOblast>());
		rec5 = recenzentRepository.save(rec5);			
		Recenzent rec6 = new Recenzent(null, "rec6", "rec6", "Ištvan", "Sabo", "ispravi!!!isabo@live.com", new Point(new Double(47.49801), new Double(19.03991)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), cas1, "dr", new ArrayList<NaucnaOblast>());
		rec6 = recenzentRepository.save(rec6);	
//UREDNICI
		Urednik urednikNG = new Urednik(null, "a", "a", "Mika", "Mikić", "ispravi!!!mika@gmail.com", new Point(45.25167, 19.83694), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casNG, "dr");
		urednikNG = urednikRepository.save(urednikNG);
		casNG.setUrednik(urednikNG);
	
		Urednik urednikMU = new Urednik(null, "urednikMU", "urednikMU", "Pera", "peric", "ispravi!!!pera@gmail.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), casMU, "dr");
		urednikMU = urednikRepository.save(urednikMU);
		casMU.setUrednik(urednikMU);
		casMU = casopisRepository.save(casMU);
//NAUCNA OBLAST
		NaucnaOblast naucnaOblast0 = new NaucnaOblast(null, "Inženjerstvo i Tehnologije", 2, "Ostalo", 11, "");
		naucnaOblast0 = naucnaOblastRepository.save(naucnaOblast0);
		NaucnaOblast naucnaOblast1 = new NaucnaOblast(null, "Prirodne Nauke", 1, "Biologija", 6, "");
		naucnaOblast1 = naucnaOblastRepository.save(naucnaOblast1);
		NaucnaOblast naucnaOblast2 = new NaucnaOblast(null, "Medicina i Zdravlje", 3, "Ostalo", 5, "");
		naucnaOblast2 = naucnaOblastRepository.save(naucnaOblast2);
		NaucnaOblast naucnaOblast3 = new NaucnaOblast(null, "Prirodne Nauke", 1, "Kompjuteri i Informacione Nauke", 2, "");
		naucnaOblast3 = naucnaOblastRepository.save(naucnaOblast3);
		
		ArrayList<NaucnaOblast> nOblastNG = new ArrayList<NaucnaOblast>();
		nOblastNG.add(naucnaOblast1);
		nOblastNG.add(naucnaOblast2);
		casNG.setNaucneOblasti(nOblastNG);
		casNG = casopisRepository.save(casNG);
		
		ArrayList<NaucnaOblast> nOblastMU = new ArrayList<NaucnaOblast>();
		nOblastMU.add(naucnaOblast0);
		nOblastMU.add(naucnaOblast3);
		casMU.setNaucneOblasti(nOblastMU);
		casMU = casopisRepository.save(casMU);
//UREDNIK_NO
		
		UrednikNO urednikNONG = new UrednikNO(null, "urednik1", "urednik1", "Bojan", "Bojanić", "ispravi!!!bbojanic@live.com", new Point(new Double(45.25167), new Double(19.83694)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), new ArrayList<Casopis>(), "dr",  new ArrayList<NaucnaOblast>(), casNG, naucnaOblast1);
		urednikNONG = urednikNORepository.save(urednikNONG);
		UrednikNO urednik1NONG = new UrednikNO(null, "urednik2", "urednik2", "Mirko", "Mirkić", "ispravi!!!mmirko@live.com", new Point(new Double(43.32472), new Double(21.90333)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), new ArrayList<Casopis>(), "dr",  new ArrayList<NaucnaOblast>(), casNG, naucnaOblast2);
		urednik1NONG = urednikNORepository.save(urednik1NONG);
		UrednikNO urednikNOMU = new UrednikNO(null, "urednik3", "urednik3", "Marko", "Marković", "ispravi!!!mmarko@live.com", new Point(new Double(44.786568), new Double(20.448922)), new ArrayList<Kupljeno>(), new ArrayList<PretplataNaCasopis>(), new ArrayList<Casopis>(), "prof dr",  new ArrayList<NaucnaOblast>(), casMU, naucnaOblast0);
		urednikNOMU = urednikNORepository.save(urednikNOMU);		
//RADOVI
		ArrayList<Korisnik> recenzenti1NG = new ArrayList<Korisnik>();
		recenzenti1NG.add(rec3);
		recenzenti1NG.add(rec4);
		ArrayList<Korisnik> recenzenti2NG = new ArrayList<Korisnik>();
		recenzenti2NG.add(rec3);
		recenzenti2NG.add(rec2);
		ArrayList<Korisnik> recenzenti3NG = new ArrayList<Korisnik>();
		recenzenti3NG.add(rec4);
		recenzenti3NG.add(rec2);
		
		ArrayList<Korisnik> recenzenti1MU = new ArrayList<Korisnik>();
		recenzenti1MU.add(rec2);
		recenzenti1MU.add(rec3);
		ArrayList<Korisnik> recenzenti2MU = new ArrayList<Korisnik>();
		recenzenti2MU.add(rec4);
		recenzenti2MU.add(rec5);
		ArrayList<Korisnik> recenzenti3MU = new ArrayList<Korisnik>();
		recenzenti3MU.add(rec4);
		recenzenti3MU.add(rec2);
		
		Rad radMU1 = new Rad(null, "Оцена популарности станице према објектима у околини", "Горана Гојић, gorana.gojic@uns.ac.rs; Ангелина Вујановић, avujanovic@uns.ac.rs", autorrad1MU, "рударење подaтака, стабло одлучивања, корелација, статистика, Capital Bikeshare, станице бицикaла, промет, модел систем", "nema", "", naucnaOblast0, StatusRada.PRIHVACEN, recenzenti1NG);
		radMU1 = radRepository.save(radMU1);//rad1
		Rad radMU2 = new Rad(null, "Predikcija cene nekretnine na osnovu teksta oglasa, slika i geografske lokacije nekretnine", "Mladen Vidović, mladenvidovic@uns.ac.rs; Ivan Radosavljević, ivanradosavljevic@uns.ac.rs", autorrad2MU, "stanovi, cene, predikcija, slike, nekretnine, regresija", "nema", "", naucnaOblast0, StatusRada.PRIHVACEN, recenzenti2NG);
		radMU2 = radRepository.save(radMU2);//rad2
		Rad radMU3 = new Rad(null, "Одређивање степена конзумације алкохола код средњошколаца на основу социјалних фактора", "Милош Марић, milososig@gmail.com; Дражен Ђанић, djanic.home@gmail.com", autorrad3MU, "конзумација алкохола, млади, фактори, предвиђање, Support vector machines, Наивни Бајес, рударење податак ", "nema", "", naucnaOblast0, StatusRada.PRIJAVLJEN, new ArrayList<Korisnik>());
		radMU3 = radRepository.save(radMU3);//rad3
		Rad radMU4 = new Rad(null, "Uticaj Microsoft-a na razvoj kompjutera i kompjuterskog softvera", "Filip Jerenić", autorrad3MU, "", "nema", "Microsoft, uticaj, kompjuteri, razvoj", naucnaOblast3, StatusRada.PRIHVACEN, recenzenti3NG);
		radMU4 = radRepository.save(radMU4);//rad8
		
		ArrayList<Rad> radoviMU = new ArrayList<Rad>();
		radoviMU.add(radMU1);
		radoviMU.add(radMU2);
		radoviMU.add(radMU3);
		radoviMU.add(radMU4);
		izd2MU.setRadovi(radoviMU);
		izd2MU = izdanjeRepository.save(izd2MU);
		
		Rad radNG1 = new Rad(null, "Izolacija bakterija rezistentnih na metale iz zemljišta", "", autorrad1NG, "zagađenje zemljišta, teški metali, bioremedijacija, izolacija bakterija", "nema", "", naucnaOblast1, StatusRada.PRIHVACEN, recenzenti1MU);
		radNG1 = radRepository.save(radNG1);//rad6
		Rad radNG2 = new Rad(null, "Anatomsko-fiziološke osnove reprodukcije domaćih životinja", "", autorrad1NG, "reprodukcija, razvoj, polne ćelije, ciklus, sisar, živina, stoka", "nema", "", naucnaOblast2, StatusRada.PRIJAVLJEN, new ArrayList<Korisnik>());
		radNG2 = radRepository.save(radNG2);//rad4
		Rad radNG3 = new Rad(null, "Očuvanje genetičkih resursa autohtonih rasa domaćih životinja u Srbiji", "Darko Drobnjak, ddrobnjak@edu.rs; Milivoje Urošević, m.urosevic@edu.rs", autorrad2NG, "životinjski resursi, autohtone rase, očuvanje, stočarska proizvodnja", "nema", "", naucnaOblast2, StatusRada.PRIJAVLJEN, new ArrayList<Korisnik>());
		radNG3 = radRepository.save(radNG3);//rad5
		Rad radNG4 = new Rad(null, "Upravljanje organskim otpadom beogradskih pijaca", "", autorrad3NG, "organski otpad, bio otpad, gradske pijace, anaerobna digestija, kompostiranje, prirodno đubriv", "nema", "", naucnaOblast1, StatusRada.PRIHVACEN, recenzenti3MU);
		radNG4 = radRepository.save(radNG4);//rad7
		
		ArrayList<Rad> radoviNG = new ArrayList<Rad>();
		radoviNG.add(radNG1);
		radoviNG.add(radNG2);
		radoviNG.add(radNG3);
		izd2NG.setRadovi(radoviNG);
		izd2NG = izdanjeRepository.save(izd2NG);
		
		
	}
}
