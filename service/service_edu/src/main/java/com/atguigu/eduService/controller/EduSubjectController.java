package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduSubject;
import com.atguigu.eduService.entity.subject.OneSubject;
import com.atguigu.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-30
 */
@RestController
@RequestMapping("/eduService/edu-subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容读取出来
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        //获取上传的excel文件 MultipartFile
        eduSubjectService.saveSubject(file,eduSubjectService);
        //判断返回集合是否为空

        return R.ok();
    }

    //课程分类列表(树形)
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list= eduSubjectService.getAllOneTwoSubject();
        System.out.println(list);
        return R.ok().data("list",list);
    }

    @GetMapping("/a")
    public R a(){
        List<EduSubject> list = eduSubjectService.getAll();
        return R.ok().data("list", list);
    }

}

