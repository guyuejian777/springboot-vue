package com.dajian.vhrserver.controller.system.basic;

import com.dajian.vhrserver.pojo.JobLevel;
import com.dajian.vhrserver.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/basicMsg/jobTitle")
public class JobTitlelController {

    @Autowired
    private JobLevelService jobLevelService;

    @GetMapping("/all")
    public ResponseEntity<List<JobLevel>> getAllJobTitle() {
        List<JobLevel> jobLevels = jobLevelService.getAllJobTitle();
        if (CollectionUtils.isEmpty(jobLevels)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobLevels);
    }

    /**
     * 添加职称
     * @param name
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveJobTitle(@RequestParam("name") String name, @RequestParam("titleLevel") String titleLevel) {
        List<String> names= getNames();
        if (names.contains(name)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        jobLevelService.saveJobTitle(name, titleLevel);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateJobTitle(JobLevel jobLevel){
        List<String> names= getNames();
        if (names.contains(jobLevel.getName())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        jobLevelService.updateJobTitle(jobLevel);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{ids}")
    public ResponseEntity<Void> deleteJobTitleById(@PathVariable("ids") List<Long> ids){
        jobLevelService.deleteJobTitleById(ids);
        return ResponseEntity.ok().build();
    }

    private List<String> getNames(){
        List<JobLevel> jobLevels = jobLevelService.getAllJobTitle();
        List<String> names = new ArrayList<>();
        jobLevels.forEach(jobLevel -> {
            names.add(jobLevel.getName());
        });
        return names;
    }
}
