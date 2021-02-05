package com.tourism.DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import com.tourism.DTO.Employee;
import com.tourism.DTO.Position;
import com.tourism.DTO.TourPosition;
import com.tourism.DTO.TouristGroup;

public class TourPositionRepository implements Repositories<TourPosition, Long>{
	Logger logger = Logger.getLogger(this.getClass().getName());
	Connector connector = new MysqlConnector();
	@Override
	public TourPosition save(TourPosition entity) {
		List<TourPosition> roleTours = new ArrayList<TourPosition>();
		roleTours.add(entity);
		return saveAll(roleTours).get(0);
	}

	@Override
	public List<TourPosition> saveAll(Iterable<TourPosition> entities) {
		List<Long> ids = new ArrayList<Long>();
		entities.forEach(e -> {
			//Save tourist group
			e.getTouristGroup().setTourPositions(new ArrayList<TourPosition>());
			e.setTouristGroup(new TouristGroupRepository().save(e.getTouristGroup()));
			e.setTouristGroupId(e.getTouristGroup().getId());
			//Save position
			e.setPosition(new PositionRepository().save(e.getPosition()));
			e.setPositionId(e.getPosition().getId());
			//Save employee
			e.getEmployee().setTourPositions(new ArrayList<TourPosition>());
			e.setEmployee(new EmployeeRepository().save(e.getEmployee()));
			e.setEmployeeId(e.getEmployee().getId());
			
			if (findById(e.getId()).isPresent()) {
				StringBuilder updateQuery = new StringBuilder("UPDATE position_in_tour SET ");
				updateQuery.append("tourist_group_id = \"" + e.getTouristGroupId() + "\" ");
				updateQuery.append("position_id = \"" + e.getPositionId() + "\", ");
				updateQuery.append("employee_id = \"" + e.getEmployeeId() + "\", ");
				updateQuery.append("WHERE id = \"" + e.getId() + "\" ;");
				this.connector.executeUpdate(updateQuery.toString());
			} else {
				StringBuilder insertQuery = new StringBuilder(
						"INSERT INTO position_in_tour(`tourist_group_id`,`position_id`,`employee_id`) VALUES ");
				insertQuery.append("( \"" + e.getTouristGroupId() + "\", ");
				insertQuery.append("\""+e.getPositionId() + "\", ");
				insertQuery.append("\""+e.getEmployeeId() + "\" ); ");
				connector.executeUpdate(insertQuery.toString());
				ResultSet returnedResultSet = connector
						.executeQuery("SELECT * FROM position_in_tour ORDER BY `id` DESC LIMIT 1");
				try {
					while (returnedResultSet != null && returnedResultSet.next()) {
						e.setId(Long.valueOf(returnedResultSet.getString("id")));
					}
				} catch (Exception e1) {
				}
			}

			ids.add(e.getId());
		});
		return findAllById(ids);
	}
	
	@Override
	public Optional<TourPosition> findById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		return Optional.ofNullable(findAllById(ids).get(0));
	}

	@Override
	public List<TourPosition> findAll() {
		List<TourPosition> tourPositions = new ArrayList<TourPosition>();
		ResultSet rsTourPosition = this.connector.executeQuery("SELECT * FROM position_in_tour ;");
		try {
			while (rsTourPosition.next()) {
				TourPosition tp = new TourPosition();
				tp.setId(Long.valueOf(rsTourPosition.getLong("id")));
				tp.setTouristGroupId(Long.valueOf(rsTourPosition.getLong("tourist_group_id)")));
				tp.setPositionId(Long.valueOf(rsTourPosition.getLong("position_id")));
				tp.setEmployeeId(Long.valueOf(rsTourPosition.getLong("employee_id")));
				// Set tourist group
				if (tp.getTouristGroup() == null) {
					tp.setTouristGroup(
							new TouristGroupRepository().findById(tp.getTouristGroupId()).orElse(new TouristGroup()));
				}
				// Set position
				if (tp.getPosition() == null) {
					tp.setPosition(new PositionRepository().findById(tp.getPositionId()).orElse(new Position()));
				}
				// Set employee
				if (tp.getEmployee() == null) {
					tp.setEmployee(new EmployeeRepository().findById(tp.getEmployeeId()).orElse(new Employee()));
				}
				tourPositions.add(tp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tourPositions;
	}

	@Override
	public List<TourPosition> findAllById(Iterable<Long> ids) {
		List<TourPosition> tourPositions = new ArrayList<TourPosition>();
	    ids.forEach(id->{
	    	ResultSet rsTourPosition = this.connector.executeQuery("SELECT * FROM position_in_tour WHERE id = \"" +id+ "\" ;");
	    	try {
	    		while(rsTourPosition.next()) {
	    			TourPosition tp = new TourPosition();
	    			tp.setId(Long.valueOf(rsTourPosition.getLong("id")));
	    			tp.setTouristGroupId(Long.valueOf(rsTourPosition.getLong("tourist_group_id)")));
	    			tp.setPositionId(Long.valueOf(rsTourPosition.getLong("position_id")));
	    			tp.setEmployeeId(Long.valueOf(rsTourPosition.getLong("employee_id")));
	    			//Set tourist group
	    			if(tp.getTouristGroup() == null) {
	    			tp.setTouristGroup(new TouristGroupRepository().findById(tp.getTouristGroupId()).orElse(new TouristGroup()));
	    			}
	    			//Set position
	    			if(tp.getPosition() == null) {
	    			tp.setPosition(new PositionRepository().findById(tp.getPositionId()).orElse(new Position()));
	    			}
	    			//Set employee
	    			if(tp.getEmployee() == null) {
	    				tp.setEmployee(new EmployeeRepository().findById(tp.getEmployeeId()).orElse(new Employee()));
	    			}
	    			tourPositions.add(tp);
	        	}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    });
	    return tourPositions;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(TourPosition entity) {
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
	public void deleteAll(Iterable<? extends TourPosition> entities) {
		// TODO Auto-generated method stub
	}	

}