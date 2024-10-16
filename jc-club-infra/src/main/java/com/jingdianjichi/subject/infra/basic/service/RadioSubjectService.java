package com.jingdianjichi.subject.infra.basic.service;

import com.jingdianjichi.subject.infra.basic.entity.SubjectRadio;

import java.util.List;

public interface RadioSubjectService {
    SubjectRadio insert(SubjectRadio subjectRadio);
    
    void insertBatch(List<SubjectRadio> subjectRadioList);
    
    List<SubjectRadio> querySubjectInfo(SubjectRadio subjectRadio);
}
