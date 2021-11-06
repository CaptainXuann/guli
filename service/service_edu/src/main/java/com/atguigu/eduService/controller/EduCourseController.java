package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.EduCourseDescription;
import com.atguigu.eduService.entity.EduSubject;
import com.atguigu.eduService.entity.EduTeacher;
import com.atguigu.eduService.entity.vo.CourseInfoVo;
import com.atguigu.eduService.entity.vo.CoursePublishVo;
import com.atguigu.eduService.entity.vo.CourseQuery;
import com.atguigu.eduService.service.EduCourseDescriptionService;
import com.atguigu.eduService.service.EduCourseService;
import com.atguigu.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-31
 */
@RestController
@RequestMapping("/eduService/edu-course")
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    //课程列表 基本实现
    @GetMapping("/getCourseList")
    public R getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }

    //多条件查询课程带分页
    @PostMapping("/getCourseList/{current}/{size}")
    public R pageCourseCondition(@PathVariable("current") Long current,
                                 @PathVariable("size") Long size,
                                 @RequestBody CourseQuery courseQuery){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(courseQuery.getTitle())){
            wrapper.like("title", courseQuery.getTitle());
        }
        if(!StringUtils.isEmpty(courseQuery.getStatus())){
            wrapper.eq("status", courseQuery.getStatus());
        }
        if(!StringUtils.isEmpty(courseQuery.getBegin())){
            wrapper.ge("gmt_create", courseQuery.getBegin());
        }
        if(!StringUtils.isEmpty(courseQuery.getEnd())){
            wrapper.le("gmt_modified", courseQuery.getEnd());
        }
        wrapper.orderByDesc("gmt_create");
        Page<EduCourse> page = eduCourseService.page(new Page<EduCourse>(current, size), wrapper);
        return R.ok().data("rows", page.getRecords()).data("total", page.getTotal());
    }

    //添加课程基本信息
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo infoVo){
        String id = eduCourseService.saveCourseInfo(infoVo);
        return R.ok().data("courseId", id);
    }

    //根据id查询课程信息
    @GetMapping("/getCourseInfo/{id}")
    public R getCourseInfoById(@PathVariable("id") String id){
        CourseInfoVo courseInfoForm = eduCourseService.getCourseInfo(id);
        return R.ok().data("courseInfoForm",courseInfoForm);
    }

    //根据返回对象修改信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo publishCourseInfo = eduCourseService.getPublishCourseInfo(id);
            return R.ok().data("publishCourse",publishCourseInfo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setStatus("Normal"); //设置课程发布状态
        eduCourse.setId(id);
        boolean flag = eduCourseService.updateById(eduCourse);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //课程列表中删除课程方法
    @DeleteMapping("/{id}")
    public R removeCourseById(@PathVariable String id){
        boolean flag = eduCourseService.removeCourse(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


}

