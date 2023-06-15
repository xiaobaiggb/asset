package com.javaee.lqsx.asset.po;


import lombok.Data;

/**
 * 实体类
 */
@Data
public class User{

    private String id;
    private String no;
    private String image;//新增
    private String name;
    private String sex;
    private String age;
    private String password;
    private String dept;
    private String position;
    private String address;
    private String phone;
    private String situation;
    private String createTime;

}
