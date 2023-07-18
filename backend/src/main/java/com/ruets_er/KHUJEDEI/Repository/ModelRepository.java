/*
 * Copyright (c) 2022.
 * Document   : ModelRepository.java
 * Created on : 7/9/22, 10:58 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.Repository;

import com.ruets_er.KHUJEDEI.model.Catagory;
import com.ruets_er.KHUJEDEI.model.Model;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {

    @Query("Select m.id,m.modelName from Model m where catagory_id=:id")
    public List<Object[]> findByIdc(@Param("id")Integer id);

    @Query("Select m.id from Model m join m.catagory c where m.modelName=:modelName and c.id=:catagoryId")
    public Integer findByModelNameAndCatagoryId(@Param("modelName")String modelName ,@Param("catagoryId")Integer catagoryId);

    @Query("Select m.id,m.modelName from Model m join m.catagory c where m.modelName LIKE CONCAT(:modelFragment,'%') and c.id=:catagoryId ")
    public List<Object[]> findByModelNameLikeAndCatagoryId(@Param("catagoryId")Integer catagoryId,@Param("modelFragment") String modelFragment , Pageable pageable);

}