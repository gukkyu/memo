package com.memo.user;

import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserRestController {

    @Autowired
    private UserBO userBO;

    @GetMapping("/is-duplicate-id")
    public Map<String, Object> isDuplicateId(
            @RequestParam("loginId") String loginId
    ){
        UserEntity user = userBO.getUserEntityByLoginId(loginId);
        boolean isDuplicate = false;
        if(user != null){
            isDuplicate = true;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("is_duplicate_id", isDuplicate);
        return result;
    }

    /**
     * 회원가입 API
     * @param loginId
     * @param password
     * @param name
     * @param email
     * @return
     */
    @PostMapping("/sign-up")
    public Map<String, Object> signUp(
            @RequestParam("userId") String loginId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ){

        boolean isSuccess = userBO.addUser(loginId, password, name, email);

        Map<String, Object> result = new HashMap<>();
        if(isSuccess){
            result.put("code", 200);
            result.put("result", "성공");
        } else{
            result.put("code", 200);
            result.put("result", "성공");
        }
        return result;
    }

    @PostMapping("/sign-in")
    public Map<String, Object> signIn(
            @RequestParam("id") String loginId,
            @RequestParam("password") String password,
            HttpServletRequest request
    ){
        // db select
        UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, password);

        // recall
        Map<String, Object> result = new HashMap<>();
        if(user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userLoginId", user.getLoginId());
            session.setAttribute("userName", user.getName());

            result.put("code", 200);
            result.put("result", "성공");
        } else{
            result.put("code", 300);
            result.put("error_message", "존재하지 않는 사용자입니다.");
        }
        return result;
    }
}
