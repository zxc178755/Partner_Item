package com.gdut.builder.controller;

import com.gdut.builder.model.Result;
import com.gdut.builder.service.GenerateService;
import com.gdut.builder.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndexController {

    @Autowired
    private GenerateService generateService;

    @Autowired
    private GradeService gradeService;

    @PostMapping("/generate")
    public List<Result> generate(Integer questionNum, Integer maxLimit) {
        return generateService.generateList(questionNum,maxLimit);
    }

    @PostMapping("/outputGrade")
    public String outputGrade(String correct,String wrong) {
        gradeService.outputGrade(correct,wrong);
        return "操作成功";
    }
}
