package com.jingdianjichi.subject.domain.convert;

import com.jingdianjichi.subject.domain.entity.SubjectInfoBO;
import com.jingdianjichi.subject.domain.entity.SubjectOptionBO;
import com.jingdianjichi.subject.infra.basic.entity.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoConverter {
     SubjectInfoConverter INSTANCE = Mappers.getMapper(SubjectInfoConverter.class);
     SubjectInfo SubjectBOToSubjectInfo(SubjectInfoBO subjectInfoBO);
     List<SubjectInfoBO> SubjectInfoToSubjectBO(List<SubjectInfo> subjectInfoList);
     SubjectInfoBO convertOptionAndInfoToBo(SubjectOptionBO subjectOptionBO, SubjectInfo subjectInfo);
}
