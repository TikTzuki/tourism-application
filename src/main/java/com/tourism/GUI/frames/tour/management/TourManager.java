package com.tourism.GUI.frames.tour.management;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import com.tourism.GUI.Resources;

public class TourManager extends JPanel {
	private static final long serialVersionUID = 1L;
	static TourDetailPanel detailPanel;
	static TourSearchPanel searchPanel;
	static TourTablePanel managerTable;
	static JPanel mainContent;
	static GroupLayout layout;
	
	public TourManager() {
		initData();
		initComp();
	}
	
	public void initData() {
		detailPanel = new TourDetailPanel();
		searchPanel = new TourSearchPanel();
		managerTable = new TourTablePanel();
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
	
	public static void ReloadManagerTable() {
		TourTablePanel temp = new TourTablePanel();
		layout.replace(managerTable, temp);
		managerTable = temp;
;		managerTable.getParent().revalidate();
		managerTable.repaint();
	}
}
