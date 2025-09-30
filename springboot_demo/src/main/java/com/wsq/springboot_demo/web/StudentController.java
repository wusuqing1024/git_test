package com.wsq.springboot_demo.web;


import com.wsq.springboot_demo.pojo.Student;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
//    restful风格的接口
//    查询：get、添加：post、更新：put、删除：delete

//    查询
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable int id) {
        Student student = new Student("wsq",22);
        return student;
    }

//    查询所有
    @GetMapping
    public List<Student> getStudents() {
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("wsq",22));
        students.add(new Student("gq",19));
        students.add(new Student("zl",21));
        return students;
    }

//    新增
    @PostMapping
    public String addStudent(@RequestBody Student student) {
        System.out.println(student);
        return "新增成功";
    }

//    更新
    @PutMapping
    public String updateStudent(@RequestBody Student student) {
        System.out.println(student);
        return "修改成功";
    }

//    删除
    @DeleteMapping("{id}")
    public String deleteStudent(@PathVariable int id) {
        System.out.println(id);
        return "删除成功";
    }
}
