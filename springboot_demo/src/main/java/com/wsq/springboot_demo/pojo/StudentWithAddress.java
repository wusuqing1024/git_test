package com.wsq.springboot_demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentWithAddress {
    private String name;
    private Integer age;
    private Address address;
}
