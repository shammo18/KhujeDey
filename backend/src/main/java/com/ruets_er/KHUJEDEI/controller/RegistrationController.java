/*
 * Copyright (c) 2022.
 * Document    : RegistrationController.java
 * Created On  : 7/20/22, 12:34 AM
 * Author      : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class RegistrationController extends ApiController {

    @PostMapping("/registerUser")
    public HashMap<String,Object> registerUser( @RequestParam(name ="memberName") String memberName ,
                                                @RequestParam(name ="memberPhone") String memberPhone ,
                                                @RequestParam(name ="memberAddress") String memberAddress ,
                                                @RequestParam(name ="userName") String userName ,
                                                @RequestParam(name ="userPassword") String userPassword )
    {

        String userRole = "USER";
        HashMap<String,Object> returnObj = new HashMap<>();

        try
        {

            if(registrationService.checkUserNameAvailablity(userName)) {
                String verifyToken = registrationService.createUser(memberName, memberPhone, memberAddress, userName, userPassword, userRole);

                HashMap<String, Object> result = new HashMap<>();

                result.put("VerificationMessage", "Verify : " + verifyToken);
                result.put("Instractions", " To Active Your Account ,Follow these steps : " +
                        " 1. Go to your Messenger App " +
                        " 2. Search Khuje Dei in Bangla" +
                        " 3. Send this Verification Message ");

                returnObj.put("ResponseCode", "1");
                returnObj.put("Response", "Successfull");
                returnObj.put("ResponseData", result);
            }
            else
            {

                returnObj.put("ResponseCode", "0");
                returnObj.put("Response", "Failed");
                returnObj.put("ResponseData", "Username not available");
            }
        } catch (Exception e)
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Failed");
            returnObj.put("ResponseData", "Something Went Wrong");
        }
        return returnObj;
    }

    @PostMapping("/checkUserNameAvailablity")
    public HashMap<String,Object> checkUserNameAvailablity( @RequestParam(name ="userName") String userName )
    {

        HashMap<String,Object> returnObj = new HashMap<>();

        try
        {
            if(registrationService.checkUserNameAvailablity(userName))
            {
                returnObj.put("ResponseCode", "1");
                returnObj.put("Response", "Successfull");
                returnObj.put("ResponseData", "UserName Is Available");
            }
            else
            {
                returnObj.put("ResponseCode", "0");
                returnObj.put("Response", "Failed");
                returnObj.put("ResponseData", "UserName is not available");
            }


        } catch (Exception e)
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Failed");
            returnObj.put("ResponseData", "Something Went Wrong");
        }
        return returnObj;
    }
}
