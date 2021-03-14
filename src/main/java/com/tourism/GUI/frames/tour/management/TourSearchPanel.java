package com.tourism.GUI.frames.tour.management;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import com.tourism.BUS.TourController;
import com.tourism.BUS.TypeController;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.tour.TourMainPanel;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.frames.touristgroup.management.TouristGroupManager;
import com.tourism.GUI.util.DatePicker;

public class TourSearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	GroupLayout layout;
	
	JLabel lblName;
	JTextField txtName;
	JPanel pnlName;
	
	JLabel lblId;
	JTextField txtId;
	JPanel pnlId;
	
	JLabel lblTypeTour;
	JComboBox<String> cbxTypeTour;
	JPanel pnlTypeTour;
	
	
	JLabel lblStatus;
	JComboBox<String> cbxStatus;
	JPanel pnlStatus;
	
	JButton btnCreate;
	JButton btnSearch;
	
	TourController tourController = new TourController();
	TypeController typeController = new TypeController();
	public TourSearchPanel() {
		initData();
		initComp();
	}
	
	public void initData() {	
		layout = new GroupLayout(this);
		lblId = new JLabel("Mã Tour");
		txtId = new JTextField();
		pnlId = new JPanel();
		
		lblName = new JLabel("Tên Tour");
		txtName = new JTextField();
		pnlName = new JPanel();
		
		lblTypeTour = new JLabel("Loại tour");
		cbxTypeTour = new JComboBox<String>();
		pnlTypeTour = new JPanel();
		
		lblStatus = new JLabel("Trạng thái");
		cbxStatus = new JComboBox<String>(Resources.TOUR_STATUSES);
		pnlStatus = new JPanel();

		btnSearch = new JButton("Tìm");
	}
	
	public void initComp() {
		
		cbxTypeTour.addItem("");
		cbxTypeTour.setSelectedItem("");
		typeController.getAll().forEach(type -> {
			cbxTypeTour.addItem(type.getId() + ". " +
					type.getName());
		});
		
		cbxStatus.addItem("");
		cbxStatus.setSelectedItem("");
		
		JComponent[] compInputs = { txtName,cbxTypeTour, txtId,  cbxStatus};
		for (JComponent comp : compInputs) {
			comp.setPreferredSize(Resources.INPUT_SEARCH_TEXTFIELD);
		}
		
		
		btnSearch.setBackground(Resources.PRIMARY_DARK);
		btnSearch.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				Tour tour = new Tour();
				tour.setId(txtId.getText().equals("") ? null : Long.valueOf(txtId.getText()));
				String status = cbxStatus.getSelectedItem().toString();
				tour.setStatus(status.equals("") ? null : status);
				String typeTour= cbxTypeTour.getSelectedItem().toString();
				tour.setTypeId(typeTour.equals("") ? null : Long.valueOf(typeTour.substring(0, typeTour.indexOf("."))));
				tour.setName(txtName.getText());
				TourMainPanel.Tours = tourController.searchTour(tour);
				TourManager.ReloadManagerTable();
			}
		});
		
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblId)
						.addComponent(lblName))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtId)
						.addComponent(txtName))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblTypeTour)
						.addComponent(lblStatus))
				.addGroup(layout.createParallelGroup()
						.addComponent(cbxTypeTour)
						.addComponent(cbxStatus))
				.addGap(ImageObserver.FRAMEBITS)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnSearch))
				);
		
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblId)
								.addComponent(txtId)
								.addComponent(lblTypeTour)
								.addComponent(cbxTypeTour))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblName)
								.addComponent(txtName)
								.addComponent(lblStatus)
								.addComponent(cbxStatus))			
						)
				.addGap(100)
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnSearch)
						)
				);
	}
}
