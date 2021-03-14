package com.tourism.BUS;

import java.util.List;

import com.tourism.DAL.LocationRepository;
import com.tourism.DAL.TourRepository;
import com.tourism.DTO.Location;

public class LocationController {
	LocationRepository loRepository = new LocationRepository();
	TourRepository tourRepository = new TourRepository();
	
	public List<Location> getAll(){
		List<Location> locations;
		locations = loRepository.findAll();
		locations.forEach(location -> {
			location.setTours(tourRepository.findAllByLocationId(location.getId()));
		});
		return locations;
	}
	
	public Location getById(Long id) {
		Location location = new Location();
		location = loRepository.findById(id).get();
		location.setTours(tourRepository.findAllByLocationId(location.getId()));
		return location;
	}
	
	public List<Location> findAllByAddress1(String address1){
		List<Location> locations;
		locations = loRepository.findAllByAddress1(address1);
		return locations;
	}
	
	public List<Location> findAllDistinct() {
		List<Location> locations;
		locations = loRepository.findAllDistinct();
		return locations;
	}

}
