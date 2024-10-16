package com.jingdianjichi.subject.infra.basic.service.impl;

import com.jingdianjichi.subject.infra.basic.entity.SubjectMultiple;
import com.jingdianjichi.subject.infra.basic.mapper.SubjectMultipleDao;
import com.jingdianjichi.subject.infra.basic.service.MultipleSubjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 多选题信息表(SubjectMultiple)表服务实现类
 *
 * @author makejava
 * @since 2023-10-05 21:30:05
 */
@Service("subjectMultipleService")
public class SubjectMultipleServiceImpl implements MultipleSubjectService {
    @Resource
    private SubjectMultipleDao subjectMultipleDao;


    /**
     * 新增数据
     *
     * @param subjectMultiple 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectMultiple insert(SubjectMultiple subjectMultiple) {
        this.subjectMultipleDao.insert(subjectMultiple);
        return subjectMultiple;
    }
    
    @Override
    public void batchInsert(List<SubjectMultiple> subjectMultipleList) {
        subjectMultipleDao.insertBatch(subjectMultipleList);
    }
    
    @Override
    public List<SubjectMultiple> queryByCondition(SubjectMultiple subjectMultiple) {
        return subjectMultipleDao.queryAllByLimit(subjectMultiple);
    }
}
