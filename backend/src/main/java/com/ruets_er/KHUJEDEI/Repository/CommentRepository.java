/*
 * Copyright (c) 2022.
 * Document   : CommentRepository.java
 * Created on : 7/9/22, 10:57 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.Repository;

import com.ruets_er.KHUJEDEI.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query ("Select cm.id,mem.id,mem.memberName,cm.commentDescription from Comment cm join cm.commentWoner mem join cm.commentPost p where cm.commentStatus=1 and p.id=:postId order by cm.id desc")
    public List<Object[]> findCommentByPostId(@Param("postId")Integer postId);

    @Modifying
    @Query(value="delete from comments where comment_post_id = :postId ", nativeQuery=true)
    public void deleteCommentByPostId(@Param("postId")Integer postId);
}