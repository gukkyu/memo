package com.memo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileManagerService {
//    public final static String FILE_UPLOAD_PATH = "D:\\aiden\\6_spring_project\\m_images/";
    public final static String FILE_UPLOAD_PATH = "C:\\Users\\comet\\Desktop\\aiden\\images_memo/";

    public String uploadFile(MultipartFile file, int userId){
        // 폴더 (directory) - ex) aaaa_15559324/sun.png
        String directoryName = userId + "_" + System.currentTimeMillis();
        String filePath = FILE_UPLOAD_PATH + directoryName + "/";

        // 폴더 생성
        File directory = new File(filePath);
        if(directory.mkdir() == false){
            return null;
        }

        try {
            byte[] bytes = file.getBytes();
            // 파일명을 영문자로 변경할 것.
            Path path = Paths.get(filePath + file.getOriginalFilename());
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace(); // 에러 메세지 출력
            return null; // upload fail 시 null return
        }

        // directory path return
        // 주소가 이렇게 될것이다~ (예언)
        // ex) /images/1_2355532/sun.jpg
        return "/images/" + directoryName + "/" + file.getOriginalFilename();
    }

    public void deleteFile(String imagePath){
        Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));

        // 삭제할 이미지가 존재하는가?
        if(Files.exists(path)){
            // 이미지 삭제
            try {
                Files.delete(path);
            } catch (IOException e) {
                log.info("[### 파일 매니저 이미지 삭제] imagePath:{}", imagePath);
                return;
            }

            // 디렉토리 삭제
            path = path.getParent();
            if(Files.exists(path)){
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    log.info("[### 파일 매니저 디렉토리 삭제] imagePath:{}", imagePath);
                }
            }
        }
    }
}
