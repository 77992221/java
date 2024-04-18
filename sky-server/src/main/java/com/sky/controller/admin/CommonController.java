package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Api("通用接口")
@Slf4j
public class CommonController {
    /**
     * 文件上传
     * @param file
     * @return
     */
//    @PostMapping("/upload")
//    public Result<String> upload(MultipartFile file) throws IOException {
//        log.info("文件路径上传：{}",file);
//
//        String originalFilename = file.getOriginalFilename();
//        String newFileName = originalFilename.substring(originalFilename.lastIndexOf("."));
//        file.transferTo(new File("D:/ware/"+newFileName));
//        String filePath = "D:\\ware\\" +newFileName;
//        return Result.success(filePath);
//    }
    @PostMapping("upload")
    public Result upload(MultipartFile file) throws Exception {

        String originalFilename = file.getOriginalFilename();
        String s = UUID.randomUUID().toString();
        String fileName = s + originalFilename;
        //将文件存储在服务器的磁盘目录
        file.transferTo(new File("D:/ware/" + fileName));

        String url = "http://localhost:8080/UserProfilePicture/" + fileName;
        System.out.println("地址为"+url);
        return Result.success(url);
    }

}
