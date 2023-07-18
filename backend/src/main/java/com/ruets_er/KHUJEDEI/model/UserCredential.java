/*
 * Copyright (c) 2022.
 * Document   : UserCredential.java
 * Created on : 7/9/22, 10:57 AM
 * Author     : NAHID RUET CSE'18
 */
package com.ruets_er.KHUJEDEI.model;

import javax.persistence.*;

@Entity
@Table(name = "user_credential")
public class UserCredential {
    @Id
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(name = "user_password", nullable = false, length = 30)
    private String userPassword;

    @Column(name = "user_messenger_id", length = 1000)
    private String userMessengerId;

    @Column(name = "user_role", nullable = false, length = 30)
    private String userRole;

    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMessengerId() {
        return userMessengerId;
    }

    public void setUserMessengerId(String userMessengerId) {
        this.userMessengerId = userMessengerId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean getUserStatus() {
        if(userStatus==1)
            return true;
        return  false;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

}


