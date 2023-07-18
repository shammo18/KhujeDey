/*
 * Copyright (c) 2022.
 * Document   : Catagory.java
 * Created on : 7/9/22, 10:56 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "catagory")
public class Catagory {

    @Id
    @Column(name = "catagory_id", nullable = false)
    private Integer id;

    @Column(name = "catagory_name", nullable = false, length = 100)
    private String catagoryName;

    @OneToMany(mappedBy = "catagory")
    private Set<Model> models = new LinkedHashSet<>();

    @OneToMany(mappedBy = "catagory")
    private Set<Post> posts = new LinkedHashSet<>();

    public Catagory() {
    }

    public Catagory(Integer id, String catagoryName) {
        this.id = id;
        this.catagoryName = catagoryName;
    }

    public Catagory(Integer id, String catagoryName, Set<Model> models, Set<Post> posts) {
        this.id = id;
        this.catagoryName = catagoryName;
        this.models = models;
        this.posts = posts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }


}