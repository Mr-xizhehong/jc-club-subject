package com.jingdianjichi.subject.infra.basic.service;

import com.jingdianjichi.subject.infra.basic.entity.SubjectJudge;

import java.util.List;

public interface JudgeSubjectService {
    SubjectJudge insert(SubjectJudge subjectJudge);
    
    List<SubjectJudge> queryByCondition(SubjectJudge subjectJudge);
}
