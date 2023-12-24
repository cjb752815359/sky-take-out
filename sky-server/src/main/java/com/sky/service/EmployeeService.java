package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工注册
     *
     * @param employeeDTO
     */
    void register(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pagenitionEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 员工状态更新
     *
     * @param status
     * @param id
     * @return
     */
    void updateEmployeeStatus(Integer status, Long id);

    /**
     * 更新员工信息
     *
     * @param employeeDTO
     */
    void updateEmployee(EmployeeDTO employeeDTO);

    /**
     * 员工查询
     *
     * @param id
     * @return
     */
    Employee getEmployeeById(Integer id);
}
