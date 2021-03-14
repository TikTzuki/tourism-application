package com.tourism.BUS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tourism.DAL.LocationRepository;
import com.tourism.DAL.TourCostRepository;
import com.tourism.DAL.TourRepository;
import com.tourism.DAL.TouristGroupRepository;
import com.tourism.DAL.TypeRepository;
import com.tourism.DTO.Location;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TouristGroup;

public class TourController {
	TourRepository tourRepository = new TourRepository();
	LocationRepository locationRepository = new LocationRepository();
	TouristGroupRepository touristGroupRepository = new TouristGroupRepository();
	TourCostRepository tourCostRepository = new TourCostRepository();
	TypeRepository typeRepository = new TypeRepository();
	
	public TourController() {
		
	}
	
	public List<Tour> getAll(){
		List<Tour> tours = new ArrayList<Tour>();
		tours = tourRepository.findAllNotDeleted();
		tours.forEach(tour->{
			tour.setLocations(locationRepository.findAllByTourId(tour.getId()));
			tour.setTouristGroups(touristGroupRepository.findAllByTourId(tour.getId()));
			tour.setTourCosts(tourCostRepository.findAllByTourId(tour.getId()));
			tour.setType(typeRepository.findById(tour.getTypeId()).orElse(null));
		});
		return tours;
	}
	
	public List<Tour> getAllNotDeleted(){
		return null;
	}
	
	public List<Tour> searchTour(Tour object){
		List<Tour> tours = new ArrayList<Tour>();
		tours = tourRepository.searchTour(object);
		tours.forEach(tour -> {
			tour.setLocations(locationRepository.findAllByTourId(tour.getId()));
			tour.setTouristGroups(touristGroupRepository.findAllByTourId(tour.getId()));
			tour.setTourCosts(tourCostRepository.findAllByTourId(tour.getId()));
			tour.setType(typeRepository.findById(tour.getTypeId()).orElse(null));
		});
		return tours;
	}
	
	public Tour getByIdNotDeleted(Long id){
		Tour tour;
		tour = tourRepository.findByIdNotDeleted(id).orElse(new Tour());
		if(tour.getId() == null) {
			return tour;
		}
		tour.setLocations(locationRepository.findAllByTourId(tour.getId()));
		tour.setTouristGroups(touristGroupRepository.findAllByTourId(tour.getId()));
		tour.setTourCosts(tourCostRepository.findAllByTourId(tour.getId()));
		tour.setType(typeRepository.findById(tour.getTypeId()).orElse(null));
		return tour;
	}
	
	public void saveAllWithRelationships(Iterable<Tour> tours){
		List<Tour> saveTours = new ArrayList<Tour>();
		saveTours = tourRepository.saveAll(tours);
		Iterator<Tour> toursIterator = tours.iterator();
		int i = 0;
		while(toursIterator.hasNext()){
			Tour tour = toursIterator.next();
			saveTours.get(i).setLocations(tour.getLocations());
			i++;
		}
		tourRepository.saveAllRelationship(saveTours);
	}
	
	public void saveWithRelationships(Tour tour) {
		List<Tour> tours = new ArrayList<Tour>();
		tours.add(tour);
		saveAllWithRelationships(tours);
	}
	
	public Tour changeStatusToDeleted(Tour tour) {
		tour = tourRepository.findByIdNotDeleted(tour.getId()).get();
		tour.setStatus("deleted");
		return tourRepository.save(tour);
	}
}
