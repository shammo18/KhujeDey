/*
 * Copyright (c) 2022.
 * Document   : PostRepository.java
 * Created on : 7/9/22, 10:58 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.Repository;

import com.ruets_er.KHUJEDEI.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("Select p.id,mem.memberName,mem.id,c.catagoryName,m.modelName,p.areaName,p.postDate,p.postDescription,p.postImageLink from Post p join p.postWoner mem join p.catagory c join p.model m  where p.postStatus=1 order by p.postDate desc")
    public List<Object[]> findall( Pageable pageable);

    @Query("Select p.id,mem.memberName,mem.id,c.catagoryName,m.modelName,p.areaName,p.postDate,p.postDescription,p.postImageLink ,p.imageUid from Post p join p.postWoner mem join p.catagory c join p.model m  where p.postStatus=1 and c.catagoryName='human' order by p.postDate desc")
    public List<Object[]> findallHumanPost();

    @Query("Select p.id,mem.memberName,mem.id,c.catagoryName,m.modelName,p.areaName,p.postDate,p.postDescription,p.postImageLink from Post p join p.postWoner mem join p.catagory c join p.model m  where p.postStatus=1 and p.id = :postId")
    public List<Object[]> findPostById(@Param("postId") Integer postId);

    @Query("Select uc.userMessengerId from Post p join p.postWoner mem join mem.userCredential uc join p.catagory c join p.model m  where p.postStatus=:postStatus and c.id=:catagoryId and m.id=:modelId and SQRT(POW(69.1 * (p.areaLatitude - :latitude), 2) + POW(69.1 * (:longitude - p.areaLongitude) * COS(p.areaLatitude / 57.3), 2))<=5 ")
    public List<String> findUsersMessengerIdByFilteringFoundPosts(@Param("catagoryId") Integer catagoryId, @Param("modelId") Integer modelId , @Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("postStatus") Integer postStatus);

    @Query("Select p.id from Post p join p.postWoner mem join mem.userCredential uc join p.catagory c join p.model m  where p.postStatus=1 and c.id=:catagoryId and m.id=:modelId and p.postDescription =:description ")
    public List<Integer> findPostIdsByFilteringLostPosts(@Param("catagoryId") Integer catagoryId, @Param("modelId") Integer modelId, @Param("description") String description);

    @Query("Select uc.userMessengerId from Post p join p.postWoner mem join mem.userCredential uc where p.id=:postId")
    public String findUserOfSpesificPost(@Param("postId") Integer postId);

}