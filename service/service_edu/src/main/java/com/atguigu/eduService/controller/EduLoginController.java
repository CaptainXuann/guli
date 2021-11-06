package com.atguigu.eduService.controller;

import com.atguigu.commonutils.R;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 */
@RestController
@RequestMapping("/eduService/user")
public class EduLoginController {

    //login
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token", "admin");
    }


    //info
    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles", "admin")
                .data("name", "admin")
                .data("avatar", "http://img.desktx.com/d/file/wallpaper/scenery/20170303/dfe53a7300794009a029131a062836d5.jpg");
    }
}
