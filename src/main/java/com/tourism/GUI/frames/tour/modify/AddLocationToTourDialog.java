package com.tourism.GUI.frames.tour.modify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.LocationController;
import com.tourism.BUS.TourController;
import com.tourism.DTO.Customer;
import com.tourism.DTO.Location;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourLocation;
import com.tourism.GUI.CustomTable;

public class AddLocationToTourDialog {
	JDialog dialog;
	JPanel pnl;
	GroupLayout layout;
	
	DefaultTableModel model;
	JTable tbl;
	JScrollPane scroller;
	
	JButton btnAdd;
	JButton btnCancel;
	
	JComboBox<String> cbxAddress1;
	
	TourController tourController;
	LocationController locationController;
	Tour tour;
	Location location;
	
	public AddLocationToTourDialog(Tour tour) {
		this.tour = tour;
		initData();
		initComp();
	}
	private void initData() {
		dialog = new JDialog();
		pnl = new JPanel();
		layout = new GroupLayout(pnl);
		
		model = new DefaultTableModel(new Object[] {"Mã địa điểm", "Tên địa điểm"}, 0);
		tbl = new CustomTable(model);
		scroller = new JScrollPane(tbl);
		
		btnAdd = new JButton("Thêm");
		btnCancel = new JButton("Xóa");
		
		locationController = new LocationController();
		tourController = new TourController();
		
		cbxAddress1= new JComboBox<String>();
		locationController.findAllDistinct().forEach(location->{			
			cbxAddress1.addItem(0+"."+location.getAddress1());
		});
		
		locationController.getAll().forEach(location ->{
			//model.setRowCount(0);
			model.addRow(new Object[] {
					location.getId() , location.getName()
			});
		});
	}
	
	private void initComp() {
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				Long locationId = Long.valueOf(tbl.getValueAt(tbl.getSelectedRow(), 0).toString());
				location = locationController.getById(locationId);
				dialog.dispose();
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				location = null;
				dialog.dispose();
			}
		});
		
		cbxAddress1.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = cbxAddress1.getSelectedItem().toString();
				String address1 = String.valueOf(selected.substring(2,selected.lastIndexOf("")));
				List<Location> locations = locationController.findAllByAddress1(address1);
				model.setRowCount(0);
				locations.forEach(location -> {
					
					model.addRow(new Object[] {
							location.getId(), location.getName()
					});
				});
			}
		});
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(cbxAddress1))
				.addComponent(scroller)
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAdd)
						.addComponent(btnCancel)));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(cbxAddress1))
				.addComponent(scroller)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnAdd)
						.addComponent(btnCancel)));
		
		pnl.setLayout(layout);
		dialog.add(pnl);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public Optional<Location> addLocationToTour(){
		return location!= null ? Optional.of(location) : Optional.empty();
	}
}
