package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工web接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "员工登出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 员工注册
     *
     * @return
     */
    @PostMapping
    @ApiOperation(value = "员工注册")
    public Result register(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工: {}", employeeDTO);
        employeeService.register(employeeDTO);
        return Result.success();
    }

    /**
     * 员工分页查询
     *
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "员工分页查询")
    public Result<PageResult> pagenitionEmployee(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询");
        PageResult pagenitionEmployee = employeeService.pagenitionEmployee(employeePageQueryDTO);
        return Result.success(pagenitionEmployee);
    }

    /**
     * 员工状态更新
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "员工状态更新")
    public Result updateEmployeeStatus(@PathVariable Integer status, Long id){
        log.info("员工状态更新:{},{}", status, id);
        employeeService.updateEmployeeStatus(status, id);
        return Result.success();
    }

    /**
     * 员工查询
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "员工查询")
    public Result<Employee> getEmployeeById(@PathVariable Integer id){
        log.info("查询员工:{}", id);
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }

    /**
     * 更新员工信息
     *
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "更新员工信息")
    public Result updateEmployee(@RequestBody EmployeeDTO employeeDTO){
        log.info("更新员工信息");
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }
}
