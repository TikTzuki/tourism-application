package com.tourism.BUS;

import java.util.List;

import com.tourism.DAL.EmployeeRepository;
import com.tourism.DTO.Employee;

public class EmployeeController {
	EmployeeRepository employeeRepository;
	
	public EmployeeController() {
		employeeRepository = new EmployeeRepository();
	}
	
	public List<Employee> getAll(){
		List<Employee> emps = employeeRepository.findAll();
		return emps;
	}
	
	public List<Employee> getAllNotDelete(){
		List<Employee> emps = employeeRepository.findAll();
		emps.removeIf(emp -> (emp.getStatus().equals("deleted")));
		return emps;
	}
	
	public Employee getById(Long id) {
		return employeeRepository.findById(id).get();
	}
	
	public Employee getByIdNotDelete(Long id) {
		Employee emp = employeeRepository.findById(id).get();
		return emp.getStatus().equals("deleted")? new Employee() : emp;
	}
	
	public void createEmployee(Employee employee) {
		employee.setId(null);
		employee.setStatus("active");
		employeeRepository.save(employee);
	}

	public void modifyEmployee(Employee employee) {
		employeeRepository.save(employee);
	}
	public void deleteEmployee(Long id) {
		Employee emp = employeeRepository.findById(id).get();
		emp.setStatus("deleted");
		employeeRepository.save(emp);
	}
	
}
