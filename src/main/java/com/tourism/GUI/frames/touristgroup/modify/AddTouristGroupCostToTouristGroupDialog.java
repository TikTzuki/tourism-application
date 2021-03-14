package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.TouristGroupCostController;
import com.tourism.DAL.CostTypeRepository;
import com.tourism.BUS.TouristGroupController;
import com.tourism.DTO.TouristGroupCost;
import com.tourism.DTO.TouristGroupCostItem;
import com.tourism.DTO.CostType;
import com.tourism.DTO.TouristGroup;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.MainFrame;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AddTouristGroupCostToTouristGroupDialog {
	JDialog dialog;
	JPanel pnl;
	GroupLayout layout;
	
	JLabel lblTotalCost;
	JTextField txtTotalCost;
	
	JLabel lblDescription;
	JComboBox<String> cbxCostType;
	List<CostItem> costItems;
	JTextField txtQuantity;
	JButton btnAddCostType;
	JButton btnDeleteCostType;
	JPanel pnlAddCostType;
	DefaultTableModel model;
	JTable tblDescription;
	JScrollPane scroller;
	
	JButton btnAdd;
	JButton btnCancel;
	
	TouristGroupCostController touristGroupCostController;
	TouristGroupController touristGroupController;
	TouristGroupCost selectedTouristGroupCost;
	
	public AddTouristGroupCostToTouristGroupDialog() {
		this.selectedTouristGroupCost = new TouristGroupCost();
		initData();
		initComp();
	}
	
	public AddTouristGroupCostToTouristGroupDialog(TouristGroupCost cost) {
		this.selectedTouristGroupCost = cost;
		initData();
		initComp();
	}
	
	private void initData() {
		dialog = new JDialog();
		pnl = new JPanel();
		layout = new GroupLayout(pnl);
		
		lblTotalCost =new JLabel("Tổng chi phí");
		txtTotalCost = new JTextField();

		lblDescription = new JLabel("Chi tiết hóa đơn:");
		cbxCostType = new JComboBox<String>();
		costItems = new ArrayList<CostItem>();
		txtQuantity = new JTextField("0");
		btnAddCostType = new JButton(Resources.ADD_ICON_S);
		btnDeleteCostType = new JButton("Xóa #");
		pnlAddCostType = new JPanel(new FlowLayout(FlowLayout.LEADING));
		model = new DefaultTableModel(new Object[] {"Phí", "Giá", "#", "Thành tiền"}, 0);
		tblDescription = new CustomTable(model);
		scroller = new JScrollPane(tblDescription);
		
		btnAdd = new JButton("Lưu");
		btnCancel = new JButton("Hủy");
		
		touristGroupCostController = new TouristGroupCostController();
		touristGroupController = new TouristGroupController();
	}
	
	private void initComp() {
		if(selectedTouristGroupCost.getId()!=null) {
			txtTotalCost.setText(selectedTouristGroupCost.getTotalPrice().toString());
			selectedTouristGroupCost.getDescription().forEach(item->{
				model.addRow(new Object[] {item.getName(), item.getCost(), item.getQuantity(), item.getQuantity()*item.getCost()});
			});
		}
	
		txtQuantity.setPreferredSize(new Dimension(60,24));
		pnlAddCostType.add(cbxCostType);
		pnlAddCostType.add(txtQuantity);
		pnlAddCostType.add(btnAddCostType);
		pnlAddCostType.add(new JPanel());
		pnlAddCostType.add(btnDeleteCostType, -1);
		pnlAddCostType.setBackground(Resources.PRIMARY);
		
		List<CostType> costTypes = new CostTypeRepository().findAll();
		costTypes.forEach(costType->{
			costItems.add(new CostItem(costType.getName(), costType));
			cbxCostType.addItem(costType.getName() + " : " + costType.getDescription());
		});
		
		btnAddCostType.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int selectedIndex = cbxCostType.getSelectedIndex();
				CostItem item = costItems.get(selectedIndex);
				Double totalCost = Double.valueOf(0);
				model.addRow(new Object[] {
						item.getItem().getName(), 
						item.getItem().getDescription(),
						txtQuantity.getText(),
						Double.valueOf(item.item.getDescription())*Double.valueOf(txtQuantity.getText())});
				
				txtTotalCost.setText(calTotalCost().toString());
			}
		});
		
		btnDeleteCostType.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String btnTitle = btnDeleteCostType.getText();
				int indexForDelete = Integer.valueOf(btnTitle.substring(btnTitle.indexOf("#")+1));
				model.removeRow(indexForDelete);
				txtTotalCost.setText(calTotalCost().toString());
			}
		});
		
		tblDescription.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			int selectedRow = tblDescription.getSelectedRow();
			btnDeleteCostType.setText("Xóa #"+selectedRow);
			}
		});
		
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				selectedTouristGroupCost.setTouristGroupId(TouristGroupMainPanel.selectedTouristGroup.getId());
				List<TouristGroupCostItem> costItems = new ArrayList<TouristGroupCostItem>();
				Double totalCost = Double.valueOf(0);
				for(int i=0; i<model.getRowCount(); i++) {
					costItems.add(new TouristGroupCostItem(
							tblDescription.getValueAt(i, 0).toString(),
							Double.valueOf(tblDescription.getValueAt(i, 1).toString()),
							Integer.valueOf(tblDescription.getValueAt(i, 2).toString())
							));
					totalCost += Double.valueOf(tblDescription.getValueAt(i, 3).toString());
				}
				selectedTouristGroupCost.setDescription(costItems);
				selectedTouristGroupCost.setTotalPrice(Double.valueOf(txtTotalCost.getText()));
				dialog.dispose();
			}
		});
		
		btnCancel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				dialog.dispose();
			}
		});
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(pnlAddCostType))
				.addComponent(lblDescription)
				.addComponent(scroller)
				.addGroup(layout.createSequentialGroup()
						.addComponent(lblTotalCost)
						.addComponent(txtTotalCost))
				.addGroup(layout.createSequentialGroup()
						.addComponent(btnAdd)
						.addComponent(btnCancel))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(pnlAddCostType, Resources.INPUT_HEIGHT_M, Resources.INPUT_HEIGHT_M, Resources.INPUT_HEIGHT_M))
				.addComponent(lblDescription)
				.addComponent(scroller)
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblTotalCost)
						.addComponent(txtTotalCost,Resources.INPUT_HEIGHT_S, Resources.INPUT_HEIGHT_S, Resources.INPUT_HEIGHT_S))
				.addGroup(layout.createParallelGroup()
						.addComponent(btnAdd)
						.addComponent(btnCancel))
				);
		
		pnl.setBackground(Resources.SECONDARY);
		pnl.setPreferredSize(Resources.SQUARE_XXL);
		pnl.setLayout(layout);
		dialog.add(pnl);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public Optional<TouristGroupCost> addCostToTouristGroup(){
		selectedTouristGroupCost = touristGroupCostController.create(selectedTouristGroupCost);
		return selectedTouristGroupCost!=null ? Optional.of(selectedTouristGroupCost) : Optional.empty();
	}
	
	public TouristGroupCost modifyCostTouristGroup(){
		selectedTouristGroupCost = touristGroupCostController.modify(selectedTouristGroupCost);
		return selectedTouristGroupCost;
	}
	
	public Double calTotalCost() {
		Double total = Double.valueOf(0);
		for(int i=0; i<model.getRowCount(); i++) {
			Double quantity = Double.valueOf(model.getValueAt(i, 2).toString());
			Double cost = Double.valueOf(model.getValueAt(i, 1).toString());
			total+=quantity*cost;
		}
		return Double.valueOf(total);
	}
	public static void main(String[] args) {
		new AddTouristGroupCostToTouristGroupDialog();
	}
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class CostItem{
	String  name;
	CostType item;
}