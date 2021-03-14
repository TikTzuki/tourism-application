package com.tourism.GUI.util;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageDialog {
	JDialog dialog;
	JLabel lblMessage;
	JButton btnYes;
	JPanel pnlContent;
	GroupLayout layout;
	
	public MessageDialog(String message) {
		dialog = new JDialog();
		dialog.setModal(true);
		
		lblMessage = new JLabel(message);
		btnYes = new JButton("OK");
		
		pnlContent = new JPanel();
		layout = new GroupLayout(pnlContent);

		btnYes.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				btnYes.getActionCommand();
				dialog.dispose();
			}
		});
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
				.addComponent(lblMessage)
				.addComponent(btnYes));
		layout.setVerticalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
						.addGap(20)
						.addComponent(lblMessage)
						.addGap(20)
						.addComponent(btnYes)));
		
		pnlContent.setLayout(layout);
		
		dialog.setContentPane(pnlContent);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MessageDialog("this isssssssssss");
	}
}
