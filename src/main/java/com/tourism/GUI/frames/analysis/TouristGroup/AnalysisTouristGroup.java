package com.tourism.GUI.frames.analysis.TouristGroup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.modify.TouristGroupCustomerTable;
import com.tourism.GUI.util.DatePicker;

public class AnalysisTouristGroup extends JPanel{
	AnalysisController analysisController;
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
	
	JLabel lblTotal;
	JLabel lblTotalRevenue;
	JLabel lblTotalRevenueValue;
	JLabel lblTotalCost;
	JLabel lblTotalCostValue;
	JLabel lblInterest;
	JLabel lblInterestValue;
	JPanel pnlBottom;
	public AnalysisTouristGroup() {
		initData();
		initComp();
	}
	
	private void initData() {
		analysisController = new AnalysisController();
		lblStartDate = new JLabel("Từ:");
		txtStartDate = new JTextField();
		btnStartDate = new JButton(Resources.CALENDAR_ICON);
		
		lblEndDate = new JLabel("Đến:");
		txtEndDate = new JTextField();
		btnEndDate = new JButton(Resources.CALENDAR_ICON);
		
		btnAnalysis = new JButton("Thống kê");
		pnlAnalysis = new JPanel();
		
		model = new DefaultTableModel(new Object[] {"Mã", "Tên đoàn", "Tour", "Ngày khởi hành", "Ngày kết thúc", "Số khách", "Trạng thái", "Tổng thu", "Chi phí", "Lợi nhuận"}, 0); 
		tbl = new CustomTable(model);
		scroller = new JScrollPane(tbl);
		pnlTable = new JPanel(new BorderLayout());
		layout = new GroupLayout(this);
	
		lblTotal = new JLabel("Tổng:    ");
		lblTotalRevenue = new JLabel("Doanh thu:");
		lblTotalRevenueValue = new JLabel("0");
		lblTotalCost = new JLabel("    Chi phí:");
		lblTotalCostValue = new JLabel("0");
		lblInterest = new JLabel("   Lãi:");
		lblInterestValue = new JLabel("0"); 
		pnlBottom = new JPanel();
	}
	
	private void initComp() {
		txtStartDate.setPreferredSize(Resources.INPUT_TYPE_DATE);
		txtStartDate.setText(Resources.simpleDateFormat.format( (new Date().getTime() + 1000*60*60*24*30) ));
		btnStartDate.setPreferredSize(Resources.SQUARE_XXS);
		btnStartDate.setBackground(Resources.PRIMARY_DARK);
		btnStartDate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
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
				loadBottomBar();
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
		
		//BottomBar
		lblTotal.setFont(Resources.H3Regular);
		lblTotalRevenue.setFont(Resources.H3Regular);
		lblTotalRevenueValue.setFont(Resources.BlodNumber);
		lblTotalCost.setFont(Resources.H3Regular);
		lblTotalCostValue.setFont(Resources.BlodNumber);
		lblInterest.setFont(Resources.H3Regular);
		lblInterestValue.setFont(Resources.BlodNumber);
		pnlBottom.add(lblTotal);
		pnlBottom.add(lblTotalRevenue);
		pnlBottom.add(lblTotalRevenueValue);
		pnlBottom.add(lblTotalCost);
		pnlBottom.add(lblTotalCostValue);
		pnlBottom.add(lblInterest);
		pnlBottom.add(lblInterestValue);
		pnlBottom.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.INPUT_HEIGHT_L));
		pnlBottom.setBackground(Resources.PRIMARY);
		loadBottomBar();
		
		//Group layout
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(pnlAnalysis)
				.addComponent(pnlTable)
				.addComponent(pnlBottom));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(pnlAnalysis, Resources.INPUT_HEIGHT_XXL, Resources.INPUT_HEIGHT_XXL, Resources.INPUT_HEIGHT_XXL)
				.addComponent(pnlTable)
				.addComponent(pnlBottom));
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY_DARK);
		this.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.MAIN_CONTENT_HEIGHT-Resources.INPUT_HEIGHT_L));
	}
	
	public void loadTable() {
		List<Object[]> rows = new ArrayList<Object[]>();
		try {
			rows = analysisController.getTouristGroupActivity(
					Resources.simpleDateFormat.parse(txtStartDate.getText()), 
					Resources.simpleDateFormat.parse(txtEndDate.getText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		model.setRowCount(0);
		rows.forEach(row->{
			model.addRow(row);
		});
		tbl.getParent().revalidate();
		tbl.repaint();
	}
	
	public void loadBottomBar() {
		double totalRevenue = 0;
		double totalCost = 0;
		double totalInterest = 0;
		for(int i=0; i<model.getRowCount(); i++) {
			totalRevenue+= Double.valueOf(model.getValueAt(i, 7).toString());
			totalCost+= Double.valueOf(model.getValueAt(i, 8).toString());
			totalInterest+= Double.valueOf(model.getValueAt(i, 9).toString());
		}
		lblTotalRevenueValue.setText(totalRevenue+"");
		lblTotalCostValue.setText(totalCost+"");
		lblInterestValue.setText(totalInterest+"");
	}
}
