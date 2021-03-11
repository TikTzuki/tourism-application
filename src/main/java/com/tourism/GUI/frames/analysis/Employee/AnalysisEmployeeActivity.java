package com.tourism.GUI.frames.analysis.Employee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.AnalysisController;
import com.tourism.DTO.Employee;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.util.DatePicker;

public class AnalysisEmployeeActivity extends JPanel {
	AnalysisController analysisController;
	List<Employee> employees;
	JLabel lblStartDate;
	JTextField txtStartDate;
	JButton btnStartDate;
	
	JLabel lblEndDate;
	JTextField txtEndDate;
	JButton btnEndDate;
	
	JButton btnAnalysis;
	JPanel pnlAnalysis;
	
	DefaultTableModel model;
	JTable tbl;
	JScrollPane scroller;
	JPanel pnlTable;
	GroupLayout layout;
	public AnalysisEmployeeActivity() {
		initData();
		initComp();
	}
	
	public void initData() {
	analysisController = new AnalysisController();
	employees = new ArrayList<>();
	lblStartDate = new JLabel("Từ:");
	txtStartDate = new JTextField();
	btnStartDate = new JButton(Resources.CALENDAR_ICON);
	
	lblEndDate = new JLabel("Đến:");
	txtEndDate = new JTextField();
	btnEndDate = new JButton(Resources.CALENDAR_ICON);
	
	btnAnalysis = new JButton("Thống kê");
	pnlAnalysis = new JPanel();
	
	model = new DefaultTableModel(new Object[] {"Mã", "Tên", "Sđt", "Địa chỉ", "Số lần đi tour"}, 0);
	tbl = new CustomTable(model);
	scroller = new JScrollPane(tbl);
	pnlTable = new JPanel(new BorderLayout());
	layout = new GroupLayout(this);
	}
	
	public void initComp() {
		// Activity Panel
		txtStartDate.setPreferredSize(Resources.INPUT_TYPE_DATE);
		txtStartDate.setText( Resources.simpleDateFormat.format( (new Date().getTime() + 1000*60*60*24*30) ));
		btnStartDate.setPreferredSize(Resources.SQUARE_XXS);
		btnStartDate.setBackground(Resources.PRIMARY_DARK);
		btnStartDate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				txtStartDate.setText(new DatePicker().getPickedDate("yyyy-MM-dd"));
			}
		});
		
		txtEndDate.setText(Resources.simpleDateFormat.format(new Date()));
		txtEndDate.setPreferredSize(Resources.INPUT_TYPE_DATE);
		btnEndDate.setPreferredSize(Resources.SQUARE_XXS);
		btnEndDate.setBackground(Resources.PRIMARY_DARK);
		btnEndDate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				txtEndDate.setText(new DatePicker().getPickedDate("yyyy-MM-dd"));
			}
		});
		
		btnAnalysis.setPreferredSize(Resources.RECTANGLE_XXS);
		btnAnalysis.setBackground(Resources.PRIMARY_DARK);
		btnAnalysis.setForeground(Resources.SECONDARY_DARK);
		btnAnalysis.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				loadTable();
			}
		});
		pnlAnalysis.add(lblStartDate);
		pnlAnalysis.add(txtStartDate);
		pnlAnalysis.add(btnStartDate);
		pnlAnalysis.add(lblEndDate);
		pnlAnalysis.add(txtEndDate);
		pnlAnalysis.add(btnEndDate);
		pnlAnalysis.add(btnAnalysis);
		pnlAnalysis.setBackground(Resources.PRIMARY);
		//Table
		
		loadTable();
		pnlTable.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlTable.add(scroller, BorderLayout.CENTER);
		pnlTable.setBackground(Resources.PRIMARY);
		//Group layout
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(pnlAnalysis)
				.addComponent(pnlTable));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(pnlAnalysis, Resources.INPUT_HEIGHT_XXL, Resources.INPUT_HEIGHT_XXL, Resources.INPUT_HEIGHT_XXL)
				.addComponent(pnlTable));
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY_DARK);
		this.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.MAIN_CONTENT_HEIGHT-Resources.INPUT_HEIGHT_L));
	}
	
	public void loadTable() {
		try {
			employees = analysisController.getEmployeeActivity(
					Resources.simpleDateFormat.parse(txtStartDate.getText()),
					Resources.simpleDateFormat.parse(txtEndDate.getText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.setRowCount(0);
		employees.forEach(emp->{
			model.addRow(new Object[] {
					emp.getId(),
					emp.getName(),
					emp.getPhoneNumber(),
					emp.getAddress1(),
					emp.getTouristGroups().size()});
		});
		tbl.getParent().revalidate();
		tbl.repaint();
	}
}
