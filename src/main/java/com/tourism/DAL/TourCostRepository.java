package com.tourism.DAL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.tourism.DTO.Location;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.GUI.Resources;

public class TourCostRepository implements Repositories<TourCost, Long> {
	Connector connector = new MysqlConnector();
	Logger logger = Logger.getLogger(this.getClass().getName());
	@Override
	public TourCost save(TourCost entity) {
		List<TourCost> tourCosts = new ArrayList<TourCost>();
		tourCosts.add(entity);
		return saveAll(tourCosts).get(0);
	}

	@Override
	public List<TourCost> saveAll(Iterable<TourCost> entities) {
		List<Long> ids = new ArrayList<Long>();
		entities.forEach(e -> {
//			if(e.getType() !=null &&  e.getType().getId()!=null)
//				e.setTypeId(e.getType().getId());
			if (findById(e.getId()).isPresent()) {
				StringBuilder updateQuery = new StringBuilder("UPDATE tour_cost SET ");
				updateQuery.append("tour_id = \"" + e.getTourId() + "\", ");
				updateQuery.append("price = \"" + e.getPrice() + "\", ");
				updateQuery.append("price_from_time = \"" + Resources.simpleDateFormat.format(e.getPriceFromTime()) + "\", ");
				updateQuery.append("price_to_time = \"" + Resources.simpleDateFormat.format(e.getPriceToTime()) + "\" ");
				updateQuery.append("WHERE id = \"" + e.getId() + "\" ;");
				logger.info(updateQuery.toString());
				this.connector.executeUpdate(updateQuery.toString());
			} else {
				StringBuilder insertQuery = new StringBuilder(
						"INSERT INTO tour_cost(`tour_id`, `price`, `price_from_time`, `price_to_time`) VALUES ");
				insertQuery.append("( \"" + e.getTourId() + "\", ");
				insertQuery.append("\"" + e.getPrice() + "\", ");
				insertQuery.append("\"" + Resources.simpleDateFormat.format(e.getPriceFromTime()) + "\", ");
				insertQuery.append("\"" + Resources.simpleDateFormat.format(e.getPriceToTime()) + "\"); ");
				this.connector.executeUpdate(insertQuery.toString());
				ResultSet returnedResultSet = connector
						.executeQuery("SELECT * FROM tour_cost ORDER BY `id` DESC LIMIT 1");
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
	public Optional<TourCost> findById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<TourCost> objs = findAllById(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0));
	}

	@Override
	public List<TourCost> findAll() {
		ResultSet rsTourCost = this.connector.executeQuery("select * from tour_cost");
		return extractResultSet(rsTourCost);
	}

	@Override
	public List<TourCost> findAllById(Iterable<Long> ids) {
		if(!ids.iterator().hasNext())
			return new ArrayList<TourCost>();
		List<TourCost> tours = new ArrayList<TourCost>();
		StringBuilder query = new StringBuilder("SELECT * FROM tour_cost WHERE ");
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
	public void delete(TourCost entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		deleteAllById(ids);
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		StringBuilder query = new StringBuilder("DELETE FROM tour_cost WHERE ");
		ids.forEach(id -> {
			query.append(" id = " + id + " OR");
		});
		this.connector.executeUpdate(query.substring(0, query.length() - 2));
		
	}

	@Override
	public void deleteAll(Iterable<? extends TourCost> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean testPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<TourCost> extractResultSet(ResultSet rs){
		List<TourCost> tourCosts = new ArrayList<TourCost>();
		try {
			while (rs!=null && rs.next()) {
				TourCost tourCost = new TourCost();
				tourCost.setId(Long.valueOf(rs.getLong("id")));
				tourCost.setTourId(Long.valueOf(rs.getString("tour_id")));
				tourCost.setPrice(Double.valueOf(rs.getString("price")));
				tourCost.setPriceFromTime(rs.getDate("price_from_time"));
				tourCost.setPriceToTime(rs.getDate("price_to_time"));

				tourCosts.add(tourCost);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tourCosts;
	}
	
	public Double getPriceById(Long id) {
		Double price = new Double(0);
		ResultSet rsPrice = this.connector.executeQuery("SELECT price from tour_cost where tour_id ='"+id+"'");
		try {
			while(rsPrice.next()) {
				Double Price = new Double(rsPrice.getDouble("price"));
				price = Price;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return price;
	}
	
	public Long getIdByTourId(Long tour_id)  {
		Long id = new Long(0);
		ResultSet rsPrice = this.connector.executeQuery("SELECT id from tour_cost where tour_id ='"+tour_id+"'");
		try {
			while(rsPrice.next()) {
				Long Id = new Long(rsPrice.getLong("id"));
				id = Id;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public List<TourCost> findAllByTourId(Long id){
		StringBuilder query = new StringBuilder("SELECT * FROM tour_cost where tour_id = \"");
		query.append(id+"\"; ");
		ResultSet rs = connector.executeQuery(query.toString());
		return extractResultSet(rs);
	}
	
	public String getPriceFromTime(Long id) {
		String priceFromTime = null;
		ResultSet rsPrice = this.connector.executeQuery("SELECT price_from_time from tour_cost"
				+ " where id ='"+id+"'");
		try {
			while(rsPrice.next()) {
				String PriceFromTime = new String(rsPrice.getString("price_from_time"));
				priceFromTime = PriceFromTime;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return priceFromTime;
	}
	
	public String getPriceToTime(Long id) {
		String priceToTime = null;
		StringBuilder query = new StringBuilder("SELECT price_to_time from tour_cost"
				+ " where id ='"+id+"'");
		ResultSet rsPrice = this.connector.executeQuery(query.toString());
		try {
			while(rsPrice.next()) {
				String PriceToTime = new String(rsPrice.getString("price_to_time"));
				priceToTime = PriceToTime;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return priceToTime;
	}
	
	public Optional<TourCost> findByTourId(Long id) {
		List<Long> ids = new ArrayList<Long>();
		ids.add(id);
		List<TourCost> objs = findAllByTourId(ids);
		return objs.isEmpty() ? Optional.empty() : Optional.ofNullable(objs.get(0));
	}
	
	public List<TourCost> findAllByTourId(Iterable<Long> ids) {
		if(!ids.iterator().hasNext())
			return new ArrayList<TourCost>();
		StringBuilder query = new StringBuilder("SELECT * FROM tour_cost WHERE ");
		ids.forEach(id -> {
			query.append("tour_id=\"" + id +"\" OR ");
		});
		ResultSet rs = connector.executeQuery(query.substring(0, query.lastIndexOf("OR")));
		return extractResultSet(rs);
	}
	

}
