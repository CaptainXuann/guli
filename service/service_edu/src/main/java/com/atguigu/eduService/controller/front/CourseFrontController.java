package com.atguigu.eduService.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.OrderVo.CourseWebVoOrder;
import com.atguigu.commonutils.R;
import com.atguigu.eduService.client.OrderClient;
import com.atguigu.eduService.entity.EduCourse;
import com.atguigu.eduService.entity.chapter.ChapterVo;
import com.atguigu.eduService.entity.frontVo.CourseFrontVo;
import com.atguigu.eduService.entity.frontVo.CourseWebVo;
import com.atguigu.eduService.service.EduChapterService;
import com.atguigu.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
@RestController
@RequestMapping("/eduService/courseFront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;
    //课程条件查询带分页
    @PostMapping("/getCourseFrontList/{current}/{size}")
    public R getCourseFrontList(@PathVariable("current") Long current,
                                @PathVariable("size") Long size,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> page = new Page<>(current,size);
        Map<String,Object> map = courseService.getCourseFrontList(page,courseFrontVo);
        return R.ok().data(map);
    }

    //课程详情的方法
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id，查询章节和小节信息
        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoByCourseId(courseId);
        //查询用户是否购买
        Boolean isBuy = false;
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(!StringUtils.isEmpty(memberId)){
            isBuy = orderClient.isBuyCourse(memberId, courseId);
        }

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList).data("isBuy", isBuy);
    }

    //根据课程id获取用户信息
    @GetMapping("/getInfoById/{courseId}")
    public CourseWebVoOrder getInfoById(@PathVariable("courseId") String id){
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        BeanUtils.copyProperties(courseInfo, courseWebVoOrder);
        return courseWebVoOrder;
    }
}
