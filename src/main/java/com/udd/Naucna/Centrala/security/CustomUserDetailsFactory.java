package com.udd.Naucna.Centrala.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.udd.Naucna.Centrala.model.Korisnik;

public class CustomUserDetailsFactory {
private CustomUserDetailsFactory() {}
	
	public static CustomUserDetails createCustomUserDetails(Korisnik korisnik) {

		return new CustomUserDetails(
				korisnik.getKorisnickoIme(),
				korisnik.getId(),
				mapToGrantedAuthorities(korisnik)
				);
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(Korisnik k) {
   //OVO JAKO NE VALJA     
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(k.toString()));
		
		return authorities;
    }
	
}