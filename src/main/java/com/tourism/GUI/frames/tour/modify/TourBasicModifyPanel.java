package com.tourism.GUI.frames.tour.modify;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import com.tourism.BUS.TypeController;
import com.tourism.DTO.Tour;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.tour.TourMainPanel;
import com.tourism.GUI.util.DatePicker;

public class TourBasicModifyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	GroupLayout layout;
	
	JLabel lblId;
	JTextField txtId;
	
	JLabel lblDecription;
	static JTextField txtDecription;
	
	JLabel lblTypeTour;
	static JComboBox<String> cbxTypeTour;
	
	static JLabel lblName;
	static JTextField txtName;
	
	JLabel lblStatus;
	static JComboBox<String> cbxStatus;
	
	TypeController typeController = new TypeController();
	Tour tour;
	public TourBasicModifyPanel() {
		tour = TourMainPanel.selectedTour;
		initData();
		initComp();
	}
	
	public void initData() {
		layout = new GroupLayout(this);
		
		lblId = new JLabel("Mã Tour");
		txtId = new JTextField(tour.getId()!=null ? tour.getId().toString() : "");

		lblName = new JLabel("Tên Tour");
		txtName= new JTextField(tour.getName());
		
		lblDecription = new JLabel("Mô tả");
		txtDecription= new JTextField(tour.getDescription());

		lblStatus = new JLabel("Trạng thái");
		cbxStatus = new JComboBox<String>(Resources.TOUR_STATUSES);

		lblTypeTour = new JLabel("Loại Tour");
		cbxTypeTour  = new JComboBox<String>();
		typeController.getAll().forEach(type->{
			cbxTypeTour.addItem(type.getId()+ ". " + type.getName());
			if(type.getId() == tour.getTypeId())
				cbxTypeTour.setSelectedItem( type.getId() + ". "  + type.getName() );
		});
	}
	
	public void initComp() {
		txtId.setEditable(false);
		txtId.setBackground(Resources.PRIMARY);
		
		cbxStatus.setSelectedItem(tour.getStatus());
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblId)
						.addComponent(lblTypeTour))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtId, 100,Resources.INPUT_WIDTH_M, Resources.INPUT_WIDTH_L)
						.addComponent(cbxTypeTour, 100, Resources.INPUT_WIDTH_M, Resources.INPUT_WIDTH_L)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(lblName)
						.addComponent(lblDecription))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtName, 100, Resources.INPUT_WIDTH_M, Resources.INPUT_WIDTH_L)
						.addComponent(txtDecription, 100, Resources.INPUT_WIDTH_M, Resources.INPUT_WIDTH_L))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblStatus))
				.addGroup(layout.createParallelGroup()
						.addComponent(cbxStatus, 100, Resources.INPUT_WIDTH_M, Resources.INPUT_WIDTH_L))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblId)
						.addComponent(txtId)
						.addComponent(lblName)
						.addComponent(txtName)
						.addComponent(lblStatus)
						.addComponent(cbxStatus))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblTypeTour)
						.addComponent(cbxTypeTour)
						.addComponent(lblDecription)
						.addComponent(txtDecription))
				);
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
	}
	
	public static Boolean commitToSelectedTouristGroup() {
		Tour tour = TourMainPanel.selectedTour;
		if(txtName.getText().equals("")) {
			return false;
		}
		if(txtDecription.getText().equals("")) {
			return false;
		}
		if(cbxStatus.getSelectedItem().toString().equals("")) {
			return false;
		}
		if(cbxTypeTour.getSelectedItem().toString().equals("")) {
			return false;
		}
		tour.setName(txtName.getText());
		tour.setDescription(txtDecription.getText());
		tour.setStatus(cbxStatus.getSelectedItem().toString());
		String typeTour = cbxTypeTour.getSelectedItem().toString();
		tour.setTypeId(Long.valueOf(typeTour.substring(0, typeTour.lastIndexOf("."))));
		
		return true;
	}
}
