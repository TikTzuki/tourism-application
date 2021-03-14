package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.TouristGroupController;
import com.tourism.DAL.TouristGroupCostRepository;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.DTO.TouristGroupCostItem;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.ConfirmDialog;
import com.tourism.GUI.util.MessageDialog;

public class TouristGroupCostTable extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TouristGroup TG;
	
	GroupLayout layout;
	JLabel lblHotelList;
	
	JButton btnAdd;
	
	JPanel pnlSelectedCost;
	JLabel lblSelectedCostId;
	JLabel lblSeletecHotel;
	
	JButton btnModify;
	JButton btnRemove;
	
	JScrollPane scroller;
	JTable tbl;
	DefaultTableModel model;
	TouristGroupController touristGroupController;
	TouristGroupCost selectedTouristGroupCost;
	public TouristGroupCostTable() {
		TG = TouristGroupMainPanel.selectedTouristGroup;
		initData();
		initComp();
	}
	
	public void initData() {
		touristGroupController = new TouristGroupController();
		layout = new GroupLayout(this);
		model = new DefaultTableModel(new Object[] {"Mã", "Tổng", "Chi tiết"} , 0);
		
		lblHotelList = new JLabel("Danh sách chi phí");
		
		btnAdd = new JButton(Resources.ADD_ICON);
		
		pnlSelectedCost = new JPanel();
		lblSeletecHotel = new JLabel("Chi phí:");
		lblSelectedCostId = new JLabel();
		
		btnModify = new JButton("Sửa");
		btnRemove = new JButton("Xóa");
		
		tbl = new CustomTable(model);
		scroller = new JScrollPane(tbl);
	}
	
	public void initComp() {
		btnAdd.setBackground(Resources.PRIMARY);
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				Optional<TouristGroupCost> opt = new AddTouristGroupCostToTouristGroupDialog().addCostToTouristGroup();
				opt.ifPresent(cost-> {
					TG.getTouristGroupCosts().add(cost);
				});
				loadTable();
			}
		});
		
		pnlSelectedCost.add(lblSeletecHotel);
		pnlSelectedCost.add(lblSelectedCostId);
		pnlSelectedCost.setBackground(Resources.PRIMARY);
		
		btnModify.setBackground(Resources.PRIMARY_DARK);
		btnModify.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				selectedTouristGroupCost = new TouristGroupCostRepository().findById(Long.parseLong(lblSelectedCostId.getText())).get();
				selectedTouristGroupCost = new AddTouristGroupCostToTouristGroupDialog(selectedTouristGroupCost).modifyCostTouristGroup();

				TouristGroupMainPanel.selectedTouristGroup.getTouristGroupCosts().removeIf(
						cost->(cost.getId()==selectedTouristGroupCost.getId()));
				TouristGroupMainPanel.selectedTouristGroup.getTouristGroupCosts().add(selectedTouristGroupCost);
				
				loadTable();
			}
		});
		btnRemove.setBackground(Resources.PRIMARY_DARK);
		btnRemove.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(new ConfirmDialog("Xóa khỏi danh sách?").confirm()) {
					Long costId = Long.valueOf(lblSelectedCostId.getText());
					TouristGroupMainPanel.selectedTouristGroup.getTouristGroupCosts().removeIf(
							cost->(cost.getId() == costId ));
					loadTable();
				}
			}
		});
		
		loadTable();
		
		tbl.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				String costId = tbl.getValueAt(tbl.getSelectedRow(), 0).toString();
				lblSelectedCostId.setText(costId);
			}
		});
		tbl.setBackground(Resources.PRIMARY);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblHotelList))
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAdd, Resources.SQUARE_EDGE_XXS ,Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
						.addContainerGap()
						.addComponent(pnlSelectedCost)
						.addComponent(btnModify)
						.addComponent(btnRemove))
				.addComponent(scroller));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(lblHotelList)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnAdd, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS, Resources.SQUARE_EDGE_XXS)
						.addComponent(pnlSelectedCost)
						.addComponent(btnModify)
						.addComponent(btnRemove))
				.addComponent(scroller));
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
	}
	
	public void loadTable() {
		model.setRowCount(0);
		if(TouristGroupMainPanel.selectedTouristGroup.getTouristGroupCosts() != null )
			TouristGroupMainPanel.selectedTouristGroup.getTouristGroupCosts().forEach(cost ->{
				String description = "";
				for(TouristGroupCostItem item: cost.getDescription()) {
					description+=item.getName()+" | Gía "+item.getCost()+" | SL: "+item.getQuantity()+"\n";
				}
				model.addRow(new Object[] {
						cost.getId(), cost.getTotalPrice(), description
				});
			});;
	}
}
