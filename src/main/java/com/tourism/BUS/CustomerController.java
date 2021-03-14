package com.tourism.BUS;

import java.util.List;
import java.util.Optional;

import com.tourism.DAL.CustomerRepository;
import com.tourism.DAL.TouristGroupRepository;
import com.tourism.DTO.Customer;
import com.tourism.GUI.util.MessageDialog;

public class CustomerController {
	CustomerRepository customerRepository;
	TouristGroupRepository touristGroupRepository;

	public CustomerController() {
		customerRepository = new CustomerRepository();
		touristGroupRepository = new TouristGroupRepository();
	}

	public List<Customer> getAll(){
		List<Customer> customers;
		customers = customerRepository.findAll();
		customers.forEach(customer->{
			customer.setTouristGroups(touristGroupRepository.findAllByCustomerId(customer.getId()));
		});;
		return customers;
	}

	public Customer getById(Long id) {
		Customer customer = new Customer();
		customer = customerRepository.findById(id).get();
		customer.setTouristGroups(touristGroupRepository.findAllByCustomerId(customer.getId()));
		return customer;
	}

	public void createCustomer(Customer customer) {
		String errorMessage = "";
		customer.setId(null);
		if(getByIdentityCard(customer.getIdentityCard()).size() > 0 ) {
			errorMessage += ("CMND da duoc su dung. ");
		}
		if(getByPhoneNumber(customer.getPhoneNumber()).size() > 0) {
			errorMessage += "So dien thoai da duoc su dung. ";
		}
		if(!errorMessage.equals("")) {
			new MessageDialog(errorMessage);
			return;
		}
		customerRepository.save(customer);
	}

	public void modifyCustomer(Customer customer) {
		String errorMessage = "";
		List<Customer> customers = getByIdentityCard(customer.getIdentityCard());
		customers.removeIf(cus -> (customer.getId().equals(cus.getId())));
		if(customers.size() > 0) {
			errorMessage += "CMND da duoc su dung. ";
		}
		customers = getByPhoneNumber(customer.getPhoneNumber());
		customers.removeIf(cus -> (cus.getId().equals(customer.getId())));
		if(customers.size() > 0) {
			errorMessage += "So dien thoai da duoc su dung. ";
		}
		if(!errorMessage.equals("")) {
			new MessageDialog(errorMessage);
			return;
		}
		customerRepository.save(customer);
	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}

	public List<Customer> getByIdentityCard(String identityCard){
		List<Customer> customers = customerRepository.findAll();
		customers.removeIf(cus -> (!cus.getIdentityCard().equals(identityCard)));
		return customers;
	}

	public List<Customer> getByPhoneNumber(String phoneNumber){
		List<Customer> customers = customerRepository.findAll();
		customers.removeIf(cus -> (!cus.getPhoneNumber().equals(phoneNumber)));
		return customers;
	}
}
