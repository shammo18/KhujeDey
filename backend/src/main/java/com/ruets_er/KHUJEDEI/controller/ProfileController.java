/*
 * Copyright (c) 2022.
 * Document    : ProfileController.java
 * Created On  : 7/23/22, 5:46 PM
 * Author      : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class ProfileController extends ApiController{

    @PostMapping("/profileDetails")
    public HashMap<String,Object> profileDetails(@RequestParam("memberId") Integer memberId)
    {
        HashMap<String,Object> returnObj = new HashMap<>();
        try {

            HashMap<String,Object> resultObj = profileService.profileDetails(memberId);

            returnObj.put("ResponseCode", "1");
            returnObj.put("Response", "Successfull");
            returnObj.put("ResponseData", resultObj);
        } catch (Exception e)
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Failed");
            returnObj.put("ResponseData", "Something Went Wrong");
        }
        return returnObj;
    }

    @PostMapping("/editProfile")
    public HashMap<String,Object> editProfile(@RequestHeader("Authorization") String bearerToken ,
                                              @RequestParam(name ="memberName" , required = false) String memberName ,
                                              @RequestParam(name ="memberPhone" ,required = false )String memberPhone ,
                                              @RequestParam(name ="memberAddress" ,required = false ) String memberAddress)
    {
        String jwt = bearerToken.substring(7);
        HashMap<String,Object> returnObj = new HashMap<>();
        try {

            profileService.editProfile(jwt ,  memberName , memberPhone , memberAddress);

            returnObj.put("ResponseCode", "1");
            returnObj.put("Response", "Successfull");
            returnObj.put("ResponseData", "Successfully Edited!!");
        } catch (Exception e)
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Failed");
            returnObj.put("ResponseData", "Something Went Wrong");
        }
        return returnObj;
    }
}
