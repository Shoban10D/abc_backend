package Org;

import javax.inject.Singleton;
import io.smallrye.jwt.build.Jwt;

import java.sql.Time;
import java.time.Instant;
import java.util.*;

@Singleton
public class JwtService {

    public String generateJwt(){
//        Set<String> roles = new HashSet<>(
//                Arrays.asList("admin")
//        );

//    Time time = new Time(0,0,0);
//    Date date = new Date();
//        System.out.println(System.currentTimeMillis());
//        System.out.println(System.currentTimeMillis()+ 1000*60*5);

        return Jwt.issuer("AbcHealth-jwt").subject("AbcHealth-jwt")
                .groups("patient")
                .expiresIn(120)
                .sign();


    }

    public String generatePracJwt(){
        Time time = new Time(0,0,0);
        Date date = new Date();
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis()+ 1000*60*5);

        return Jwt.issuer("AbcHealth-jwt").subject("AbcHealth-jwt")
                .groups("practitioner").expiresIn(120)
                .sign();

    }

}
