package com.tourism.GUI.frames.analysis.TourCost.copy;

import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.DTO.Tour;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;

public class TourCatalog extends JPanel {
	
	JComboBox<String> cbx;
	
	DefaultTableModel model;
	JTable tbl;
	JScrollPane scoller;
	GroupLayout layout;
	public TourCatalog() {
		initData();
		initComp();
	}
	private void initData() {
	cbx = new JComboBox<String>();

	model = new DefaultTableModel(new Object[] {}, 0);
	tbl = new CustomTable(model);
	scoller = new JScrollPane();
	layout = new GroupLayout(this);
	}
	private void initComp() {
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(cbx))
				.addComponent(scoller));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(cbx))
				.addComponent(scoller));
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY_DARK);
		this.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.MAIN_CONTENT_HEIGHT-Resources.INPUT_HEIGHT_L));
	}
}

class ComboItem {
	String title;
	Tour tour;
	public ComboItem(Tour tour) {
		this.tour = tour;
		this.title = tour.getName();
	}
}