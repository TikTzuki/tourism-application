package com.tourism.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import com.tourism.DTO.User;
import com.tourism.GUI.frames.analysis.AnalysisMainPanel;
import com.tourism.GUI.frames.customer.CustomerMainPanel;
import com.tourism.GUI.frames.employee.EmployeeMainPanel;
import com.tourism.GUI.frames.tour.TourMainPanel;
import com.tourism.GUI.frames.touristgroup.TouristGroupMainPanel;
import com.tourism.GUI.util.IconUtil;

import lombok.Data;

/**
 * MainFrame
 */
public class MainFrame extends JFrame {
	static final long serialVersionUID = 4L;
	private User user;
	// Left side menu
	private JPanel pnlLeftSideMenu;
	private JPanel pnlTopBar;
	JLabel functionSelected;
	String titleMenuItems[];
	ImageIcon iconMenuItems[];
	MenuItem pnlMenuItems[];

	// Array Jpanel nội dung chính
	JPanel[] pnlMainContents;
	public static JPanel pnlTourManager;
	public static JPanel pnlTouristGroupManager;
	public static JPanel pnlAnalysis;
	public static JPanel pnlEmployeeManager;
	public static JPanel pnlCustomerManager;
	JLayeredPane layeredContent = new JLayeredPane();

	public MainFrame(User user) {
		super();
		this.user = user; 
		initData();
		initComp();
	}
	
	public void initData() {
		user = new User(null, "Long", "09456345", "password");
		// Left side menu
		pnlLeftSideMenu = new JPanel(new FlowLayout(FlowLayout.CENTER, -2, -2));
		pnlTopBar = new JPanel(null);
		functionSelected = new JLabel("--------\\/--------");
		
		titleMenuItems = new String[] {"Thống kê", "Quản lý tour", "Quản lý đoàn", "Quản lý nhân viên", "Quản lý khách hàng"};
		iconMenuItems = new ImageIcon[] {Resources.DASHBOARD, Resources.TRAVEL, Resources.NETWORK, Resources.EMPLOYEE, Resources.TRAVELER};
		pnlMenuItems = new MenuItem[titleMenuItems.length];
		// Array Jpanel nội dung chính
		pnlTourManager = new TourMainPanel();
		pnlTouristGroupManager = new TouristGroupMainPanel();
		pnlAnalysis = new AnalysisMainPanel();
		pnlEmployeeManager = new EmployeeMainPanel();
		pnlCustomerManager = new CustomerMainPanel();
		pnlMainContents = new JPanel[] { pnlAnalysis, pnlTourManager, pnlTouristGroupManager, pnlEmployeeManager, pnlCustomerManager };
		layeredContent = new JLayeredPane();
	}
	
	public void initComp() {
		setLayout(new BorderLayout());

		// Left side menu
		pnlLeftSideMenu.setPreferredSize(new Dimension(200, 600));
		pnlLeftSideMenu.setBackground(Resources.PRIMARY_DARK);
		Dimension dimensionMenuItem = new Dimension(204, 50);

		
		for (int i = 0; i < titleMenuItems.length; i++) {
			pnlMenuItems[i] = new MenuItem(titleMenuItems[i], iconMenuItems[i]);
			pnlMenuItems[i].setBackground(Resources.PRIMARY_DARK);
			pnlMenuItems[i].setPreferredSize(dimensionMenuItem);
			int selectedPanelIndex = i;
			pnlMenuItems[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					switchPanel(selectedPanelIndex);
				}
			});
			pnlLeftSideMenu.add(pnlMenuItems[i]);
		}
		// Top mennu
		ImageIcon iconApp = Resources.TOURISM_LOGO; 
		JLabel lblLogo = new JLabel(iconApp);
		lblLogo.setBounds(20, 0, 160, 50);

		// Selected
		functionSelected = new JLabel("--------\\\\/--------");
		functionSelected.setFont(Resources.H3Regular);
		functionSelected.setForeground(Resources.SECONDARY_DARK);
		functionSelected.setBounds(650, 0, 300, 50);
		// User
		JLabel lblUser = new JLabel("Hello, " + user.getName());
		lblUser.setBounds(1150, 0, 200, 50);

		pnlTopBar.add(lblLogo);
		pnlTopBar.add(functionSelected);
		pnlTopBar.add(lblUser);
		pnlTopBar.setBackground(Resources.PRIMARY_DARK);
		pnlTopBar.setPreferredSize(new Dimension(1100, 50));

		this.add(pnlLeftSideMenu, BorderLayout.WEST);
		this.add(pnlTopBar, BorderLayout.NORTH);
		// Main content
		layeredContent.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		layeredContent.setPreferredSize(new Dimension(1110, 700));

		for (int i = 0; i < pnlMainContents.length; i++) {
			this.layeredContent.add(pnlMainContents[i], pnlMainContents.length - i, 0);
		}
		
		this.setResizable(false);
		this.add(layeredContent, BorderLayout.CENTER);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void switchPanel(int selectPanelIndex) {
		
		for (int i = 0; i < pnlMenuItems.length; i++) {
			if (i == selectPanelIndex) {
				pnlMenuItems[i].setSelectedState();
				functionSelected.setText(pnlMenuItems[i].getTitle());
				continue;
			}
			pnlMenuItems[i].setUnselectedState();
		}

		layeredContent.removeAll();
		layeredContent.add(pnlMainContents[selectPanelIndex]);
		layeredContent.repaint();
		layeredContent.revalidate();
	}

	public static void main(String[] args) {
		MainFrame main = new MainFrame(new User(Long.valueOf(1), "Long", "094545", "123456"));
	}
}

@Data
class MenuItem extends JPanel{
	String title;
	ImageIcon icon;
	JLabel label;
	
	public MenuItem(String title, ImageIcon icon) {
		super(new GridLayout());
		this.title = title;
		this.icon = icon;
		initComp();
	}
	
	public void initComp() {
		label = new JLabel(this.title, this.icon, JLabel.CENTER);
		label.setForeground(Resources.SECONDARY_LIGHT);
		this.add(label);
	}
	
	public void setSelectedState() {
		label.setForeground(Resources.SECONDARY_DARK);
	}
	
	public void setUnselectedState() {
		label.setForeground(Resources.SECONDARY_LIGHT);
	}
}