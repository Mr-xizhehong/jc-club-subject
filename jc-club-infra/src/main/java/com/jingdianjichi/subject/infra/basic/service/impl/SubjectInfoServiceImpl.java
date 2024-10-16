package com.jingdianjichi.subject.infra.basic.service.impl;

import com.jingdianjichi.subject.infra.basic.entity.SubjectInfo;
import com.jingdianjichi.subject.infra.basic.mapper.SubjectInfoDao;
import com.jingdianjichi.subject.infra.basic.service.SubjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Slf4j
//@Service("subjectInfoService")
@Service
public class SubjectInfoServiceImpl implements SubjectInfoService {
    
    @Resource
    private SubjectInfoDao subjectInfoDao;
    
    @Override
    public void insert(SubjectInfo subjectInfo) {
        subjectInfoDao.insert(subjectInfo);
    }
    
    @Override
    public int countByCondition(SubjectInfo subjectInfo, Long categoryId, Long labelId) {
        return subjectInfoDao.countByCondition(subjectInfo, categoryId, labelId);
    }
    
    @Override
    public List<SubjectInfo> queryPage(SubjectInfo subjectInfo, Long categoryId, Long labelId, int start, Integer pageSize) {
        return subjectInfoDao.queryPage(subjectInfo, categoryId, labelId, start, pageSize);
    }
    
    @Override
    public SubjectInfo queryById(Long id) {
        return subjectInfoDao.queryById(id);
        
    }
}
