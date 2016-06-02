package com.example.service;

import com.example.entity.User;
import com.tlitiwwhtmi.filter.SaverFilter;
import com.tlitiwwhtmi.saver.BaseSaver;
import com.tlitiwwhtmi.saver.fileSaver.BaseFileSaver;
import com.tlitiwwhtmi.saver.fileSaver.parser.Parser;
import com.tlitiwwhtmi.saver.fileSaver.parser.StringParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by saver on 16/5/31.
 */

@Service
public class UserService {

    @Autowired
    private BaseSaver userFileSaver;

    public boolean testSaverInsert(){
        User user = new User();
        user.setUserName("saver");
        user.setAge(12);
        user.setSex("boy");
        user.setTest("example");

        return userFileSaver.insert(user);
    }

    public List testSaverList(){
        return userFileSaver.list();
    }

    public boolean testSaverDelete(User user){
        return userFileSaver.delete(user);
    }

    public User testGetById(String id){
        return (User) userFileSaver.getById(id);
    }

    public boolean testUpdateUser(User user){
        return userFileSaver.update(user);
    }

    public List testQuery(SaverFilter filter){
        return userFileSaver.query(filter);
    }

}
