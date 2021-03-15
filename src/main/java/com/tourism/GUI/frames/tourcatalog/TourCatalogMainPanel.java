package com.tourism.GUI.frames.tourcatalog;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.TourController;
import com.tourism.BUS.TourCostController;
import com.tourism.DTO.Tour;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;

public class TourCatalogMainPanel extends JPanel {
	TourController tourController;
	TourCostController tourCostController;
	List<ComboItem> tourCbxItems;
	JLabel lblTourSelector;
	JComboBox<String> cbx;
	
	DefaultTableModel model;
	JTable tbl;
	JScrollPane scoller;
	GroupLayout layout;
	JPanel pnl;
	public TourCatalogMainPanel() {
		initData();
		initComp();
	}
	private void initData() {
		tourController = new TourController();
		tourCostController = new TourCostController();
		tourCbxItems = new ArrayList<ComboItem>();
	
		lblTourSelector = new JLabel("Chọn tour:");
	cbx = new JComboBox<String>();

	model = new DefaultTableModel(new Object[] {"#", "Giá", "Từ", "Đến"}, 0);
	tbl = new CustomTable(model);
	scoller = new JScrollPane(tbl);
	layout = new GroupLayout(this);
	}
	private void initComp() {
		tourController.getAll().forEach(tour->{
			ComboItem item = new ComboItem(tour);
			tourCbxItems.add(item);
			cbx.addItem(item.title);
		});

		loadTable();

		cbx.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadTable();
			}
		});
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblTourSelector)
						.addComponent(cbx)
						.addGap(Integer.MAX_VALUE))
				.addGroup(layout.createSequentialGroup()
						.addComponent(scoller, 0, 200, Resources.SEARCH_PANEL_WIDTH)
						));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblTourSelector)
						.addComponent(cbx, 0, Resources.INPUT_HEIGHT_M, Resources.INPUT_HEIGHT_M))
				.addComponent(scoller));
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY_DARK);
		this.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.MAIN_CONTENT_HEIGHT));
	}
	
	private void loadTable() {
		model.setRowCount(0);
		Tour tour = tourCbxItems.get(cbx.getSelectedIndex()).tour;
		tour.getTourCosts().forEach(tourCost -> {
			model.addRow(new Object[] {tourCost.getId(), tourCost.getPrice(), tourCost.getPriceFromTime(), tourCost.getPriceToTime()});
		});
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