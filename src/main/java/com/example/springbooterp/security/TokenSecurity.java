package com.example.springbooterp.security;

import com.example.springbooterp.dao.Employee;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

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

    public String createJWT(Employee employee) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
        Transform TextCodec = null;
        String KeyPassword = "p12-key-password";
        KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
        Enumeration e = store.aliases();
        String alias = (String) e.nextElement();

        PrivateKey key = (PrivateKey) store.getKey(alias, KeyPassword.toCharArray());
        String jws = Jwts.builder()
                .setIssuer("Stormpath")
                .setSubject("msilverman")
                .claim("name", employee.getFirstName())
                .claim("scope", "admins")
                // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                .setIssuedAt(Date.from(Instant.ofEpochSecond(1466796822L)))
                // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                .setExpiration(Date.from(Instant.ofEpochSecond(4622470422L)))
                .signWith(SignatureAlgorithm.ES256,
                        key
                        )
                //may posses problems
                .compact();
        return jws;
    }
}
