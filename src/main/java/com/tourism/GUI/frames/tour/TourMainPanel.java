package com.tourism.GUI.frames.tour;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JPanel;

import com.tourism.BUS.TourController;
import com.tourism.BUS.TourCostController;
import com.tourism.DAL.TourCostRepository;
import com.tourism.DTO.Customer;
import com.tourism.DTO.Location;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.DTO.TourPosition;
import com.tourism.DTO.TouristGroup;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.GUI.frames.tour.management.TourManager;
import com.tourism.GUI.frames.tour.modify.TourCostModify;
import com.tourism.GUI.frames.tour.modify.TourModify;
import com.tourism.GUI.frames.touristgroup.TestFrame;
import com.tourism.GUI.frames.touristgroup.management.TouristGroupManager;
import com.tourism.GUI.frames.touristgroup.modify.TouristGroupModify;


public class TourMainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	private static TourController tourController = new TourController();
	private static TourCostController tourCostController = new TourCostController();
	public static List<Tour> Tours;
	public static List<TourCost> tourCosts;
	public static Tour selectedTour;
	public static TourCost selectedTourCost;
	public static JPanel mainContent;
	
	public TourMainPanel() {
		super(new FlowLayout(0,0,0));
		mainContent = new JPanel(new FlowLayout(0,0,0));
		Tours = tourController.getAll();
		tourCosts = tourCostController.getAll();
		selectedTour = new Tour();
		selectedTourCost = new TourCost();
		mainContent.add(new TourManager());
		this.add(mainContent);
	}
	
	public static void initManagerPanel() {
		Tours = tourController.getAll();
		tourCosts = tourCostController.getAll();
		selectedTour = new Tour();
		selectedTourCost = new TourCost();
		mainContent.removeAll();
		mainContent.add(new TourManager());
		mainContent.getParent().revalidate();
		mainContent.repaint();
	}
	
	public static void initModifyPanel() {
		Tours = tourController.getAllNotDeleted();
		mainContent.removeAll();
		mainContent.add(new TourModify(selectedTour.getId()));
		mainContent.getParent().revalidate();
		mainContent.repaint();
	}
	
	public static void initCreatorPanel() {
		Tours = tourController.getAll();
		selectedTour = new Tour();
		selectedTour.setLocations(new ArrayList<Location>());
		selectedTour.setTourCosts(new ArrayList<TourCost>());
		mainContent.removeAll();
		mainContent.add(new TourModify());
		mainContent.getParent().revalidate();
		mainContent.repaint();
	}
	
	public static void initCreatorTourCostPanel() {
		tourCosts = tourCostController.getAll();
		selectedTourCost = new TourCost();
		new TourCostModify();
	}
	
	public static void initModifyTourCostPanel() {
		new TourCostModify(selectedTourCost.getId());
	}
	
	public static void main(String[] args) {
		new TestFrame(new TourMainPanel());
	}
}
