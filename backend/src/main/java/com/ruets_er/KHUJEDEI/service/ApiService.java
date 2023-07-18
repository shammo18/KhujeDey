/*
 * Copyright (c) 2022.
 * Document   : ApiService.java
 * Created on : 7/17/22, 03:05 PM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.service;

import com.ruets_er.KHUJEDEI.Repository.*;
import com.ruets_er.KHUJEDEI.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class ApiService {
    @Autowired
    CatagoryRepository catagoryRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RegistryRepository registryRepository;

    @Autowired
    UserCredentialRepository userCredentialRepository;

    @Autowired
    public JwtUtil jwtUtil;
}
