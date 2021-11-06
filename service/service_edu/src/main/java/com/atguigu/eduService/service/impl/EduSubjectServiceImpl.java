package com.atguigu.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduService.entity.EduSubject;
import com.atguigu.eduService.entity.excel.SubjectData;
import com.atguigu.eduService.entity.subject.OneSubject;
import com.atguigu.eduService.entity.subject.TwoSubject;
import com.atguigu.eduService.listence.SubjectExcelListener;
import com.atguigu.eduService.mapper.EduSubjectMapper;
import com.atguigu.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-30
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        List<OneSubject> list = new ArrayList<>();
        //查询一级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", '0');
        List<EduSubject> oneList = baseMapper.selectList(wrapper1);
        //查询二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", '0');
        List<EduSubject> twoList = baseMapper.selectList(wrapper2);
        //封装一级分类
        for (EduSubject oneSub : oneList) {
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(oneSub, oneSubject);
            ArrayList<TwoSubject> twoSubjects = new ArrayList<>();
            for (EduSubject twoSub : twoList) {
                if(oneSubject.getId().equals(twoSub.getParentId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoSub, twoSubject);
                    twoSubjects.add(twoSubject);
                }
            }
            oneSubject.setList(twoSubjects);
            list.add(oneSubject);
        }
        return list;
    }

    private EduSubject buildList(EduSubject eduSubject, List<EduSubject> allList) {
        eduSubject.setChildren(new ArrayList<>());
        for (EduSubject subject : allList) {
            if(subject.getParentId().equals(eduSubject.getId())){
                eduSubject.getChildren().add(buildList(subject, allList));
            }
        }
        return eduSubject;
    }

    public List<EduSubject> getAll(){
        //递归封装
        //查询所有数据
        List<EduSubject> allList = baseMapper.selectList(null);
        //查询所有一级数据  递归入口
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",0);
        ArrayList<EduSubject> list = new ArrayList<>();
        List<EduSubject> oneList = baseMapper.selectList(wrapper);
        for (EduSubject eduSubject : oneList) {
            list.add(buildList(eduSubject,allList));
        }
        return list;
    }



}
