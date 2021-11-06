package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.entity.EduTeacher;
import com.atguigu.eduService.entity.vo.EduTeacherQuery;
import com.atguigu.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-26
 */
@Slf4j
@RestController
@RequestMapping("/eduService/edu-teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询讲师表所有数据
    @GetMapping("/findAll")
    public R findAll(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //讲师逻辑删除功能
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable String id){
        boolean isSuccess = eduTeacherService.removeById(id);
        if(isSuccess){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //讲师查询分页功能
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageTeacher(@PathVariable("current") Long current,
                         @PathVariable("size") Long size){
        Page<EduTeacher> page = new Page<>(current,size);

        List<EduTeacher> eduTeacherList = eduTeacherService.page(page).getRecords();
        Long total=eduTeacherService.page(page).getTotal();
        return R.ok().data("total",total).data("rows", eduTeacherList);
    }

    //讲师多条件查询
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable("current") Long current,
                                  @PathVariable("size") Long size,
                                  @RequestBody(required = false) EduTeacherQuery teacherQuery){
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(teacherQuery.getName())){
            wrapper.like("name", teacherQuery.getName());
        }
        if(!StringUtils.isEmpty(teacherQuery.getLevel())){
            wrapper.eq("level", teacherQuery.getLevel());
        }
        if(!StringUtils.isEmpty(teacherQuery.getBegin())){
            wrapper.ge("gmt_create", teacherQuery.getBegin());
        }
        if(!StringUtils.isEmpty(teacherQuery.getEnd())){
            wrapper.le("gmt_modified", teacherQuery.getEnd());
        }
        wrapper.orderByDesc("gmt_create");
        Page<EduTeacher> page = eduTeacherService.page(new Page<EduTeacher>(current, size), wrapper);
        return R.ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    //添加讲师
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.save(teacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id进行查询
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        EduTeacher teacher = eduTeacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    //讲师修改
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.updateById(teacher);
        return flag?R.ok():R.error();
    }


}

