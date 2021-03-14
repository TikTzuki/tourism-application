package com.tourism.GUI.frames.analysis.TourOperationSituation;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.AnalysisController;
import com.tourism.BUS.TourController;
import com.tourism.DTO.Tour;
import com.tourism.GUI.Resources;

public class TourOperationSituationDetail extends JPanel{
	AnalysisController analysisController;
	TourController tourController;
	Tour selectedTour;
	JComboBox<String> cbxTour;
	JButton btnChangeCurrentTour;
	JButton btnBack;
	JPanel pnlControl;
	
	DefaultTableModel model;
	JTable tbl;
	JScrollPane scroller;
	
	JLabel lblTotal;
	JLabel lblTotalRevenue;
	JLabel lblTotalRevenueValue;
	JLabel lblTotalCost;
	JLabel lblTotalCostValue;
	JLabel lblInterest;
	JLabel lblInterestValue;
	JPanel pnlBottom;
	
	public TourOperationSituationDetail(Tour selectedTour) {
		this.selectedTour = selectedTour;
		initData();
		initComp();
	}
	
	public void initData() {
		analysisController = new AnalysisController();
		tourController = new TourController();
		cbxTour = new JComboBox<String>();
	btnChangeCurrentTour = new JButton("Xem");
	btnBack = new JButton("Quay lại");
	pnlControl = new JPanel();
	
	model = new DefaultTableModel(new Object[] {"#", "Đoàn đi", "Số khách", "Giá tour", "Doanh thu", "Tổng chi phí", "Lãi"}, 0);
	tbl = new JTable(model);
	scroller = new JScrollPane(tbl);
	
	lblTotal = new JLabel("Tổng:    ");
	lblTotalRevenue = new JLabel("Doanh thu:");
	lblTotalRevenueValue = new JLabel("0");
	lblTotalCost = new JLabel("    Chi phí:");
	lblTotalCostValue = new JLabel("0");
	lblInterest = new JLabel("   Lãi:");
	lblInterestValue = new JLabel("0"); 
	pnlBottom = new JPanel();
	}
	
	public void initComp() {
		List<Tour> tours = tourController.getAll();
		tours.forEach(tour->{
			cbxTour.addItem(tour.getId() +". "+ tour.getName());
			if(tour.getId()==selectedTour.getId()) {
				cbxTour.setSelectedItem(tour.getId() + ". " + tour.getName());
			}
			});
		
		btnChangeCurrentTour.setBackground(Resources.SECONDARY);
		btnChangeCurrentTour.setForeground(Resources.PRIMARY);
		btnChangeCurrentTour.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				String selectedString = cbxTour.getSelectedItem().toString();
				Long selectedId = Long.valueOf(selectedString.substring(0, selectedString.indexOf(".")));
				selectedTour = tourController.getById(selectedId);
				loadTable();
			};
		});
		
		btnBack.setBackground(Resources.SECONDARY);
		btnBack.setForeground(Resources.PRIMARY);
		btnBack.addMouseListener(new MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
				TourOperationSituationMain.initContent();
			};
		});

		pnlControl.add(cbxTour);
		pnlControl.add(btnChangeCurrentTour);
		pnlControl.add(btnBack);
		pnlControl.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.INPUT_HEIGHT_L));
		pnlControl.setBackground(Resources.PRIMARY);
		
		loadTable();
		scroller.setPreferredSize(new Dimension(Resources.DETAIL_WIDTH, Resources.DETAIL_HEIGHT_XL));
		
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
		this.add(pnlControl);
		this.add(scroller);
		this.add(pnlBottom);
		this.setBackground(Resources.PRIMARY);
		this.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.MAIN_CONTENT_HEIGHT-Resources.INPUT_HEIGHT_L));
	}
	
	public void loadTable(){
		model.setRowCount(0);
		analysisController.getTourOperationDetails(selectedTour.getId()).forEach(row->{
			model.addRow(row);
		});;
		loadBottomBar();
	}
	
	public void loadBottomBar() {
		double totalRevenue = 0;
		double totalCost = 0;
		double totalInterest = 0;
		for(int i=0; i<model.getRowCount(); i++) {
			totalRevenue+= Double.valueOf(model.getValueAt(i, 4).toString());
			totalCost+= Double.valueOf(model.getValueAt(i, 5).toString());
			totalInterest+= Double.valueOf(model.getValueAt(i, 6).toString());
		}
		lblTotalRevenueValue.setText(totalRevenue+"");
		lblTotalCostValue.setText(totalCost+"");
		lblInterestValue.setText(totalInterest+"");
	}
}
