package com.tpe.security;

import com.tpe.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtils {
    //hash(abc)---> aaasdabffdjf---> abc ye geri donusturulemez(tek yonlu sifreleme)
    //JWT token: header + payload(userla ilgili bilgiler tasiyoruz) + signature(secret imza)

    private long jwtExpirationtime = 86400000;//24*60*60*1000

    private String secretKey="techpro";



    //*************************1-JWT generate***************************
    public String generateToken(Authentication authentication){

        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();//LOGIN OLMUS USER(AUTHENTICATED) ULASMAMIZI SAGLAR

        //login olan userin usernameini token icerisine enjekte edelim
        return Jwts.builder() //jwt olsturucuyu saglar.
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date()) //system.currentTimeMillis()
                .setExpiration(new Date(new Date().getTime()+ jwtExpirationtime))
                .signWith(SignatureAlgorithm.HS512,secretKey)//hasleme ile tek yonlu sifreleme, karsilastirmalarda kullanilir
                .compact();//ayarlari tamamlar ve tokun olusturur
    }


    //********************************2-JWT token validate********************************
    public boolean validateToken(String token){

        try {
            Jwts.parser() //ayristirici
                    .setSigningKey(secretKey) // dogrulamayi bu anahatar ile karsilastir
                    .parseClaimsJws(token);//imzalar uyumlu ise,Jwt gecerli olacak

            return true;
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
        } catch (MalformedJwtException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return false;


    }


    //********************************3-JWT tokendan username almamiz gerekiyor.***************************
    public String getUSerNameFromJwtToken(String token){

        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)//dogrulanmis tokenin icersindeki claimsleri donduruyor
                .getBody()
                .getSubject();//username

    }



}
