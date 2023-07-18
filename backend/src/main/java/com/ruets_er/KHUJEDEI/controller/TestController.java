/*
 * Copyright (c) 2022.
 * Document   : TestController.java
 * Created on : 7/9/22, 10:55 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.*;



@RestController
public class TestController {

    @GetMapping("/testUser")
    public String testu()
    {
        return  "test User!!";
    }
    @GetMapping("/testAdmin")
    public String testa()
    {
        return  "test Admin!!";
    }
    @GetMapping("/")
    public String testw()
    {

        return "Welcome";
    }


}

class Respons {
    String request_id;
    String time_used,confidence;
    Object thresholds;

}
