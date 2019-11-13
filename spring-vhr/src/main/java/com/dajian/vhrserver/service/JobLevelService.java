package com.dajian.vhrserver.service;

import com.dajian.vhrserver.mapper.JobLevelMapper;
import com.dajian.vhrserver.pojo.JobLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobLevelService {

    @Autowired
    private JobLevelMapper jobLevelMapper;

    public List<JobLevel> getAllJobTitle() {
        return jobLevelMapper.getAllJobTitle();
    }

    public void saveJobTitle(String name,String titleLevel) {
        JobLevel jobLevel = new JobLevel();
        jobLevel.setId(null);
        jobLevel.setName(name);
        jobLevel.setTitleLevel(titleLevel);
        jobLevel.setCreateDate(new Date());
        jobLevelMapper.addJobTitle(jobLevel);
    }

    public void updateJobTitle(JobLevel jobLevel) {
        jobLevelMapper.updateJobTitle(jobLevel);
    }

    public void deleteJobTitleById(List<Long> ids) {
        for (Long id : ids) {
            jobLevelMapper.deleteJobTitleById(id);
        }
    }
}
