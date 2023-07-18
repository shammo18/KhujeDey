/*
 * Copyright (c) 2022.
 * Document    : ProfileService.java
 * Created On  : 7/22/22, 3:08 AM
 * Author      : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.service;

import com.ruets_er.KHUJEDEI.model.Member;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ProfileService extends ApiService{

    public  HashMap<String,Object> profileDetails(Integer memberId)
    {
        Member m = memberRepository.getReferenceById(memberId);
        HashMap<String,Object> resultsObj = new HashMap<>();

        resultsObj.put("memberName",m.getMemberName());
        resultsObj.put("memberPhone",m.getMemberPhone());
        resultsObj.put("memberAddress",m.getMemberAdderss());

        return resultsObj;
    }
    public void editProfile(String jwt , String memberName , String memberPhone , String memberAddress){

        String username = jwtUtil.extractUsername(jwt);

        Member m = memberRepository.findByUsername(username);


        if(memberName!=null) m.setMemberName(memberName);
        if(memberPhone!=null) m.setMemberPhone(memberPhone);
        if(memberAddress!=null) m.setMemberAdderss(memberAddress);

        memberRepository.save(m);
    }

}
