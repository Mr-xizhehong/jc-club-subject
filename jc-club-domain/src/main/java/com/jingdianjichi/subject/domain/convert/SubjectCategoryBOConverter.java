package com.jingdianjichi.subject.domain.convert;
import com.jingdianjichi.subject.domain.entity.SubjectCategoryBo;
import com.jingdianjichi.subject.infra.basic.entity.SubjectCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface SubjectCategoryBOConverter {
    SubjectCategoryBOConverter Instance = Mappers.getMapper(SubjectCategoryBOConverter.class);
    SubjectCategory ConverterBoToPo(SubjectCategoryBo bo);
    List<SubjectCategoryBo> ConverterCategoryToBo(List<SubjectCategory> categoryList);
}
