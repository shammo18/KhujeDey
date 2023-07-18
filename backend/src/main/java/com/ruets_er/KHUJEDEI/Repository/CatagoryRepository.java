/*
 * Copyright (c) 2022.
 * Document   : CatagoryRepository.java
 * Created on : 7/9/22, 10:57 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.Repository;

import com.ruets_er.KHUJEDEI.model.Catagory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CatagoryRepository extends JpaRepository<Catagory, Integer> {

//    It's just a way... But way more difficult to handle more  attributes ..Leave it
//    @Query("Select new Catagory(c.id,c.catagoryName) from Catagory c")
//    List<Catagory> findal();
    @Query("Select c.id,c.catagoryName from Catagory c")
    List<Object[]> findal();

    @Query("Select c.id from Catagory c where c.catagoryName=:catagoryName")
    public Integer findByCatagoryName(@Param("catagoryName") String catagoryName);

    @Query("Select c.id,c.catagoryName from Catagory c where c.catagoryName LIKE CONCAT(:catagoryFragment,'%')")
    public List<Object[]> findByCatagoryNameLike(@Param("catagoryFragment") String catagoryFragment , Pageable pageable);

}