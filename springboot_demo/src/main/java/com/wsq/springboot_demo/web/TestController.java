package com.wsq.springboot_demo.web;


import com.wsq.springboot_demo.pojo.Student;
import com.wsq.springboot_demo.pojo.StudentWithAddress;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

//@RequestMapping("/path")
//可以通过path或value属性指定多个访问路径
@RequestMapping(path = {"/path","/path_2","/path_3"})
//@RestController是@Controller + @ResponseBody
//@Controller作用：1、标识一个类为Web控制器   2、返回视图，例如返回"create-user"视图名称,视图解析器会找到视图名称对应的视图 create-user.html
//@ResponseBody作用：告诉Spring，方法的返回值不是视图名称，而是直接写在响应体里的数据。如果返回值是str则直接写入，如果返回值是对象则转成json再写入
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "这是一个RequestMapping的test案例。";
    }

//    post请求
    @RequestMapping(path = "stu",method = RequestMethod.POST)
    public String get_student_info(HttpServletRequest request) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        System.out.println("name:"+name+",age:"+age);
        return "成功接收";
    }

//   springboot中接受参数的方式，注意请求参数名与方法参数名一致
//   @RequestMapping可以处理Get、Post、Put和Delete请求
    @RequestMapping("/sample_param")
    public String sample_param(String name,Integer age) {
        System.out.println("name:"+name+",age:"+age);
        return "成功接收";
    }

//   @RequestParam，解决请求参数名和方法参数名不一致的问题
    @RequestMapping("/sample_param_2")
    public String sample_param_2(@RequestParam("stu_name") String name, Integer age) {
        System.out.println("name:"+name+",age:"+age);
        return "成功接收";
    }

//   方法参数为对象
    @RequestMapping("/sample_pojo")
    public String sample_pojo(Student student) {
        System.out.println("student:"+student);
        return "成功接收";
    }

//    方法参数为都对象嵌套对象
    @RequestMapping("/sample_pojo_2")
    public String sample_pojo_2(StudentWithAddress student) {
        System.out.println(student);
        return "成功接收";
    }

//    数组映射
    @RequestMapping("/sample_array")
    public String sample_array(String[] fruits) {
        System.out.println("fruits:"+ Arrays.toString(fruits));
        return "ok";
    }

//    集合映射，需要借助@RequestParam实现
    @RequestMapping("/sample_list")
    public String sample_list(@RequestParam List<String> fruits ) {
        System.out.println("fruits:"+ fruits);
        return "ok";
    }

//    日期映射，需要借助@DateTimeFormat
    @RequestMapping("/date_param")
    public String date_param(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")LocalDateTime time) {
        System.out.println("time:"+time);
        return "ok";
    }

//    Json映射，必须使用@RequestBody
    @RequestMapping("/json_param")
    public String json_param(@RequestBody StudentWithAddress student) {
        System.out.println("student:"+student);
        return "ok";
    }

//  @PathVariable 可以在url路径中传递参数，url中的参数名要与方法的参数名一致
    @RequestMapping("/url_param/{id}")
    public String url_param(@PathVariable Integer id) {
        System.out.println("id:"+id);
        return "ok";
    }

// @PathVariable还可以在url中传递多个参数，如果url中参数名与方法中的参数名不一致，可以在@PathVariable中的value值修改
//    注意区分@RequestParam("")与@PathVariable("")
    @RequestMapping("/url_more_param/{id}/{name}")
    public String url_more_param(@PathVariable Integer id, @PathVariable("name") String username) {
        System.out.println(id);
        System.out.println(username);
        return "ok";
    }

//    @RequestParam、@RequestBody和@RequestPath的区别
//    @RequestParam从请求参数中获取数据
//    @RequestBody从请求体中获取数据
//    @PathVariable从URL路径中获取数据
}
