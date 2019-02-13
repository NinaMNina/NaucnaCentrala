package com.udd.Naucna.Centrala.token;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.udd.Naucna.Centrala.model.Korisnik;
import com.udd.Naucna.Centrala.repository.KorisnikRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils 
{

    private String secret = "ProjekatWXS";

    private Long expiration = new Long(2*60*60);

    @Autowired
    private KorisnikRepository korisnikRepository;


    public String getUsernameFromToken(String token) 
    {
        String username;
        try 
        {
            Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } 
        catch (Exception e) 
        {
            username = null;
        }
       
        return username;
    }

    
    private Claims getClaimsFromToken(String token) 
    {
        Claims claims;
        try 
        {
            claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        } 
        catch (Exception e) 
        {
            claims = null;
        }
        return claims;
    }

    
    public Date getExpirationDateFromToken(String token) 
    {
        Date expiration;
        try 
        {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } 
        catch (Exception e) 
        {
            expiration = null;
        }
        return expiration;
    }

    
    public boolean isTokenExpired(String token) 
    {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    
    public boolean validateToken(String token, UserDetails userDetails) 
    {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
        
        
    }

    @Transactional
    public String generateToken(UserDetails userDetails) 
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put("kreirano", new Date(System.currentTimeMillis()));
        claims.put("istice", new Date(System.currentTimeMillis() + expiration * 1000));
        claims.put("uloga", setUloga(userDetails.getAuthorities()));
        
        Korisnik korisnik = korisnikRepository.findByKorisnickoIme(userDetails.getUsername());
        
        if(korisnik==null) {
        	throw new UsernameNotFoundException("Nepostojeci korisnik");
        }
          
        try
        {
            claims.put("id", korisnik.getId());
        }catch(NullPointerException e){
        	System.out.println("Greska kod generisanja tokena");
        }
       
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }


	private String setUloga(Collection<? extends GrantedAuthority> authorities) {
		if(authorities.toString().contains("Autor"))
			return "autor";
		if(authorities.toString().contains("Urednik"))
			return "urednik";
		if(authorities.toString().contains("UrednikNO"))
			return "urednikNO";
		if(authorities.toString().contains("Recenzent"))
			return "recenzent";
		return "undefined";
	}



}