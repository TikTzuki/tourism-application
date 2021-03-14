package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.EmployeeController;
import com.tourism.BUS.PositionController;
import com.tourism.BUS.TourPositionController;
import com.tourism.DTO.TourPosition;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.CustomTable;

public class AddEmployeeToTouristGroupDialog {
	JDialog dialog;
	GroupLayout layout;
	JPanel pnl;
	
	JComboBox<String> cbxPosition;
	
	JTable tbl;
	DefaultTableModel model;
	JScrollPane scroller;
	
	JButton btnAdd;
	JButton btnCancel;
	
	EmployeeController employeeController;
	PositionController positionController;
	TourPositionController tourPositionController;
	TouristGroup TG;
	TourPosition selectedTourPosition;
	
	public AddEmployeeToTouristGroupDialog(TouristGroup TG) {
		this.TG = TG;
		initData();
		initComp();
	}
	
	private void initData() {
		employeeController = new EmployeeController();
		positionController = new PositionController();
		tourPositionController = new TourPositionController();
		dialog = new JDialog();
		pnl = new JPanel();
		layout = new GroupLayout(pnl);
		
		cbxPosition = new JComboBox<String>();
		
		model = new DefaultTableModel(new Object[] {"Mã", "Tên", "Sđt", "Cmnd", "Giới tính", "Địa chỉ"}, 0);
		tbl = new CustomTable(model);
		scroller = new JScrollPane(tbl);
		
		btnAdd = new JButton("Thêm");
		btnCancel = new JButton("Hủy");
		
		positionController.getAll().forEach(position->{
			cbxPosition.addItem(position.getId()+". "+position.getName());
		});
		
		employeeController.getAllNotDelete().forEach(emp -> {
			model.addRow(new Object[] {
					emp.getId(),
					emp.getName(),
					emp.getPhoneNumber(),
					emp.getIdentityCard(),
					emp.getGender(),
					emp.getStreet() + emp.getAddress3() + emp.getAddress2() + emp.getAddress1()
			});
		});
	}
	
	private void initComp(){
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				Long empId = Long.valueOf( tbl.getValueAt(tbl.getSelectedRow(), 0).toString() );
				String positionString = cbxPosition.getSelectedItem().toString();
				Long positionId = Long.valueOf(positionString.substring(0, positionString.indexOf(".")));
				selectedTourPosition = tourPositionController.initFromTouristGroupByEmployeeAndPosition(TG.getId(), empId, positionId);
				dialog.dispose();
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				selectedTourPosition = null;
				dialog.dispose();
			}
		});
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(cbxPosition))
				.addComponent(scroller)
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAdd)
						.addComponent(btnCancel)));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(cbxPosition))
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
	
	public Optional<TourPosition> addEmployeeToTouristGroup() {
		return selectedTourPosition!=null ? Optional.of(selectedTourPosition) : Optional.empty();
	}
}
