package com.tourism.BUS;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.tourism.DAL.TourCostRepository;
import com.tourism.DTO.TourCost;
import com.tourism.GUI.Resources;

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
	
	public List<TourCost> getByTourIdSortFromNow(Long tourId){
		List<TourCost> tourCosts = tourCostRepository.findAllByTourId(tourId);
		Collections.sort(tourCosts, (a,b)->{
				int state1 = (a.getPriceFromTime().compareTo(
						new Date()));
				int state2 = (new Date().compareTo(a.getPriceToTime()));
				if(state1 < 0 && state2 < 0)
					return -1;
				return 1;
		});
		return tourCosts;
	}
}
