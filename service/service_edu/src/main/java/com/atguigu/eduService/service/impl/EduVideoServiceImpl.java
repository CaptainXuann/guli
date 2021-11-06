package com.atguigu.eduService.service.impl;

import com.atguigu.eduService.client.VodClient;
import com.atguigu.eduService.entity.EduChapter;
import com.atguigu.eduService.entity.EduVideo;
import com.atguigu.eduService.mapper.EduVideoMapper;
import com.atguigu.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-31
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String id) {
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",id);
        List<EduVideo> eduVideos = baseMapper.selectList(videoWrapper);
        List<String> ids=new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
                ids.add(eduVideo.getVideoSourceId());
            }
        }
        if(!ids.isEmpty()){
            vodClient.removeBatch(ids);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
