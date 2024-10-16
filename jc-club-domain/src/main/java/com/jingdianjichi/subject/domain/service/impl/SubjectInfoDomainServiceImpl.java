package com.jingdianjichi.subject.domain.service.impl;

import com.jingdianjichi.subject.common.entity.PageResult;
import com.jingdianjichi.subject.common.enums.IsDeletedFlagEnum;
import com.jingdianjichi.subject.domain.convert.SubjectInfoConverter;
import com.jingdianjichi.subject.domain.entity.SubjectInfoBO;
import com.jingdianjichi.subject.domain.entity.SubjectOptionBO;
import com.jingdianjichi.subject.domain.handler.subject.SubjectHandler;
import com.jingdianjichi.subject.domain.handler.subject.SubjectHandlerFactory;
import com.jingdianjichi.subject.domain.service.SubjectInfoDomainService;
import com.jingdianjichi.subject.infra.basic.entity.SubjectInfo;
import com.jingdianjichi.subject.infra.basic.entity.SubjectLabel;
import com.jingdianjichi.subject.infra.basic.entity.SubjectMapping;
import com.jingdianjichi.subject.infra.basic.service.SubjectInfoService;
import com.jingdianjichi.subject.infra.basic.service.SubjectLabelService;
import com.jingdianjichi.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {
    
    @Resource
    private SubjectInfoService subjectInfoService;
    
    @Resource
    private SubjectHandlerFactory subjectHandlerFactory;
    
    @Resource
    private SubjectMappingService subjectMappingService;
    
    @Resource
    private SubjectLabelService subjectLabelService;
    
    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()){
            log.info("SubjectInfoDomainServiceImpl add subjectInfoBO:{}",subjectInfoBO);
        }
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.SubjectBOToSubjectInfo(subjectInfoBO);
        subjectInfoBO.setId(subjectInfo.getId());
        SubjectHandler subjectHandler = subjectHandlerFactory.getSubjectHandler(subjectInfo.getSubjectType());
        subjectHandler.add(subjectInfoBO);
        List<Integer> categoryIds = subjectInfoBO.getCategoryIds();
        List<Integer> labelIds = subjectInfoBO.getLabelIds();
        List<SubjectMapping> mappingList = new LinkedList<>();
        categoryIds.forEach(categoryId->{
            labelIds.forEach(labelId->{
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfo.getId());
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setLabelId(Long.valueOf(labelId));
                mappingList.add(subjectMapping);
            });
        });
        subjectMappingService.batchInsert(mappingList);
        
        
    }
    
    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()){
            log.info("SubjectInfoDomainServiceImpl getSubjectPage subjectInfoBO:{}",subjectInfoBO);
        }
        PageResult<SubjectInfoBO> pageResult = new PageResult<>();
        pageResult.setPageNo(subjectInfoBO.getPageNo());
        pageResult.setPageSize(subjectInfoBO.getPageSize());
        int start = (subjectInfoBO.getPageNo() - 1) * subjectInfoBO.getPageSize();
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.SubjectBOToSubjectInfo(subjectInfoBO);
        int count = subjectInfoService.countByCondition(subjectInfo, subjectInfoBO.getCategoryId()
                , subjectInfoBO.getLabelId());
        if (count == 0){    //查询结果数量等于0直接返回
            return pageResult;
        }
        List<SubjectInfo> subjectInfoList = subjectInfoService.queryPage(subjectInfo, subjectInfoBO.getCategoryId()
                , subjectInfoBO.getLabelId(), start, subjectInfoBO.getPageSize());
        List<SubjectInfoBO> subjectInfoBOS = SubjectInfoConverter.INSTANCE.SubjectInfoToSubjectBO(subjectInfoList);
        pageResult.setRecords(subjectInfoBOS);
        pageResult.setTotal(count);
        return pageResult;
    }
    
    @Override
    public SubjectInfoBO querySubjectInfo(Long id) {
        SubjectInfo subjectInfo = subjectInfoService.queryById(id);
        SubjectHandler subjectHandler = subjectHandlerFactory.getSubjectHandler(subjectInfo.getSubjectType());
        SubjectOptionBO subjectOptionBO = subjectHandler.querySubjectInfo(id);
        SubjectInfoBO subjectInfoBO = SubjectInfoConverter.INSTANCE.convertOptionAndInfoToBo(subjectOptionBO, subjectInfo);
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectInfo.getId());
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);
        List<String> labelNameList = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
        subjectInfoBO.setLabelName(labelNameList);
        return subjectInfoBO;
    }
}
