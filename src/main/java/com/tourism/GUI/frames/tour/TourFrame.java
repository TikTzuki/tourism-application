
package com.tourism.GUI.frames.tour;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.tourism.DAL.TourCostRepository;
import com.tourism.DAL.TourRepository;
import com.tourism.DAL.TypeRepository;
import com.tourism.DTO.Tour;
import com.tourism.DTO.Type;


import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TourFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txt_timTenTour;
	private JTextField txt_timDiaDiem;
	private JComboBox cbb_loaiTour = new JComboBox();
	private JTable table;
	
	/**
	 * Function
	 */
	List<Tour> listTour = new ArrayList<>();
	List<com.tourism.DTO.Type> listType = new ArrayList<>();
	DefaultTableModel tableModel;
	TourRepository tourRepository = new TourRepository();
	TypeRepository typeRepository = new TypeRepository();
	TourCostRepository tourCosrRepository = new TourCostRepository();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private void findAll() {
		listTour = tourRepository.findAll();
		tableModel.setRowCount(0);
		listTour.forEach((tour)->{			
				tableModel.addRow(new Object[] {
					tour.getId(), tour.getName(), typeRepository.getNameById(tour.getTypeId()), tour.getDescription(), tour.getStatus(), tour.getImage()
				});
			
		});
	}
	
	private void getTypleOfTourToCombobox() {
		listType = typeRepository.findAll();
		cbb_loaiTour.addItem("");
		for(com.tourism.DTO.Type item : listType) {
			cbb_loaiTour.addItem(item.getName());
			
		}
	}
	
	private static Object formatMoney(Object money){
        Locale locale = new Locale("vi","VN");
        DecimalFormat format = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols formatSymbol = new DecimalFormatSymbols();
        formatSymbol.setCurrencySymbol("");
        format.setDecimalFormatSymbols(formatSymbol);
        return format.format(money);
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TourFrame frame = new TourFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TourFrame() {
		//Frame============================================================
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 1079, 656);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBounds(10, 0, 1059, 95);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Qu\u1EA3n l\u00FD Tour");
		lblNewLabel.setBounds(476, 11, 139, 21);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		panel_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1_5 = new JLabel("T\u00EAn tour");
		lblNewLabel_1_5.setBounds(77, 55, 53, 17);
		panel_1.add(lblNewLabel_1_5);
		lblNewLabel_1_5.setFont(new Font("Times New Roman", Font.BOLD, 14));
		
		txt_timTenTour = new JTextField();
		txt_timTenTour.setBounds(136, 53, 166, 20);
		panel_1.add(txt_timTenTour);
		txt_timTenTour.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txt_timTenTour.setColumns(10);
		
		JLabel lblNewLabel_1_5_1 = new JLabel("\u0110\u1ECBa \u0111i\u1EC3m");
		lblNewLabel_1_5_1.setBounds(380, 55, 65, 17);
		panel_1.add(lblNewLabel_1_5_1);
		lblNewLabel_1_5_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		
		txt_timDiaDiem = new JTextField();
		txt_timDiaDiem.setBounds(441, 53, 166, 20);
		panel_1.add(txt_timDiaDiem);
		txt_timDiaDiem.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txt_timDiaDiem.setColumns(10);
		
		JLabel lblNewLabel_1_5_2 = new JLabel("Lo\u1EA1i h\u00ECnh");
		lblNewLabel_1_5_2.setBounds(687, 55, 65, 17);
		panel_1.add(lblNewLabel_1_5_2);
		lblNewLabel_1_5_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		cbb_loaiTour.setBounds(750, 52, 180, 22);
		panel_1.add(cbb_loaiTour);
		
		
		cbb_loaiTour.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		JButton btn_tim = new JButton("T\u00ECm");
		
		btn_tim.setBounds(960, 52, 89, 23);
		panel_1.add(btn_tim);
		btn_tim.setFont(new Font("Times New Roman", Font.BOLD, 14));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setBounds(10, 144, 1059, 67);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JRadioButton radio_tenTour = new JRadioButton("Tên Tour");
		buttonGroup.add(radio_tenTour);
		radio_tenTour.setFont(new Font("Times New Roman", Font.BOLD, 14));
		radio_tenTour.setBounds(131, 23, 109, 23);
		panel_2.add(radio_tenTour);
		
		JRadioButton radio_giaTour = new JRadioButton("Giá Tour");
		radio_giaTour.setFont(new Font("Times New Roman", Font.BOLD, 14));
		buttonGroup.add(radio_giaTour);
		radio_giaTour.setBounds(279, 23, 109, 23);
		panel_2.add(radio_giaTour);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Trạng thái");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		rdbtnNewRadioButton.setBounds(418, 23, 109, 23);
		panel_2.add(rdbtnNewRadioButton);
		
		JButton btnNewButton = new JButton("Giảm dần");
		
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnNewButton.setBounds(570, 23, 98, 23);
		panel_2.add(btnNewButton);
		
		JButton btnTngDn = new JButton("Tăng dần");
		
		btnTngDn.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTngDn.setBounds(727, 23, 98, 23);
		panel_2.add(btnTngDn);
		
		JButton btnTiLi = new JButton("Tải lại");
		
		btnTiLi.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTiLi.setBounds(882, 23, 98, 23);
		panel_2.add(btnTiLi);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_3.setBounds(0, 222, 1079, 394);
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1079, 388);
		panel_3.add(scrollPane);
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setSurrendersFocusOnKeystroke(true);
		table.setForeground(Color.DARK_GRAY);
		table.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		JTableHeader header = table.getTableHeader();
	      header.setBackground(Color.blue);
	      header.setForeground(Color.white);
	      header.setOpaque(false);
	      Font headerFont = new Font("Times New Roman", Font.BOLD, 14);
	      table.getTableHeader().setFont(headerFont);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"M\u00E3 Tour", "T\u00EAn Tour", "Lo\u1EA1i Tour", "M\u00F4 t\u1EA3", "Tr\u1EA1ng th\u00E1i", "H\u00ECnh \u1EA3nh"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Object.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(0).setMaxWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(3).setMaxWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		table.getColumnModel().getColumn(4).setMaxWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(83);
		table.getColumnModel().getColumn(5).setMaxWidth(105);
		scrollPane.setViewportView(table);
		
		JButton btn_them = new JButton("Th\u00EAm");
		btn_them.setBounds(491, 622, 89, 23);
		panel.add(btn_them);
		btn_them.setFont(new Font("Times New Roman", Font.BOLD, 18));
		//// button thêm
		btn_them.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//tourRepository.AddEmptyTour();
			//	AddTour addTour = new AddTour();
				//addTour.setVisible(true);
			}
		});
		//Function=========================================================
			tableModel = (DefaultTableModel) table.getModel();
			findAll();
			getTypleOfTourToCombobox();
			//// click dòng hiện tab properties
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
				PropertiesTour propertiesTour = new PropertiesTour();
				int index = table.getSelectedRow();
				PropertiesTour.setMaTour(tableModel.getValueAt(index, 0).toString());
				propertiesTour.setTenTour(tableModel.getValueAt(index, 1).toString());
				propertiesTour.setLoaiTour(tableModel.getValueAt(index, 2).toString());
				propertiesTour.setMoTa(tableModel.getValueAt(index, 3).toString());
				propertiesTour.setTrangThai(tableModel.getValueAt(index,4).toString());
				propertiesTour.setHinhAnh(tableModel.getValueAt(index, 5).toString());
				propertiesTour.setGiaBatDau(tourRepository.getDateFromTime(tableModel.getValueAt(index, 0).toString()));
				propertiesTour.setGiaKetThuc(tourRepository.getDateToTime(tableModel.getValueAt(index, 0).toString()));
				propertiesTour.setGia(tourRepository.getPriceById(tableModel.getValueAt(index, 0).toString()));
				propertiesTour.setVisible(true);
				}
			});
			// tìm kiếm
