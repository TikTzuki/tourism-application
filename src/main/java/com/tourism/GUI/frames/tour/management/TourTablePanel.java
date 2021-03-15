package com.tourism.GUI.frames.tour.management;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.TourController;
import com.tourism.BUS.TypeController;
import com.tourism.DAL.TourCostRepository;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.DTO.TouristGroup;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.tour.TourMainPanel;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.frames.touristgroup.management.TouristGroupDetailPanel;

public class TourTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	SimpleDateFormat sDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	JTable tbl;
	DefaultTableModel model;
	JScrollPane scroller;
	
	public TourTablePanel() {
		initData();
		initComp();
	}
	
	public void initData() {
		model = new DefaultTableModel(new Object[] { "Mã", "Tên Tour", "Loại Tour",
				"Mô tả", "Trạng thái"}, 0);
		tbl = new CustomTable(model);
		scroller = new JScrollPane(tbl);
		
		TourMainPanel.Tours.forEach(tour -> {
			
			model.addRow(new Object[] { tour.getId(), tour.getName(), new TypeController().getNameById(tour.getTypeId()),
					tour.getDescription(), tour.getStatus()});
		});
		
	}
	
	public void initComp() {
		tbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Long selectedId = (Long) tbl.getValueAt(tbl.getSelectedRow() , 0);
				
				for(Tour obj : TourMainPanel.Tours) {
					if(obj.getId() == selectedId) {
						TourMainPanel.selectedTour=obj;
						TourMainPanel.selectedTourCost = new TourCost();
					}
				}
				TourDetailPanel.reload();
			}
		});
		
		tbl.setPreferredSize(Resources.MANAGER_TABLE);
		scroller.setPreferredSize(Resources.MANAGER_TABLE_SCROLLER);
		add(scroller);
		setBackground(Resources.PRIMARY);
	}
	
	public void reloadTable() {
		List<Tour> tours = new ArrayList<Tour>();
		tours = new TourController().getAll();
		tours.forEach(tour -> {
			model.addRow(new Object[] {
					tour.getId(), tour.getName(), new TypeController().getNameById(tour.getTypeId()),
					tour.getDescription(), tour.getStatus()
			});
		});
	}
}
