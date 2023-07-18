/*
 * Copyright (c) 2022.
 * Document   : Member.java
 * Created on : 7/9/22, 10:56 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "member")
public class Member {
    @Id
    @Column(name = "member_id", nullable = false)
    private Integer id;

    @Column(name = "member_name", length = 300)
    private String memberName;

    @Column(name = "member_phone", length = 15)
    private String memberPhone;

    @Column(name = "member_adderss", length = 1000)
    private String memberAdderss;

    @OneToMany(mappedBy = "commentWoner")
    private Set<Comment> comments = new LinkedHashSet<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private UserCredential userCredential;

    @OneToMany(mappedBy = "postWoner")
    private Set<Post> posts = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberAdderss() {
        return memberAdderss;
    }

    public void setMemberAdderss(String memberAdderss) {
        this.memberAdderss = memberAdderss;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

}