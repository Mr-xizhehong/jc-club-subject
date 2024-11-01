package com.jingdianjichi.subject.domain.service;

import com.jingdianjichi.subject.domain.entity.SubjectCategoryBo;
import com.jingdianjichi.subject.infra.basic.entity.SubjectCategory;

import java.util.List;

public interface SubjectCategoryDomainService {
    void addSubjectCategory(SubjectCategoryBo subjectCategorybo);

    Boolean update(SubjectCategoryBo subjectCategoryBO);
    
    List<SubjectCategoryBo> queryCategory(SubjectCategoryBo subjectCategoryBO);
    
    Boolean delete(SubjectCategoryBo subjectCategoryBO);
    
    List<SubjectCategoryBo> queryCategoryAndLabel(SubjectCategoryBo subjectCategoryBo);
}
