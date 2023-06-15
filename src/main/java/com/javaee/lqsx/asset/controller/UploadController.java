package com.javaee.lqsx.asset.controller;

import com.javaee.lqsx.asset.po.Result;
import com.javaee.lqsx.asset.util.AliOSSUtils;
import com.javaee.lqsx.asset.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description: 控制层
 */

@Slf4j
@Controller
@RequestMapping(value = "/user")
public class UploadController {

    @Autowired
    AliOSSUtils aliOS;

    @ResponseBody
    @RequestMapping(value="/upload")
    public Result Upload(@RequestParam(value = "file", required = false) MultipartFile image) throws IOException {
        log.info("文件上传，文件：{}",image);
        log.info("文件上传，文件名：{}",image.getOriginalFilename());
        String url = aliOS.upload(image);
        User user = new User();
        user.setImage(url);
        log.info("文件上传，url：{}",url);
        return Result.success(url);
    }
}
