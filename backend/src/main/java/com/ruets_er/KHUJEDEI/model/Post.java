/*
 * Copyright (c) 2022.
 * Document   : Post.java
 * Created on : 7/9/22, 10:56 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @Column(name = "post_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_woner_id", nullable = false)
    private Member postWoner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "catagory_id", nullable = false)
    private Catagory catagory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id")
    private Model model;

    @Column(name = "area_latitude")
    private Double areaLatitude;

    @Column(name = "area_longitude")
    private Double areaLongitude;

    @Column(name = "area_name", length = 300)
    private String areaName;

    @Column(name = "post_date")
    private LocalDate postDate;

    @Lob
    @Column(name = "post_description")
    private String postDescription;

    @Column(name = "post_image_link", length = 300)
    private String postImageLink;

    @Column(name = "image_uid", length = 100)
    private String imageUid;

    @Column(name = "post_status", nullable = false)
    private Integer postStatus;

    @OneToMany(mappedBy = "commentPost")
    private Set<Comment> comments = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Member getPostWoner() {
        return postWoner;
    }

    public void setPostWoner(Member postWoner) {
        this.postWoner = postWoner;
    }

    public Catagory getCatagory() {
        return catagory;
    }

    public void setCatagory(Catagory catagory) {
        this.catagory = catagory;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Double getAreaLatitude() {
        return areaLatitude;
    }

    public void setAreaLatitude(Double areaLatitude) {
        this.areaLatitude = areaLatitude;
    }

    public Double getAreaLongitude() {
        return areaLongitude;
    }

    public void setAreaLongitude(Double areaLongitude) {
        this.areaLongitude = areaLongitude;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public String getPostImageLink() {
        return postImageLink;
    }

    public void setPostImageLink(String postImageLink) {
        this.postImageLink = postImageLink;
    }

    public String getImageUid() {
        return imageUid;
    }

    public void setImageUid(String imageUid) {
        this.imageUid = imageUid;
    }

    public Integer getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Integer postStatus) {
        this.postStatus = postStatus;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}