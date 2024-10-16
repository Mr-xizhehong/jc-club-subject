package com.jingdianjichi.subject.infra.basic.service;

import com.jingdianjichi.subject.infra.basic.entity.SubjectInfo;

import java.util.List;

public interface SubjectInfoService {
    void insert(SubjectInfo subjectInfo);
    
    int countByCondition(SubjectInfo subjectInfo, Long categoryId, Long labelId);
    
    List<SubjectInfo> queryPage(SubjectInfo subjectInfo, Long categoryId, Long labelId, int start, Integer pageSize);
    
    SubjectInfo queryById(Long id);
}
