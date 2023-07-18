/*
 * Copyright (c) 2022.
 * Document    : FaceSearchAiController.java
 * Created On  : 8/8/22, 4:22 PM
 * Author      : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class FaceSearchAiController extends ApiController{

    @PostMapping("/searchPostByImage")
    public HashMap<String,Object> getPosts(@RequestParam(name ="searchFaceToken") String searchFaceToken){
        HashMap<String,Object> returnObj = new HashMap<>();


            List<HashMap<String,Object>> resultsArray = faceSearchAIService.getPostsByFaceSearch(searchFaceToken);

            returnObj.put("ResponseCode", "1");
            returnObj.put("Response", "Successfull");
            returnObj.put("ResponseData", resultsArray);

        return returnObj;
    }

}
