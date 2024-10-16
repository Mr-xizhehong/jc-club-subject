package com.jingdianjichi.subject.infra.basic.service;

import com.jingdianjichi.subject.infra.basic.entity.SubjectMultiple;

import java.util.List;

public interface MultipleSubjectService {
    SubjectMultiple insert(SubjectMultiple subjectMultiple);
    
    void batchInsert(List<SubjectMultiple> subjectMultipleList);
    
    List<SubjectMultiple> queryByCondition(SubjectMultiple subjectMultiple);
}