//			btn_tim.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					listTour = tourRepository.searchTour(txt_timTenTour.getText(), txt_timDiaDiem.getText(), cbb_loaiTour.getSelectedItem().toString());
//					tableModel.setRowCount(0);
//					listTour.forEach((tour)->{			
//							tableModel.addRow(new Object[] {
//								tour.getId(), tour.getName(), typeRepository.getNameById(tour.getTypeId()), tour.getDescription(), tour.getStatus(), tour.getImage()
//							});
//						
//					});
//					
//				}
//			});
			
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(radio_tenTour.isSelected()) {
						listTour = tourRepository.sortTourByName(txt_timTenTour.getText(), txt_timDiaDiem.getText(), cbb_loaiTour.getSelectedItem().toString(),1);
					}
					if(radio_giaTour.isSelected()) {
						listTour = tourRepository.sortTourByPrice(txt_timTenTour.getText(), txt_timDiaDiem.getText(), cbb_loaiTour.getSelectedItem().toString(),1);
					}
					if(rdbtnNewRadioButton.isSelected()) {
						listTour = tourRepository.sortTourByStatus(txt_timTenTour.getText(), txt_timDiaDiem.getText(), cbb_loaiTour.getSelectedItem().toString(),1);
					}
					tableModel.setRowCount(0);
					listTour.forEach((tour)->{			
							tableModel.addRow(new Object[] {
								tour.getId(), tour.getName(), typeRepository.getNameById(tour.getTypeId()), tour.getDescription(), tour.getStatus(), tour.getImage()
							});
						
					});
				}
			});
			
			btnTngDn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(radio_tenTour.isSelected()) {
						listTour = tourRepository.sortTourByName(txt_timTenTour.getText(), txt_timDiaDiem.getText(), cbb_loaiTour.getSelectedItem().toString(),0);
					}
					if(radio_giaTour.isSelected()) {
						listTour = tourRepository.sortTourByPrice(txt_timTenTour.getText(), txt_timDiaDiem.getText(), cbb_loaiTour.getSelectedItem().toString(),0);
					}
					if(rdbtnNewRadioButton.isSelected()) {
						listTour = tourRepository.sortTourByStatus(txt_timTenTour.getText(), txt_timDiaDiem.getText(), cbb_loaiTour.getSelectedItem().toString(),0);
					}
					tableModel.setRowCount(0);
					listTour.forEach((tour)->{			
							tableModel.addRow(new Object[] {
								tour.getId(), tour.getName(), typeRepository.getNameById(tour.getTypeId()), tour.getDescription(), tour.getStatus(), tour.getImage()
							});
						
					});
				}
			});
			
			btnTiLi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					findAll();
					cbb_loaiTour.setSelectedIndex(0);
				}
			});
			
		
	}
}

