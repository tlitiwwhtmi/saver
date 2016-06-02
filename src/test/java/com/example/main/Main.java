package com.example.main;

import com.example.entity.User;
import com.example.service.UserService;
import com.tlitiwwhtmi.filter.SaverFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main {

    @Autowired
    private UserService userService;


    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("ApplicationContext.xml");

        Main p = context.getBean(Main.class);
        p.start();
    }

    private void start() {
        /*System.out.println(userService.testSaverInsert());
        List<User> users = (List<User>) userService.testSaverList();
        System.out.println(userService.testSaverDelete(users.get(0)));
        User user = userService.testGetById("0e430419ef834c829bae71f87a8b85c1");
        user.setUserName("liuyonglang");
        System.out.println(userService.testUpdateUser(user));*/

        SaverFilter filter = new SaverFilter();
        filter.equal("userName","saver");
        filter.notEqual("userId","bea6e4a0522a4482ad2c359ab4ef2688");
        List<User> users = (List<User>) userService.testQuery(filter);
        for(User user : users){
            System.out.println(user.getUserId());
        }


    }
}

