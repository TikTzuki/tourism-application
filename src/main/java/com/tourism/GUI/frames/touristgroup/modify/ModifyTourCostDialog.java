package com.tourism.GUI.frames.touristgroup.modify;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tourism.BUS.TourController;
import com.tourism.BUS.TourCostController;
import com.tourism.DAL.TourCostRepository;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.service.Validation;

public class ModifyTourCostDialog {
	List<TourCost> tourCosts; 
	Double currentRevenue;
	Tour tour;
	JDialog dialog;
	JPanel pnl;
	GroupLayout layout;

	JLabel lblDate;
	JComboBox<String> cbxDate;

	JLabel lblCost;
	JLabel lblCostValue;

	JLabel lblCustomer;
	JLabel lblCustomerValue;

	JLabel lblRevenue;
	JLabel lblRevenueValue;
	
	JButton btnSave;
	JButton btnCancel;

	public ModifyTourCostDialog(Double revenue, Long tourId) {
		this.currentRevenue = revenue;
		this.tour = new TourController().getById(tourId);
		initData();
		initComp();
	}

	public void initData() {
		this.tourCosts = new TourCostController().getByTourIdSortFromNow(tour.getId());
	dialog = new JDialog();
	pnl = new JPanel();
	layout = new GroupLayout(pnl);

	lblDate = new JLabel("Thời gian: ");
	cbxDate = new JComboBox<String>();

	lblCost = new JLabel("Giá tour: ");
	lblCostValue = new JLabel(tourCosts.size()>0 ? tourCosts.get(0).getPrice().toString() : "0");

	lblCustomer = new JLabel("Khách hàng: ");
	lblCustomerValue = new JLabel(TouristGroupMainPanel.selectedTouristGroup.getCustomers().size()+"");

	lblRevenue = new JLabel("Doanh thu");
	lblRevenueValue = new JLabel(this.currentRevenue.toString());
	
	btnSave = new JButton("Lưu");
	btnCancel = new JButton("Hủy");
	}

	public void initComp() {

		tourCosts.forEach(tourCost->{
			cbxDate.addItem("Từ: " + tourCost.getPriceFromTime() + "  Đến: " + tourCost.getPriceToTime());
		});
		
		cbxDate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lblCostValue.setText(
						tourCosts.get(
								cbxDate.getSelectedIndex()
						).getPrice().toString()
				);
				Double newRevenue = Double.valueOf(lblCostValue.getText()) * Double.valueOf(lblCustomerValue.getText());
				System.out.println(newRevenue);
				lblRevenueValue.setText(
						newRevenue.toString()
				);
			}
		});
		
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				currentRevenue = Double.valueOf(lblRevenueValue.getText()); 
				dialog.dispose();
			}
		});
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dialog.dispose();
			}
		});
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addComponent(lblDate)
				.addComponent(cbxDate)
				.addComponent(lblCost)
				.addComponent(lblCostValue)
				.addComponent(lblCustomer)
				.addComponent(lblCustomerValue)
				.addComponent(lblRevenue)
				.addComponent(lblRevenueValue)
				.addComponent(btnSave)
				.addComponent(btnCancel));
		
		layout.setVerticalGroup(layout.createParallelGroup()
				.addComponent(lblDate)
				.addComponent(cbxDate)
				.addComponent(lblCost)
				.addComponent(lblCostValue)
				.addComponent(lblCustomer)
				.addComponent(lblCustomerValue)
				.addComponent(lblRevenue)
				.addComponent(lblRevenueValue)
				.addComponent(btnSave)
				.addComponent(btnCancel));
		pnl.setBackground(Resources.PRIMARY_DARK);
		pnl.setLayout(layout);
		dialog.add(pnl);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public Double modifyTouristGroupRevenue() {
		return  currentRevenue;
	}
	public static void main(String[] args) {
	}
}
