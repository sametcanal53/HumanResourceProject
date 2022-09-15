package com.sametcanal.security.jwt.controller;

import com.sametcanal.security.jwt.TokenUtil;
import com.sametcanal.security.jwt.dto.UserDto;
import com.sametcanal.security.jwt.dto.request.JwtRequest;
import com.sametcanal.security.jwt.dto.response.JwtResponse;
import com.sametcanal.security.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;
    private final UserService userService;


    //Kayıt olmak
    // http://localhost:8080/register ==> POST { "username":"Hamit","password":"123"
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
        return ResponseEntity.ok(userService.save(user));
    }

    //Login bilgilerle sistemin bana bearer token verecek yer
    //http://localhost:8080/authenticate  ==> POST { "username":"Hamit","password":"123"
    //Dönen bearer token ekleyerek sisteme giriş sağlamak
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = tokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

