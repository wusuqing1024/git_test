package com.wsq.springboot_demo.service;

import com.wsq.springboot_demo.dao.EmpDao;
import com.wsq.springboot_demo.pojo.Emp;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpServicelmpl_02 implements EmpService{

//    Dao层
    @Autowired
    private EmpDao empDao;

    @Override
    public Emp getEmpById(String id) {
        Emp emp = empDao.getEmpById(id);
        return emp;
    }
}
