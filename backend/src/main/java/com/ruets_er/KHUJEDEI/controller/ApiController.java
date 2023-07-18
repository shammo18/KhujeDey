/*
 * Copyright (c) 2022.
 * Document   : ApiController.java
 * Created on : 7/9/22, 10:52 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.controller;

import com.ruets_er.KHUJEDEI.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ApiController {

    @Autowired
    CommentService commentService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    PostService postService;

    @Autowired
    RegistrationService registrationService;

    @Autowired
    ProfileService profileService;

    @Autowired FaceSearchAIService faceSearchAIService;
}
