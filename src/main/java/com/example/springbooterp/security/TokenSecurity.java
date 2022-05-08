package com.example.springbooterp.security;

import com.example.springbooterp.dao.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.xml.crypto.dsig.Transform;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.Instant;
import java.util.Date;
import java.util.Enumeration;
import java.security.KeyStore;
import java.security.KeyStoreException;



@Service
public class TokenSecurity {

    @SuppressWarnings("deprecation")
    public  String generateJwtToken(Employee employee) {
        String token = Jwts.builder().setSubject("adam")
                .setExpiration(new Date(2022, 6, 1))
                .setIssuer("info@wstutorial.com")
                .setSubject(employee.getEmail())
                .claim("groups", new String[] { "user", "admin" })
                // HMAC using SHA-512  and 12345678 base64 encoded
                .signWith(SignatureAlgorithm.HS512, "MTIzNDU2Nzg=").compact();
        System.out.println("Generated JWT Token : " + token);
        return token;
    }

    public String  validateJWT(String jwtTokenInput) {
        String token = jwtTokenInput;
        System.out.println(token);
        return printBody(token);
    }

    private String printBody(String token) {
        Claims body = Jwts.parser().setSigningKey("MTIzNDU2Nzg=").parseClaimsJws(token).getBody();
        System.out.println("Issuer     : " + body.getIssuer());
        System.out.println("Subject    : " + body.getSubject());
        System.out.println("Expiration : " + body.getExpiration());
        return body.getSubject();
    }
}
