package com.wsq.springboot_demo.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data自动生成getter、setter、equals、hashCode、toString方法
//@AllArgsConstructor 全参构造
//@NoArgsConstructor 无参构造
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private Integer age;
}
