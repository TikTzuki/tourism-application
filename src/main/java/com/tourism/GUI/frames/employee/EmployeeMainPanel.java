package com.tourism.GUI.frames.employee;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.tourism.BUS.EmployeeController;
import com.tourism.DTO.Employee;
import com.tourism.GUI.CustomTable;
import com.tourism.GUI.Resources;
import com.tourism.GUI.util.ConfirmDialog;
import com.tourism.GUI.util.MessageDialog;
import com.tourism.service.Validation;

public class EmployeeMainPanel extends JPanel{
	String ERROR_MESSAGE;
	EmployeeController employeeController; 
	static Employee selectedEmployee;
	JPanel pnlEmployee;
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

	public EmployeeMainPanel() {
		initData();
		initComp();
	}
	
	public void initData() {
	employeeController = new EmployeeController();
	selectedEmployee = new Employee();
	
	pnlEmployee = new JPanel();
	layout = new GroupLayout(pnlEmployee);
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
		txtId.setEditable(false);
		btnCreate.setBackground(Resources.PRIMARY_DARK);
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {			
				if(!validateInput()) {
					new MessageDialog(ERROR_MESSAGE);
					return;
				}
				commitSelectedEmployee();
				employeeController.createEmployee(selectedEmployee);
				loadTable();
			}
		});
		btnDelete.setBackground(Resources.PRIMARY_DARK);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!validateInput()) {
					new MessageDialog(ERROR_MESSAGE);
					return;
				}
				if(new ConfirmDialog("Bạn chắc chắn muốn xóa?").confirm()) {				
					commitSelectedEmployee();
					employeeController.deleteEmployee(selectedEmployee.getId());
					loadTable();
				}
			}
		});
		
		btnSave.setBackground(Resources.PRIMARY_DARK);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(!validateInput()) {
					new MessageDialog(ERROR_MESSAGE);
					return;				
				}
				commitSelectedEmployee();
				employeeController.modifyEmployee(selectedEmployee);
				loadTable();
			}
		});
		
		btnClear.setBackground(Resources.PRIMARY_DARK);
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				selectedEmployee = new Employee();
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
				selectedEmployee = employeeController.getById(selectedId);
				txtId.setText(selectedEmployee.getId().toString());
				txtName.setText(selectedEmployee.getName());
				txtIdentityCard.setText(selectedEmployee.getIdentityCard());
				txtGender.setText(selectedEmployee.getGender());
				txtAddress.setText(selectedEmployee.getAddress1());
				txtPhoneNumber.setText(selectedEmployee.getPhoneNumber());
			}
		});
		
		pnlEmployee.setBackground(Resources.PRIMARY);
		pnlEmployee.setLayout(layout);
		pnlEmployee.setPreferredSize(Resources.DETAIL_PANEL_S);
	
		pnlTable.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlTable.add(scroller, BorderLayout.CENTER);
		pnlTable.setPreferredSize(Resources.MANAGER_TABLE_PANEL);
		pnlTable.setBackground(Resources.PRIMARY);
		this.setBackground(Resources.PRIMARY_DARK);
		this.setPreferredSize(Resources.MAIN_CONTENT);
		this.add(pnlEmployee);
		this.add(pnlTable);
	}

	public void commitSelectedEmployee() {
		selectedEmployee.setId(!txtId.getText().equals("") ? Long.valueOf(txtId.getText()) : null );
		selectedEmployee.setName(txtName.getText());
		selectedEmployee.setIdentityCard(txtIdentityCard.getText());
		selectedEmployee.setAddress1(txtAddress.getText());
		selectedEmployee.setGender(txtGender.getText());
		selectedEmployee.setPhoneNumber(txtPhoneNumber.getText());
	}
	
	public boolean validateInput() {
		if(!Validation.checkPhone_Number(txtPhoneNumber.getText()) || txtPhoneNumber.equals(null)) {
			ERROR_MESSAGE = "Số điện thoại không đúng";
			return false;
			}
		return true;
	}
	
	public void loadTable() {
		model.setRowCount(0);		
		for (Iterator<Employee> itr = employeeController.getAllNotDelete().iterator(); itr.hasNext();) {
			Employee emp = itr.next();
			model.addRow(new Object[]{emp.getId(), emp.getName(), emp.getIdentityCard(), emp.getAddress1(), emp.getGender(), emp.getPhoneNumber()});
		}
	}
	public static void main(String[] args) {
		new TestFrame();
	}

}

class TestFrame extends JFrame {
	
	public TestFrame() {
		initComp();
	}
	
	public void initComp() {
		this.add(new EmployeeMainPanel());
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}