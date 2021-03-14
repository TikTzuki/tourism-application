package com.tourism.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.tourism.DTO.Customer;
import com.tourism.DTO.Customer;

/**
 * Customer
 */
public class CustomerRepository implements Repositories<Customer, Long> {
	Logger logger = Logger.getLogger(this.getClass().getName());
	Connector connector = new MysqlConnector();

	@Override
	public Customer save(Customer entity) {
		List<Customer> cus = new ArrayList<Customer>();
		cus.add(entity);
		return saveAll(cus).get(0);
	}

	@Override
	public List<Customer> saveAll(Iterable<Customer> entities) {
		List<Long> ids = new ArrayList<Long>();
		entities.forEach(e -> {
			if (findById(e.getId()).isPresent()) {
				StringBuilder updateQuery = new StringBuilder("UPDATE customer SET ");
				updateQuery.append("name = \"" + e.getName() + "\", ");
				updateQuery.append("identity_card = \"" + e.getIdentityCard() + "\", ");
				updateQuery.append("address1 = \"" + e.getAddress1() + "\", ");
				updateQuery.append("address2 = \"" + e.getAddress2() + "\", ");
				updateQuery.append("address3 = \"" + e.getAddress3() + "\", ");
				updateQuery.append("street = \"" + e.getStreet() + "\", ");
				updateQuery.append("gender = \"" + e.getGender() + "\", ");
				updateQuery.append("phone_number = \"" + e.getPhoneNumber() + "\"");
				updateQuery.append(" WHERE id = \"" + e.getId() + "\" ;");
				logger.info(updateQuery.toString());
				this.connector.executeUpdate(updateQuery.toString());
			} else {
				StringBuilder insertQuery = new StringBuilder(
						"INSERT INTO customer(`name`, `identity_card`, `address1`, `address2`, `address3`, `street`, `gender`, `phone_number`) VALUES ");
				insertQuery.append("( \"" + e.getName() + "\", ");
				insertQuery.append("\"" + e.getIdentityCard() + "\", ");
				insertQuery.append("\"" + e.getAddress1() + "\", ");
				insertQuery.append("\""+e.getAddress2() + "\", ");
				insertQuery.append("\""+e.getAddress3() + "\", ");
				insertQuery.append("\""+e.getStreet() + "\", ");
				insertQuery.append(" \"" + e.getGender() + "\", ");
				insertQuery.append("\"" + e.getPhoneNumber() + "\") ");
				connector.executeUpdate(insertQuery.toString());
				ResultSet returnedResutSet = connector
						.executeQuery("SELECT * FROM customer ORDER BY `id` DESC LIMIT 1");
				try {
					while (returnedResutSet != null && returnedResutSet.next()) {
						 e.setId(Long.valueOf(returnedResutSet.getString("id")));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
//			//Save tourist groups
//			e.getTouristGroups().forEach(tg -> {
//				tg.setCustomers(new ArrayList<Customer>());
//				tg = new TouristGroupRepository().save(tg);
//				connector.executeUpdate(
//						"INSERT INTO tourist_group_customer (`tourist_group_id`, `customer_id`) VALUES (\""
//						+tg.getId() + "\", \"" + e.getId() + "\" );");
//			});
			ids.add(e.getId());
		});
		return findAllById(ids);
	}
	
	@Override
	public Optional<Customer> findById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<Customer> objs = findAllById(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0)); 
	}

	@Override
	public List<Customer> findAll() {
		ResultSet rs = connector.executeQuery("SELECT * FROM customer");
		return extractResultSet(rs);
	}

	@Override
	public List<Customer> findAllById(Iterable<Long> ids) {
		if(!ids.iterator().hasNext())
			return new ArrayList<Customer>();
		StringBuilder query = new StringBuilder("SELECT * FROM customer WHERE ");
		ids.forEach(id->{
			query.append("id = \"" + id + "\" OR ");
		});
		ResultSet rs = this.connector.executeQuery(query.substring(0, query.lastIndexOf("OR")));
		return extractResultSet(rs);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Customer entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		String deleteRelationShip = "DELETE FROM tourist_group_customer WHERE customer_id = \"" + id + "\" ;";
		String query = "DELETE FROM customer WHERE id = \"" +id + "\" ;";
		this.connector.executeUpdate(deleteRelationShip);
		this.connector.executeUpdate(query);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
	  StringBuilder query = new StringBuilder("DELETE FROM customer WHERE ");
	  ids.forEach(id->{
		  query.append(" id = "+id+" OR");
	  });
	  this.connector.executeUpdate(query.substring(0, query.length()-2));
	}

	@Override
	public void deleteAll(Iterable<? extends Customer> entities) {
		List<Long> ids = new ArrayList<Long>();
		entities.forEach(e -> {
			ids.add(e.getId());
		});
		deleteAllById(ids);
	}
	
	public List<Customer> findAllByTouristGroupId(Long id){
		StringBuilder query = new StringBuilder(
				"SELECT * FROM customer c, tourist_group_customer temp WHERE temp.customer_id=c.id AND temp.tourist_group_id= \"");
		query.append(id + "\";" );
		ResultSet rs = this.connector.executeQuery(query.toString());
		return extractResultSet(rs);
	}
	
	public List<Customer> extractResultSet(ResultSet rs){
		List<Customer> customers = new ArrayList<Customer>();
		try {
			while (rs!=null && rs.next()) {
				Customer customer = new Customer();
				customer.setId(Long.valueOf(rs.getLong("id")));
				customer.setName(rs.getString("name"));
				customer.setIdentityCard(rs.getString("identity_card"));
				customer.setAddress1(rs.getString("address1"));
				customer.setAddress2(rs.getString("address2"));
				customer.setAddress3(rs.getString("address3"));
				customer.setStreet(rs.getString("street"));
				customer.setGender(rs.getString("gender"));
				customer.setPhoneNumber(rs.getString("phone_number"));
//				//Set tourist groups
//				if(customer.getTouristGroups() == null) {
//					ResultSet rsTG = this.connector.executeQuery(
//							"SELECT tg.id FROM tourist_group tg, tourist_group_customer temp WHERE temp.tourist_group_id=tg.id AND temp.customer_id="
//							+customer.getId() +" GROUP BY tg.id");
//					List<Long> idTGs = new ArrayList<Long>();
//					while(rsTG.next() && rsTG!=null) {
//						idTGs.add(Long.valueOf(rsTG.getLong("id")));
//					}
//					customer.setTouristGroups(new TouristGroupRepository().findAllById(idTGs));
//				}
				customers.add(customer);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return customers;
	}
	
	public static void main(String[] args) {
		CustomerRepository customerRepository = new CustomerRepository();
	}

	@Override
	public boolean testPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return false;
	}
}
