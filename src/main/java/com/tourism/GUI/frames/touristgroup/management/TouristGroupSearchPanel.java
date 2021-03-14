package com.tourism.GUI.frames.touristgroup.management;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.text.ParseException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.tourism.BUS.TourController;
import com.tourism.BUS.TouristGroupController;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.DatePicker;


public class TouristGroupSearchPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	GroupLayout layout;
	
	JLabel lblTitle;
	
	JLabel lblId;
	JTextField txtId;
	JPanel pnlId;
	
	JLabel lblName;
	JTextField txtName;
	JPanel pnlName;
	
	JLabel lblTourName;
	JComboBox<String> cbxTourName;
	JPanel pnlTourName;
	
	JLabel lblDepatureDate;
	JTextField txtDepatureDate;
	JButton btnDepatureDate;
	JPanel pnlDepatureDate;
	
	JLabel lblEndDate;
	JTextField txtEndDate;
	JButton btnEndDate;
	JPanel pnlEndDate;
	
	JLabel lblStatus;
	JComboBox<String> cbxStatus;
	JPanel pnlStatus;
	
	JButton btnCreate;
	JButton btnSearch;
	
	TouristGroupController touristGroupController = new TouristGroupController();
	TourController tourController = new TourController();
	public TouristGroupSearchPanel() {
		initData();
		initComp();
	}
	
	public void initData() {	
		layout = new GroupLayout(this);
		
		lblTitle = new JLabel("TÌM KIẾM");
		
		lblId = new JLabel("Mã đoàn");
		txtId = new JTextField();
		pnlId = new JPanel();
		
		lblName = new JLabel("Tên đoàn");
		txtName = new JTextField();
		pnlName = new JPanel();
		
		lblTourName = new JLabel("Tên tour");
		cbxTourName = new JComboBox<String>();
		pnlTourName = new JPanel();
		
		lblDepatureDate = new JLabel("Ngày khởi hành");
		txtDepatureDate = new JTextField();
		btnDepatureDate = new JButton(Resources.CALENDAR_ICON);
		pnlDepatureDate = new JPanel();
		
		lblEndDate = new JLabel("Ngày kết thúc");
		txtEndDate = new JTextField();
		btnEndDate = new JButton(Resources.CALENDAR_ICON);
		pnlEndDate = new JPanel();
		
		lblStatus = new JLabel("Trạng thái");
		cbxStatus = new JComboBox<String>(Resources.TOURIST_GROUP_STATUSES);
		pnlStatus = new JPanel();

		btnCreate = new JButton("Thêm đoàn");
		btnSearch = new JButton("Tìm");
	}
	
	public void initComp() {
		lblTitle.setFont(Resources.H3Regular);
		cbxTourName.addItem("");
		cbxTourName.setSelectedItem("");
		tourController.getAll().forEach(tour -> {
			cbxTourName.addItem(tour.getId() + ". " + tour.getName());
		});
		
		cbxStatus.addItem("");
		cbxStatus.setSelectedItem("");
		
		JComponent[] compInputs = {txtId, txtName,cbxTourName, txtDepatureDate, txtEndDate, cbxStatus};
		for (JComponent comp : compInputs) {
			comp.setPreferredSize(Resources.INPUT_SEARCH_TEXTFIELD);
		}
		
		btnDepatureDate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				txtDepatureDate.setText(new DatePicker().getPickedDate("yyyy-MM-dd"));
			}
		});
		btnDepatureDate.setBackground(Resources.PRIMARY_DARK);
		
		btnEndDate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				txtEndDate.setText(new DatePicker().getPickedDate("yyyy-MM-dd"));
			}
		});
		btnEndDate.setBackground(Resources.PRIMARY_DARK);
		
		btnSearch.setBackground(Resources.PRIMARY_DARK);
		btnSearch.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				TouristGroup search= new TouristGroup();
				search.setId(txtId.getText().equals("") ? null : Long.valueOf(txtId.getText()));
				search.setName(txtName.getText());
				String tourName = cbxTourName.getSelectedItem().toString();
				search.setTourId(tourName.equals("") ? null : Long.valueOf(tourName.substring(0, tourName.indexOf("."))));
				try {
					search.setDepatureDate(txtDepatureDate.getText().equals("") ? null : Resources.simpleDateFormat.parse( txtDepatureDate.getText()));
					search.setEndDate(txtEndDate.getText().equals("") ? null : Resources.simpleDateFormat.parse(txtEndDate.getText()) );
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String status = cbxStatus.getSelectedItem().toString();
				search.setStatus(status.equals("") ? null : status);
				TouristGroupMainPanel.touristGroups = touristGroupController.search(search);
				TouristGroupManager.reloadManagerTable();
			}
		});
		
		btnCreate.setBackground(Resources.PRIMARY_DARK);
		btnCreate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				TouristGroupMainPanel.initCreatorPanel();
			}
		});
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblTitle))
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup()
								.addComponent(lblId)
								.addComponent(lblName)
								.addComponent(lblTourName))
						.addGroup(layout.createParallelGroup()
								.addComponent(txtId)
								.addComponent(txtName)
								.addComponent(cbxTourName))
						.addGroup(layout.createParallelGroup()
								.addComponent(lblDepatureDate)
								.addComponent(lblEndDate)
								.addComponent(lblStatus))
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup()
												.addComponent(txtDepatureDate)
												.addComponent(txtEndDate))
										.addGroup(layout.createParallelGroup()
												.addComponent(btnDepatureDate, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
												.addComponent(btnEndDate, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)))
								.addComponent(cbxStatus))
						.addGap(ImageObserver.FRAMEBITS)
						.addGroup(layout.createParallelGroup()
								.addComponent(btnCreate)
								.addComponent(btnSearch))
						)
				);
		
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblTitle)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblId)
								.addComponent(txtId)
								.addComponent(lblDepatureDate)
								.addComponent(txtDepatureDate)
								.addComponent(btnDepatureDate))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblName)
								.addComponent(txtName)
								.addComponent(lblEndDate)
								.addComponent(txtEndDate)
								.addComponent(btnEndDate))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblTourName)
								.addComponent(cbxTourName)
								.addComponent(lblStatus)
								.addComponent(cbxStatus))
						)
				.addGap(100)
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnCreate)
						.addGap(ImageObserver.FRAMEBITS)
						.addComponent(btnSearch)
						)
				);
	}
}
