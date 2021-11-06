package com.atguigu.eduService.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduService.client.VodClient;
import com.atguigu.eduService.entity.EduVideo;
import com.atguigu.eduService.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-31
 */
@RestController
@RequestMapping("/eduService/edu-video")
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //根据章节id添加小节
    @PostMapping("/addVideo")
    public R addVideoBy(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //查询云端视频id
        EduVideo eduVideo = eduVideoService.getById(id);
        if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
            vodClient.removeAliyunVideoById(eduVideo.getVideoSourceId());
        }
        eduVideoService.removeById(id);
        return R.ok();
    }

    //根据id进行查询
    @GetMapping("/getVideo/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

