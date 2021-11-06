package com.atguigu.eduService.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduService.client.UcenterClient;
import com.atguigu.eduService.entity.EduComment;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.service.EduCommentService;
import com.atguigu.eduService.service.EduCourseService;
import com.atguigu.servicebase.config.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-04
 */
@RestController
@RequestMapping("/eduService/edu-comment")
public class EduCommentController {
    @Autowired
    private EduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduCourseService courseService;
    //评论添加数据
    @PostMapping("/insertComment/{courseId}")
    public R insertComment(@RequestBody EduComment eduComment,
//                           HttpServletRequest request,
                           @PathVariable("courseId") Long id){
        eduComment.setCourseId(String.valueOf(id));
        //根据课程id查询讲师id
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        EduCourse course = courseService.getOne(wrapper);
        eduComment.setTeacherId(course.getTeacherId());
//        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        String memberId=eduComment.getMemberId();
        if(StringUtils.isEmpty(memberId)){
            throw new GuliException(20001, "请先登录");
        }
        eduComment.setMemberId(memberId);
        R r = ucenterClient.getMemberInfoById(memberId);
        Map<String, Object> data = r.getData();
        eduComment.setNickname(String.valueOf(data.get("nickname")));
        eduComment.setAvatar(String.valueOf(data.get("avatar")));
        commentService.save(eduComment);
        return R.ok();
    }


}

