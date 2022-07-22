package com.ssafy.api.controller;

import com.ssafy.api.service.UserServiceImpl;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Optional;

@RestController
public class TestController {
    @Autowired
    UserServiceImpl userService;
    @PostMapping("/test")
    public User getProfile(HttpServletRequest req, HttpServletResponse res, HttpSession session){
        Long no=Long.parseLong(req.getParameter("id"));
        System.out.println(no);
        Optional<User> temp = userService.findByUserNo(no);
        User user= temp.get();
        System.out.println(user.toString());
        System.out.println(user.getUserId());
        System.out.println(user.getUserPhoneNo());
        System.out.println(user.getUserNickname());
        System.out.println(user.getUserComment());
        return user;
    }
    @PostMapping("/update")
    public String updateProfile(HttpServletRequest req, HttpServletResponse res, HttpSession session){
        Long id=Long.parseLong(req.getParameter("id"));
        User user=new User();
        user.setUserNickname(req.getParameter("userNickname"));
        user.setUserId(req.getParameter("userEmail"));
        user.setUserComment(req.getParameter("userComment"));
        user.setUserDesc(req.getParameter("userDesc"));
        try {
            Long checkId = userService.update(id, user);
        }catch(SQLException e){
            e.printStackTrace();
        }

        if(id==checkId){
            return "SUCCESS";
        }else{
            return "FAIL";
        }
    }
}
