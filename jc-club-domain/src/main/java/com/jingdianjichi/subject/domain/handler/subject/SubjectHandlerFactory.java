package com.jingdianjichi.subject.domain.handler.subject;

import com.jingdianjichi.subject.common.enums.SubjectInfoTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SubjectHandlerFactory implements InitializingBean {
    
    @Resource
    List<SubjectHandler> subjectHandlers;
    
    private Map<SubjectInfoTypeEnum, SubjectHandler> subjectHandlerMap = new HashMap<>();
    
    
    @Override
    public void afterPropertiesSet() throws Exception {
        subjectHandlers.forEach(subjectHandler ->
                subjectHandlerMap.put(subjectHandler.getHandlerType(), subjectHandler));
    }
    
    public SubjectHandler getSubjectHandler(Integer subjectTypeId) {
        SubjectInfoTypeEnum subjectInfoTypeEnum = SubjectInfoTypeEnum.getByCode(subjectTypeId);
        return subjectHandlerMap.get(subjectInfoTypeEnum);
    }
}
