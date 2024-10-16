package com.jingdianjichi.subject.infra.basic.service.impl;

import com.jingdianjichi.subject.infra.basic.entity.SubjectJudge;
import com.jingdianjichi.subject.infra.basic.mapper.SubjectJudgeDao;
import com.jingdianjichi.subject.infra.basic.service.JudgeSubjectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 判断题(SubjectJudge)表服务实现类
 *
 * @author makejava
 * @since 2023-10-05 21:29:47
 */
@Service("subjectJudgeService")
public class SubjectJudgeServiceImpl implements JudgeSubjectService {
    @Resource
    private SubjectJudgeDao subjectJudgeDao;
    
    /**
     * 新增数据
     *
     * @param subjectJudge 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectJudge insert(SubjectJudge subjectJudge) {
        this.subjectJudgeDao.insert(subjectJudge);
        return subjectJudge;
    }
    
    @Override
    public List<SubjectJudge> queryByCondition(SubjectJudge subjectJudge) {
        return subjectJudgeDao.queryAllByLimit(subjectJudge);
    }
    
}
