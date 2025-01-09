package com.memo.user;

import com.memo.user.entity.UserEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserRestController {

    @GetMapping("/is_duplicate-id")
    public Map<String, Object> isDuplicateId(
            @RequestParam("loginId") String loginId
    ){
        // UserEntity user = userBO.getUserEntityByLoginId(LoginId);
//        boolean isDuplicate = false;
//        if(user != null){
//            isDuplicate = true;
//        }

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("is_duplicate_id", true);
        return result;
    }
}
