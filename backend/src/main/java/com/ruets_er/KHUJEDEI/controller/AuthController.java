package com.ruets_er.KHUJEDEI.controller;

import com.ruets_er.KHUJEDEI.Repository.MemberRepository;
import com.ruets_er.KHUJEDEI.Repository.UserCredentialRepository;
import com.ruets_er.KHUJEDEI.model.Member;
import com.ruets_er.KHUJEDEI.model.auth.AuthenticationRequest;
import com.ruets_er.KHUJEDEI.model.auth.AuthenticationResponse;
import com.ruets_er.KHUJEDEI.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class AuthController {

    @Autowired
    UserCredentialRepository userCredentialRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        HashMap<String,Object> returnObj = new HashMap<>();

        Integer userStatus = userCredentialRepository.findStatusByUsername(authenticationRequest.getUsername());

        if(userStatus==0)
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Feiled ");
            returnObj.put("ResponseData", "Your Account isn't Active");
            return ResponseEntity.ok(returnObj);
        }

        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {

            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Feiled ");
            returnObj.put("ResponseData", "Incorrect username or password");
            return ResponseEntity.ok(returnObj);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        Member m = memberRepository.findByUsername(authenticationRequest.getUsername());

        HashMap<String,Object> resultObj = new HashMap<>();
        resultObj.put("memberName" , m.getMemberName());
        resultObj.put("memberId", m.getId());
        resultObj.put("authToken" ,new AuthenticationResponse(jwt));

        returnObj.put("ResponseCode", "1");
        returnObj.put("Response", "Successfull");
        returnObj.put("ResponseData", resultObj);

        return ResponseEntity.ok(returnObj);
    }
}
