package com.jingdianjichi.subject.application.controller;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.jingdianjichi.subject.application.convert.SubjectCategoryDTOConverter;
import com.jingdianjichi.subject.application.dto.SubjectCategoryDTO;
import com.jingdianjichi.subject.common.entity.Result;
import com.jingdianjichi.subject.domain.convert.SubjectCategoryBOConverter;
import com.jingdianjichi.subject.domain.entity.SubjectCategoryBo;
import com.jingdianjichi.subject.domain.service.SubjectCategoryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/subject/category")
@Slf4j
public class SubjectCategoryController {

    @Resource
    SubjectCategoryDomainService subjectCategoryDomainService;

    
    /**
     * 新增题目分类
     */
    @PostMapping("/add")
    public Result<Boolean> addSubjectCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.add.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }

            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(subjectCategoryDTO.getCategoryName()), "分类名称不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "分类父级id不能为空");

            SubjectCategoryBo subjectCategoryBo = SubjectCategoryDTOConverter.Instance.subjectCategoryDtoToBo(subjectCategoryDTO);
            subjectCategoryDomainService.addSubjectCategory(subjectCategoryBo);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增分类失败");
        }
    }

    /**
     * 更新题目分类
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.update.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBo subjectCategoryBO = SubjectCategoryDTOConverter.Instance.subjectCategoryDtoToBo(subjectCategoryDTO);
            Boolean result = subjectCategoryDomainService.update(subjectCategoryBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectCategoryController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新分类失败");
        }
    }
    
    /**
     * 查询岗位一级分类，如前端后端
     */
    @PostMapping("/queryPrimaryCategory")
    public Result<List<SubjectCategoryDTO>> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()){
                log.info("SubjectCategoryController.queryCategoryByPrimary.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBo subjectCategoryBO = SubjectCategoryDTOConverter.Instance.subjectCategoryDtoToBo(subjectCategoryDTO);
            List<SubjectCategoryBo> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.Instance.subjectCategoryBoToDto(subjectCategoryBOList);
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error:{}", e.getMessage(), e);
            return Result.fail("查询失败");
        }
    }
    
    /**
     * 删除题目种类分类
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.delete.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBo subjectCategoryBO = SubjectCategoryDTOConverter.Instance.
                    subjectCategoryDtoToBo(subjectCategoryDTO);
            Boolean result = subjectCategoryDomainService.delete(subjectCategoryBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectCategoryController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除分类失败");
        }
    }
    
    /**
     * 一次查询所有分类和标签
     */
    @PostMapping("/queryCategoryAndLabel")
    public Result<List<SubjectCategoryDTO>> queryCategoryAndLabel(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        if (log.isInfoEnabled()){
            log.info("SubjectCategoryController.queryCategoryAndLabel.dto:{}", JSON.toJSONString(subjectCategoryDTO));
        }
        Preconditions.checkNotNull(subjectCategoryDTO.getId(), "分类id不能为空");
        SubjectCategoryBo subjectCategoryBo = SubjectCategoryDTOConverter.Instance.subjectCategoryDtoToBo(subjectCategoryDTO);
        List<SubjectCategoryBo> subjectCategoryBoList = subjectCategoryDomainService.queryCategoryAndLabel(subjectCategoryBo);
        
        
        return Result.ok();
    }
    
}
