package com.tourism.GUI.frames.analysis.TourOperationSituation;

import java.awt.Dimension;
import java.awt.ScrollPane;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.AnalysisController;
import com.tourism.BUS.TourController;
import com.tourism.BUS.TouristGroupController;
import com.tourism.DTO.Tour;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;//ThỐng kê tour có ba

public class TourOperationSituation extends JPanel {
	AnalysisController analysisController = new AnalysisController();
	TourController tourController = new TourController();
	JLabel lblSelectedTour;
	JLabel lblSelectedTourValue;
	JButton btnDetail;
	JPanel pnlDetail;
	
	DefaultTableModel model;
	JTable tbl;
	JScrollPane scroller;
	
	public TourOperationSituation() {
		initData();
		initComp();
	}

	public void initData() {
	lblSelectedTour = new JLabel("Tour :");
	lblSelectedTourValue = new JLabel();
	btnDetail = new JButton("Chi tiết");
	pnlDetail = new JPanel();
	
	model = new DefaultTableModel(new Object[] {"#", "Tour", "Số đoàn đi", "Doanh thu", "Chi phí", "Lãi"}, 0);
	tbl = new CustomTable(model);
	scroller = new JScrollPane(tbl);
	}	
	public void initComp() {
		List<Object[]> rows = analysisController.getTourOperations();
		rows.forEach(row ->{
			model.addRow(row);
		});
		
		btnDetail.setBackground(Resources.SECONDARY);
		btnDetail.setForeground(Resources.PRIMARY);
		btnDetail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				TourOperationSituationMain.initDetail(TourOperationSituationMain.selectedTour);
			}
		});
		pnlDetail.add(lblSelectedTour);
		pnlDetail.add(lblSelectedTourValue);
		pnlDetail.add(btnDetail);
		pnlDetail.setBackground(Resources.PRIMARY);
		pnlDetail.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.INPUT_HEIGHT_L));
		
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int selectedRow = tbl.getSelectedRow();
				Long selectedId = Long.valueOf(model.getValueAt(selectedRow, 0).toString());
				TourOperationSituationMain.selectedTour = tourController.getById(selectedId);
				lblSelectedTourValue.setText(TourOperationSituationMain.selectedTour.getName());
			}
		});
		
		scroller.setPreferredSize(Resources.DETAIL_PANEL);
		scroller.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.DETAIL_HEIGHT_XXL));
		this.add(pnlDetail);
		this.add(scroller);
		this.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.MAIN_CONTENT_HEIGHT-Resources.INPUT_HEIGHT_L));
		this.setBackground(Resources.PRIMARY);
	}

}
