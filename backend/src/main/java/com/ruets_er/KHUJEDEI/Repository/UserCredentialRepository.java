/*
 * Copyright (c) 2022.
 * Document   : UserCredentialRepository.java
 * Created on : 7/9/22, 10:58 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.Repository;

import com.ruets_er.KHUJEDEI.model.Member;
import com.ruets_er.KHUJEDEI.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

    @Query("Select uc.userStatus from UserCredential uc where uc.userName=:userName")
    public Integer findStatusByUsername(@Param("userName")String userName);

    @Query("from UserCredential uc where uc.userName=:userName")
    public UserCredential findUserByUsername(@Param("userName")String userName);
}