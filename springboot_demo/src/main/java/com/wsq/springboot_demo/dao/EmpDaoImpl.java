package com.wsq.springboot_demo.dao;

import com.wsq.springboot_demo.pojo.Emp;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component
//@Repository是@Component的衍生注解
@Repository
public class EmpDaoImpl implements EmpDao{
    @Override
    public Emp getEmpById(String id) {
        Emp emp = new Emp(id,"wsq",23);
        return emp;
    }
}
