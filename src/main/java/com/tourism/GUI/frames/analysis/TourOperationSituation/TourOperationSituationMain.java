package com.tourism.GUI.frames.analysis.TourOperationSituation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tourism.BUS.TourController;
import com.tourism.DTO.Location;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TouristGroup;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.DTO.Type;
import com.tourism.GUI.Resources;

public class TourOperationSituationMain extends JPanel {
	private static final long serialVersionUID = 1L;
	static TourController tourController;
	public static List<Tour> tours;
	public static Tour selectedTour;
	public static JPanel mainContent;

	public TourOperationSituationMain() {
		tourController = new TourController();
		tours = tourController.getAll();
		selectedTour = new Tour();
		selectedTour.setLocations(new ArrayList<Location>());
		selectedTour.setTouristGroups(new ArrayList<TouristGroup>());
		selectedTour.setType(new Type());
		mainContent = new JPanel(new FlowLayout(0,0,0));
		this.add(mainContent);
		this.setBackground(Resources.PRIMARY_DARK);
		this.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.MAIN_CONTENT_HEIGHT-Resources.INPUT_HEIGHT_L));
		initContent();
	}
	
	public static void initContent() {
		mainContent.removeAll();
		mainContent.add(new TourOperationSituation());
		mainContent.getParent().revalidate();
		mainContent.repaint();
	}
	
	public static void initDetail(Tour selectedTour) {
		mainContent.removeAll();
		mainContent.add(new TourOperationSituationDetail(selectedTour));
		mainContent.getParent().revalidate();
		mainContent.repaint();
	}
}
