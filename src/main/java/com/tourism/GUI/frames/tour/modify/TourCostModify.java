package com.tourism.GUI.frames.tour.modify;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import com.tourism.BUS.TourController;
import com.tourism.BUS.TourCostController;
import com.tourism.DTO.Customer;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.tour.TourMainPanel;
import com.tourism.GUI.util.DatePicker;
import com.tourism.GUI.util.MessageDialog;
import com.tourism.service.Validation;

public class TourCostModify extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	GroupLayout layout;
	JDialog dialog;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	JLabel lblId;
	JTextField txtId;
	
	JLabel lblPrice;
	static JTextField txtPrice;
	
	JLabel lblTourId;
	public static JComboBox<String> cbxTourId;
	
	JLabel lblPriceFromTime;
	static JTextField txtPriceFromTime;
	JButton btnPriceFromTime;
	JPanel pnlPriceFromTime;
	
	JLabel lblPriceToTime;
	static JTextField txtPriceToTime;
	JButton btnPriceToTime;
	JPanel pnlPriceToTime;
	
	JButton btnSave;
	JButton btnCancel;
	
	
	public static TourCost tourCost;
	public static Tour tour;
	public static TourCostController tourCostController = new TourCostController();;
	TourController tourController;
	public TourCostModify() {
		tourCost = TourMainPanel.selectedTourCost;
		initData();
		initComp();
	}
	
	public TourCostModify(long id) {
		TourCostModify.tourCost = tourCostController.getById(id);
		initData();
		initComp();
	}
	
	public void initData() {
		tourController = new TourController();
		layout = new GroupLayout(this);
		dialog = new JDialog();
		
		lblId = new JLabel("Mã giá Tour");
		txtId = new JTextField(tourCost.getId()!=null ? tourCost.getId().toString() : "");
		
		lblPrice = new JLabel("Giá tiền");
		txtPrice= new JTextField(tourCost.getPrice()!=null ? tourCost.getPrice().toString() : "");
		
		lblTourId = new JLabel("Tour");
		cbxTourId = new JComboBox<String>();
		tourController.getAll().forEach(tour->{
			cbxTourId.addItem(tour.getId()+ ". " + tour.getName());
			if(tour.getId() == tourCost.getTourId())
				cbxTourId.setSelectedItem( tour.getId() + ". "  + tour.getName() );
		});
		
		lblPriceFromTime = new JLabel("Giá áp dụng từ ngày");
		txtPriceFromTime= new JTextField(tourCost.getPriceFromTime() != null ? sdf.format(tourCost.getPriceFromTime() ) : "");
		btnPriceFromTime = new JButton(Resources.CALENDAR_ICON);
		pnlPriceFromTime = new JPanel(new FlowLayout(0,0,0));
		
		lblPriceToTime= new JLabel("Đến ngày");
		txtPriceToTime= new JTextField(tourCost.getPriceToTime() !=null ? sdf.format(tourCost.getPriceToTime()) : "");
		btnPriceToTime = new JButton(Resources.CALENDAR_ICON);
		pnlPriceToTime = new JPanel(new FlowLayout(0,0,0));
		if(tourCost.getId() != null) {
			cbxTourId.setEnabled(false);
		}
		btnSave = new JButton("Lưu");		
		btnCancel = new JButton("Hủy");
	}
	
	public void initComp() {
		txtId.setEditable(false);
		txtId.setBackground(Resources.PRIMARY);
		txtPriceFromTime.setPreferredSize(Resources.INPUT_TYPE_DATE);
		btnPriceFromTime.setPreferredSize(Resources.SQUARE_XXS);
		btnPriceFromTime.setBackground(Resources.PRIMARY_DARK);
		btnPriceFromTime.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				txtPriceFromTime.setText(new DatePicker().getPickedDate("yyyy-MM-dd"));
			}
		});
		pnlPriceFromTime.setBackground(Resources.PRIMARY);
		pnlPriceFromTime.add(txtPriceFromTime);
		pnlPriceFromTime.add(btnPriceFromTime);
		
		txtPriceToTime.setPreferredSize(Resources.INPUT_TYPE_DATE);
		btnPriceToTime.setPreferredSize(Resources.SQUARE_XXS);
		btnPriceToTime.setBackground(Resources.PRIMARY_DARK);
		btnPriceToTime.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				txtPriceToTime.setText(new DatePicker().getPickedDate("yyyy-MM-dd"));
			}
		});
		pnlPriceToTime.setBackground(Resources.PRIMARY);
		pnlPriceToTime.add(txtPriceToTime);
		pnlPriceToTime.add(btnPriceToTime);
		
		btnSave.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(!TourCostModify.commitToSelectedTourCost()) {
					new MessageDialog("Thông tin nhập không hợp lệ!");
					return;
				}
				tourCostController.save(TourMainPanel.selectedTourCost);
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
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblId)
						.addComponent(lblTourId)
						.addComponent(lblPrice)
						.addComponent(lblPriceFromTime)
						.addComponent(lblPriceToTime)
						.addComponent(btnSave))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtId)
						.addComponent(cbxTourId)
						.addComponent(txtPrice)
						.addComponent(pnlPriceFromTime)
						.addComponent(pnlPriceToTime)	
						.addComponent(btnCancel)
						)
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblId)
						.addComponent(txtId))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblTourId)
						.addComponent(cbxTourId))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblPrice)
						.addComponent(txtPrice))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblPriceFromTime)
						.addComponent(pnlPriceFromTime))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblPriceToTime)
						.addComponent(pnlPriceToTime)
						)				
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(btnSave)
						.addComponent(btnCancel))
				);
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
		dialog.add(this);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setModal(true);
		dialog.setVisible(true);
	}
	
	public static Boolean commitToSelectedTourCost() {
		TourCost tourCost = TourMainPanel.selectedTourCost;
		if(txtPrice.getText().equals("")) {
			return false;
		}
		if(!Validation.checkText(txtPrice.getText())) {
			new MessageDialog("Không được nhập số tiền bằng chữ!");
			return false;
		}
		int checkPrice = Integer.parseInt(txtPrice.getText());
		if((checkPrice % 100000 != 0) || !(checkPrice >= 500000)) {
			new MessageDialog("Số tiền không hợp lệ! Giá tour phải lớn hơn 500.000 VND");
			return false;
		}
		if(!Validation.checkDate(txtPriceFromTime.getText()))
			return false;
		if(!Validation.checkDate(txtPriceToTime.getText()))
			return false;
		try {
			if( Resources.simpleDateFormat.parse(txtPriceFromTime.getText())
					.after(Resources.simpleDateFormat.parse(txtPriceToTime.getText())))
				return false;
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if(cbxTourId.getSelectedItem().toString().equals("")) {
			return false;
		}
		tourCost.setPrice(Double.parseDouble(txtPrice.getText()));
		try {
			tourCost.setPriceFromTime(Resources.simpleDateFormat.parse(txtPriceFromTime.getText()));
			tourCost.setPriceToTime(Resources.simpleDateFormat.parse(txtPriceToTime.getText()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String tourName = cbxTourId.getSelectedItem().toString();
		tourCost.setTourId(Long.valueOf(tourName.substring(0, tourName.lastIndexOf("."))));
		return true;
	}
	
	public Optional<TourCost> addCustomerToTouristGroup(){
		return tourCost!= null ? Optional.of(tourCost) : Optional.empty();
	}
	
}
