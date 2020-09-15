package com.crud.employee.service;

import com.crud.employee.model.EmployeeRequestModel;
import com.crud.employee.model.EmployeeResponseModel;

import java.util.List;

public interface EmployeeCrudService {

    EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel);

    List<EmployeeResponseModel> getAllEmployees();

    EmployeeResponseModel getEmployeeById(int id);

    EmployeeResponseModel updateEmployeeById(int id,EmployeeRequestModel employeeRequestModel);

    void deleteEmployeeById(int id);


}
