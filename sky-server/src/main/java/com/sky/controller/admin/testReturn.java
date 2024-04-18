package com.sky.controller.admin.common;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class testReturn {
    /**
     * 返回图片
     * @param fileName
     * @return
     */
    @GetMapping("/UserProfilePicture/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            // 替换为你的文件存储路径
            Path filePath = Paths.get("D:/ware/" + fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity
                        .ok()
                        .header("Content-Disposition", "attachment; filename=file." + "jpg")  //修改返回类型
                        .body(resource);
            } else {
                // 文件未找到
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // 文件读取出错
            return ResponseEntity.internalServerError().build();
        }
    }
}