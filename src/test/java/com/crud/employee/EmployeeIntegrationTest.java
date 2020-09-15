package com.crud.employee;

import com.crud.employee.entity.EmployeeEntity;
import com.crud.employee.model.EmployeeRequestModel;
import com.crud.employee.model.EmployeeResponseModel;
import com.crud.employee.repo.EmployeeRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    EmployeeRequestModel requestData = EmployeeRequestModel.builder()
            .firstName("Test")
            .lastName("Test")
            .deptName("Test")
            .build();
    EmployeeRequestModel updateRequestData = EmployeeRequestModel.builder()
            .firstName("Test1")
            .lastName("Test1")
            .deptName("Test1")
            .build();
    public static int id = 0;

    @Before
    public void beforeTest(){
        EmployeeEntity daoData = EmployeeEntity.builder()
                .firstName("Test")
                .lastName("Test")
                .deptName("Test")
                .build();
        EmployeeEntity daoData1 =employeeRepository.save(daoData);
        id =daoData1.getId();

    }

    @Test
    @Order(1)
    public void addEmployeeTest() {
        ResponseEntity<EmployeeResponseModel> postResponse = restTemplate.postForEntity(getRootUrl()
                + "/employee/add", requestData, EmployeeResponseModel.class);
        assertNotNull(postResponse);
        assertThat(postResponse.getBody()).hasFieldOrPropertyWithValue("firstName",requestData.getFirstName());
        assertThat(postResponse.getBody()).hasFieldOrPropertyWithValue("lastName",requestData.getLastName());
        assertThat(postResponse.getBody()).hasFieldOrPropertyWithValue("deptName",requestData.getDeptName());
        assertNotEquals(postResponse.getBody().getId(),0);
    }

    @Test
    @Order(2)
    public void updateEmployeeTest() {
        HttpEntity<EmployeeRequestModel> httpRequest = new HttpEntity<EmployeeRequestModel>(updateRequestData, new HttpHeaders());
        ResponseEntity<EmployeeResponseModel> postResponse = restTemplate.exchange(getRootUrl()
                + "/employee/edit/"+id, HttpMethod.PUT,httpRequest, EmployeeResponseModel.class);
        assertNotNull(postResponse);
        assertThat(postResponse.getBody()).hasFieldOrPropertyWithValue("firstName",updateRequestData.getFirstName());
        assertThat(postResponse.getBody()).hasFieldOrPropertyWithValue("lastName",updateRequestData.getLastName());
        assertThat(postResponse.getBody()).hasFieldOrPropertyWithValue("deptName",updateRequestData.getDeptName());
        assertNotEquals(postResponse.getBody().getId(),0);
    }

    @Test
    @Order(3)
    public void fetchEmployeeTest(){
        ResponseEntity<EmployeeResponseModel> getResponse = restTemplate.getForEntity(URI.create(getRootUrl()
                + "/employee/get/"+id),EmployeeResponseModel.class);
        assertNotNull(getResponse);
        assertEquals(getResponse.getStatusCodeValue(),200);
        assertThat(getResponse.getBody()).hasFieldOrPropertyWithValue("firstName","Test");
        assertThat(getResponse.getBody()).hasFieldOrPropertyWithValue("lastName","Test");
        assertThat(getResponse.getBody()).hasFieldOrPropertyWithValue("deptName","Test");
        assertNotEquals(getResponse.getBody().getId(),0);

    }

    @Test
    @Order(4)
    public void deleteEmployeeTest(){
        restTemplate.delete(URI.create(getRootUrl()
                + "/employee/delete/"+id));
        assertFalse(employeeRepository.existsById(id));

    }

    @After
    public void afterTest(){
        if(employeeRepository.existsById(id))
        employeeRepository.deleteById(id);
    }

}
