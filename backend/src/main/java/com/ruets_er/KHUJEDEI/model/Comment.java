/*
 * Copyright (c) 2022.
 * Document   : Comment.java
 * Created on : 7/9/22, 10:56 AM
 * Author     : NAHID RUET CSE'18
 */

package com.ruets_er.KHUJEDEI.model;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "comment_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_post_id", nullable = false)
    private Post commentPost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "comment_woner_id", nullable = false)
    private Member commentWoner;

    @Lob
    @Column(name = "comment_description", nullable = false)
    private String commentDescription;

    @Column(name = "comment_status", nullable = false)
    private Integer commentStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Post getCommentPost() {
        return commentPost;
    }

    public void setCommentPost(Post commentPost) {
        this.commentPost = commentPost;
    }

    public Member getCommentWoner() {
        return commentWoner;
    }

    public void setCommentWoner(Member commentWoner) {
        this.commentWoner = commentWoner;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

}