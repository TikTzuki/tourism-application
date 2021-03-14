package com.tourism.GUI.frames.tour.modify;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.TourController;
import com.tourism.DTO.Customer;
import com.tourism.DTO.Location;
import com.tourism.DTO.Tour;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.tour.TourMainPanel;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.frames.touristgroup.modify.AddCustomerToTouristGroupDialog;
import com.tourism.GUI.util.ConfirmDialog;
import com.tourism.GUI.util.MessageDialog;

public class TourLocationTable extends JPanel {
	private static final long serialVersionUID = 1L;
	TourController tourController = new TourController();
	
	GroupLayout layout;
	JLabel lblLocationList;
	
	JButton btnAdd1;
	
	JPanel pnlSelectedLocation;
	JLabel lblSelectedLocation;
	JLabel lblSelectedLocationId;
	
	JScrollPane scroller1;
	JTable tbl1;
	DefaultTableModel model1;
	
	JButton btnRemove1;
	
	Tour tour;
	
	public TourLocationTable() {
		tour = TourMainPanel.selectedTour;
		initData();
		initComp();
	}
	
	public void initData() {
		//table 1
		layout  = new GroupLayout(this);
		model1 = new DefaultTableModel(new Object[] {"Mã địa điểm", "Tên địa điểm"}, 0);
		
		lblLocationList = new JLabel("Danh sách địa điểm");
		
		btnAdd1 = new JButton(Resources.ADD_ICON);
		
		pnlSelectedLocation= new JPanel();
		lblSelectedLocation = new JLabel("Địa điểm");
		lblSelectedLocationId = new JLabel();
		btnRemove1 = new JButton("Xóa");
		
		tbl1 = new CustomTable(model1);	
		scroller1 = new JScrollPane(tbl1);
	}
	
	public void initComp() {
		btnAdd1.setBackground(Resources.PRIMARY_DARK);
		btnAdd1.setPreferredSize(Resources.SQUARE_XXS);
		btnAdd1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				Optional<Location> opt = new AddLocationToTourDialog(tour).addLocationToTour();
				opt.ifPresent(location -> {
					for(Location obj: tour.getLocations()) {
						if(obj.getId() == location.getId()) {
							new MessageDialog("Địa điểm đã có trong danh sách");
							return;
						}
					}
					tour.getLocations().add(location);
				});
				loadTable();
			}
		});
		pnlSelectedLocation.add(lblSelectedLocation);
		pnlSelectedLocation.add(lblSelectedLocationId);
		pnlSelectedLocation.setBackground(Resources.PRIMARY);
		
		btnRemove1.setBackground(Resources.PRIMARY_DARK);
		btnRemove1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(new ConfirmDialog("Xóa khách hàng khỏi danh sách").confirm()) {
					Long locationId = Long.valueOf(lblSelectedLocationId.getText());
					TourMainPanel.selectedTour.getLocations().removeIf(
							location->(location.getId()==locationId));
					loadTable();
				}
			}
		});
		loadTable();
		
		tbl1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				String locationId = tbl1.getValueAt(tbl1.getSelectedRow(), 0).toString();
				lblSelectedLocationId.setText(locationId);				
			}
		});
		tbl1.setBackground(Resources.PRIMARY);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblLocationList))
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAdd1, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
						.addContainerGap()
						.addComponent(pnlSelectedLocation)
						.addComponent(btnRemove1))
				.addComponent(scroller1)
				);
			
		
		layout.setVerticalGroup(layout.createSequentialGroup()			
				.addComponent(lblLocationList)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnAdd1, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
						.addComponent(pnlSelectedLocation)
						.addComponent(btnRemove1))
				.addComponent(scroller1)
				);
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
	}
	
	private void loadTable() {
		model1.setRowCount(0);
		if(TourMainPanel.selectedTour.getLocations()!=null)
			TourMainPanel.selectedTour.getLocations().forEach(location ->{
				model1.addRow(new Object[] {
						location.getId(),
						location.getName(),
				});
			});
	}
}
