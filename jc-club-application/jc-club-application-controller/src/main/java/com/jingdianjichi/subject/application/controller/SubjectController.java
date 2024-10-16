package com.jingdianjichi.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.base.Preconditions;
import com.jingdianjichi.subject.application.convert.SubjectAnswerDTOConverter;
import com.jingdianjichi.subject.application.convert.SubjectInfoDTOConverter;
import com.jingdianjichi.subject.application.dto.SubjectInfoDTO;
import com.jingdianjichi.subject.common.entity.PageResult;
import com.jingdianjichi.subject.common.entity.Result;
import com.jingdianjichi.subject.domain.convert.SubjectInfoConverter;
import com.jingdianjichi.subject.domain.entity.SubjectAnswerBO;
import com.jingdianjichi.subject.domain.entity.SubjectInfoBO;
import com.jingdianjichi.subject.domain.service.SubjectInfoDomainService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/subject")
@Slf4j
public class SubjectController {
    
    @Resource
    private SubjectInfoDomainService subjectInfoDomainService;
    
    /**
     * 添加题目
     * @param subjectInfoDTO
     * @return
     */
    @PostMapping("/add")
    public Object add(@RequestBody SubjectInfoDTO subjectInfoDTO){
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(subjectInfoDTO.getSubjectName()),
                    "题目名称不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectDifficult(), "题目难度不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectType(), "题目类型不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectScore(), "题目分数不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDTO.getCategoryIds())
                    , "分类id不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDTO.getLabelIds())
                    , "标签id不能为空");
            
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE
                    .subjectInfoDTOToBO(subjectInfoDTO);
            List<SubjectAnswerBO> subjectAnswerBOS =
                    SubjectAnswerDTOConverter.INSTANCE
                            .SubjectAnswerDTOToBO(subjectInfoDTO.getOptionList());
            subjectInfoBO.setOptionList(subjectAnswerBOS);
            subjectInfoDomainService.add(subjectInfoBO);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增题目失败");
        }
        
    }
    
    @PostMapping("/getSubjectPage")
    public Result<PageResult<SubjectInfoDTO>> getSubjectPage(@RequestBody SubjectInfoDTO subjectInfoDTO){
        if (log.isInfoEnabled()){
            log.info("SubjectController.getSubjectPage.dto:{}", JSON.toJSONString(subjectInfoDTO));
        }
        Preconditions.checkNotNull(subjectInfoDTO.getCategoryId(), "分类id不能为空");
        Preconditions.checkNotNull(subjectInfoDTO.getLabelId(), "标签id不能为空");
        SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.subjectInfoDTOToBO(subjectInfoDTO);
        PageResult<SubjectInfoBO> subjectInfoBOPageResult = subjectInfoDomainService.getSubjectPage(subjectInfoBO);
        PageResult<SubjectInfoDTO> subjectInfoDTOPageResult = SubjectInfoDTOConverter.INSTANCE
                .pageSubjectInfoBOToDTO(subjectInfoBOPageResult);
        return Result.ok(subjectInfoDTOPageResult);
    }
    
    @PostMapping("/querySubjectInfo")
    public Result<SubjectInfoDTO> querySubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO){
        if (log.isInfoEnabled()){
            log.info("SubjectController.querySubjectInfo.dto:{}", JSON.toJSONString(subjectInfoDTO));
        }
        Preconditions.checkNotNull(subjectInfoDTO.getId(), "题目id不能为空");
        SubjectInfoBO subjectInfoBO = subjectInfoDomainService.querySubjectInfo(subjectInfoDTO.getId());
        SubjectInfoDTO subjectInfoDTO1 = SubjectInfoDTOConverter.INSTANCE.subjectInfoBOToDTO(subjectInfoBO);
        return Result.ok(subjectInfoDTO1);
    }
    
}
