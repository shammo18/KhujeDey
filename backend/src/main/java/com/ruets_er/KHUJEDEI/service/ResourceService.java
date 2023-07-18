/*
 * Copyright (c) 2022.
 * Document   : ApiService.java
 * Created on : 7/17/22, 05:47 PM
 * Author     : NAHID RUET CSE'18
 */
package com.ruets_er.KHUJEDEI.service;

import com.ruets_er.KHUJEDEI.model.Catagory;
import com.ruets_er.KHUJEDEI.model.Model;
import com.ruets_er.KHUJEDEI.model.Registry;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ResourceService extends ApiService{

    public List<HashMap<String,Object>> getCatagory(){
        List<HashMap<String,Object>> resultsArray = new ArrayList<>();

        List<Object[]> list= catagoryRepository.findal();
        for (Object[] ob : list) {

            HashMap<String,Object> resultsObj = new HashMap<>();
            resultsObj.put("catagoryId",(Integer) ob[0]);
            resultsObj.put("catagoryName",(String) ob[1]);

            resultsArray.add(resultsObj);
        }
        return resultsArray;
    }

    public List<HashMap<String,Object>> getCatagoryLike( String catagoryFragment){
        List<HashMap<String,Object>> resultsArray = new ArrayList<>();

        List<Object[]> list= catagoryRepository.findByCatagoryNameLike(catagoryFragment, PageRequest.of(0,5));
        for (Object[] ob : list) {

            HashMap<String,Object> resultsObj = new HashMap<>();
            resultsObj.put("catagoryId",(Integer) ob[0]);
            resultsObj.put("catagoryName",(String) ob[1]);

            resultsArray.add(resultsObj);
        }

        return resultsArray;
    }

    @Transactional
    Integer createCatagory(String catagoryName)
    {
            Catagory c =  new Catagory();

            // get Primary key of catagory table
            Registry r = registryRepository.getReferenceById(1);
            Integer id = r.getPrimaryId() + 1;
            r.setPrimaryId(id);
            registryRepository.save(r);

            //save values
            c.setId(id);
            c.setCatagoryName(catagoryName);

            catagoryRepository.save(c);
            return id;
    }

    public Integer findCatagoryIdByCatagoryName(String catagoryName)
    {
        // find in Catagory already exist or not in database
        Integer catagoryId = catagoryRepository.findByCatagoryName(catagoryName) == null ? 0 : catagoryRepository.findByCatagoryName(catagoryName);

        //if not exist then create
        if(catagoryId==0) catagoryId = createCatagory(catagoryName);

        return catagoryId;
    }

    //________________________________________________________________________

    public List<HashMap<String,Object>> getModel(Integer id){
        List<HashMap<String,Object>> resultsArray = new ArrayList<>();

        List<Object[]> list= modelRepository.findByIdc(id);
        for (Object[] ob : list) {

            HashMap<String,Object> resultsObj = new HashMap<>();
            resultsObj.put("modelId",(Integer) ob[0]);
            resultsObj.put("modelName",(String) ob[1]);

            resultsArray.add(resultsObj);
        }
        return resultsArray;
    }

    public List<HashMap<String,Object>> getModelLike(Integer catagoryId ,String modelFragment ){
        List<HashMap<String,Object>> resultsArray = new ArrayList<>();
        System.out.println(modelFragment +catagoryId );
        List<Object[]> list= modelRepository.findByModelNameLikeAndCatagoryId(catagoryId ,modelFragment,PageRequest.of(0,5));
        for (Object[] ob : list) {

            HashMap<String,Object> resultsObj = new HashMap<>();
            resultsObj.put("modelId",(Integer) ob[0]);
            resultsObj.put("modelName",(String) ob[1]);

            resultsArray.add(resultsObj);
        }
        return resultsArray;
    }

    @Transactional
    Integer createModel(String modelName , Integer catagoryId)
    {
        Model m =  new Model();

        // get Primary key of model table
        Registry r = registryRepository.getReferenceById(4);
        Integer id = r.getPrimaryId() + 1;
        r.setPrimaryId(id);
        registryRepository.save(r);

        // get catagory
        Catagory c= catagoryRepository.getReferenceById(catagoryId);

        //save values
        m.setId(id);
        m.setCatagory(c);
        m.setModelName(modelName);

        modelRepository.save(m);

        return id;
    }

    Integer findModelIdByModelNameAndCatagoryId(String modelName ,Integer catagoryId)
    {
        // find in Model for this Catagory already exist or not in database
        Integer modeId = modelRepository.findByModelNameAndCatagoryId(modelName , catagoryId) == null ? 0 : modelRepository.findByModelNameAndCatagoryId(modelName , catagoryId);

        //if not exist then create
        if(modeId==0) modeId = createModel(modelName , catagoryId);

        return modeId;
    }
}
