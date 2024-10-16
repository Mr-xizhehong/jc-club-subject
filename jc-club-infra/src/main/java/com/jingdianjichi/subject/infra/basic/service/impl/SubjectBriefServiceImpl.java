package com.jingdianjichi.subject.infra.basic.service.impl;

import com.jingdianjichi.subject.infra.basic.entity.SubjectBrief;
import com.jingdianjichi.subject.infra.basic.mapper.SubjectBriefDao;
import com.jingdianjichi.subject.infra.basic.service.BriefSubjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 简答题(SubjectBrief)表服务实现类
 *
 * @author makejava
 * @since 2023-10-05 21:29:22
 */
@Service("subjectBriefService")
public class SubjectBriefServiceImpl implements BriefSubjectService {
    
    @Resource
    private SubjectBriefDao subjectBriefDao;
    
    @Override
    public SubjectBrief insert(SubjectBrief subjectBrief) {
        this.subjectBriefDao.insert(subjectBrief);
        return subjectBrief;
    }
    
    @Override
    public SubjectBrief queryByCondition(SubjectBrief subjectBrief) {
        return subjectBriefDao.queryAllByLimit(subjectBrief);
    }
    
    
}
