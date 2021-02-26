package com.tourism.GUI.frames.customer;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.Session.SessionEventListener;
import com.mysql.cj.x.protobuf.MysqlxResultset.FetchDoneMoreOutParams;
import com.tourism.BUS.CustomerController;
import com.tourism.DTO.Customer;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.frames.touristgroup.modify.TouristGroupCustomerTable;
import com.tourism.GUI.util.MessageDialog;
import com.tourism.service.Validation;

public class CustomerMainPanel extends JPanel{
	CustomerController customerController;
	static Customer selectedCustomer;
	JPanel pnlCustomer;
	GroupLayout layout;
	JLabel lblId;
	JTextField txtId;
	JLabel lblName;
	JTextField txtName;
	JLabel lblIdentityCard;
	JTextField txtIdentityCard;
	JLabel lblAddress;
	JTextField txtAddress;
	JLabel lblGender;
	JTextField txtGender;
	JLabel lblPhoneNumber;
	JTextField txtPhoneNumber;
	
	JTable tbl;
	JScrollPane scroller;
	DefaultTableModel model;
	JPanel pnlTable;
	
	JButton btnCreate;
	JButton btnSave;
	JButton btnDelete;
	JButton btnClear;
	public CustomerMainPanel() {
		initData();
		initComp();
	}
	public void initData() {
		customerController = new CustomerController();
		selectedCustomer = new Customer();
		
		pnlCustomer = new JPanel();
		layout = new GroupLayout(pnlCustomer);
		lblId = new JLabel("ID");
		txtId = new JTextField();
		lblName = new JLabel("Tên");
		txtName = new JTextField();
		lblIdentityCard = new JLabel("CMND");
		txtIdentityCard = new JTextField();
		lblAddress = new JLabel("Địa chỉ");
		txtAddress = new JTextField();
		lblGender = new JLabel("Giới tính");
		txtGender = new JTextField();
		lblPhoneNumber = new JLabel("Số điện thoại");
		txtPhoneNumber = new JTextField();
		
		model = new DefaultTableModel(new Object[] {"Mã", "Tên", "CMND", "Địa chỉ", "Giới tính", "Số điện thoại"},0);
		tbl = new CustomTable(model);
		scroller = new JScrollPane(tbl);
		pnlTable = new JPanel(new BorderLayout());
		
		btnCreate = new JButton("Tạo mới");
		btnSave = new JButton("Sửa");
		btnDelete = new JButton("Xóa");
		btnClear = new JButton("Làm trống");
	}
	
