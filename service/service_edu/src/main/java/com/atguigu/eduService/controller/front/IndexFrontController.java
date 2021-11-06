package com.atguigu.eduService.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.EduTeacher;
import com.atguigu.eduService.service.EduCourseService;
import com.atguigu.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author
 */
@RestController
@RequestMapping("/eduService/indexFront")
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询前8条热门课程,前4个名师
    @GetMapping("/index")
    public R index(){
        //课程
        QueryWrapper<EduCourse> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id").last("limit 8");
        List<EduCourse> courseList = eduCourseService.list(wrapper1);
        //名师
        QueryWrapper<EduTeacher> wrapper2 = new QueryWrapper<>();
        wrapper2.orderByDesc("id").last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(wrapper2);
        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }
}
