package com.memo.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 회원가입 화면
     */
    @GetMapping("/sign-up-view")
    public String signupView(){
        return "user/signUp";
    }

    /**
     * 로그인 화면
     */
    @GetMapping("/sign-in-view")
    public String signInView(){
        return "user/signIn";
    }

    @GetMapping("/sign-out")
    public String signOut(HttpSession session){
        session.removeAttribute("userId");
        session.removeAttribute("userLoginId");
        session.removeAttribute("userName");

        return "redirect:/user/sign-in-view";
    }
}
