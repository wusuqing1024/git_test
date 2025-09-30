package com.wsq.springboot_demo.dao;

import com.wsq.springboot_demo.pojo.Emp;

public interface EmpDao {
    public Emp getEmpById(String id);
}
