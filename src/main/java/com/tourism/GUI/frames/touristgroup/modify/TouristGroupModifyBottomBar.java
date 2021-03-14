package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.tourism.BUS.TouristGroupController;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.ConfirmDialog;
import com.tourism.GUI.util.MessageDialog;

public class TouristGroupModifyBottomBar extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	JButton btnSave;
	JButton btnDelete;
	JButton btnCancel;
	GroupLayout layout;
	TouristGroupController touristGroupController = new TouristGroupController();
	
	public TouristGroupModifyBottomBar() {
		initData();
		initComp();
	}
	
	public void initData() {
		layout = new GroupLayout(this);
		btnSave = new JButton("Lưu");
		btnDelete = new JButton("Xóa");
		btnCancel = new JButton("Hủy");
	}
	
	public void initComp() {
		btnCancel.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				TouristGroupMainPanel.initManagerPanel();
			}
		});
		btnCancel.setBackground(Resources.SECONDARY);
		btnCancel.setForeground(Resources.PRIMARY);
		
		btnSave.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(!TouristGroupBasicModifyPanel.commitToSelectedTouristGroup()) {
					return;}
				touristGroupController.saveWithRelationships(TouristGroupMainPanel.selectedTouristGroup);
				TouristGroupMainPanel.initManagerPanel();
			}
		});
		btnSave.setBackground(Resources.SECONDARY);
		btnSave.setForeground(Resources.PRIMARY);
		
		btnDelete.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(TouristGroupMainPanel.selectedTouristGroup != null && TouristGroupMainPanel.selectedTouristGroup.getId() != null) {
					if(new ConfirmDialog("Xác nhận xóa").confirm())
						touristGroupController.changeStatusToDeleted(TouristGroupMainPanel.selectedTouristGroup);
					TouristGroupMainPanel.initManagerPanel();
				}
			}
		});
		btnDelete.setBackground(Resources.PRIMARY_DARK);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING,layout.createSequentialGroup()
						.addContainerGap(0, Short.MAX_VALUE)
						.addComponent(btnSave, Resources.INPUT_WIDTH_XS, Resources.INPUT_WIDTH_XS, Resources.INPUT_WIDTH_XS)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnCancel, Resources.INPUT_WIDTH_XS, Resources.INPUT_WIDTH_XS, Resources.INPUT_WIDTH_XS )
						.addGap(0, 0, Short.MAX_VALUE)
						.addComponent(btnDelete)));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(btnSave, Resources.SQUARE_EDGE_XS, Resources.SQUARE_EDGE_XS, Resources.SQUARE_EDGE_XS)
						.addComponent(btnCancel, Resources.SQUARE_EDGE_XS, Resources.SQUARE_EDGE_XS, Resources.SQUARE_EDGE_XS)
						.addComponent(btnDelete, Resources.SQUARE_EDGE_XS, Resources.SQUARE_EDGE_XS, Resources.SQUARE_EDGE_XS)
						));
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
	}
}
