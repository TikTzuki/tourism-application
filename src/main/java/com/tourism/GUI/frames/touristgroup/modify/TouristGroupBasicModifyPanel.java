package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

import com.tourism.BUS.TourController;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TestFrame;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.DatePicker;
import com.tourism.service.Validation;

public class TouristGroupBasicModifyPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	GroupLayout layout;
	
	JLabel lblId;
	JTextField txtId;
	
	JLabel lblName;
	static JTextField txtName;
	
	static JLabel lblTourName;
	static JComboBox<String> cbxTourName;
	
	JLabel lblDepatureDate;
	static JTextField txtDepatureDate;
	JButton btnDepatureDate;
	JPanel pnlDepatureDate;
	
	JLabel lblEnDate;
	static JTextField txtEndDate;
	JButton btnEndDate;
	JPanel pnlEndDate;
	
	JLabel lblStatus;
	static JComboBox<String> cbxStatus;
	
	JLabel lblRevenue;
	static JTextField txtRevenueValue;
	JButton btnModifyRevenue;
	JPanel pnlRevenue;
	
	TouristGroup TG;
	TourController tourController;
	
	JPanel pnlHotelTable;
	public TouristGroupBasicModifyPanel() {	
		TG = TouristGroupMainPanel.selectedTouristGroup;
		initData();
		initComp();
	}

	public void initData() {
		tourController = new TourController();
		layout = new GroupLayout(this);
		
		lblId = new JLabel("Mã");
		txtId = new JTextField(TG.getId()!=null ? TG.getId().toString() : "");

		lblName = new JLabel("Tên đoàn");
		txtName= new JTextField(TG.getName());

		lblDepatureDate = new JLabel("Ngày khởi hành");
		txtDepatureDate= new JTextField(TG.getDepatureDate() != null ? sdf.format(TG.getDepatureDate()) : "");
		btnDepatureDate = new JButton(Resources.CALENDAR_ICON_S);
		pnlDepatureDate = new JPanel(new FlowLayout(0,0,0));
		
		lblEnDate = new JLabel("Ngày kết thúc");
		txtEndDate= new JTextField(TG.getEndDate() !=null ? sdf.format(TG.getEndDate()) : "");
		btnEndDate = new JButton(Resources.CALENDAR_ICON_S);
		pnlEndDate = new JPanel(new FlowLayout(0,0,0));
		
		lblStatus = new JLabel("Trạng thái");
		cbxStatus = new JComboBox<String>(Resources.TOURIST_GROUP_STATUSES);

		lblTourName = new JLabel("Tour");
		cbxTourName = new JComboBox<String>();
		List<Tour> tours = tourController.getAll();
		tours.forEach(tour->{
			cbxTourName.addItem(tour.getId()+ ". " + tour.getName());
			if(tour.getId() == TG.getTourId())
				cbxTourName.setSelectedItem( tour.getId() + ". "  + tour.getName() );
		});
		
		lblRevenue = new JLabel("Doanh thu");
		txtRevenueValue = new JTextField(TG.getRevenue() != null ? TG.getRevenue().toString() : "0");
		btnModifyRevenue = new JButton("Cập nhât");
		pnlRevenue = new JPanel(new FlowLayout(0, 0, 0));
	}
	
	public void initComp() {
		txtId.setEditable(false);
		txtId.setBackground(Resources.PRIMARY);
				
		cbxStatus.setSelectedItem(TG.getStatus());
		
		txtDepatureDate.setPreferredSize(Resources.INPUT_TYPE_DATE);
		btnDepatureDate.setPreferredSize(Resources.SQUARE_XXS);
		btnDepatureDate.setBackground(Resources.PRIMARY_DARK);
		btnDepatureDate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				txtDepatureDate.setText(new DatePicker().getPickedDate("yyyy-MM-dd"));
			}
		});
		pnlDepatureDate.setBackground(Resources.PRIMARY);
		pnlDepatureDate.add(txtDepatureDate);
		pnlDepatureDate.add(btnDepatureDate);
		
		
		txtEndDate.setPreferredSize(Resources.INPUT_TYPE_DATE);
		btnEndDate.setPreferredSize(Resources.SQUARE_XXS);
		btnEndDate.setBackground(Resources.PRIMARY_DARK);
		btnEndDate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				txtEndDate.setText(new DatePicker().getPickedDate("yyyy-MM-dd"));
			}
		});
		pnlEndDate.setBackground(Resources.SECONDARY);
		pnlEndDate.add(txtEndDate);
		pnlEndDate.add(btnEndDate);

		btnModifyRevenue.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String tourName = cbxTourName.getSelectedItem().toString();
				Long tourIdSelected = Long.valueOf(tourName.substring(0, tourName.lastIndexOf(".")));
				Double revenueTemp = new ModifyTourCostDialog(TG.getRevenue(), tourIdSelected)
						.modifyTouristGroupRevenue();
				TG.setRevenue(revenueTemp);
				txtRevenueValue.setText(revenueTemp.toString());
			}
		});
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblId)
						.addComponent(lblTourName)
						.addComponent(lblRevenue)
						.addComponent(lblName)
						.addComponent(lblDepatureDate)
						.addComponent(lblEnDate)
						.addComponent(lblStatus))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtId)
						.addComponent(cbxTourName)
						.addGroup(layout.createSequentialGroup()
								.addComponent(txtRevenueValue)
								.addComponent(btnModifyRevenue))
						.addComponent(txtName)
						.addGroup(layout.createSequentialGroup()
								.addComponent(txtDepatureDate)
								.addComponent(btnDepatureDate))
						.addGroup(layout.createSequentialGroup()
								.addComponent(txtEndDate)
								.addComponent(btnEndDate))
						.addComponent(cbxStatus))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblId)
						.addComponent(txtId))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblTourName)
						.addComponent(cbxTourName))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblRevenue)
						.addComponent(txtRevenueValue)
						.addComponent(btnModifyRevenue))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblName)
						.addComponent(txtName))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblDepatureDate)
						.addComponent(txtDepatureDate)
						.addComponent(btnDepatureDate))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblEnDate)
						.addComponent(txtEndDate)
						.addComponent(btnEndDate))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblStatus)
						.addComponent(cbxStatus))
				);
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
	}
	
	public static Boolean commitToSelectedTouristGroup() {
		TouristGroup TG=TouristGroupMainPanel.selectedTouristGroup;
		if(txtName.getText().equals(""))
			return false;
		if(!Validation.checkDate(txtDepatureDate.getText()))
			return false;
		if(!Validation.checkDate(txtEndDate.getText()))
			return false;
		try {
			if( Resources.simpleDateFormat.parse(txtDepatureDate.getText())
					.after(Resources.simpleDateFormat.parse(txtEndDate.getText())))
				return false;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if(cbxStatus.getSelectedItem().toString().equals(""))
			return false;
		
		TG.setName(txtName.getText());
		try {
			TG.setDepatureDate(Resources.simpleDateFormat.parse(txtDepatureDate.getText()));
			TG.setEndDate(Resources.simpleDateFormat.parse(txtEndDate.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		TG.setStatus(cbxStatus.getSelectedItem().toString());
		String tourName = cbxTourName.getSelectedItem().toString();
		TG.setTourId(Long.valueOf(tourName.substring(0, tourName.lastIndexOf("."))));
		TG.setRevenue(Double.valueOf( txtRevenueValue.getText()));
		return true;
	}
	
	public static void main(String[] args) {
		new TestFrame(new TouristGroupModify());
	}
}
