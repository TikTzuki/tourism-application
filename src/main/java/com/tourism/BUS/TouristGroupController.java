package com.tourism.BUS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.tourism.DAL.CustomerRepository;
import com.tourism.DAL.TourPositionRepository;
import com.tourism.DAL.TourRepository;
import com.tourism.DAL.TouristGroupCostRepository;
import com.tourism.DAL.TouristGroupRepository;
import com.tourism.DTO.Customer;
import com.tourism.DTO.Employee;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.DTO.Position;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourPosition;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.frames.touristgroup.management.TouristGroupSearchPanel;

public class TouristGroupController {
	Logger logger = Logger.getLogger(getClass().getName());
	TouristGroupRepository TGRepository;
	CustomerRepository customerRepository;
	TourPositionRepository tourPositionRepository;
	TourRepository tourRepository;
	TouristGroupCostRepository touristGroupCostRepository ;
	
	public TouristGroupController() {
		TGRepository = new TouristGroupRepository();
		customerRepository = new CustomerRepository();
		tourPositionRepository = new TourPositionRepository();
		tourRepository = new TourRepository();
		touristGroupCostRepository = new TouristGroupCostRepository();
	}
	
	public List<TouristGroup> getAllNotDeleted(){
		List<TouristGroup> TGs = new ArrayList<TouristGroup>();
		
		TGs = TGRepository.findAllNotDeleted();
		TGs.forEach(TG->{
			TG.setCustomers(customerRepository.findAllByTouristGroupId(TG.getId()));
			TG.setTourPositions(tourPositionRepository.findAllByTouristGroupId(TG.getId()));
			TG.setTour(tourRepository.findById(TG.getTourId()).orElse(null));
			TG.setTouristGroupCosts(touristGroupCostRepository.findAllByTouristGroupId(TG.getId()));
		});
		return TGs;
	}
	
	public List<TouristGroup> getAll(){
		List<TouristGroup> TGs = new ArrayList<TouristGroup>();
		
		TGs = TGRepository.findAll();
		TGs.forEach(TG->{
			TG.setCustomers(customerRepository.findAllByTouristGroupId(TG.getId()));
			TG.setTourPositions(tourPositionRepository.findAllByTouristGroupId(TG.getId()));
			TG.setTour(tourRepository.findById(TG.getTourId()).orElse(null));
			TG.setTouristGroupCosts(touristGroupCostRepository.findAllByTouristGroupId(TG.getId()));
		});
		return TGs;
	}
	
	public TouristGroup getById(Long id){
		TouristGroup TG;
		TG = TGRepository.findById(id).orElse(new TouristGroup());
		if(TG.getId() == null) {
			return TG;
		}
		TG.setTouristGroupCosts(touristGroupCostRepository.findAllByTouristGroupId(id));
		TG.setCustomers(customerRepository.findAllByTouristGroupId(id));
		TG.setTourPositions(tourPositionRepository.findAllByTouristGroupId(id));
		TG.setTour(tourRepository.findById(TG.getTourId()).orElse(null));
		return TG;
	}
	
	public TouristGroup getByIdNotDeleted(Long id){
		TouristGroup TG;
		TG = TGRepository.findByIdNotDeleted(id).orElse(new TouristGroup());
		if(TG.getId() == null) {
			return TG;
		}
		TG.setTouristGroupCosts(touristGroupCostRepository.findAllByTouristGroupId(id));
		TG.setCustomers(customerRepository.findAllByTouristGroupId(id));
		TG.setTourPositions(tourPositionRepository.findAllByTouristGroupId(id));
		TG.setTour(tourRepository.findById(TG.getTourId()).orElse(null));
		return TG;
	}
	
	public List<TourPosition> groupAllTourPositionByTourPositionId(TouristGroup TG, Long positionId) {
		List<TourPosition> tourPositions = new ArrayList<TourPosition>();
		TG.getTourPositions().forEach(tourPosition->{
			if(positionId == 0 || tourPosition.getPositionId() == positionId) {
				tourPositions.add(tourPosition);
			}
		});
		return tourPositions;
	}
	
	public List<TouristGroup> saveAll(Iterable<TouristGroup> touristGroups){
		return TGRepository.saveAll(touristGroups);
	}
	
	public void saveAllWithRelationShips(Iterable<TouristGroup> touristGroups){
		List<TouristGroup> savedTGs = new ArrayList<TouristGroup>();
		savedTGs = TGRepository.saveAll(touristGroups);
		Iterator<TouristGroup> touristGroupsIterator = touristGroups.iterator();
		int i = 0;
		while(touristGroupsIterator.hasNext()){
			TouristGroup TG = touristGroupsIterator.next();
			savedTGs.get(i).setCustomers(TG.getCustomers());

			TG.getTourPositions().forEach(position ->{
				position.setTouristGroupId(TG.getId());
			});
			savedTGs.get(i).setTourPositions(TG.getTourPositions());

			TG.getTouristGroupCosts().forEach(cost->{
				cost.setTouristGroupId(TG.getId());
			});
			savedTGs.get(i).setTouristGroupCosts(TG.getTouristGroupCosts());
			i++;
		}
		TGRepository.saveAllRelationShip(savedTGs);
	}
	
	public void saveWithRelationships(TouristGroup touristGroup) {
		List<TouristGroup> TGs = new ArrayList<TouristGroup>();
		TGs.add(touristGroup);
		saveAllWithRelationShips(TGs);
	}
	
	public TouristGroup changeStatusToDeleted(TouristGroup touristGroup) {
		touristGroup = TGRepository.findByIdNotDeleted(touristGroup.getId()).get();
		touristGroup.setStatus("deleted");
		return TGRepository.save(touristGroup);
	}
	
	public List<TouristGroup> search(TouristGroup searchObject){
		List<TouristGroup> TGs = TGRepository.findAllLike(searchObject);
		TGs.forEach(TG->{
			TG.setCustomers(customerRepository.findAllByTouristGroupId(TG.getId()));
			TG.setTourPositions(tourPositionRepository.findAllByTouristGroupId(TG.getId()));
			TG.setTour(tourRepository.findById(TG.getTourId()).orElse(null));
			TG.setTouristGroupCosts(touristGroupCostRepository.findAllByTouristGroupId(TG.getId()));
		});
		return TGs;
	}
}
