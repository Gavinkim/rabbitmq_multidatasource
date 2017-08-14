package com.example.queuedemo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by gs on 2017. 8. 11..
 *
 ======mysql=====================
 create table Member(
    id bigint auto_increment
    primary key,
    name varchar(100) not null,
    age int null,
    constraint Member_id_uindex unique (id));

 =========postgresql=============
 create sequence member_id;
 create table Member(
    id bigint not null default nextval('member_id') primary key,
    name varchar(100) not null,
    age int not null);
 */
@Data
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
}
