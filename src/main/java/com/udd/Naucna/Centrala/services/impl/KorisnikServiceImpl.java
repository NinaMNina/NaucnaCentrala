package com.udd.Naucna.Centrala.services.impl;

import java.util.ArrayList;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udd.Naucna.Centrala.dto.FormFieldsCamundaDTO;
import com.udd.Naucna.Centrala.dto.KorisnikDTO;
import com.udd.Naucna.Centrala.dto.ZadaciDTO;
import com.udd.Naucna.Centrala.model.Autor;
import com.udd.Naucna.Centrala.model.Casopis;
import com.udd.Naucna.Centrala.model.Izdanje;
import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.model.Rad;
import com.udd.Naucna.Centrala.model.Urednik;
import com.udd.Naucna.Centrala.model.enums.StatusRada;
import com.udd.Naucna.Centrala.model.enums.ZadatakTip;
import com.udd.Naucna.Centrala.repository.AutorRepository;
import com.udd.Naucna.Centrala.repository.CasopisRepository;
import com.udd.Naucna.Centrala.repository.IzdanjeRepository;
import com.udd.Naucna.Centrala.repository.KorisnikRepository;
import com.udd.Naucna.Centrala.repository.RadRepository;
import com.udd.Naucna.Centrala.services.KorisnikService;
@Service
public class KorisnikServiceImpl implements KorisnikService{
	@Autowired
	private KorisnikRepository korisnikRepository;
	@Autowired
	private AutorRepository autorRepository;
	@Autowired
	private CasopisRepository casopisRepository;
	@Autowired
	private RadRepository radRepository;
	@Autowired
	private IzdanjeRepository izdanjeRepository;
	@Autowired
	private IdentityService identityService;
	
	@Override
	public Korisnik uloguj(KorisnikDTO korisnik) {
		Korisnik k0 = korisnikRepository.findByKorisnickoIme(korisnik.getIme());
		if(korisnik == null) {
	    	return null;
	    }
	    
	    if(!korisnik.getLozinka().equals(korisnik.getLozinka())) {
	    	return null;
	    }
		return k0;
	}


	@Override
	public Korisnik findByKorisnickoIme(String username) {
		return korisnikRepository.findByKorisnickoIme(username);
	}


	@Override
	public ArrayList<ZadaciDTO> getZadaci(Korisnik k) {
		ArrayList<ZadaciDTO> retVal = new ArrayList<ZadaciDTO>();
		if(k==null)
			return null;
		if(k instanceof Autor){
			retVal = getAutorZadaci((Autor)k);
		}
		if(k instanceof Urednik){
			retVal = getUrednikZadaci((Urednik)k);
		}
		return retVal;
	}


	private ArrayList<ZadaciDTO> getUrednikZadaci(Urednik k) {
		Casopis casopis = casopisRepository.findByUrednikId(k.getId());
		ArrayList<Izdanje> izdanja = (ArrayList<Izdanje>) izdanjeRepository.findByIzCasopisaId(casopis.getId());
		ArrayList<ZadaciDTO> retVal = new ArrayList<ZadaciDTO>();
		for(Izdanje i0 : izdanja){
			for(Rad r0 : i0.getRadovi()){
				int size = 0;
				for(Korisnik rec : r0.getRecenzenti())
					size++;
				if(r0.getStatus().equals(StatusRada.PRIJAVLJEN) && size<2){
					ZadaciDTO zad = new ZadaciDTO("Potreban odabir recenzenata za rad sa nazivom \""+r0.getNaslov()+
							"\", koji je prijavio autor "+r0.getOdgovorniAutor().getIme()+" "+r0.getOdgovorniAutor().getPrezime(), r0.getId(), ZadatakTip.DODAJ_RECENZENTA);
					retVal.add(zad);
				}
			}
		}
		return retVal;
	}


	private ArrayList<ZadaciDTO> getAutorZadaci(Autor k) {
		ArrayList<Rad> radovi = radRepository.findByOdgovorniAutorId(k.getId());
		ArrayList<ZadaciDTO> retVal = new ArrayList<ZadaciDTO>();
		for(Rad r0 : radovi){
			if(r0.getStatus().equals(StatusRada.PRIHVACEN) && r0.getLokacijaRada().equals("")){
				ZadaciDTO zad = new ZadaciDTO("Potreban upload rada - \""+r0.getNaslov()+"\"", r0.getId(), ZadatakTip.DODAJ_PUTANJU);
				retVal.add(zad);
			}
		}
		return retVal;
	}
/*
	@Override
	public Korisnik registrujForm(FormFieldsCamunda ffc, KorisnikDTO korisnik) {
		Autor a = new Autor(null, ffc.getFormFields().get(0).getValue().toString(),
				ffc.getFormFields().get(1).getValue().toString(),
				ffc.getFormFields().get(2).getValue().toString(),
				ffc.getFormFields().get(3).getValue().toString(),
				ffc.getFormFields().get(4).getValue().toString(), null, null, null);
		autorRepository.save(a);
		Korisnik k = new Korisnik(korisnik.getIme(), korisnik.getLozinka(), new ArrayList<>(), new ArrayList<>(),
				a, null, null, null);
		return korisnikRepository.save(k);
	}
	*/


	@Override
	public Korisnik registruj(FormFieldsCamundaDTO ffc, KorisnikDTO korisnik) {
		Autor retVal = new Autor(null, korisnik.getIme(), korisnik.getLozinka(), ffc.findImeValue(), ffc.findPrezimeValue(), ffc.findEmailValue(), ffc.findPointValue(), new ArrayList<>(), new ArrayList<>());
		retVal = autorRepository.save(retVal);
		if(retVal!=null){
			User u = identityService.newUser(korisnik.getIme());
			u.setFirstName(ffc.findImeValue());
			u.setLastName(ffc.findPrezimeValue());
			u.setEmail(ffc.findEmailValue());
			u.setPassword(korisnik.getLozinka());
			identityService.saveUser(u);
			identityService.createMembership(korisnik.getIme(), "autor");
			return retVal;
		}
		return null;
	}

}
