package com.wsq.springboot_demo.service;

import com.wsq.springboot_demo.dao.EmpDao;
import com.wsq.springboot_demo.dao.EmpDaoImpl;
import com.wsq.springboot_demo.pojo.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component
//@Service是@Component的衍生注解，更好区分Bean是属于哪一层的
@Service
public class EmpServiceImpl implements EmpService{
//    dao层对象
//    程序员手动管理
//    private EmpDao empDao = new EmpDaoImpl();

//    IOC容器来管理，程序需要时则注入该对象
    @Autowired
    private EmpDao empDao;

    @Override
    public Emp getEmpById(String id) {
        Emp emp = empDao.getEmpById(id);
        return emp;
    }
}
