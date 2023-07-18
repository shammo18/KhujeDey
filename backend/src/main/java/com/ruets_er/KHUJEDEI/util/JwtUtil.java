/*
 * Copyright (c) 2022.
 * Document   : JwtUtil.java
 * Created on : 7/9/22, 10:59 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "nahid#@ruet.cse'18";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateMessageToken(String plain)
    {
        String token ="";
        for (int i =0;i<plain.length();i++)
        {
            int charecterAscii = (int)plain.charAt(i);
            char Divisor = (char)(charecterAscii/13 + (int)('A') + 13);
            char modVal = (char)(charecterAscii%13 + (int)('A'));

            token += Divisor;
            token += modVal;
        }
        return  token;
    }

    public String decodeMessageToken(String encodedString)
    {
        String decodedMiddleString =" ";
        for (int i =1;i<encodedString.length();i++)
        {
            int Divisor = (int)encodedString.charAt(i-1)- (int)('A')-13;
            int modVal = (int)encodedString.charAt(i)- (int)('A');
            int charecterAscii =Divisor*13+modVal;
            decodedMiddleString += (char)(charecterAscii);
        }
        String decodedString = "";
        for (int i =0;i<decodedMiddleString.length();i+=2)
        {
            decodedString+=decodedMiddleString.charAt(i+1);
        }
        return  decodedString;
    }
}