package com.tourism.GUI.frames.touristgroup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;

import com.tourism.BUS.TouristGroupController;
import com.tourism.DAL.TourPositionRepository;
import com.tourism.DAL.TouristGroupRepository;
import com.tourism.DTO.TourPosition;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.MainFrame;
import com.tourism.GUI.frames.touristgroup.management.TouristGroupManager;
import com.tourism.GUI.frames.touristgroup.modify.TouristGroupModify;

public class TouristGroupMainPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	private static TouristGroupController touristGroupCntroller = new TouristGroupController();
	public static List<TouristGroup> touristGroups;
	public static TouristGroup selectedTouristGroup;
	public static JPanel mainContent;
	List<TourPosition> tourPosition; 

	public TouristGroupMainPanel() {
		super(new FlowLayout(0,0,0));
		mainContent = new JPanel();
		touristGroups = touristGroupCntroller.getAll();
		selectedTouristGroup = new TouristGroup();
		mainContent.add(new TouristGroupManager());
		this.add(mainContent);
	}
	
	public static void loadManagerPanel() {
		selectedTouristGroup = new TouristGroup();
		mainContent.removeAll();
		mainContent.add(new TouristGroupManager());
		mainContent.getParent().revalidate();
		mainContent.repaint();
	}
	
	public static void loadModifyPanel() {
		touristGroups = touristGroupCntroller.getAll();
		mainContent.removeAll();
		mainContent.add(new TouristGroupModify(selectedTouristGroup.getId()));
		mainContent.getParent().revalidate();
		mainContent.repaint();
	}

	public static void initCreatorPanel() {
		touristGroups = touristGroupCntroller.getAll();
		selectedTouristGroup = new TouristGroup();
		mainContent.removeAll();
		mainContent.add(new TouristGroupModify());
		mainContent.getParent().revalidate();
		mainContent.repaint();
	}

	public static void main(String[] args) {
		new TestFrame(new TouristGroupMainPanel());
	}
}
