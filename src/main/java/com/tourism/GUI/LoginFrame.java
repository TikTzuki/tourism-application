package com.tourism.GUI;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.tourism.BUS.UserController;
import com.tourism.DTO.User;
import com.tourism.GUI.util.MessageDialog;

public class LoginFrame extends JFrame{
	UserController userController;
	JLabel lblLogin;
	JLabel lblBackground;
	
	JTextField txtUserName;
	
	JPasswordField pwfPassword;
	
	JButton btnLogin;
	JPanel pnlContent;
	JFrame mainFrame;
	public LoginFrame(){
		initData();
		initComp();
	};
	
	public void initData() {
		userController = new UserController();
		lblLogin = new JLabel("Đăng nhập", JLabel.CENTER);
		lblBackground = new JLabel(Resources.LOGIN_BACKGROUND);
	
		txtUserName = new JTextField();
		
		pwfPassword = new JPasswordField();
		btnLogin = new JButton("Đăng nhập");
		pnlContent = new JPanel(null);
	}
	
	public void initComp() {
		lblLogin.setBounds(0, 50, 300, 50);
		lblLogin.setFont(new Font("Arial", Font.BOLD, 30));
		lblLogin.setForeground(Resources.SECONDARY_DARK);
		pnlContent.add(lblLogin);
		
		txtUserName.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Resources.SECONDARY_DARK), "Số điện thoại", 0, 1));
		txtUserName.setBounds(50, 150, 200, 50);
		txtUserName.setForeground(Resources.SECONDARY_DARK);
		txtUserName.setBackground(Resources.PRIMARY);
		pnlContent.add(txtUserName);
		
		pwfPassword.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Resources.SECONDARY), "Mật khẩu", 0, 1));
		pwfPassword.setBounds(50, 250, 200, 50);
		pwfPassword.setForeground(Resources.SECONDARY);
		pwfPassword.setBackground(Resources.PRIMARY_DARK);
		pnlContent.add(pwfPassword);
		
		btnLogin.setBounds(70, 370, 150, 45);
		btnLogin.setBackground(Resources.PRIMARY_DARK);
		btnLogin.setForeground(Resources.SECONDARY);
		btnLogin.setFont(new Font("Arial", Font.BOLD, 20));
		btnLogin.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				login();
			}
		});
		pnlContent.add(btnLogin);
		
		lblBackground.setBounds(0, 0,300, 500);
		pnlContent.add(lblBackground);
		lblBackground.setBounds(0, 0,300, 500);
		pnlContent.setPreferredSize(new Dimension(300, 500));
		this.setContentPane(pnlContent);
		this.setVisible(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void login() {
		txtUserName.getText();
		pwfPassword.getPassword();
		User user = userController.login(txtUserName.getText(), pwfPassword.getPassword());
		if( user == null ) {
			new MessageDialog("Thông tin đăng nhập sai");
		} else {
			this.dispose();
			mainFrame = new MainFrameV2(user);
		}
	}
	
	public static void main(String[] args) {
		LoginFrame loginFrame = new LoginFrame();
	}
}
