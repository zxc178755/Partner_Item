package com.gdut.builder;

import com.gdut.builder.model.Result;
import com.gdut.builder.service.GenerateService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class BuilderApplicationTests {

    @Autowired
    private GenerateService generateService;

    @Test
    void testGenerate() {
        generateService.generateList(10000, 10);
    }

}
