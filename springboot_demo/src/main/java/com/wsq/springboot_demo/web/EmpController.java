package com.wsq.springboot_demo.web;

import com.wsq.springboot_demo.pojo.Emp;
import com.wsq.springboot_demo.service.EmpService;
import com.wsq.springboot_demo.service.EmpServiceImpl;
import com.wsq.springboot_demo.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/emp")
public class EmpController {
//    service层对象
//    程序员手动管理
//    EmpService empService = new EmpServiceImpl();

//    IOC容器来管理，程序需要时则注入该对象
//    @Autowired
//    private EmpService empService;


//    @Qualifier和@Resource可以解决IOC容器中存在多个类型相同的Bean，我们通过Bean名来注入

//    *@Qualifier是根据名称注入，必须搭配@Autowired使用*
//    @Qualifier("empServicelmpl_02")
//    @Autowired
//    private EmpService empService;

//    @Resource是根据bean名来进行注入，@Autowired是根据类型来注入
//    @Resource是JDK提供的注解，@Autowired是Spring提供的注解
    @Resource(name = "empServicelmpl_02")
    private EmpService empService;

    @GetMapping("{id}")
    public Result getEmp(@PathVariable String id) {
        Emp emp = empService.getEmpById(id);
        return Result.success(emp);
    }
}
