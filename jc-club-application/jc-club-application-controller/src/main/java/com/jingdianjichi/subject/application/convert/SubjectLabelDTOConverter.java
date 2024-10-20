package com.jingdianjichi.subject.application.convert;

import com.jingdianjichi.subject.application.dto.SubjectLabelDTO;
import com.jingdianjichi.subject.domain.entity.SubjectLabelBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelDTOConverter {
    SubjectLabelDTOConverter INSTANCE = Mappers.getMapper(SubjectLabelDTOConverter.class);
    
    SubjectLabelBO convertDtoToLabelBO(SubjectLabelDTO subjectLabelDTO);
    
    List<SubjectLabelDTO> convertBOToLabelDTOList(List<SubjectLabelBO> boList);
}
