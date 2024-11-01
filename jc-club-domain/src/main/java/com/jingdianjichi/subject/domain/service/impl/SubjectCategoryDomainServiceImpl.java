package com.jingdianjichi.subject.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.jingdianjichi.subject.common.enums.IsDeletedFlagEnum;
import com.jingdianjichi.subject.domain.convert.SubjectCategoryBOConverter;
import com.jingdianjichi.subject.domain.entity.SubjectCategoryBo;
import com.jingdianjichi.subject.domain.entity.SubjectLabelBO;
import com.jingdianjichi.subject.domain.service.SubjectCategoryDomainService;
import com.jingdianjichi.subject.domain.service.SubjectLabelDomainService;
import com.jingdianjichi.subject.infra.basic.entity.SubjectCategory;
import com.jingdianjichi.subject.infra.basic.entity.SubjectLabel;
import com.jingdianjichi.subject.infra.basic.entity.SubjectMapping;
import com.jingdianjichi.subject.infra.basic.service.SubjectCategoryService;
import com.jingdianjichi.subject.infra.basic.service.SubjectLabelService;
import com.jingdianjichi.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Slf4j
@Service
public class SubjectCategoryDomainServiceImpl implements SubjectCategoryDomainService {
    
    @Resource
    private SubjectCategoryService subjectCategoryService;
   
    @Resource
    private SubjectMappingService subjectMappingService;
    
    @Resource
    private SubjectLabelService subjectLabelService;
    
    @Resource
    private ThreadPoolExecutor labelThreadPool;
   
    @Override
    public void addSubjectCategory(SubjectCategoryBo subjectCategoryBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.add.bo:{}", JSON.toJSONString(subjectCategoryBO));
        }
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.Instance.ConverterBoToPo(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectCategoryService.insert(subjectCategory);
        
    }
    
    @Override
    public Boolean update(SubjectCategoryBo subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.Instance.ConverterBoToPo(subjectCategoryBO);
        int count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }
    
    @Override
    public List<SubjectCategoryBo> queryCategory(SubjectCategoryBo subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.Instance.ConverterBoToPo(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        List<SubjectCategoryBo> boList = SubjectCategoryBOConverter.Instance.ConverterCategoryToBo(subjectCategoryList);
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryController.queryPrimaryCategory.boList:{}",
                    JSON.toJSONString(boList));
        }
        return boList;
    }
    
    @Override
    public Boolean delete(SubjectCategoryBo subjectCategoryBO) {
        SubjectCategory subjectCategory = SubjectCategoryBOConverter.Instance.ConverterBoToPo(subjectCategoryBO);
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        int count = subjectCategoryService.update(subjectCategory);
        return count > 0;
    }
    
    @Override
    public List<SubjectCategoryBo> queryCategoryAndLabel(SubjectCategoryBo subjectCategoryBo) {
        SubjectCategory subjectCategory = new SubjectCategory();
        Map<Long, List<SubjectLabelBO>> resultmap = new HashMap<>();
        
        subjectCategory.setParentId(subjectCategoryBo.getId());
        subjectCategory.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectCategory> subjectCategoryList = subjectCategoryService.queryCategory(subjectCategory);
        
        if (CollectionUtils.isEmpty(subjectCategoryList)){
            return Collections.emptyList();
        }
        
        List<SubjectCategoryBo> boList = SubjectCategoryBOConverter.Instance.ConverterCategoryToBo(subjectCategoryList);
        List<CompletableFuture<Map<Long, List<SubjectLabelBO>>>> futureList =
        boList.stream()
                .map(category -> CompletableFuture.supplyAsync(() ->
                getLabelBOList(category),labelThreadPool))
                .collect(Collectors.toList());
        
        futureList.forEach(future -> {
            try {
                Map<Long, List<SubjectLabelBO>> labelMap = future.get();
                if (!CollectionUtils.isEmpty(labelMap)){
                    resultmap.putAll(labelMap);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        boList.forEach(CategoryBo -> {
            if (resultmap.containsKey(CategoryBo.getId())){
                CategoryBo.setLabelBOList(resultmap.get(CategoryBo.getId()));
            }
        });
        return boList;
    }
    
    private Map<Long, List<SubjectLabelBO>> getLabelBOList(SubjectCategoryBo category) {
        if (log.isInfoEnabled()) {
            log.info("getLabelBOList:{}", JSON.toJSONString(category));
        }
        Map<Long, List<SubjectLabelBO>> labelMap = new HashMap<>();
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(category.getId());
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        
        if (CollectionUtils.isEmpty(mappingList)) {
            return Collections.emptyMap();
        }
        List<Long> labelIdList = mappingList.stream()
                .map(SubjectMapping::getLabelId)
                .collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);
        List<SubjectLabelBO> labelBOList = new LinkedList<>();
        labelList.forEach(label -> {
            SubjectLabelBO subjectLabelBO = new SubjectLabelBO();
            subjectLabelBO.setId(label.getId());
            subjectLabelBO.setLabelName(label.getLabelName());
            subjectLabelBO.setCategoryId(label.getCategoryId());
            subjectLabelBO.setSortNum(label.getSortNum());
            labelBOList.add(subjectLabelBO);
        });
        labelMap.put(category.getId(), labelBOList);
        return labelMap;
    }
    
    
}
