package com.example.entity;

import com.tlitiwwhtmi.annotation.Exclude;
import com.tlitiwwhtmi.annotation.PrimayKey;

/**
 * Created by saver on 16/5/31.
 */
public class User {

    @PrimayKey
    private String userId;

    private String userName;

    private String sex;

    private int age;

    @Exclude
    private String test;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
