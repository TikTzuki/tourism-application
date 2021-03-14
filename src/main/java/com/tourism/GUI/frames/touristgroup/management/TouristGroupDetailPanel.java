package com.tourism.GUI.frames.touristgroup.management;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tourism.BUS.TouristGroupController;
import com.tourism.DTO.TouristGroup;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.ConfirmDialog;

public class TouristGroupDetailPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	GroupLayout layout;
	
	JLabel lblId;
	JTextField txtId;
	
	JLabel lblTour;
	JTextField txtTour;
	
	JLabel lblName;
	JTextField txtName;

	JLabel lblDepatureDate;
	JTextField txtDepatureDate;

	JLabel lblEndDate;
	JTextField txtEndDate;


	
	JLabel lblStatus;
	JTextField txtStatus;
	
		JLabel lblTotalCost;
	JTextField txtTotalCost;
	
	JLabel lblCustomerCount;
	JTextField txtCustomerCount;
	
	JLabel lblEmployeeCount;
	JTextField txtEmployeeCount;

	JButton btnModify;
	JButton btnDelete;
	
	TouristGroupController touristGroupController;
	
	public TouristGroupDetailPanel() {
		initData();
		initComp();
	}
	
	public void initData() {
		touristGroupController = new TouristGroupController();
		layout = new GroupLayout(this);
		lblId = new JLabel("Mã đoàn");
		txtId = new JTextField();
		
		lblTour = new JLabel("Tour");
		txtTour = new JTextField();
		
		lblName = new JLabel("Tên đoàn");
		txtName = new JTextField();

		lblDepatureDate = new JLabel("Ngày khởi hành");
		txtDepatureDate = new JTextField();
		
		lblEndDate = new JLabel("Ngày kết thúc");
		txtEndDate = new JTextField();

		lblStatus= new JLabel("Trạng thái");
		txtStatus = new JTextField();
		
		lblTotalCost= new JLabel("Tổng chi phí");
		txtTotalCost = new JTextField();

		lblCustomerCount= new JLabel("Số khách");
		txtCustomerCount = new JTextField();

		lblEmployeeCount= new JLabel("Số nhân viên");
		txtEmployeeCount = new JTextField();

		btnModify= new JButton("Sửa");
		btnDelete = new JButton("Xóa");
	}
	
	public void initComp() {
		JTextField[] txtField = new JTextField[] {txtId, txtName, txtDepatureDate, txtEndDate, txtTotalCost, txtTour, txtStatus, txtCustomerCount, txtEmployeeCount};
		for(JTextField txt: txtField) {
			txt.setEditable(false);
			txt.setBackground(Resources.PRIMARY);
			txt.setBorder(BorderFactory.createLineBorder(Resources.PRIMARY_DARK, 1, true));
		}
		
		btnModify.setBackground(Resources.PRIMARY_DARK);
		btnModify.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(TouristGroupMainPanel.selectedTouristGroup != null && TouristGroupMainPanel.selectedTouristGroup.getId() != null)
					TouristGroupMainPanel.initModifyPanel();
			}
		});
		btnDelete.setBackground(Resources.PRIMARY_DARK);
		btnDelete.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(TouristGroupMainPanel.selectedTouristGroup != null && TouristGroupMainPanel.selectedTouristGroup.getId() != null) {
					if(new ConfirmDialog("Xác nhận xóa").confirm())
						touristGroupController.changeStatusToDeleted(TouristGroupMainPanel.selectedTouristGroup);
					TouristGroupMainPanel.initManagerPanel();
				}
			}
		});
		
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblId)
						.addComponent(lblTour)
						.addComponent(lblName))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtId)
						.addComponent(txtTour)
						.addComponent(txtName))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblDepatureDate)
						.addComponent(lblEndDate)
						.addComponent(lblStatus))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtDepatureDate)
						.addComponent(txtEndDate)
						.addComponent(txtStatus))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblTotalCost)
						.addComponent(lblCustomerCount)
						.addComponent(lblEmployeeCount)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(txtTotalCost)
						.addComponent(txtCustomerCount)
						.addComponent(txtEmployeeCount)
						)
				.addGap(ImageObserver.FRAMEBITS)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnDelete)
						.addComponent(btnModify))
				);
		
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblId)
								.addComponent(txtId)
								.addComponent(lblDepatureDate)
								.addComponent(txtDepatureDate)
								.addComponent(lblTotalCost)
								.addComponent(txtTotalCost)
								)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblTour)
								.addComponent(txtTour)
								.addComponent(lblEndDate)
								.addComponent(txtEndDate)
								.addComponent(lblCustomerCount)
								.addComponent(txtCustomerCount))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblName)
								.addComponent(txtName)
								.addComponent(lblStatus)
								.addComponent(txtStatus)
								.addComponent(lblEmployeeCount)
								.addComponent(txtEmployeeCount))
						)
				.addGroup(layout.createSequentialGroup()
								.addComponent(btnModify)
								.addGap(ImageObserver.FRAMEBITS)
								.addComponent(btnDelete))
				);
	}

	static public void reload() {
		TouristGroup TG = TouristGroupMainPanel.selectedTouristGroup;
		TouristGroupDetailPanel detailPanel = TouristGroupManager.detailPanel; 
		detailPanel.txtId.setText(TG.getId().toString());
		detailPanel.txtTour.setText(TG.getTour().getName());
		detailPanel.txtName.setText(TG.getName());
		detailPanel.txtDepatureDate.setText(Resources.simpleDateFormat.format(TG.getDepatureDate()));
		detailPanel.txtEndDate.setText(Resources.simpleDateFormat.format(TG.getEndDate()));
		detailPanel.txtStatus.setText(TG.getStatus());
		Double totalCost = Double.valueOf(0);
		if(!TG.getTouristGroupCosts().isEmpty())
			for(TouristGroupCost TGCost: TG.getTouristGroupCosts()) {
				totalCost += TGCost.getTotalPrice();
			}
		detailPanel.txtTotalCost.setText(totalCost.toString());
		detailPanel.txtCustomerCount.setText(TG.getCustomers().size()+"");
		detailPanel.txtEmployeeCount.setText(TG.getTourPositions().size()+"");
	}
}