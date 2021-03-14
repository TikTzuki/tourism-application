
package com.tourism.DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tourism.DTO.User;

public class UserRepository implements Repositories<User, Long> {
	Connector connector = new MysqlConnector();
	
	@Override
	public User save(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> saveAll(Iterable<User> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> findById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<User> objs = findAllById(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0));
	}
public Optional<User> findByPhone(String phoneNumber){
	String query = "SELECT * FROM user WHERE phone_number = \"" + phoneNumber +"\" ;";
	ResultSet rs = connector.executeQuery(query);
	List<User> objs = extractResultSet(rs);
	System.out.println(query);
	return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0));
}
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAllById(Iterable<Long> ids) {
		StringBuilder query = new StringBuilder("SELECT * FROM user WHERE ");
		ids.forEach(id->{
			query.append(" id = \"" + id + "\" OR ");
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
	public void delete(User entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends User> entities) {
		// TODO Auto-generated method stub
		
	}
	
	private List<User> extractResultSet(ResultSet rs){
		List<User> users = new ArrayList<User>();
		try {
			while(rs.next()) {
				User user = new User();
				user.setId(Long.valueOf(rs.getLong("id")));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setPhoneNumber(rs.getString("phone_number"));
				users.add(user);
			}
		} catch (Exception e) {
		}
		return users;
	}

	@Override
	public boolean testPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return false;
	}
}
