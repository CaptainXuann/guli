package com.atguigu.eduService.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.EduTeacher;
import com.atguigu.eduService.service.EduCourseService;
import com.atguigu.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * @author
 */
@RestController
@RequestMapping("/eduService/teacherFront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //分页查询讲师
    @GetMapping("/getTeacherFrontList/{current}/{size}")
    public R getTeacherFrontList(@PathVariable("current") Long current,
                                 @PathVariable("size") Long size){
        Page<EduTeacher> page = new Page<>(current, size);
        Page<EduTeacher> teacherPageList = teacherService.page(page);
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", teacherPageList.getRecords());
        map.put("total", teacherPageList.getTotal());
        map.put("hasPrevious",teacherPageList.hasPrevious());
        map.put("hasNext",teacherPageList.hasNext());
        map.put("pages",teacherPageList.getPages());
        map.put("current", teacherPageList.getCurrent());
        map.put("size", teacherPageList.getSize());
        return R.ok().data(map);
    }

    //根据id查询讲师信息（讲师本身信息+讲师所讲课程信息）
    @GetMapping("/getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable("id") Long id){
        EduTeacher teacher = teacherService.getById(id);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList  = courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}

