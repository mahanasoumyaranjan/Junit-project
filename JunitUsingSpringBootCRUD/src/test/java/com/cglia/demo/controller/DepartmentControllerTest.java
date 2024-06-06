package com.cglia.demo.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cglia.demo.entity.Department;
import com.cglia.demo.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DepartmentService departmentService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testSaveDepartment() throws Exception {
		Department department = new Department();
		department.setDepartmentName("IT");
		department.setDepartmentAddress("New York");
		department.setDepartmentCode("IT-001");

		when(departmentService.saveDepartment(any(Department.class))).thenReturn(department);

		mockMvc.perform(post("/api/v1/departments").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(department))).andExpect(status().isOk())
				.andExpect(jsonPath("$.departmentName", is(department.getDepartmentName())));
	}

	@Test
	public void testFetchDepartmentList() throws Exception {
		Department department1 = new Department();
		department1.setDepartmentName("IT");
		department1.setDepartmentAddress("New York");
		department1.setDepartmentCode("IT-001");

		Department department2 = new Department();
		department2.setDepartmentName("HR");
		department2.setDepartmentAddress("Los Angeles");
		department2.setDepartmentCode("HR-001");

		List<Department> departmentList = Arrays.asList(department1, department2);

		when(departmentService.fetchDepartmentList()).thenReturn(departmentList);

		mockMvc.perform(get("/api/v1/departments")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].departmentName", is(department1.getDepartmentName())))
				.andExpect(jsonPath("$[1].departmentName", is(department2.getDepartmentName())));
	}

	@Test
	public void testUpdateDepartment() throws Exception {
		Long departmentId = 1L;

		Department department = new Department();
		department.setDepartmentName("IT");
		department.setDepartmentAddress("New York");
		department.setDepartmentCode("IT-001");

		when(departmentService.updateDepartment(any(Department.class), any(Long.class))).thenReturn(department);

		mockMvc.perform(put("/api/v1/departments/{id}", departmentId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(department))).andExpect(status().isOk())
				.andExpect(jsonPath("$.departmentName", is(department.getDepartmentName())));
	}

	@Test
	public void testDeleteDepartmentById() throws Exception {
		Long departmentId = 1L;

		mockMvc.perform(delete("/api/v1/departments/{id}", departmentId)).andExpect(status().isOk())
				.andExpect(content().string("Deleted Successfully"));
	}
}
