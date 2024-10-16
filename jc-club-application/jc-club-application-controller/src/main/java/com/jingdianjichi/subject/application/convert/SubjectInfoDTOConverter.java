package com.jingdianjichi.subject.application.convert;

import com.jingdianjichi.subject.application.dto.SubjectInfoDTO;
import com.jingdianjichi.subject.common.entity.PageResult;
import com.jingdianjichi.subject.domain.entity.SubjectInfoBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectInfoDTOConverter {
    SubjectInfoDTOConverter INSTANCE = Mappers.getMapper(SubjectInfoDTOConverter.class);
    SubjectInfoBO subjectInfoDTOToBO(SubjectInfoDTO subjectInfoDTO);
    
    PageResult<SubjectInfoDTO> pageSubjectInfoBOToDTO(PageResult<SubjectInfoBO> subjectInfoBOPageResult);
    SubjectInfoDTO subjectInfoBOToDTO(SubjectInfoBO subjectInfoBO);
}
