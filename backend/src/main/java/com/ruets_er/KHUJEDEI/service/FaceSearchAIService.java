/*
 * Copyright (c) 2022.
 * Document    : FaceSearchAIService.java
 * Created On  : 8/8/22, 3:12 PM
 * Author      : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.service;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class FaceSearchAIService extends ApiService{


    public List<HashMap<String,Object>> getPostsByFaceSearch(String searchFaceToken){
        List<HashMap<String,Object>> resultsArray = new ArrayList<>();
        List<Object[]> list= postRepository.findallHumanPost();
        for (Object[] ob : list) {

            HashMap<String,Object> resultsObj = new HashMap<>();

            resultsObj.put("postId",(Integer) ob[0]);
            resultsObj.put("memberName",(String)ob[1]);
            resultsObj.put("memberId",(Integer)ob[2]);
            resultsObj.put("lostHumanName",(String) ob[4]);
            resultsObj.put("address",(String) ob[5]);
            resultsObj.put("postDate",(LocalDate) ob[6]);
            resultsObj.put("postDescription",(String) ob[7]);
            resultsObj.put("postImageLink",(String) ob[8]);
            String faceToken =(String) ob[9];

            if(getCompireResult(faceToken,searchFaceToken)>60)
                                        resultsArray.add(resultsObj);
        }
        return resultsArray;
    }


    public Float getCompireResult(String face1, String face2)
    {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        final String uri = "https://api-us.faceplusplus.com/facepp/v3/compare?api_key=WwMDx528j9rdIPozfPHmPRrlDi_ZB-H4&api_secret=ARaboX1i4hGXwl6BdHXSXf6OJ10AMO-P";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("face_token1", face1);
        body.add("face_token2", face2);

        HttpEntity<MultiValueMap<String, Object>> requestEntity= new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        String result = restTemplate.postForEntity(uri, requestEntity,
                String.class).getBody();

        Gson gson = new Gson();

        Respons gfg = gson.fromJson(result, Respons.class);


        return  Float.valueOf(gfg.confidence);
    }



    class Respons {
        String request_id;
        String time_used,confidence;
        Object thresholds;
        Object faces1;

    }

}
