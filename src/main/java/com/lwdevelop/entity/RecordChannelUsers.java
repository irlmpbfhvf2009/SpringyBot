package com.lwdevelop.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.Data;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RecordChannelUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private String firstname;

    private String username;

    private String lastname;

    private Long channelId;

    private String channelTitle;

    private Boolean status;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date updateTime;

}