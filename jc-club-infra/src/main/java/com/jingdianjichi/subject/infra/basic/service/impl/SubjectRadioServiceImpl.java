package com.jingdianjichi.subject.infra.basic.service.impl;

import com.jingdianjichi.subject.infra.basic.entity.SubjectRadio;
import com.jingdianjichi.subject.infra.basic.mapper.SubjectRadioDao;
import com.jingdianjichi.subject.infra.basic.service.RadioSubjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 单选题信息表(SubjectRadio)表服务实现类
 *
 * @author makejava
 * @since 2023-10-05 21:30:19
 */
@Service("subjectRadioService")
public class SubjectRadioServiceImpl implements RadioSubjectService {
    
    @Resource
    private SubjectRadioDao subjectRadioDao;
    
    
    /**
     * 新增数据
     *
     * @param subjectRadio 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectRadio insert(SubjectRadio subjectRadio) {
        this.subjectRadioDao.insert(subjectRadio);
        return subjectRadio;
    }
    
    @Override
    public void insertBatch(List<SubjectRadio> subjectRadioList) {
        subjectRadioDao.insertBatch(subjectRadioList);
    }
    
    @Override
    public List<SubjectRadio> querySubjectInfo(SubjectRadio subjectRadio) {
        return subjectRadioDao.queryAllByLimit(subjectRadio);
    }
}
