
/*
 * Copyright (c) 2022.
 * Document   : CommentController.java
 * Created on : 7/9/22, 10:54 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class CommentController extends ApiController {
    @PostMapping("/comments")
    public HashMap<String,Object> geComments(@RequestParam("postId") Integer postId) {

        HashMap<String,Object> returnObj = new HashMap<>();

        try {
            List<HashMap<String,Object>> resultsArray = commentService.geComments(postId);

            returnObj.put("ResponseCode", "1");
            returnObj.put("Response", "Successfull");
            returnObj.put("ResponseData", resultsArray);
        } catch (Exception e)
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Failed");
            returnObj.put("ResponseData", "Something Went Wrong");
        }
        return returnObj;
    }

    @PostMapping("/addComment")
    public HashMap<String,Object> saveComments(@RequestHeader("Authorization") String bearerToken ,@RequestParam(name ="postId")Integer postId, @RequestParam(name ="description")String description){

        String jwt = bearerToken.substring(7);
        HashMap<String,Object> returnObj = new HashMap<>();

        try
        {
            commentService.saveComments(jwt , postId , description);

            returnObj.put("ResponseCode", "1");
            returnObj.put("Response", "Successfull");
            returnObj.put("ResponseData", "Successfully Added Your Comment");
        } catch (Exception e)
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Failed");
            returnObj.put("ResponseData", "Something Went Wrong");
        }
        return returnObj;
    }

    @PostMapping("/deleteComment")
    public HashMap<String,Object> deleteComments(@RequestHeader("Authorization") String bearerToken ,@RequestParam(name ="commentId")Integer commentId){
        String jwt = bearerToken.substring(7);
        HashMap<String,Object> returnObj = new HashMap<>();

        try
        {
            boolean status = commentService.deleteComments(jwt , commentId);

            if(status)
            {
                returnObj.put("ResponseCode", "1");
                returnObj.put("Response", "Successfull");
                returnObj.put("ResponseData", "Successfully Deleted Your Comment");
            }
            else
            {
                returnObj.put("ResponseCode", "0");
                returnObj.put("Response", "Failed");
                returnObj.put("ResponseData", "Illigal Action");
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
