package com.memo.user.bo;

import com.memo.common.EncryptUtils;
import com.memo.user.entity.UserEntity;
import com.memo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBO {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserEntityByLoginId(String loginId){
        return userRepository.findByLoginId(loginId).orElse(null);
    }

    public UserEntity getUserEntityByLoginIdPassword(String loginId, String password){
        String hashedPassword = EncryptUtils.md5(password);

        return userRepository.findByLoginIdAndPassword(loginId, hashedPassword).orElse(null);
    }

    public boolean addUser(
            String loginId,
            String password,
            String name,
            String email
    ){
        // md5 알고리즘 - hashing
        String hashedPassword = EncryptUtils.md5(password);
        UserEntity user = userRepository.save(
                UserEntity.builder()
                        .loginId(loginId)
                        .password(hashedPassword)
                        .name(name)
                        .email(email)
                        .build()
        );

        // 실질적으로 오류 발생시 null 되지 않음. (try-catch 처리해야함)
        return user == null ? false : true;
    }
}
