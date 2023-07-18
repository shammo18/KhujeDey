/*
 * Copyright (c) 2022.
 * Document   : Registry.java
 * Created on : 7/9/22, 10:57 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.model;

import javax.persistence.*;

@Entity
@Table(name = "registry")
public class Registry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registry_id", nullable = false)
    private Integer id;

    @Column(name = "table_name", length = 100)
    private String tableName;

    @Column(name = "primary_id", nullable = false)
    private Integer primaryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Integer primaryId) {
        this.primaryId = primaryId;
    }

}