	public void initComp() {
		btnCreate.setBackground(Resources.PRIMARY_DARK);
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!validateInput()) {
					new MessageDialog("ThÔng tin điền vào không hợp lệ");
					return;
				}
				commitSelectedCustomer();
				customerController.createCustomer(selectedCustomer);
				loadTable();
			}
		});
		
		btnDelete.setBackground(Resources.PRIMARY_DARK);
		btnDelete.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(!validateInput()) {
					new MessageDialog("Thông tin điền vào không hợp lệ");
					return;
				}
				commitSelectedCustomer();
				customerController.deleteCustomer(selectedCustomer.getId());
				loadTable();
			};
		});
		
		btnSave.setBackground(Resources.PRIMARY_DARK);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!validateInput()) {
					new MessageDialog("Thông tin điền vào không hợp lệ");
					return;
				}
				commitSelectedCustomer();
				customerController.modifyCustomer(selectedCustomer);
				loadTable();
			}
		});
		
		btnClear.setBackground(Resources.PRIMARY_DARK);
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectedCustomer = new Customer();
				txtId.setText(null);
				txtName.setText(null);
				txtIdentityCard.setText(null);
				txtAddress.setText(null);
				txtGender.setText(null);
				txtPhoneNumber.setText(null);
			}
		});
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(lblId)
						.addComponent(lblName))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtId)
						.addComponent(txtName))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblIdentityCard)
						.addComponent(lblAddress))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtIdentityCard)
						.addComponent(txtAddress))
				.addGroup(layout.createParallelGroup()
						.addComponent(lblGender)
						.addComponent(lblPhoneNumber))
				.addGroup(layout.createParallelGroup()
						.addComponent(txtGender)
						.addComponent(txtPhoneNumber))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(btnCreate, Resources.BUTTON_WIDTH, Resources.BUTTON_WIDTH, Resources.BUTTON_WIDTH)
						.addComponent(btnDelete, Resources.BUTTON_WIDTH, Resources.BUTTON_WIDTH, Resources.BUTTON_WIDTH))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(btnSave, Resources.BUTTON_WIDTH, Resources.BUTTON_WIDTH, Resources.BUTTON_WIDTH)
						.addComponent(btnClear, Resources.BUTTON_WIDTH, Resources.BUTTON_WIDTH, Resources.BUTTON_WIDTH))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblId)
						.addComponent(txtId)
						.addComponent(lblIdentityCard)
						.addComponent(txtIdentityCard)
						.addComponent(lblGender)
						.addComponent(txtGender)
						.addComponent(btnCreate)
						.addComponent(btnSave))
				.addGroup(layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblName)
						.addComponent(txtName)
						.addComponent(lblAddress)
						.addComponent(txtAddress)
						.addComponent(lblPhoneNumber)
						.addComponent(txtPhoneNumber)
						.addComponent(btnDelete)
						.addComponent(btnClear)));
	
		//Set model
		loadTable();
		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Long selectedId = Long.parseLong(tbl.getValueAt(tbl.getSelectedRow(), 0).toString());
				selectedCustomer = customerController.getById(selectedId);
				txtId.setText(selectedCustomer.getId().toString());
				txtName.setText(selectedCustomer.getName());
				txtIdentityCard.setText(selectedCustomer.getIdentityCard());
				txtGender.setText(selectedCustomer.getGender());
				txtAddress.setText(selectedCustomer.getAddress1());
				txtPhoneNumber.setText(selectedCustomer.getPhoneNumber());
			}
		});
		
		pnlCustomer.setBackground(Resources.PRIMARY);
		pnlCustomer.setLayout(layout);
		pnlCustomer.setPreferredSize(Resources.DETAIL_PANEL_S);
		
		pnlTable.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlTable.add(scroller, BorderLayout.CENTER);
		pnlTable.setPreferredSize(Resources.MANAGER_TABLE_PANEL);
		pnlTable.setBackground(Resources.PRIMARY);
		this.setBackground(Resources.PRIMARY_DARK);
		this.setPreferredSize(Resources.MAIN_CONTENT);
		this.add(pnlCustomer);
		this.add(pnlTable);
	}
	
	public void commitSelectedCustomer() {
		selectedCustomer.setId(!txtId.getText().equals("") ? Long.valueOf(txtId.getText()) : null);
		selectedCustomer.setName(txtName.getText());
		selectedCustomer.setIdentityCard(txtIdentityCard.getText());
		selectedCustomer.setAddress1(txtAddress.getText());
		selectedCustomer.setGender(txtGender.getText());
		selectedCustomer.setPhoneNumber(txtPhoneNumber.getText());
	}
	
	public boolean validateInput() {
		if(!Validation.checkDigit(txtId.getText())) {
			return false;
		}
		if(!Validation.checkPhone_Number(txtPhoneNumber.getText()) || txtPhoneNumber.equals(null)){
			return false;
		}
		return true;
	}
	public void loadTable() {
		model.setRowCount(0);
		for(Iterator<Customer> itr = customerController.getAll().iterator(); itr.hasNext() ;) {
			Customer customer = itr.next();
			model.addRow(new Object[] {customer.getId(), customer.getName(), customer.getIdentityCard(), customer.getAddress1(), customer.getGender(), customer.getPhoneNumber()});
		}
	}
}
