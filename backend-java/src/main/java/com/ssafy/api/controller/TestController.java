package com.ssafy.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {
    @PostMapping("/test")
    public String getMsg(HttpServletRequest req, HttpServletResponse res, HttpSession session){
        System.out.println(req.getPathInfo());
        System.out.println(req.getParameter("title"));
        return "ok";
    }
}
