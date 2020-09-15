package com.crud.employee.service;

import com.crud.employee.entity.EmployeeEntity;
import com.crud.employee.exceptions.InvalidParamException;
import com.crud.employee.model.EmployeeRequestModel;
import com.crud.employee.model.EmployeeResponseModel;
import com.crud.employee.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeCrudServiceImpl implements EmployeeCrudService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public EmployeeResponseModel addEmployee(EmployeeRequestModel employeeRequestModel) {
        return convertDtoToModel(employeeRepository.save(convertModelToDto(employeeRequestModel)));
    }

    @Override
    public List<EmployeeResponseModel> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeCrudServiceImpl::convertDtoToModel)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseModel getEmployeeById(int id) {
        Optional<EmployeeEntity> employeeOp = employeeRepository.findById(id);
        return convertDtoToModel(checkEmployeeExists(id));
    }

    @Override
    public EmployeeResponseModel updateEmployeeById(int id,EmployeeRequestModel employeeRequestModel) {
        checkEmployeeExists(id);
        EmployeeEntity updateEntity = convertModelToDto(employeeRequestModel);
        updateEntity.setId(id);
        return convertDtoToModel(employeeRepository.save(updateEntity));
    }

    @Override
    public void deleteEmployeeById(int id) {
        checkEmployeeExists(id);
        employeeRepository.deleteById(id);
    }

    private static EmployeeResponseModel convertDtoToModel(EmployeeEntity employeeEntity){
        return EmployeeResponseModel.builder()
                .id(employeeEntity.getId())
                .deptName(employeeEntity.getDeptName())
                .firstName(employeeEntity.getFirstName())
                .lastName(employeeEntity.getLastName())
                .build();
    }

    private static EmployeeEntity convertModelToDto(EmployeeRequestModel employeeRequestModel){
        return EmployeeEntity.builder()
                .deptName(employeeRequestModel.getDeptName())
                .firstName(employeeRequestModel.getFirstName())
                .lastName(employeeRequestModel.getLastName())
                .build();
    }
    private  EmployeeEntity checkEmployeeExists(int id){
        Optional<EmployeeEntity> employeeOp = employeeRepository.findById(id);
        if(employeeOp.isEmpty()){
            throw new InvalidParamException("Invalid Employee Id");
        }
        return employeeOp.get();
    }
}
