package com.tourism.BUS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tourism.DAL.TourRepository;
import com.tourism.DAL.TypeRepository;
import com.tourism.DTO.Tour;
import com.tourism.DTO.Type;

public class TypeController {
	TypeRepository typeRepository = new TypeRepository();
	TourRepository tourRepository = new TourRepository();
	
	public TypeController() {
		
	}
	public List<Type> getAll(){
		List<Type> types = new ArrayList<Type>();
		types = typeRepository.findAll();
		types.forEach(tyoe -> {
			tyoe.setTours(tourRepository.findAllByTypeId(tyoe.getId()));
		});
		return types;
	}
	
	public String getNameById(Long id) {
		return typeRepository.getNameById(id);
	}
}
