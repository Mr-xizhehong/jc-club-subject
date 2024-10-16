package com.jingdianjichi.subject.application.convert;

import com.jingdianjichi.subject.application.dto.SubjectCategoryDTO;
import com.jingdianjichi.subject.domain.entity.SubjectCategoryBo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryDTOConverter {
    SubjectCategoryDTOConverter Instance = Mappers.getMapper(SubjectCategoryDTOConverter.class);
    SubjectCategoryBo subjectCategoryDtoToBo(SubjectCategoryDTO dto);
    List<SubjectCategoryDTO> subjectCategoryBoToDto(List<SubjectCategoryBo> boList);
}
