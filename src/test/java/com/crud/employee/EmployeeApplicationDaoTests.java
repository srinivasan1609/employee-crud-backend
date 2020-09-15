package com.crud.employee;

import com.crud.employee.entity.EmployeeEntity;
import com.crud.employee.repo.EmployeeRepository;
import com.crud.employee.service.EmployeeCrudService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class EmployeeApplicationDaoTests {

    EmployeeEntity requestData = EmployeeEntity.builder()
            .firstName("Test")
            .lastName("Test")
            .deptName("Test")
            .build();
    EmployeeEntity requestData2 = EmployeeEntity.builder()
            .firstName("Test")
            .lastName("Test")
            .deptName("Test")
            .build();
    EmployeeEntity requestUpdateData = EmployeeEntity.builder()
            .firstName("Test_update")
            .lastName("Test_update")
            .deptName("Test_update")
            .build();
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    void addEmployeeTest() {
        EmployeeEntity savedEmployee = employeeRepository.save(requestData);
        assertNotNull(savedEmployee);
    }

    @Test
    @Order(2)
    void updateEmployeeTest() {
        EmployeeEntity savedEmployee = employeeRepository.save(requestData);
        requestUpdateData.setId(savedEmployee.getId());
        EmployeeEntity updatedEmployee = employeeRepository.save(requestUpdateData);
        assertNotNull(updatedEmployee);
    }

    @Test
    @Order(3)
    void getEmployeeTest() {
        EmployeeEntity savedEmployee = employeeRepository.save(requestData);
        assertTrue(employeeRepository.findById(savedEmployee.getId()).isPresent());
    }

    @Test
    @Order(4)
    void deleteEmployeeTest() {
        EmployeeEntity savedEmployee = employeeRepository.save(requestData);
        employeeRepository.deleteById(savedEmployee.getId());
        assertTrue(employeeRepository.findById(savedEmployee.getId()).isEmpty());
    }

    @Test
    @Order(4)
    void getAllEmployeeTest() {
        EmployeeEntity savedEmployee1 = employeeRepository.save(requestData);
        EmployeeEntity savedEmployee2 = employeeRepository.save(requestData2);
        employeeRepository.save(savedEmployee1);
        employeeRepository.save(savedEmployee2);
        Iterable<EmployeeEntity> employeeIterator  =employeeRepository.findAll();
        assertThat(employeeIterator).contains(savedEmployee1, savedEmployee2);
    }

}
