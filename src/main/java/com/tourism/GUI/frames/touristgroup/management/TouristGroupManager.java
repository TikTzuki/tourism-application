package com.tourism.GUI.frames.touristgroup.management;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TestFrame;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;

public class TouristGroupManager extends JPanel {
	private static final long serialVersionUID = 1L;
	static TouristGroupDetailPanel detailPanel;
	static TouristGroupSearchPanel searchPanel;
	static TouristGroupTablePanel managerTable;
	static JPanel mainContent;
	static GroupLayout layout;
	
	public TouristGroupManager() {
		initData();
		initComp();
	}
	
	public void initData() {
		detailPanel = new TouristGroupDetailPanel();
		searchPanel = new TouristGroupSearchPanel();
		managerTable = new TouristGroupTablePanel();
		layout = new GroupLayout(this);
	}
	
	public void initComp() {
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addComponent(detailPanel)
				.addComponent(searchPanel)
				.addComponent(managerTable));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(detailPanel, 100, Resources.DETAIL_HEIGHT, Resources.DETAIL_HEIGHT)
				.addComponent(searchPanel)
				.addComponent(managerTable));
		
		this.setLayout(layout);
		this.setPreferredSize(Resources.MAIN_CONTENT);
		this.setBackground(Resources.PRIMARY_DARK);
	}
	
	public static void reloadManagerTable() {
		TouristGroupTablePanel temp = new TouristGroupTablePanel();
		layout.replace(managerTable, temp);
		managerTable = temp;
;		managerTable.getParent().revalidate();
		managerTable.repaint();
	}
	
}
