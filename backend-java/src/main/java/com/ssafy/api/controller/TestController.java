package com.ssafy.api.controller;

import com.ssafy.api.service.UserServiceImpl;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class TestController {
    @Autowired
    UserServiceImpl userService;
    @PostMapping("/test")
    public String getMsg(HttpServletRequest req, HttpServletResponse res, HttpSession session){
        Long no=Long.parseLong(req.getParameter("id"));
        System.out.println(no);
        Optional<User> temp = userService.findByUserNo(no);
        User user= temp.get();
        System.out.println(user.toString());
        System.out.println(user.getUserId());
        System.out.println(user.getUserPhoneNo());
        System.out.println(user.getUserNickname());
        System.out.println(user.getUserComment());

        return "ok";
    }
}
