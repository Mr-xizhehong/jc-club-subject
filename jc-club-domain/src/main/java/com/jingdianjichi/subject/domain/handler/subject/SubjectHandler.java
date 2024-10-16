package com.jingdianjichi.subject.domain.handler.subject;

import com.jingdianjichi.subject.common.enums.SubjectInfoTypeEnum;
import com.jingdianjichi.subject.domain.entity.SubjectInfoBO;
import com.jingdianjichi.subject.domain.entity.SubjectOptionBO;

public interface SubjectHandler {
    
    /**
     * 获取处理器类型
     * @return
     */
    SubjectInfoTypeEnum getHandlerType();
    
    /**
     * 添加题目
     * @param subjectInfoBO
     */
    void add(SubjectInfoBO subjectInfoBO);
    
    SubjectOptionBO querySubjectInfo(Long subjectInfo);
}
