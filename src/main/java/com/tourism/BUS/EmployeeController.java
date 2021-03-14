package com.tourism.BUS;

import java.util.List;
import java.util.Optional;

import com.tourism.DAL.EmployeeRepository;
import com.tourism.DTO.Employee;
import com.tourism.GUI.util.MessageDialog;

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
		String errorMessage = "";
		employee.setId(null);
		employee.setStatus("active");
		if(getByIdentifyCard(employee.getIdentityCard()).size() > 0) {
			errorMessage += ("CMND da duoc su dung. ");
		}
		if(getByPhoneNumber(employee.getPhoneNumber()).size()>0) {
			errorMessage += ("So dien thaoi da duoc su dung. ");
		}
		if(!errorMessage.equals("")) {
			new MessageDialog(errorMessage);
			return;
		}
		employeeRepository.save(employee);
	}

	public void modifyEmployee(Employee employee) {
		String errorMessage = "";
		List<Employee> emps = getByIdentifyCard(employee.getIdentityCard());
		emps.removeIf(emp->( emp.getId().equals(employee.getId()) ));
		if(emps.size()>0) {
			errorMessage += ("CMND da duoc su dung. ");
		}
		emps = getByPhoneNumber(employee.getPhoneNumber());
		emps.removeIf(emp -> ( emp.getId().equals(employee.getId()) ));
		if(emps.size()>0) {
			errorMessage += ("So dien thoai da duoc su dung. ");
		}
		if(!errorMessage.equals("")) {
			new MessageDialog(errorMessage);
			return;
		}
		employeeRepository.save(employee);
	}
	public void deleteEmployee(Long id) {
		Employee emp = employeeRepository.findById(id).get();
		emp.setStatus("deleted");
		employeeRepository.save(emp);
	}
	
	public List<Employee> getByIdentifyCard(String identifyCard) {
		List<Employee> emps = employeeRepository.findAll();
		emps.removeIf(emp -> (!emp.getIdentityCard().equals(identifyCard) || emp.getStatus().equals("deleted")));
		return emps;
	}
	
	public List<Employee> getByPhoneNumber(String phoneNumber) {
		List<Employee> emps = employeeRepository.findAll();
		emps.removeIf(emp -> (!emp.getPhoneNumber().equals(phoneNumber) || emp.getStatus().equals("deleted") ));
		return emps;
	}
}
