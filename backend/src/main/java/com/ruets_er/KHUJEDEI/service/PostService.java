/*
 * Copyright (c) 2022.
 * Document   : ApiService.java
 * Created on : 7/17/22, 04:40 PM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.service;

import com.ruets_er.KHUJEDEI.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PostService extends ApiService{

    @Value("${deploy.url}") String deployUrl ;
    @Value("${imageResourse.path}") String imagePath ;
    @Autowired
    ResourceService resourceService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    NotificationService notificationService;

    @Autowired
    FileUploadService fileUploadService;

    public List<HashMap<String,Object>> getPosts(Integer pageNumber){
        List<HashMap<String,Object>> resultsArray = new ArrayList<>();
        List<Object[]> list= postRepository.findall(PageRequest.of(pageNumber,9));
        for (Object[] ob : list) {

            HashMap<String,Object> resultsObj = new HashMap<>();

            resultsObj.put("postId",(Integer) ob[0]);
            resultsObj.put("memberName",(String)ob[1]);
            resultsObj.put("memberId",(Integer)ob[2]);
            resultsObj.put("catagoryName",(String)ob[3]);
            resultsObj.put("modelName",(String) ob[4]);
            resultsObj.put("areaName",(String) ob[5]);
            resultsObj.put("postDate",(LocalDate) ob[6]);
            resultsObj.put("postDescription",(String) ob[7]);
            resultsObj.put("postImageLink",(String) ob[8]);

            resultsArray.add(resultsObj);
        }
        return resultsArray;
    }

    public List<HashMap<String,Object>> getFilteredPosts
            (Integer catagoryId ,Integer modelId ,Double latitude ,Double longitude , LocalDate postDate ){



        List<HashMap<String,Object>> resultsArray = new ArrayList<>();

        if(catagoryId==null && modelId==null && latitude==null && longitude==null && postDate==null) return resultsArray;

        String postDescription = null;
        List<Object[]> list= findallFiltered(  catagoryId , modelId , latitude ,longitude , postDate, postDescription );
        for (Object[] ob : list) {

            HashMap<String,Object> resultsObj = new HashMap<>();

            resultsObj.put("postId",(Integer) ob[0]);
            resultsObj.put("memberName",(String)ob[1]);
            resultsObj.put("memberId",(Integer)ob[2]);
            resultsObj.put("catagoryName",(String)ob[3]);
            resultsObj.put("modelName",(String) ob[4]);
            resultsObj.put("areaName",(String) ob[5]);
            resultsObj.put("postDate",(LocalDate) ob[6]);
            resultsObj.put("postDescription",(String) ob[7]);
            resultsObj.put("postImageLink",(String) ob[8]);

            resultsArray.add(resultsObj);
        }
        return resultsArray;
    }

    public List<HashMap<String,Object>> getPostById(Integer postId){
        List<HashMap<String,Object>> resultsArray = new ArrayList<>();
        List<Object[]> list= postRepository.findPostById(postId);
        for (Object[] ob : list) {

            HashMap<String,Object> resultsObj = new HashMap<>();

            resultsObj.put("postId",(Integer) ob[0]);
            resultsObj.put("memberName",(String)ob[1]);
            resultsObj.put("memberId",(Integer)ob[2]);
            resultsObj.put("catagoryName",(String)ob[3]);
            resultsObj.put("modelName",(String) ob[4]);
            resultsObj.put("areaName",(String) ob[5]);
            resultsObj.put("postDate",(LocalDate) ob[6]);
            resultsObj.put("postDescription",(String) ob[7]);
            resultsObj.put("postImageLink",(String) ob[8]);

            resultsArray.add(resultsObj);
        }
        return resultsArray;
    }


    @Transactional
    public void savePost(String jwt, String catagoryName, String modelName, Double latitude , Double longitude , String Areaname , String description, MultipartFile image , String imageUid, Integer postStatus )
    {
        String username = jwtUtil.extractUsername(jwt);
        Member m =  memberRepository.findByUsername(username);
        Integer catagoryId =resourceService.findCatagoryIdByCatagoryName(catagoryName);
        Integer modelId =resourceService.findModelIdByModelNameAndCatagoryId(modelName,catagoryId);
        Catagory c =  catagoryRepository.getReferenceById(catagoryId);
        Model mod =  modelRepository.getReferenceById(modelId);

        Post p = new Post();

        // get Primary key of posts table
        Registry r = registryRepository.getReferenceById(5);
        Integer id = r.getPrimaryId() + 1;
        r.setPrimaryId(id);
        registryRepository.save(r);

        //save values
        p.setId(id);
        p.setPostWoner(m);
        p.setCatagory(c);
        p.setModel(mod);
        p.setAreaLatitude(latitude);
        p.setAreaLongitude(longitude);
        p.setAreaName(Areaname);

        String imageName = fileUploadService.saveFile(image ,"postImage"+id);
        p.setPostImageLink(deployUrl+imagePath+"?imageName="+imageName);
        p.setImageUid(imageUid);
        p.setPostDescription(description);

        LocalDate postDate =LocalDate.now();
        p.setPostDate(postDate);
        p.setPostStatus(postStatus);

        postRepository.save(p);

        notificationService.checkPoststoNotify(catagoryId, modelId ,username,latitude, longitude, postStatus , description , catagoryName , modelName , Areaname , id);
    }

    @Transactional
    public boolean deletePostts(String  jwt, Integer postId){
        String username = jwtUtil.extractUsername(jwt);
        Post p = postRepository.getReferenceById(postId);
        String postWoner = p.getPostWoner().getUserCredential().getUserName();

        if(username.equals(postWoner))
        {
            commentRepository.deleteCommentByPostId(postId);
            postRepository.deleteById(postId);
            return true;
        }
        else return false;
    }

    public List<Object[]> findallFiltered(Integer catagoryId ,Integer modelId ,Double latitude ,Double longitude , LocalDate postDate , String postDescription ) {

        String Condition =" ";
        if(catagoryId!=null) Condition += " and c.id ="+catagoryId;
        if(modelId!=null) Condition += " and m.id="+modelId;
        if(latitude!=null && longitude!=null) Condition += " and SQRT(POW(69.1 * (p.areaLatitude - "+latitude+"), 2) + POW(69.1 * ("+longitude+" - p.areaLongitude) * COS(p.areaLatitude / 57.3), 2))<=5";
        if(postDate!=null) Condition += " and p.postDate='"+postDate+"'";
        if(postDescription!=null) Condition += " and p.postDescription='"+postDescription+"'";

        Session currentSession = entityManager.unwrap(Session.class);
        Query<Object[]> query = currentSession.createQuery
                ("Select p.id,mem.memberName,mem.id,c.catagoryName,m.modelName,p.areaName,p.postDate,p.postDescription,p.postImageLink from Post p join p.postWoner mem join p.catagory c join p.model m  where p.postStatus=1 "+Condition+" order by p.postDate desc");
        List<Object[]> list = query.getResultList();
        currentSession.close();

        return list;
    }
}
