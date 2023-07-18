/*
 * Copyright (c) 2022.
 * Document   : UserService.java
 * Created on : 7/9/22, 10:59 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.service;

import com.ruets_er.KHUJEDEI.Repository.MemberRepository;
import com.ruets_er.KHUJEDEI.model.Member;
import com.ruets_er.KHUJEDEI.model.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private MemberRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = userRepository.findByUsername(username);
        UserCredential uc = user.getUserCredential();
        if (user == null)
            throw new UsernameNotFoundException(username);

        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(uc.getUserPassword())
                .authorities(uc.getUserRole())
                .accountLocked(!uc.getUserStatus())
                .roles(uc.getUserRole())
                .build();
    }
}
