/*
 * Copyright (c) 2022.
 * Document   : ResourceController.java
 * Created on : 7/9/22, 10:55 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

@RestController
public class ResourceController extends ApiController {

    @Autowired
    ResourceLoader resourceLoader;
    @GetMapping(
            value = "/images",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("imageName") String imageName) throws IOException {
        Resource resource=resourceLoader.getResource("classpath:/webapp/images/"+imageName);

        File file = resource.getFile();



        return  Files.readAllBytes(file.toPath());
    }
    @PostMapping("/catagory")
    public HashMap<String,Object> getCatagory(){
        HashMap<String,Object> returnObj = new HashMap<>();

        try {
            List<HashMap<String,Object>> resultsArray = resourceService.getCatagory();

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

    @PostMapping("/catagoryLike")
    public HashMap<String,Object> getCatagoryLike(@RequestParam("catagoryFragment") String catagoryFragment){
        HashMap<String,Object> returnObj = new HashMap<>();

        try {
            List<HashMap<String,Object>> resultsArray = resourceService.getCatagoryLike(catagoryFragment);

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

    @PostMapping("/catagoryIdByName")
    public HashMap<String,Object> catagoryIdByName(@RequestParam("catagoryName") String catagoryName){
        HashMap<String,Object> returnObj = new HashMap<>();

        try {

            Integer id = resourceService.findCatagoryIdByCatagoryName(catagoryName);
            HashMap<String,Object> resultObj = new HashMap<>();
            resultObj.put("catagoryId" , id);

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


    @PostMapping("/model")
    public HashMap<String,Object> getModel(@RequestParam("catagoryId") Integer catagoryId){
        HashMap<String,Object> returnObj = new HashMap<>();

        try {
            List<HashMap<String,Object>> resultsArray = resourceService.getModel(catagoryId);

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

    @PostMapping("/modelLike")
    public HashMap<String,Object> getModelLike(@RequestParam("catagoryId") Integer catagoryId,@RequestParam("modelFragment") String modelFragment){
        HashMap<String,Object> returnObj = new HashMap<>();

        try {
            List<HashMap<String,Object>> resultsArray = resourceService.getModelLike(catagoryId,modelFragment);

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
}
