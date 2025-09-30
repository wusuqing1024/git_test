package com.wsq.springboot_demo.service;

import com.wsq.springboot_demo.dao.EmpDao;
import com.wsq.springboot_demo.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;

public interface EmpService {

    public Emp getEmpById(String id);
}
