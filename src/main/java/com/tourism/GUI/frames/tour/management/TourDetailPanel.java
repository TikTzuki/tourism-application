package com.tourism.GUI.frames.tour.management;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;

import com.tourism.BUS.TourController;
import com.tourism.BUS.TourCostController;
import com.tourism.BUS.TypeController;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.DTO.Type;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.tour.TourMainPanel;
import com.tourism.GUI.frames.tour.modify.TourCostModify;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.ConfirmDialog;

public class TourDetailPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass().getName());
	GroupLayout layout;
	
	JLabel lblId;
	JTextField txtId;
	
	JLabel lblTypeTour;
	JTextField txtTypeTour;
	
	JLabel lblName;
	JTextField txtName;

	JLabel lblPriceFromTime;
	JTextField txtPriceFromTime;

	JLabel lblPriceToTime;
	JTextField txtPriceToTime;

	JLabel lblStatus;
	JTextField txtStatus;
	
	JLabel lblCost;
	JComboBox<String> cbxCost;
	
	JLabel lblDecription;
	JTextField txtDecription;
	
	JLabel lblLocationCount;
	JTextField txtLocationCount;

	JButton btnAddPrice;
	JButton btnModify;
	JButton btnAdd;
	JButton btnModifyPrice;
	
	static TourController tourController;
	static TypeController typeController;
	static TourCostController tourCostController;
	List<TourCost> tourCosts = new ArrayList<>();
	
	Tour tour;
	TourCost tourCost;
	public TourDetailPanel() {
		//tourCost = TourMainPanel.selectedTourCost;
		initData();
		initComp();
	}
	
	public void initData() {
		tourController = new TourController();
		typeController = new TypeController();
		tourCostController = new TourCostController();
		layout = new GroupLayout(this);
		lblId = new JLabel("Mã Tour");
		txtId = new JTextField();
		
		lblTypeTour = new JLabel("Loại Tour");
		txtTypeTour= new JTextField();
		
		lblName = new JLabel("Tên Tour");
		txtName = new JTextField();

		lblPriceFromTime = new JLabel("Áp dụng từ");
		txtPriceFromTime = new JTextField();
		
		lblPriceToTime = new JLabel("Đến ngày");
		txtPriceToTime = new JTextField();

		lblStatus= new JLabel("Trạng thái");
		txtStatus = new JTextField();
		
		lblCost= new JLabel("Giá Tour");
		cbxCost = new JComboBox<String>();

		lblLocationCount= new JLabel("Số địa điểm");
		txtLocationCount = new JTextField();

		lblDecription= new JLabel("Mô tả");
		txtDecription = new JTextField();

		btnAdd = new JButton("Thêm Tour");
		btnModify= new JButton("Sửa thông tin");
		btnAddPrice = new JButton("Thêm giá Tour");
		btnModifyPrice = new JButton("Sửa giá Tour");
		
		
	}
	
	public void initComp() {		
		JTextField[] txtField = new JTextField[] {txtId, txtName, txtPriceFromTime, txtPriceToTime, txtTypeTour, txtStatus, txtLocationCount, txtDecription};
		for(JTextField txt: txtField) {
			txt.setEditable(false);
			txt.setBackground(Resources.PRIMARY);
			txt.setBorder(BorderFactory.createLineBorder(Resources.PRIMARY_DARK, 1, true));
		}
		
		btnAdd.setBackground(Resources.PRIMARY_DARK);
		btnAdd.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				TourMainPanel.initCreatorPanel();
			}
		});	
		
		btnModify.setBackground(Resources.PRIMARY_DARK);
		btnModify.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(TourMainPanel.selectedTour != null && TourMainPanel.selectedTour.getId() != null)
					TourMainPanel.initModifyPanel();
			}
		});
		
		btnAddPrice.setBackground(Resources.PRIMARY_DARK);
		btnAddPrice.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				TourMainPanel.initCreatorTourCostPanel();
			}
		});
		
		btnModifyPrice.setBackground(Resources.PRIMARY_DARK);
		btnModifyPrice.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(TourMainPanel.selectedTourCost != null && TourMainPanel.selectedTourCost.getId() != null)	
					TourMainPanel.initModifyTourCostPanel();				
			}
		});
				
		cbxCost.addActionListener (new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				TourDetailPanel detailPanel = TourManager.detailPanel;
				detailPanel.cbxCost.addItem("");
				if(!detailPanel.cbxCost.getSelectedItem().equals("")) {					
					String seclectedString = detailPanel.cbxCost.getSelectedItem().toString();
					Long id = Long.valueOf(seclectedString.substring(7, seclectedString.lastIndexOf("-")));
					// Lấy id TourCost đã chọn
					List<TourCost> tourCostss = tourCostController.getAll();

					for( TourCost tourCost : tourCostss) {
						if(tourCost.getId() == id) {
							TourMainPanel.selectedTourCost = tourCost;
						}	
					}
					
					String date1 = tourCostController.getPriceFromTime(id);
					String date2 = tourCostController.getPriceToTime(id);
					java.util.Date priceFromTime = null;
					java.util.Date priceToTime = null;
					try {
						priceFromTime = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
						priceToTime = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					detailPanel.txtPriceFromTime.setText(Resources.simpleDateFormat.format(priceFromTime));
					detailPanel.txtPriceToTime.setText(Resources.simpleDateFormat.format(priceToTime));						
				}
				detailPanel.cbxCost.removeItem("");
//				Long id = Long.parseLong(detailPanel.cbxCost.getSelectedItem().toString());
			}					
		
		});
		
		this.setLayout(layout);
		this.setBackground(Resources.PRIMARY);
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblId)
						.addComponent(lblName)
						.addComponent(lblTypeTour))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtId)
						.addComponent(txtName)
						.addComponent(txtTypeTour))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblDecription)
						.addComponent(lblStatus)
						.addComponent(lblLocationCount))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtDecription)
						.addComponent(txtStatus)
						.addComponent(txtLocationCount))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblCost)
						.addComponent(lblPriceFromTime)
						.addComponent(lblPriceToTime)
						)
				.addGroup(layout.createParallelGroup()
						.addComponent(cbxCost)
						.addComponent(txtPriceFromTime)
						.addComponent(txtPriceToTime)
						)
				.addGap(ImageObserver.FRAMEBITS)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnAdd)
						.addComponent(btnModify)
						.addComponent(btnAddPrice)
						.addComponent(btnModifyPrice))
				);
		
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblId)
								.addComponent(txtId)
								.addComponent(lblDecription)
								.addComponent(txtDecription)
								.addComponent(lblCost)
								.addComponent(cbxCost)
								)
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblName)
								.addComponent(txtName)
								.addComponent(lblStatus)
								.addComponent(txtStatus)
								.addComponent(lblPriceFromTime)
								.addComponent(txtPriceFromTime))
						.addGroup(layout.createParallelGroup(Alignment.CENTER)
								.addComponent(lblTypeTour)
								.addComponent(txtTypeTour)
								.addComponent(lblLocationCount)
								.addComponent(txtLocationCount)
								.addComponent(lblPriceToTime)
								.addComponent(txtPriceToTime))
						)
				.addGroup(layout.createSequentialGroup()
								.addComponent(btnAdd)
								.addGap(ImageObserver.FRAMEBITS)
								.addComponent(btnModify)
								.addGap(ImageObserver.FRAMEBITS)
								.addComponent(btnAddPrice)
								.addGap(ImageObserver.FRAMEBITS)
								.addComponent(btnModifyPrice))
				);
	}
	
	static public void reload() {
		Tour tour = TourMainPanel.selectedTour;
		TourDetailPanel detailPanel = TourManager.detailPanel;
		detailPanel.txtId.setText(tour.getId().toString());
		detailPanel.txtName.setText(tour.getName().toString());
		detailPanel.txtStatus.setText(tour.getStatus().toString());
		detailPanel.txtTypeTour.setText(typeController.getNameById(tour.getTypeId()));
		detailPanel.txtDecription.setText(tour.getDescription().toString());
		detailPanel.txtLocationCount.setText(tour.getLocations().size()+"");
		List<TourCost> tourCosts = tourCostController.findAllByTourId(tour.getId());
		detailPanel.cbxCost.removeAllItems();
		if (!tourCosts.isEmpty()) {
			for (TourCost tourCost : tourCosts) {
				detailPanel.cbxCost.addItem("Mã giá:"+tourCost.getId()+"-Giá: ["+tourCost.getPrice().toString()+"]");
			}
		}
		else {
			detailPanel.txtPriceFromTime.setText("");
			detailPanel.txtPriceToTime.setText("");
		}
	}
}
