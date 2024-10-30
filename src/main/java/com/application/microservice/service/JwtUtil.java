package com.application.microservice.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Service
public class JwtUtil {
	
	private static final String SECRET = "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg="; // Base64 encoded secret key

	private final Key key;
	
	public JwtUtil() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }


	
	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public void validateToken(String token) {
		Jwts.parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token);
    }

}
