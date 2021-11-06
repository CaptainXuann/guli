package com.atguigu.eduService.service;

import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.frontVo.CourseFrontVo;
import com.atguigu.eduService.entity.frontVo.CourseWebVo;
import com.atguigu.eduService.entity.vo.CourseInfoVo;
import com.atguigu.eduService.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-31
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo infoVo);

    CourseInfoVo getCourseInfo(String id);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    boolean removeCourse(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
