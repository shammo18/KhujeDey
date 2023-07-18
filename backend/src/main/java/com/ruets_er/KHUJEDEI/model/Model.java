/*
 * Copyright (c) 2022.
 * Document   : Model.java
 * Created on : 7/9/22, 10:56 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "model")
public class Model {
    @Id
    @Column(name = "model_id", nullable = false)
    private Integer id;

    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;

    @Lob
    @Column(name = "model_description")
    private String modelDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catagory_id")
    private Catagory catagory;

    @OneToMany(mappedBy = "model")
    private Set<Post> posts = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public Catagory getCatagory() {
        return catagory;
    }

    public void setCatagory(Catagory catagory) {
        this.catagory = catagory;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

}