package com.jingdianjichi.subject.domain.service;

import com.jingdianjichi.subject.common.entity.PageResult;
import com.jingdianjichi.subject.domain.entity.SubjectInfoBO;

public interface SubjectInfoDomainService {
    void add(SubjectInfoBO subjectInfoBO);
    
    PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO);
    
    SubjectInfoBO querySubjectInfo(Long id);
}
