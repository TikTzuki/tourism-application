package com.tourism.GUI.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ConfirmDialog {
	
	JDialog dialog;
	JLabel lblMessage;
	JButton btnYes;
	JButton btnNo;
	Boolean answer;
	JPanel pnlContent;
	GroupLayout layout;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public ConfirmDialog(String message) {
		dialog = new JDialog();
		dialog.setModal(true);
		
		lblMessage = new JLabel(message);
		btnYes = new JButton("Có");
		btnNo = new JButton("Không");
		answer = false; 
		pnlContent = new JPanel();
		layout = new GroupLayout(pnlContent);
		
		btnYes.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				btnYes.getActionCommand();
				answer = true;
				dialog.dispose();
			}
		});
		
		btnNo.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				btnYes.getActionCommand();
				answer = false;
				dialog.dispose();
			}
		});
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup( layout.createParallelGroup(Alignment.CENTER)
						.addComponent(lblMessage)
						.addGroup(layout.createSequentialGroup()
								.addComponent(btnYes)
								.addGap(50)
								.addComponent(btnNo))));
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGap(20)
				.addComponent(lblMessage)
				.addGap(20)
				.addGroup(layout.createParallelGroup()
						.addComponent(btnYes)
						.addComponent(btnNo)));

		pnlContent.setLayout(layout);
		dialog.setContentPane(pnlContent);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	public Boolean confirm() {
		return answer;
	}
	
	public static void main(String[] args) {
		if(new ConfirmDialog("HOHO").confirm()) {
			System.out.println("yes");
		} else {
			System.out.print("NO");
		};
	}
}
