package com.gdut.builder.service.impl;

import com.gdut.builder.service.GradeService;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class GradeServiceImpl implements GradeService {

    @Override
    public void outputGrade(String correct, String wrong) {
        BufferedOutputStream bo;
        try {
            File file = new File("./Grade.txt");
            bo = new BufferedOutputStream(new FileOutputStream(file));
            bo.write(correct.getBytes());
            bo.write("\r\n".getBytes());
            bo.write(wrong.getBytes());
            bo.flush();
            bo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("文件输入输出异常");
            e.printStackTrace();
        }
    }
}
