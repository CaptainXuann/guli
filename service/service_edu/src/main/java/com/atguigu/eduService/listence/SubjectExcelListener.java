package com.atguigu.eduService.listence;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduService.entity.EduSubject;
import com.atguigu.eduService.entity.excel.SubjectData;
import com.atguigu.eduService.service.EduSubjectService;
import com.atguigu.servicebase.config.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener態交给spring进行ioc管理，需要自己手动new，不能注入其他对象
    //不能实现数据库操作

    public EduSubjectService eduSubjectService;

    //有参，传递subjectService用于操作数据库
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    //无参
    public SubjectExcelListener() {
    }


    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuliException(20001, "文件数据为空");
        }

        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断是否有一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if(existOneSubject==null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }
        //判断是否有二级分类是否重复
        String pid=existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if(existTwoSubject==null){
            existTwoSubject=new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            eduSubjectService.save(existTwoSubject);
        }


    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name)
                .eq("parent_id","0");
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String parentId){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name)
                .eq("parent_id",parentId);
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        return twoSubject;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
