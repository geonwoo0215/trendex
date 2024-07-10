package com.trendex.trendex.global.client.webclient.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${access-key}")
    private String accessKey;

    @Value("${secret-key}")
    private String secretKey;

    public String createToken() {

        Claims claims = Jwts.claims();
        claims.put("access_key", accessKey);
        claims.put("nonce", UUID.randomUUID().toString());

        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .setClaims(claims)
                .compact();
    }

    public String createToken(String market, String side, String volume, String price, String ordType, String timeInForce) {
        HashMap<String, String> params = new HashMap<>();
        params.put("market", market);
        params.put("side", side);
        if (volume != null) {
            params.put("volume", volume);

        }
        if (price != null) {
            params.put("price", price);

        }
        params.put("ord_type", ordType);
        params.put("time_in_force", timeInForce);

        ArrayList<String> queryElements = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            queryElements.add(entry.getKey() + "=" + entry.getValue());
        }
        String queryString = String.join("&", queryElements.toArray(new String[0]));

        MessageDigest md;
        String queryHash;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(queryString.getBytes());
            queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create hash", e);
        }

        log.info("queryString = {}", queryString);

        Claims claims = Jwts.claims();
        claims.put("access_key", accessKey);
        claims.put("nonce", UUID.randomUUID().toString());
        claims.put("query_hash", queryHash);
        claims.put("query_hash_alg", "SHA512");


        return Jwts.builder()
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .setClaims(claims)
                .compact();
    }

}