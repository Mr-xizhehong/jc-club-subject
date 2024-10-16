package com.jingdianjichi.subject.infra.basic.service;

import com.jingdianjichi.subject.infra.basic.entity.SubjectBrief;

public interface BriefSubjectService {
    SubjectBrief insert(SubjectBrief subjectBrief);
    
    SubjectBrief queryByCondition(SubjectBrief subjectBrief);
}
