package com.tourism.GUI.frames.analysis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.analysis.Employee.AnalysisEmployeeActivity;
import com.tourism.GUI.frames.analysis.TourOperationSituation.TourOperationSituation;
import com.tourism.GUI.frames.analysis.TourOperationSituation.TourOperationSituationMain;
import com.tourism.GUI.frames.analysis.TouristGroup.AnalysisTouristGroup;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AnalysisMainPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	JComboBox<String> cbxAnalysis;
	JPanel pnlAnalyisCombobox;
	JPanel pnlMainContent;
	List<ComboItemPanel> comboItems; 
	GroupLayout layout;
	public AnalysisMainPanel() {
		super(new FlowLayout(FlowLayout.LEADING, 0 ,0));
		initData();
		initComp();
	}
	
	public void initData() {
		cbxAnalysis = new JComboBox<String>();
		pnlAnalyisCombobox = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		pnlMainContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		comboItems = new ArrayList<ComboItemPanel>();
		layout = new GroupLayout(this);
	}
	
	public void initComp() {
		comboItems.add(new ComboItemPanel("Số lần đi tour của nhân viên", new AnalysisEmployeeActivity()));
		comboItems.add(new ComboItemPanel("Doanh thu tour", new TourOperationSituationMain()));
		comboItems.add(new ComboItemPanel("Đoàn du lịch", new AnalysisTouristGroup()));
		comboItems.forEach(comboItem -> {
		cbxAnalysis.addItem(comboItem.getName());
		});
		cbxAnalysis.setForeground(Resources.SECONDARY_DARK);
		cbxAnalysis.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				comboItems.forEach(item -> {
					if( cbxAnalysis.getSelectedItem().equals(item.name)) {
						pnlMainContent.removeAll();
						pnlMainContent.add(item.getPanel());
						pnlMainContent.revalidate();
						pnlMainContent.repaint();
					}
				});
			}
		});
		pnlAnalyisCombobox.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.INPUT_HEIGHT_S));
		pnlAnalyisCombobox.add(cbxAnalysis);
		pnlAnalyisCombobox.setBackground(Resources.PRIMARY_DARK);
		
		pnlMainContent.add(comboItems.get(0).getPanel());
		pnlMainContent.setPreferredSize(new Dimension(Resources.MAIN_CONTENT_WIDTH, Resources.MAIN_CONTENT_HEIGHT-Resources.INPUT_HEIGHT_M));
		this.add(pnlAnalyisCombobox);
		this.add(pnlMainContent);
		this.setPreferredSize(Resources.MAIN_CONTENT);
	}
	
	public static void main(String[] args) {
		
	}
}

@Data
@AllArgsConstructor
class ComboItemPanel{
	String name;
	JPanel panel;
}