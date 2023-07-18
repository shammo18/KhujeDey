/*
 * Copyright (c) 2022.
 * Document   : MemberRepository.java
 * Created on : 7/9/22, 10:57 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.Repository;

import com.ruets_er.KHUJEDEI.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    @Query("from Member m join m.userCredential uc where uc.userName=:userName")
    public Member findByUsername(@Param("userName")String userName);

    @Query("from Member m join m.userCredential uc where uc.userMessengerId=:userMessengerId")
    public Member findByUserByMessengerId(@Param("userMessengerId")String userMessengerId);
}