
package com.tourism.DAL;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.DTO.TouristGroup;
import com.tourism.DTO.Type;
import com.tourism.GUI.Resources;

/**
 * TourRepository
 */
public class TourRepository implements Repositories<Tour, Long> {
	Connector connector = new MysqlConnector();
	Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public Tour save(Tour entity) {
		List<Tour> tours = new ArrayList<Tour>();
		tours.add(entity);
		return saveAll(tours).get(0);
	}

	@Override
	public List<Tour> saveAll(Iterable<Tour> entities) {
		List<Long> ids = new ArrayList<Long>();
		entities.forEach(e -> {
			if(e.getType() !=null &&  e.getType().getId()!=null)
				e.setTypeId(e.getType().getId());
			if (findById(e.getId()).isPresent()) {
				StringBuilder updateQuery = new StringBuilder("UPDATE tour SET ");
				updateQuery.append("type_id = \"" + e.getTypeId() + "\", ");
				updateQuery.append("name = \"" + e.getName() + "\", ");
				updateQuery.append("description = \"" + e.getDescription() + "\", ");
				updateQuery.append("status = \"" + e.getStatus() + "\" ");
				updateQuery.append("WHERE id = \"" + e.getId() + "\" ;");
				logger.info(updateQuery.toString());
				this.connector.executeUpdate(updateQuery.toString());
			} else {
				StringBuilder insertQuery = new StringBuilder(
						"INSERT INTO tour(`type_id`, `name`, `description`, `status`) VALUES ");
				insertQuery.append("( \"" + e.getTypeId() + "\", ");
				insertQuery.append("\"" + e.getName() + "\", ");
				insertQuery.append("\"" + e.getDescription() + "\", ");
				insertQuery.append("\"" + e.getStatus() + "\");");
				this.connector.executeUpdate(insertQuery.toString());
				ResultSet returnedResultSet = connector
						.executeQuery("SELECT * FROM tour ORDER BY `id` DESC LIMIT 1");
				try {
					while (returnedResultSet != null && returnedResultSet.next()) {
						e.setId(Long.valueOf(returnedResultSet.getString("id")));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			ids.add(e.getId());
		});
		return findAllById(ids);
	}

	@Override
	public Optional<Tour> findById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<Tour> objs = findAllById(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0));
	}

	@Override
	public List<Tour> findAll() {
		ResultSet rsTour = this.connector.executeQuery("select * from tour");
		return extractResultSet(rsTour);
	}
	
	public List<Tour> findAllNotDeleted() {
		ResultSet rsTour = this.connector.executeQuery("select * from tour WHERE status <> \"deleted\" ;");
		return extractResultSet(rsTour);
	}

	@Override
	public List<Tour> findAllById(Iterable<Long> ids) {
		if(!ids.iterator().hasNext())
			return new ArrayList<Tour>();
		List<Tour> tours = new ArrayList<Tour>();
		StringBuilder query = new StringBuilder("SELECT * FROM tour WHERE ");
		ids.forEach(id -> {
			query.append("id = \"" + id + "\" OR ");
		});
		ResultSet rs = connector.executeQuery(query.substring(0,query.lastIndexOf("OR")));
		return extractResultSet(rs);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Tour entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Long id) {
		ResultSet rsTour = this.connector.executeQuery("DELETE FROM tour WHERE id='"+id+"'");
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
	public void deleteAll(Iterable<? extends Tour> entities) {
		// TODO Auto-generated method stub

	}
	
	public List<Tour> extractResultSet(ResultSet rs){
		List<Tour> tours = new ArrayList<Tour>();
		try {
			while (rs!=null && rs.next()) {
				Tour tour = new Tour();
				tour.setId(Long.valueOf(rs.getLong("id")));
				tour.setTypeId(Long.valueOf(rs.getString("type_id")));
				tour.setName(rs.getString("name"));
				tour.setDescription(rs.getString("description"));
				tour.setStatus(rs.getString("status"));
				tours.add(tour);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tours;
	}

	@Override
	public boolean testPrimaryKey(String id) {
		
		return false;
	}
	
	public void deleteIdLast() {
		this.connector.executeUpdate("delete FROM tour ORDER BY id DESC limit 1");
	}
	
	public Long getIdLast() {
		ResultSet rsTour = this.connector.executeQuery("select id FROM tour ORDER BY id DESC limit 1");
		Long id = null;
		try {
			while (rsTour.next()) {
				Long Id =  new Long(rsTour.getLong("id"));
				id = Id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
	}
	
	public Double getPriceById(String tour_id) {
		ResultSet rsTour = this.connector.executeQuery("select price FROM tour_cost where tour_id ='" + tour_id + "'");
		Double price = null;
		try {
			while (rsTour.next()) {
				Double Price =  new Double(rsTour.getDouble("price"));
				price = Price;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return price;
	}
	
	public String getDateFromTime(String tour_id) {
		ResultSet rsTour = this.connector.executeQuery("select price_from_time FROM tour_cost where tour_id ='" + tour_id + "'");
		String dateFromTime = null;
		try {
			while (rsTour.next()) {
				String DateFromTime = new String(rsTour.getString("price_from_time"));
				dateFromTime = DateFromTime;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dateFromTime;
	}
	
	public String getDateToTime(String tour_id) {
		ResultSet rsTour = this.connector.executeQuery("select price_to_time FROM tour_cost where tour_id ='" + tour_id + "'");
		String dateToTime = null;
		try {
			while (rsTour.next()) {
				String DateToTime =  new String(rsTour.getString("price_to_time"));
				dateToTime = DateToTime;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dateToTime;
	}
	
	public List<Tour> searchTour(Tour tour){
		StringBuilder query = new StringBuilder("SELECT * FROM tour WHERE status <> \"deleted\" AND ");
		if( tour.getId() !=null )
			query.append(" id = \""+tour.getId() + "\" AND ");
		if( !tour.getName().equals(""))
			query.append(" name LIKE \"%"+ tour.getName() +"%\" AND " );
		if( tour.getTypeId() != null)
			query.append("type_id = \"" + tour.getTypeId()+ "\" AND ");
		if( tour.getStatus() != null)
			query.append("status = \"" + tour.getStatus() + "\" AND");
		System.out.println(query.toString());
		return extractResultSet(connector.executeQuery(
				query.substring(0,
						query.lastIndexOf("AND") != -1 ? query.lastIndexOf("AND") : query.lastIndexOf("AND"))));
	}
	public List<Tour> sortTourByName(String nameTour, String location, String typeTour, int temp){
		ResultSet rsTour;
		if(temp == 1) {
			 rsTour = this.connector.executeQuery("SELECT t.id, t.type_id, t.name ,t.description ,t.status, t.image"
					+ " from tour t, tour_location tl, type_of_tour tt, location l, tour_cost tc"
					+ " WHERE t.id = tl.tour_id AND t.type_id = tt.id AND tl.location_id = l.id AND t.id = tc.tour_id "
					+ "AND t.name LIKE '%" + nameTour + "%' "
					+ "AND tt.name LIKE '%" + typeTour + "%' "
					+ "AND l.name LIKE '%" + location + "%'\n"
					+ "order by t.name desc"
					);
		} else {
			rsTour = this.connector.executeQuery("SELECT t.id, t.type_id, t.name ,t.description ,t.status, t.image"
					+ " from tour t, tour_location tl, type_of_tour tt, location l, tour_cost tc"
					+ " WHERE t.id = tl.tour_id AND t.type_id = tt.id AND tl.location_id = l.id AND t.id = tc.tour_id "
					+ "AND t.name LIKE '%" + nameTour + "%' "
					+ "AND tt.name LIKE '%" + typeTour + "%' "
					+ "AND l.name LIKE '%" + location + "%'\n"
					+ "order by t.name asc"
					);
		}
		return extractResultSet(rsTour);
	}
	
	public List<Tour> sortTourByPrice(String nameTour, String location, String typeTour, int temp){
		ResultSet rsTour;
		if(temp == 1) {
			 rsTour = this.connector.executeQuery("SELECT t.id, t.type_id, t.name ,t.description ,t.status, t.image"
					+ " from tour t, tour_location tl, type_of_tour tt, location l, tour_cost tc"
					+ " WHERE t.id = tl.tour_id AND t.type_id = tt.id AND tl.location_id = l.id AND t.id = tc.tour_id "
					+ "AND t.name LIKE '%" + nameTour + "%' "
					+ "AND tt.name LIKE '%" + typeTour + "%' "
					+ "AND l.name LIKE '%" + location + "%'\n"
					+ "order by tc.price desc"
					);
		} else {
			rsTour = this.connector.executeQuery("SELECT t.id, t.type_id, t.name ,t.description ,t.status, t.image"
					+ " from tour t, tour_location tl, type_of_tour tt, location l, tour_cost tc"
					+ " WHERE t.id = tl.tour_id AND t.type_id = tt.id AND tl.location_id = l.id AND t.id = tc.tour_id "
					+ "AND t.name LIKE '%" + nameTour + "%' "
					+ "AND tt.name LIKE '%" + typeTour + "%' "
					+ "AND l.name LIKE '%" + location + "%'\n"
					+ "order by tc.price asc"
					);
		}
		return extractResultSet(rsTour);
	}
	
	public List<Tour> sortTourByStatus(String nameTour, String location, String typeTour, int temp){
		ResultSet rsTour;
		if(temp == 1) {
			 rsTour = this.connector.executeQuery("SELECT t.id, t.type_id, t.name ,t.description ,t.status, t.image"
					+ " from tour t, tour_location tl, type_of_tour tt, location l, tour_cost tc"
					+ " WHERE t.id = tl.tour_id AND t.type_id = tt.id AND tl.location_id = l.id AND t.id = tc.tour_id "
					+ "AND t.name LIKE '%" + nameTour + "%' "
					+ "AND tt.name LIKE '%" + typeTour + "%' "
					+ "AND l.name LIKE '%" + location + "%'\n"
					+ "order by t.status desc"
					);
		} else {
			rsTour = this.connector.executeQuery("SELECT t.id, t.type_id, t.name ,t.description ,t.status, t.image"
					+ " from tour t, tour_location tl, type_of_tour tt, location l, tour_cost tc"
					+ " WHERE t.id = tl.tour_id AND t.type_id = tt.id AND tl.location_id = l.id AND t.id = tc.tour_id "
					+ "AND t.name LIKE '%" + nameTour + "%' "
					+ "AND tt.name LIKE '%" + typeTour + "%' "
					+ "AND l.name LIKE '%" + location + "%'\n"
					+ "order by t.status asc"
					);
		}
		return extractResultSet(rsTour);
	}
	
	public List<Tour> findAllByTypeId(Long id){
		StringBuilder query = new StringBuilder("SELECT * FROM tour where type_id = \"");
		query.append(id+"\"; ");
		ResultSet rs = connector.executeQuery(query.toString());
		return extractResultSet(rs);
	}
	
	public List<Tour> findAllByLocationId(Long id){
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return findAllByLocationIds(ids);
	}
	
	public List<Tour> findAllByLocationIds(List<Long> ids){
		List<Long> idTours = new ArrayList<Long>();
		StringBuilder query = new StringBuilder("SELECT temp.tour_id as id FROM tour_location temp WHERE ");
		ids.forEach(cusId->{
			query.append(" temp.location_id =  " + cusId + " OR ");
		});
		ResultSet rs = this.connector.executeQuery(query.substring(0, query.lastIndexOf("OR")));
		try {
			while(rs != null && rs.next()) {
				idTours.add(Long.valueOf(rs.getLong("id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return findAllById(ids);
	}
	
	public Optional<Tour> findByIdNotDeleted(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<Tour> objs = findAllByIdNotDeleted(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0));
	}
	
	public List<Tour> findAllByIdNotDeleted(Iterable<Long> ids) {
		if(!ids.iterator().hasNext())
			return new ArrayList<Tour>();
		StringBuilder query = new StringBuilder("SELECT * FROM tour WHERE status <> \"deleted\" AND ");
		ids.forEach(id -> {
			query.append("id=\"" + id +"\" OR ");
		});
		ResultSet rs = connector.executeQuery(query.substring(0, query.lastIndexOf("OR")));
		return extractResultSet(rs);
	}
	
	public void saveAllRelationship(Iterable<Tour> tours) {
		List<String> insertTourLocation= new ArrayList<String>();
		StringBuilder deleteTourLocation = new StringBuilder(); 
		tours.forEach(tour -> {
			deleteTourLocation.append("DELETE FROM tour_location WHERE"
					+" tour_id = \""+ tour.getId() + "\" ");
			tour.getLocations().forEach(location->{
				insertTourLocation.add(" INSERT INTO tour_location(tour_id, location_id) VALUES ( \""+ tour.getId() + "\", \"" + location.getId() + "\"); ");
				deleteTourLocation.append(" AND location_id <> \"" +location.getId() + "\" ");
			});
			deleteTourLocation.append("; ");
			insertTourLocation.forEach(query->connector.executeUpdate(query));
			connector.executeUpdate(deleteTourLocation.toString());
		});
	}
	
	
}

