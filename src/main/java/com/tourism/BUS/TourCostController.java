package com.tourism.BUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tourism.DAL.TourCostRepository;
import com.tourism.DTO.TourCost;

public class TourCostController {
	TourCostRepository tourCostRepository = new TourCostRepository();
	
	public List<TourCost> findAllByTourId(Long id){
		List<TourCost> tourCosts = new ArrayList<>();
		tourCosts = tourCostRepository.findAllByTourId(id);
		return tourCosts;
	}
	
	public String getPriceFromTime(Long id) {
		return tourCostRepository.getPriceFromTime(id);
	}
	
	public String getPriceToTime(Long id) {
		return tourCostRepository.getPriceToTime(id);
	}
	
	public void save(TourCost tourCost) {
		tourCostRepository.save(tourCost);
	}
	
	public TourCost getByTourId(Long tourId) {
		TourCost tourCost;
		tourCost = tourCostRepository.findByTourId(tourId).orElse(new TourCost());
		return tourCost;
	}
	
	public List<TourCost> getAll(){
		List<TourCost> tourCosts = new ArrayList<TourCost>();
		tourCosts = tourCostRepository.findAll();
		return tourCosts;
	}
	
	public TourCost getById(Long id) {
		TourCost tourCost;
		tourCost = tourCostRepository.findById(id).orElse(new TourCost());
		return tourCost;
	}
	
}
