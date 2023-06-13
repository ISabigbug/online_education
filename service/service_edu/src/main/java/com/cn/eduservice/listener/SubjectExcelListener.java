package com.cn.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cn.eduservice.domain.EduSubject;
import com.cn.eduservice.domain.excel.SubjectData;
import com.cn.eduservice.service.EduSubjectService;
import com.cn.servicebase.exception.MyException;


/**
 * @Project: online_education
 * @Package: com.cn.eduservice.listener
 * @Description:
 */

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService eduSubjectService;

    SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new MyException(20001,"文件数据为空");
        }

        //添加一级分类
        EduSubject oneEduSubject = existOneSubject(subjectData.getOneSubjectName());
        if (oneEduSubject == null) {
            oneEduSubject= new EduSubject();
            oneEduSubject.setParentId("0");
            oneEduSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(oneEduSubject);
        }

        //添加二级分类
        String pid = oneEduSubject.getId();
        EduSubject twoEduSubject = existTwoSubject(subjectData.getTwoSubjectName(),pid);
        if (twoEduSubject == null) {
            twoEduSubject= new EduSubject();
            twoEduSubject.setParentId(pid);
            twoEduSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(twoEduSubject);
        }
    }
    //判断一级分类，不能重复添加
    private EduSubject existOneSubject(String name) {
        LambdaQueryWrapper<EduSubject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduSubject::getTitle,name).eq(EduSubject::getParentId,0);
        EduSubject oneEduSubject = eduSubjectService.getOne(lambdaQueryWrapper);
        return oneEduSubject;
    }

    //判断二级分类，不能重复添加
    private EduSubject existTwoSubject(String name,String pid) {
        LambdaQueryWrapper<EduSubject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(EduSubject::getTitle,name).eq(EduSubject::getParentId,pid);
        EduSubject twoEduSubject = eduSubjectService.getOne(lambdaQueryWrapper);
        return twoEduSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
