/*
 * Copyright (c) 2022.
 * Document   : PostController.java
 * Created on : 7/9/22, 10:54 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
@RestController
public class PostController extends ApiController {

    @PostMapping("/posts")
    public HashMap<String,Object> getPosts(@RequestParam(name ="pageNumber") Integer pageNumber){
        HashMap<String,Object> returnObj = new HashMap<>();

        try {
            List<HashMap<String,Object>> resultsArray = postService.getPosts(pageNumber);

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

    @PostMapping("/getFilteredPosts")
    public HashMap<String,Object> getFilteredPosts(
            @RequestParam(name ="catagoryId" , required = false) Integer catagoryId,
            @RequestParam(name ="modelId" , required = false) Integer modelId,
            @RequestParam(name ="latitude" , required = false) Double latitude,
            @RequestParam(name ="longitude" , required = false) Double longitude,
            @RequestParam(name ="postDate" , required = false) LocalDate postDate
    ){
        HashMap<String,Object> returnObj = new HashMap<>();

        try
        {
            List<HashMap<String,Object>> resultsArray = postService.getFilteredPosts(catagoryId , modelId , latitude ,longitude , postDate );

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

    @PostMapping("/postDetails")
    public HashMap<String,Object> getPostById(@RequestParam(name ="postId") Integer postId){
        HashMap<String,Object> returnObj = new HashMap<>();

        try {
            List<HashMap<String,Object>> resultsArray = postService.getPostById(postId);

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
    @PostMapping("/addPost")
    public HashMap<String,Object> savePost(@RequestHeader("Authorization") String bearerToken ,
                                           @RequestParam(name ="catagoryName")String catagoryName, @RequestParam(name ="modelName")String modelName,
                                           @RequestParam(name ="areaLatitude" ,required=false)Double latitude , @RequestParam(name ="areaLongitude" ,required=false)Double longitude ,
                                           @RequestParam(name ="areaName" ,required=false)String Areaname ,
                                           @RequestParam(name ="description" ,required=false)String description ,
                                           @RequestPart MultipartFile files, @RequestParam(name ="imageUid" ,required=false)String imageUid,
                                           @RequestParam(name ="postStatus") Integer postStatus )
    {
        String jwt = bearerToken.substring(7);
        HashMap<String,Object> returnObj = new HashMap<>();

        if((description==null && files==null)||(description.length()==0 && files.getSize()==0))
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Failed");
            returnObj.put("ResponseData", "Empty Post isn't allowed! ");
            return returnObj;
        }

        try
        {
            postService.savePost(jwt ,catagoryName , modelName ,latitude , longitude , Areaname , description, files,imageUid, postStatus);

            returnObj.put("ResponseCode", "1");
            returnObj.put("Response", "Successfull");
            returnObj.put("ResponseData", "Successfully Added Your Post");
        } catch (Exception e)
        {
            returnObj.put("ResponseCode", "0");
            returnObj.put("Response", "Failed");
            returnObj.put("ResponseData", "Something Went Wrong");
        }
        return returnObj;
    }

    @PostMapping("/deletePost")
    public HashMap<String,Object> deleteComments(@RequestHeader("Authorization") String bearerToken ,@RequestParam(name ="postId")Integer postId){
        String jwt = bearerToken.substring(7);
        HashMap<String,Object> returnObj = new HashMap<>();

        try
        {
            boolean status = postService.deletePostts(jwt,postId);

            if(status)
            {
                returnObj.put("ResponseCode", "1");
                returnObj.put("Response", "Successfull");
                returnObj.put("ResponseData", "Successfully Deleted Your Post");
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
