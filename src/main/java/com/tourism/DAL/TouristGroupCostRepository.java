
package com.tourism.DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tourism.DTO.TouristGroup;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.service.JsonTouristCost;

public class TouristGroupCostRepository implements Repositories<TouristGroupCost, Long>{
	Connector connector;
	public TouristGroupCostRepository() {
		connector = new MysqlConnector();
	}
	@Override
	public TouristGroupCost save(TouristGroupCost entity) {
		List<TouristGroupCost> touristGroupCosts = new ArrayList<TouristGroupCost>();
		touristGroupCosts.add(entity);
		return saveAll(touristGroupCosts).get(0);
	}

	@Override
	public List<TouristGroupCost> saveAll(Iterable<TouristGroupCost> entities) {
		List<Long> ids = new ArrayList<Long>();
		entities.forEach(e->{
			if(findById(e.getId()).isPresent()) {
				StringBuilder updateQuery = new StringBuilder("UPDATE tourist_group_cost SET ");
				updateQuery.append("tourist_group_id = \"" + e.getTouristGroupId() + "\", ");
				updateQuery.append("total_price = \"" + e.getTotalPrice() + "\", ");
				updateQuery.append("description = \'" + JsonTouristCost.formatToJsonArray(e.getDescription()) + "\' ");
				updateQuery.append("WHERE id = \"" + e.getId() + "\" ;");
				System.out.println(updateQuery.toString());
				this.connector.executeUpdate(updateQuery.toString());
			} else {
				StringBuilder insertQuery = new StringBuilder(
						"INSERT INTO tourist_group_cost(`tourist_group_id`, `total_price`, `description`) VALUES ");
				insertQuery.append("( \"" + e.getTouristGroupId() + "\", ");
				insertQuery.append("\"" + e.getTotalPrice() + "\", ");
				insertQuery.append("\'" + JsonTouristCost.formatToJsonArray(e.getDescription()) + "\'); ");
				System.out.println(insertQuery.toString());
				connector.executeUpdate(insertQuery.toString());
				ResultSet returnedResultSet = connector
						.executeQuery("SELECT * FROM tourist_group_cost ORDER BY `id` DESC LIMIT 1;");
				try {
					while(returnedResultSet.next()) {
						e.setId(Long.valueOf(returnedResultSet.getLong("id")));
					}
				} catch (Exception e2) {
				}
			}
			ids.add(e.getId());
		});
		return findAllById(ids);
	}

	@Override
	public Optional<TouristGroupCost> findById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<TouristGroupCost> objs = findAllById(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0)); 
	}

	@Override
	public List<TouristGroupCost> findAll() {
		ResultSet rs = connector.executeQuery("SELECT * FROM tourist_group_cost;");
		return extractResultSet(rs);
	}

	@Override
	public List<TouristGroupCost> findAllById(Iterable<Long> ids) {
		if(!ids.iterator().hasNext())
			return new ArrayList<TouristGroupCost>();
		StringBuilder query = new StringBuilder("SELECT * FROM tourist_group_cost WHERE ");
		ids.forEach(id->{
			query.append("id=\"" + id + "\" OR ");
		});
		ResultSet rs = connector.executeQuery(query.substring(0, query.lastIndexOf("OR")));
		return extractResultSet(rs);
	}

	public List<TouristGroupCost> findAllByTouristGroupId(Long id){
		ResultSet rs = connector.executeQuery(
				"SELECT * FROM tourist_group_cost WHERE tourist_group_id = \"" + id + "\" ;");
		return extractResultSet(rs);
	}
	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(TouristGroupCost entity) {
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
	
	public void deleteAllByTouristGroupNot(TouristGroup TG) {
		StringBuilder query = new StringBuilder(
				"DELETE FROM tourist_group_cost WHERE tourist_group_id = \"" + TG.getId() +"\" ");
		if(!TG.getTouristGroupCosts().isEmpty()) {
			TG.getTouristGroupCosts().forEach(touristGroupCost ->{
				query.append(" AND id <> \"" + touristGroupCost.getId() + "\" ");
			});
		}
		connector.executeUpdate(query.toString());
	}
	
	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends TouristGroupCost> entities) {
		// TODO Auto-generated method stub
		
	}
	
	private List<TouristGroupCost> extractResultSet(ResultSet rs){
		List<TouristGroupCost> touristGroupCost = new ArrayList<TouristGroupCost>();
		try {
			while(rs.next()) {
				TouristGroupCost TGCost = new TouristGroupCost();
				TGCost.setId(Long.valueOf(rs.getLong("id")));
				TGCost.setTouristGroupId(Long.valueOf(rs.getLong("tourist_group_id")));
				TGCost.setTotalPrice(Double.valueOf(rs.getDouble("total_price")));
				TGCost.setDescription(JsonTouristCost.parseToTouristGroupCostItems(rs.getString("description")));
				touristGroupCost.add(TGCost);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return touristGroupCost;
	}
	@Override
	public boolean testPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return false;
	}
}
