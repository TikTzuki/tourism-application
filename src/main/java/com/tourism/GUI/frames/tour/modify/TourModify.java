package com.tourism.GUI.frames.tour.modify;

import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;

import com.tourism.BUS.TourController;
import com.tourism.DTO.Tour;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TestFrame;
import javax.swing.LayoutStyle.ComponentPlacement;

public class TourModify extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	TourBasicModifyPanel basicPanel;
	TourLocationTable locationPanel;
	TourModifyBottomBar bottomBar;
	GroupLayout layout;
	public static Tour tour;
	public static TourController tourController = new TourController();
	
	public TourModify() {
		initData();
		initComp();
	}
	
	public TourModify(Long tourId) {
		TourModify.tour = tourController.getByIdNotDeleted(tourId);
		initData();
		initComp();
	}
	
	public void initData() {
		basicPanel = new TourBasicModifyPanel();
		locationPanel = new TourLocationTable();
		bottomBar = new TourModifyBottomBar();
		layout = new GroupLayout(this);
	}
	
	public void initComp() {
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
					.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
							.addComponent(basicPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
							.addComponent(locationPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1087, Short.MAX_VALUE)
							.addComponent(bottomBar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1077, Short.MAX_VALUE))
						.addGap(23))
			);
			layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.CENTER)
					.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(basicPanel, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(locationPanel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(bottomBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(36))
			);
			this.setLayout(layout);
		this.setBackground(Resources.PRIMARY_DARK);
		setPreferredSize(Resources.MAIN_CONTENT);
	}
	
	public static void main(String[] args) {
		new TestFrame(new TourModify());
	}
}
