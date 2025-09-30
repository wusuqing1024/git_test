package com.wsq.springboot_demo.web;

import com.wsq.springboot_demo.pojo.Student;
import com.wsq.springboot_demo.utils.Result;
import com.wsq.springboot_demo.utils.ResultCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/result_test")
public class ResultController {
//    成功
    @GetMapping("/{id}")
    public Result test(@PathVariable int id) {
        System.out.println(id);
        Student student = new Student("wsq",22);
        return Result.success(student);
    }

//    失败
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id) {
        System.out.println(id + "该id不存在");
        return Result.failure(ResultCode.DATA_NONE);
    }
}
