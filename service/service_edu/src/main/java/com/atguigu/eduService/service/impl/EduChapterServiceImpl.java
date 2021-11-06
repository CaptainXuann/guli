package com.atguigu.eduService.service.impl;

import com.atguigu.eduService.entity.EduChapter;
import com.atguigu.eduService.entity.EduVideo;
import com.atguigu.eduService.entity.chapter.ChapterVo;
import com.atguigu.eduService.entity.chapter.VideoVo;
import com.atguigu.eduService.mapper.EduChapterMapper;
import com.atguigu.eduService.mapper.EduVideoMapper;
import com.atguigu.eduService.service.EduChapterService;
import com.atguigu.eduService.service.EduVideoService;
import com.atguigu.servicebase.config.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-31
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //查询章节
        QueryWrapper<EduChapter> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(wrapper1);
        //查询小结
        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id", courseId);
        List<EduVideo> eduVideos = eduVideoService.list(wrapper2);
        //封装
        ArrayList<ChapterVo> list = new ArrayList<>();
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
            list.add(chapterVo);
        }
        return list;
    }


    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = eduVideoService.count(wrapper);
        if(count>0){
            throw new GuliException(20001, "不能删除");
        }else {
            int delete = baseMapper.deleteById(chapterId);
            return delete>0;
        }
    }

    @Override
    public void removeChapterByCourseId(String id) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
