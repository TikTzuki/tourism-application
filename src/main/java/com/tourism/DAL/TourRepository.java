package com.tourism.DAL;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.tourism.DTO.Tour;

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
//				new TypeRepository().save(e.getType());
//				new TouristGroupRepository().saveAll(e.getTouristGroups());
//				new LocationRepository().saveAll(e.getLocations()).forEach(location -> {
//					connector.executeUpdate("INSERT INTO tour_location (`tour_id`, `location_id`) VALUES (\""
//							+ e.getId() + "\", \"" + location.getId() + "\" );");
//				});
			} else {
				StringBuilder insertQuery = new StringBuilder(
						"INSERT INTO tour(`type_id`, `name`, `description`, `price`, `status`) VALUES ");
				insertQuery.append("( \"" + e.getTypeId() + "\", ");
				insertQuery.append("\"" + e.getName() + "\", ");
				insertQuery.append("\"" + e.getDescription() + "\", ");
				insertQuery.append("\"" + e.getStatus() + "\" );");
				connector.executeUpdate(insertQuery.toString());
				ResultSet returnedResultSet = connector
						.executeQuery("SELECT * FROM tourist_group ORDER BY `id` DESC LIMIT 1");
				try {
					while (returnedResultSet != null && returnedResultSet.next()) {
						e.setId(Long.valueOf(returnedResultSet.getString("id")));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
//			// Save type
//			e.getType().setTours(new ArrayList<Tour>());
//			e.setType(new TypeRepository().save(e.getType()));
//			e.setTypeId(e.getType().getId());
//			// Save tourist groups
//			e.getTouristGroups().forEach(TG -> {
//				TG.setTour(new Tour());
//				TG.setTourId(e.getId());
//				TG = new TouristGroupRepository().save(TG);
//			});
//			// Save location
//			e.getLocations().forEach(location -> {
//				location.setTours(new ArrayList<Tour>());
//				location = new LocationRepository().save(location);
//				connector.executeUpdate("INSERT INTO tour_location (`tour_id`, `location_id`) VALUES ( \"" + e.getId()
//						+ "\", \"" + location.getId() + "\" ;");
//			});
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
		ResultSet rsTour = this.connector.executeQuery("SELECT * FROM tour  ;");
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
//				// Set tourist groups
//				if (tour.getTouristGroups() == null) {
//					ResultSet rsTG = connector.executeQuery(
//							"SELECT tg.id FROM tourist_group tg WHERE tg.tour_id = \"" + tour.getId() + "\" ;");
//					List<Long> idTGs = new ArrayList<Long>();
//					while (rsTG != null && rsTG.next()) {
//						idTGs.add(Long.valueOf(rsTG.getLong("id")));
//					}
//					tour.setTouristGroups(new TouristGroupRepository().findAllById(idTGs));
//				}
//				// Set locations
//				if (tour.getLocations() == null) {
//					ResultSet rsLocation = connector.executeQuery(
//							"SELECT temp.location_id as id FROM tour_location temp WHERE temp.tour_id= \""
//									+ tour.getId() + "\" GROUP BY temp.location_id");
//					List<Long> idLocations = new ArrayList<Long>();
//					while (rsLocation != null && rsLocation.next()) {
//						idLocations.add(Long.valueOf(rsLocation.getLong("id")));
//					}
//					tour.setLocations(new LocationRepository().findAllById(idLocations));
//				}
//				// Set type
//				if (tour.getType() == null) {
//					tour.setType(new TypeRepository().findById(tour.getTypeId()).orElse(new Type()));
//				}
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
}
