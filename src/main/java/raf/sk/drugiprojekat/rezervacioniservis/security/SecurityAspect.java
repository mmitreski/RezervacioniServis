package raf.sk.drugiprojekat.rezervacioniservis.security;

import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import raf.sk.drugiprojekat.rezervacioniservis.security.service.TokenService;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Configuration
public class SecurityAspect {
    @Value("${oauth.jwt.secret}")
    private String jwtSecret;
    private TokenService tokenService;

    public SecurityAspect(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Around("@annotation(raf.sk.drugiprojekat.rezervacioniservis.security.CheckSecurity)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        int id = -1; // id from the method
        String token = null;
        for(int i = 0; i < methodSignature.getParameterNames().length; i++) {
            if(methodSignature.getParameterNames()[i].equals("authorization")) {
                if(joinPoint.getArgs()[i].toString().startsWith("Bearer"))
                    token = joinPoint.getArgs()[i].toString().split(" ")[1];
            } else if(methodSignature.getParameterNames()[i].equals("id")) {
                id = Integer.parseInt(joinPoint.getArgs()[i].toString());
            }
        }
        if(token == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Claims claims = tokenService.parseToken(token);
        System.out.println(claims);
        if(claims == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        CheckSecurity checkSecurity = method.getAnnotation(CheckSecurity.class);
        String role = claims.get("role", String.class);

        int claims_id = switch (role) {
            case "ROLEADMIN" -> claims.get("adminid", Integer.class);
            case "ROLEMANAGER" -> claims.get("managerid", Integer.class);
            case "ROLECLIENT" -> claims.get("clientid", Integer.class);
            default -> -1;
        }; // id from the token

        if(Arrays.asList(checkSecurity.roles()).contains(role)) {
            if(role.equals("ROLEADMIN")) {
                if(!checkSecurity.admin_id_required() || id == claims_id) // if the id is required and the id's match or if the id is not required then proceed
                    return joinPoint.proceed();
            } else if(role.equals("ROLEMANAGER")) {
                if(!checkSecurity.manager_id_required() || id == claims_id)
                    return joinPoint.proceed();
            } else {
                if(!checkSecurity.client_id_required() || id == claims_id)
                    return joinPoint.proceed();
            }
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
