package com.crud.employee.controller;

import com.crud.employee.model.EmployeeRequestModel;
import com.crud.employee.model.EmployeeResponseModel;
import com.crud.employee.service.EmployeeCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeCrudRestController {

    @Autowired
    EmployeeCrudService employeeCrudService;

    @PostMapping("/add")
    EmployeeResponseModel addEmployee(@RequestBody EmployeeRequestModel employeeRequestModel){
        return employeeCrudService.addEmployee(employeeRequestModel);
    }

    @GetMapping("/get/{id}")
    EmployeeResponseModel getEmployeeById(@PathVariable int id){
        return employeeCrudService.getEmployeeById(id);
    }

    @GetMapping("/getall")
    List<EmployeeResponseModel> getAllEmployee(){
        return employeeCrudService.getAllEmployees();
    }

    @PutMapping("/edit/{id}")
    EmployeeResponseModel editEmployeeById(@PathVariable int id,@RequestBody EmployeeRequestModel employeeRequestModel){
        return employeeCrudService.updateEmployeeById(id,employeeRequestModel);
    }

    @DeleteMapping("/delete/{id}")
    void deleteEmployeeById(@PathVariable int id){
        employeeCrudService.deleteEmployeeById(id);
    }


}
