/*
 * Copyright (c) 2022.
 * Document   : ApiService.java
 * Created on : 7/17/22, 03:50 PM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.service;

import com.ruets_er.KHUJEDEI.model.Comment;
import com.ruets_er.KHUJEDEI.model.Member;
import com.ruets_er.KHUJEDEI.model.Post;
import com.ruets_er.KHUJEDEI.model.Registry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class CommentService extends ApiService {


    public List<HashMap<String,Object>> geComments(Integer postId) {

        List<HashMap<String,Object>> resultsArray = new ArrayList<>();

        List<Object[]> list= commentRepository.findCommentByPostId(postId);
        for (Object[] ob : list) {
            HashMap<String,Object> resultsObj = new HashMap<>();
            resultsObj.put("commentId",(Integer) ob[0]);
            resultsObj.put("commentWonerId",(Integer) ob[1]);
            resultsObj.put("commentWonerName",(String) ob[2]);
            resultsObj.put("commentDescription",(String) ob[3]);

            resultsArray.add(resultsObj);
        }
        return resultsArray;
    }

    @Transactional
    public void saveComments(String jwt,Integer postId,String description ){

            String username = jwtUtil.extractUsername(jwt);
            Post p = postRepository.getReferenceById(postId);
            Member m =  memberRepository.findByUsername(username);
            Comment c = new Comment();

            // get Primary key of comments table
            Registry r = registryRepository.getReferenceById(2);
            Integer id = r.getPrimaryId() + 1;
            r.setPrimaryId(id);
            registryRepository.save(r);

            //save values
            c.setId(id);
            c.setCommentPost(p);
            c.setCommentWoner(m);
            c.setCommentDescription(description);
            c.setCommentStatus(1);
            commentRepository.save(c);
    }

    @Transactional
    public boolean deleteComments(String jwt,Integer commentId){

        String username = jwtUtil.extractUsername(jwt);
        Comment c = commentRepository.getReferenceById(commentId);
        String commentWoner = c.getCommentWoner().getUserCredential().getUserName();

        if(username.equals(commentWoner))
        {
            commentRepository.deleteById(commentId);
            return true;
        }
        else return false;

    }
}
