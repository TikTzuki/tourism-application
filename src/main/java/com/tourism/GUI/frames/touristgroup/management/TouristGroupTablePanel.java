package com.tourism.GUI.frames.touristgroup.management;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.DTO.TouristGroup;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;

public class TouristGroupTablePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	SimpleDateFormat sDateFormat = new SimpleDateFormat("dd-MM-yyyy");
	JTable tbl;
	DefaultTableModel model;
	JScrollPane scroller;

	public TouristGroupTablePanel() {
		initData();
		initComp();
	}

	public void initData() {

		model = new DefaultTableModel(new Object[] { "Mã", "Tên đoàn", "Ngày khởi hành",
				"Ngày kết thúc", "Trạng thái", "Doanh thu" ,"Tổng chi phí", "Lợi nhuận"}, 0);
		tbl = new CustomTable(model);
		scroller = new JScrollPane(tbl);
		
		TouristGroupMainPanel.touristGroups.forEach(TG -> {
			Double totalCost = Double.valueOf(0);
			if(!TG.getTouristGroupCosts().isEmpty())
				for(TouristGroupCost TGCost: TG.getTouristGroupCosts()) {
					totalCost+=TGCost.getTotalPrice();
				}
			model.addRow(new Object[] { TG.getId(), TG.getName(), TG.getDepatureDate(), TG.getEndDate(), TG.getStatus(), TG.getRevenue(), totalCost, TG.getRevenue()-totalCost});
		});
	}


	public void initComp() {
		tbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				Long selectedId = (Long) tbl.getValueAt(tbl.getSelectedRow() , 0);
				
				for(TouristGroup obj : TouristGroupMainPanel.touristGroups) {
					if(obj.getId() == selectedId) {
						TouristGroupMainPanel.selectedTouristGroup=obj;
					}
				}
				TouristGroupDetailPanel.reload();
			}
		});
		
		tbl.setPreferredSize(Resources.MANAGER_TABLE);
		scroller.setPreferredSize(Resources.MANAGER_TABLE_SCROLLER);
		add(scroller);
		setBackground(Resources.PRIMARY);
	}
}