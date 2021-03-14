package com.tourism.DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tourism.DTO.Location;
import com.tourism.DTO.TourLocation;
import com.tourism.DTO.TouristGroup;

public class LocationRepository implements Repositories<Location, Long> {
	Connector connector = new MysqlConnector();
	@Override
	public Location save(Location entity) {
		List<Location> locations = new ArrayList<Location>();
		locations.add(entity);
		return saveAll(locations).get(0);
	}

	@Override
	public List<Location> saveAll(Iterable<Location> entities) {
		List<Long> ids = new ArrayList<Long>();
		entities.forEach(e->{
			if(findById(e.getId()).isPresent()) {
				StringBuilder updateQuery = new StringBuilder("UPDATE location SET ");
				updateQuery.append("name = \"" + e.getName() + "\", ");
				updateQuery.append("address1 = \"" + e.getAddress1() + "\", ");
				updateQuery.append("address2 = \"" +e.getAddress2() + "\", ");
				updateQuery.append("address2 = \"" +e.getAddress3() + "\" ");
				updateQuery.append("street = \"" + e.getStreet() + "\", ");
				updateQuery.append("WHERE id = \"" + e.getId() + "\" ;");
				connector.executeUpdate(updateQuery.toString());
			} else {
				StringBuilder insertQuery = new StringBuilder(
						"INSERT INTO location(`name`, `address1`, `address2`, `address3`, `street`) VALUES ");
				insertQuery.append("( \"" + e.getName() + "\", ");
				insertQuery.append("\"" + e.getAddress1() + "\", ");
				insertQuery.append("\"" + e.getAddress2() + "\", ");
				insertQuery.append("\"" + e.getAddress3() + "\", ");
				insertQuery.append("\""+e.getStreet() + "\" ); ");
				connector.executeUpdate(insertQuery.toString());
				ResultSet returnedResultSet = connector.executeQuery(
						"SELECT * FROM location ORDER BY `id` DESC LIMIT 1");
				try {
					while(returnedResultSet!=null && returnedResultSet.next()) {
						e.setId(Long.valueOf(returnedResultSet.getLong("id")));
					}
				} catch (Exception e2) {
				}
			}
//			//Save tours
//			e.getTours().forEach(tour ->{
//				tour.setLocations(new ArrayList<Location>());
//				tour = new TourRepository().save(tour);
//				connector.executeUpdate(
//						"INSERT INTO tour_location (`tour_id`, `location_id`) VALUES ( \""
//						+tour.getId() + "\", \"" + e.getId() + "\" );");
//			});
			ids.add(e.getId());
		});
		return findAllById(ids);
	}

	@Override
	public Optional<Location> findById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<Location> objs = findAllById(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0)); 
	}

	@Override
	public List<Location> findAll() {
		ResultSet rsLocation = connector.executeQuery("SELECT * FROM location ;");
		return extractResultSet(rsLocation);
	}

	@Override
	public List<Location> findAllById(Iterable<Long> ids){
		if(!ids.iterator().hasNext())
			return new ArrayList<Location>();
		StringBuilder query = new StringBuilder(
				"SELECT * FROM location WHERE ");
		ids.forEach(id->{
			query.append("id = \"" + id + "\" OR ");
		});
		ResultSet rs = connector.executeQuery(query.substring(0, query.lastIndexOf("OR")));
		return extractResultSet(rs);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Location entity) {
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
	public void deleteAll(Iterable<? extends Location> entities) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Location> extractResultSet(ResultSet rs){
		List<Location> locations = new ArrayList<Location>();
		try {
			while(rs!=null && rs.next()) {
				Location location = new Location();
				location.setId(Long.valueOf(rs.getLong("id")));
				location.setName(rs.getString("name"));
				location.setAddress1(rs.getString("address1"));
				location.setAddress2(rs.getString("address2"));
				location.setAddress3(rs.getString("address3"));
				location.setStreet(rs.getString("street"));
				locations.add(location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return locations;
	}
	
	public List<Location> findAllByTourId(Long id){
		StringBuilder query = new StringBuilder("SELECT * FROM location l, tour_location temp WHERE temp.location_id=l.id AND temp.tour_id=\"");
		query.append(id+"\"; ");
		ResultSet rs = connector.executeQuery(query.toString());
		return extractResultSet(rs);
	}

	@Override
	public boolean testPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Location> findAllByAddress1(String address1){
		StringBuilder query = new StringBuilder("SELECT * FROM location WHERE address1 =\"");
		query.append(address1+"\"; ");
		ResultSet rs = connector.executeQuery(query.toString());
		return extractResultSet(rs);
	}
	
	public List<Location> findAllDistinct() {
		List<Location> locations = new ArrayList<Location>();
		ResultSet rsLocation = connector.executeQuery("SELECT DISTINCT address1 FROM location ;");
		try {
			while(rsLocation!=null && rsLocation.next()) {
				Location location = new Location();
				location.setAddress1(rsLocation.getString("address1"));
				locations.add(location);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return locations;
	}
}
