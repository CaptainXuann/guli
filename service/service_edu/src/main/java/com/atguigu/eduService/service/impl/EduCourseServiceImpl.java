package com.atguigu.eduService.service.impl;

import com.atguigu.eduService.client.VodClient;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.EduCourseDescription;
import com.atguigu.eduService.entity.EduVideo;
import com.atguigu.eduService.entity.frontVo.CourseFrontVo;
import com.atguigu.eduService.entity.frontVo.CourseWebVo;
import com.atguigu.eduService.entity.vo.CourseInfoVo;
import com.atguigu.eduService.entity.vo.CoursePublishVo;
import com.atguigu.eduService.mapper.EduCourseMapper;
import com.atguigu.eduService.service.EduChapterService;
import com.atguigu.eduService.service.EduCourseDescriptionService;
import com.atguigu.eduService.service.EduCourseService;
import com.atguigu.eduService.service.EduVideoService;
import com.atguigu.servicebase.config.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-31
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private VodClient vodClient;

    @Override
    public String saveCourseInfo(CourseInfoVo infoVo) {
        //向课程表添加课程的基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(infoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert<=0){
            throw new GuliException(20001, "添加课程失败");
        }
        //向课程简介表添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(infoVo.getDescription());
        courseDescription.setId(eduCourse.getId());
        courseDescriptionService.save(courseDescription);

        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(id);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        //查询简介表
        EduCourseDescription courseDescription = courseDescriptionService.getById(id);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update==0){
            throw new GuliException(20001, "修改失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, eduCourseDescription);
        courseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public boolean removeCourse(String id) {
        //1、根据课程id删除小节,删除视频
        eduVideoService.removeVideoByCourseId(id);

        //2、根据课程id删除章节部分
        eduChapterService.removeChapterByCourseId(id);

        //3、根据课程id删除课程描述
        courseDescriptionService.removeById(id);

        //4、根据课程id删除课程本身
        int result = baseMapper.deleteById(id);

        if (result>=1){
            return true;
        }else {
            throw new GuliException(20001,"删除失败");
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> page, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接条件
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){//二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){//关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){//价格
            wrapper.orderByDesc("price");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){//最新，创建时间
            wrapper.orderByDesc("gmt_create");
        }
        baseMapper.selectPage(page, wrapper);

        long total = page.getTotal();//总记录数
        List<EduCourse> courseList = page.getRecords();//数据集合
        long size = page.getSize();//每页记录数
        long current = page.getCurrent();//当前页
        long pages = page.getPages();//总页数
        boolean hasPrevious = page.hasPrevious();//是否有上一页
        boolean hasNext = page.hasNext();//是否有下一页

        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("list",courseList);
        map.put("size",size);
        map.put("current",current);
        map.put("pages",pages);
        map.put("hasPrevious",hasPrevious);
        map.put("hasNext",hasNext);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
