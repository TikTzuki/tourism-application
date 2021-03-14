package com.tourism.GUI.frames.tour;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import com.tourism.DAL.LocationRepository;
import com.tourism.DAL.TourCostRepository;
import com.tourism.DAL.TourLocationRepository;
import com.tourism.DAL.TourRepository;
import com.tourism.DAL.TypeRepository;
import com.tourism.DTO.Tour;
import com.tourism.DTO.TourCost;
import com.tourism.DTO.TourLocation;

import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PropertiesTour extends JFrame {

	private JPanel contentPane;
	public static JTextField txt_hinhAnh;
	public static JTextField txt_tenTour;
	public static JTextField txt_moTa;
	public static JTextField txt_giaTour;
	private JTable table_1;
	private JTable table_2;
	private JButton btn_ThemDD = new JButton("Th\u00EAm \u0111\u1ECBa \u0111i\u1EC3m");
	private JButton btn_XoaDD = new JButton("X\u00F3a \u0111\u1ECBa \u0111i\u1EC3m");
	private JButton btn_suaHinh = new JButton("Thay \u0111\u1ED5i \u1EA3nh");
	private JButton btn_luu = new JButton("Lưu thay đổi");

	private JTextField textField_1;
	public static JLabel lbl_maTour = new JLabel("22");
	public static JComboBox cbb_loaiTour = new JComboBox();
	public static JComboBox cbb_trangThai = new JComboBox();
	public static JDateChooser date_giaTuNgay = new JDateChooser();
	public static JDateChooser date_giaDenNgay = new JDateChooser();
	DefaultTableModel tableModel1;
	static DefaultTableModel tableModel2;
	static List<com.tourism.DTO.Location> listLocation = new ArrayList<>();
	List<com.tourism.DTO.TourLocation> listTourLocation = new ArrayList<>();
	List<com.tourism.DTO.Type> listType = new ArrayList<>();
	static LocationRepository locationRepository = new LocationRepository();
	TourLocationRepository tourLocationRepository = new TourLocationRepository();
	TypeRepository typeRepository = new TypeRepository();
	TourRepository tourRepository = new TourRepository();
	TourCostRepository tourCostRepository = new TourCostRepository();
	/**
	 * Function
	 */
	public java.sql.Date Convert(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }
	public static void setMaTour(String data) {
		lbl_maTour.setText(data);
		findAllTourLocation();
	}
	public void setTenTour(String data) {
		txt_tenTour.setText(data);
	}
	public static void setLoaiTour(String data) {
		cbb_loaiTour.addItem(data);
	}
	public static void setMoTa(String data) {
		txt_moTa.setText(data);
	}
	public static void setTrangThai(String data) {
		cbb_trangThai.addItem(data);
	}
	public static void setGia(Double data) {
		txt_giaTour.setText(String.valueOf(data));
	}
	public static void setGiaBatDau(String data) {
		java.util.Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		date_giaTuNgay.setDate(date);
	}
	public static void setGiaKetThuc(String data) {
		java.util.Date date = null;
		try {
			date = new SimpleDateFormat("dd-MM-yyyy").parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		date_giaDenNgay.setDate(date);
	}
	public static void setHinhAnh(String data) {
		txt_hinhAnh.setText(data);
	}
	
	private void findAllLocation() {
		listLocation = locationRepository.findAll();
		tableModel1.setRowCount(0);
		listLocation.forEach((location)->{
			tableModel1.addRow(new Object[] {
					location.getId(), location.getName(), location.getStreet()
			});
		});
	}
	
	private static void findAllTourLocation() {
		listLocation = locationRepository.findAllByTourId(Long.parseLong(lbl_maTour.getText()));
		tableModel2.setRowCount(0);
		listLocation.forEach((location)->{
			tableModel2.addRow(new Object[] {
					location.getId(), location.getName(), location.getStreet()
			});
		});
	}
	
	private void NoAllowInPut() {
		txt_giaTour.setEnabled(false);
		txt_hinhAnh.setEnabled(false);
		txt_moTa.setEnabled(false);
		txt_tenTour.setEnabled(false);
		cbb_loaiTour.setEnabled(false);
		cbb_trangThai.setEnabled(false);
		date_giaDenNgay.setEnabled(false);
		date_giaTuNgay.setEnabled(false);
		btn_ThemDD.setEnabled(false);
		btn_XoaDD.setEnabled(false);
		btn_suaHinh.setEnabled(false);
		btn_luu.setEnabled(false);
	}
	private void AllowInPut() {
		txt_giaTour.setEnabled(true);
		txt_hinhAnh.setEnabled(true);
		txt_moTa.setEnabled(true);
		txt_tenTour.setEnabled(true);
		cbb_loaiTour.setEnabled(true);
		cbb_trangThai.setEnabled(true);
		date_giaDenNgay.setEnabled(true);
		date_giaTuNgay.setEnabled(true);
		btn_ThemDD.setEnabled(true);
		btn_XoaDD.setEnabled(true);
		btn_suaHinh.setEnabled(true);
		btn_luu.setEnabled(true);
	}
	
	private void getTypleOfTourToCombobox() {
		listType = typeRepository.findAll();
		cbb_loaiTour.removeAllItems();;
		for(com.tourism.DTO.Type item : listType) {
			cbb_loaiTour.addItem(item.getName());			
		}
	}
	
	private void getStatusToComboBox() {
		cbb_trangThai.removeAllItems();;
		cbb_trangThai.addItem("Kích hoạt");
		cbb_trangThai.addItem("Không kích hoạt");
	}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TourFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PropertiesTour frame = new PropertiesTour();
					frame.setVisible(true);
				} catch (Exception e){}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PropertiesTour() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel.setBounds(5, 0, 775, 265);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CHI TI\u1EBET TOUR");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel.setBounds(300, 11, 149, 40);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("M\u00E3 Tour");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 67, 75, 27);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Lo\u1EA1i Tour");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(10, 104, 75, 27);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("T\u00EAn Tour");
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(10, 144, 75, 27);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("M\u00F4 t\u1EA3");
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_3.setBounds(10, 182, 75, 27);
		panel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Gi\u00E1 Tour");
		lblNewLabel_1_4.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_4.setBounds(233, 67, 75, 27);
		panel.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Gi\u00E1 \u00E1p d\u1EE5ng t\u1EEB ng\u00E0y");
		lblNewLabel_1_5.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_5.setBounds(233, 104, 140, 27);
		panel.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("\u0110\u1EBFn ng\u00E0y");
		lblNewLabel_1_6.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_6.setBounds(233, 144, 75, 27);
		panel.add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1_7 = new JLabel("Tr\u1EA1ng th\u00E1i");
		lblNewLabel_1_7.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_7.setBounds(233, 182, 75, 27);
		panel.add(lblNewLabel_1_7);
		
		txt_hinhAnh = new JTextField();
		txt_hinhAnh.setBounds(541, 71, 228, 150);
		panel.add(txt_hinhAnh);
		txt_hinhAnh.setColumns(10);
		
		
		btn_suaHinh.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_suaHinh.setBounds(599, 232, 119, 24);
		panel.add(btn_suaHinh);
		
		
		lbl_maTour.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lbl_maTour.setBounds(92, 74, 46, 14);
		panel.add(lbl_maTour);
		
		
		cbb_loaiTour.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cbb_loaiTour.setBounds(95, 107, 128, 22);
		panel.add(cbb_loaiTour);
		
		txt_tenTour = new JTextField();
		txt_tenTour.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_tenTour.setBounds(95, 148, 128, 20);
		panel.add(txt_tenTour);
		txt_tenTour.setColumns(10);
		
		txt_moTa = new JTextField();
		txt_moTa.setBounds(95, 186, 128, 70);
		panel.add(txt_moTa);
		txt_moTa.setColumns(10);
		
		txt_giaTour = new JTextField();
		txt_giaTour.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txt_giaTour.setColumns(10);
		txt_giaTour.setBounds(375, 71, 128, 20);
		panel.add(txt_giaTour);
		
		
		date_giaTuNgay.setBounds(375, 111, 128, 20);
		panel.add(date_giaTuNgay);
		
		
		date_giaDenNgay.setBounds(375, 144, 128, 20);
		panel.add(date_giaDenNgay);
		
		
		cbb_trangThai.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		cbb_trangThai.setBounds(375, 185, 128, 22);
		panel.add(cbb_trangThai);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_1.setBounds(5, 268, 775, 200);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Danh s\u00E1ch c\u00E1c \u0111\u1ECBa \u0111i\u1EC3m");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_2.setBounds(94, 0, 170, 27);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Danh s\u00E1ch c\u00E1c \u0111\u1ECBa \u0111i\u1EC3m hi\u1EC7n t\u1EA1i ");
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_2_1.setBounds(478, 0, 234, 27);
		panel_1.add(lblNewLabel_2_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 48, 370, 117);
		panel_1.add(scrollPane);
		
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"M\u00E3 \u0111\u1ECBa \u0111i\u1EC3m", "T\u00EAn \u0111\u1ECBa \u0111i\u1EC3m", "\u0110\u01B0\u1EDDng"
			}
		));
		table_1.getColumnModel().getColumn(0).setPreferredWidth(68);
		table_1.getColumnModel().getColumn(0).setMaxWidth(150);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(53);
		table_1.getColumnModel().getColumn(2).setMaxWidth(100);
		scrollPane.setViewportView(table_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(390, 48, 375, 117);
		panel_1.add(scrollPane_1);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"M\u00E3 \u0111\u1ECBa \u0111i\u1EC3m", "T\u00EAn \u0111\u1ECBa \u0111i\u1EC3m", "\u0110\u01B0\u1EDDng"
			}
		));
		table_2.getColumnModel().getColumn(0).setPreferredWidth(68);
		table_2.getColumnModel().getColumn(0).setMaxWidth(150);
		table_2.getColumnModel().getColumn(2).setPreferredWidth(53);
		table_2.getColumnModel().getColumn(2).setMaxWidth(100);
		scrollPane_1.setViewportView(table_2);
		
		textField_1 = new JTextField();
		textField_1.setBounds(10, 24, 86, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		
		
		btn_XoaDD.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_XoaDD.setBounds(524, 168, 120, 27);
		panel_1.add(btn_XoaDD);
		
		
		btn_ThemDD.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btn_ThemDD.setBounds(131, 168, 133, 23);
		panel_1.add(btn_ThemDD);
		
		JButton btn_sua = new JButton("Thay \u0111\u1ED5i th\u00F4ng tin");
		
		btn_sua.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_sua.setBounds(90, 473, 197, 29);
		contentPane.add(btn_sua);
		
		JButton btn_thoat = new JButton("Tho\u00E1t");
		btn_thoat.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_thoat.setBounds(504, 473, 197, 29);
		contentPane.add(btn_thoat);
		
		btn_luu.setFont(new Font("Times New Roman", Font.BOLD, 16));
		btn_luu.setBounds(297, 473, 197, 29);
		contentPane.add(btn_luu);
		//function=======================================
		
		tableModel1 = (DefaultTableModel) table_1.getModel();
		tableModel2 = (DefaultTableModel) table_2.getModel();
		findAllLocation();
		NoAllowInPut();
		
		btn_thoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btn_ThemDD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index[] = table_1.getSelectedRows();
				Object row[] = new Object[3];
				
				for(int i = 0; i < index.length ; i++) {
					row [0] = tableModel1.getValueAt(index[i], 0);
					row [1] = tableModel1.getValueAt(index[i], 1);
					row [2] = tableModel1.getValueAt(index[i], 2);
					tableModel2.addRow(row);
				}
			}
		});
		
		btn_XoaDD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table_2.getSelectedRow();
				Object row[] = new Object[3];		
					row [0] = tableModel2.getValueAt(index, 0);
					row [1] = tableModel2.getValueAt(index, 1);
					row [2] = tableModel2.getValueAt(index, 2);
					tableModel2.removeRow(index);
				
			}
		});
		
		btn_sua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AllowInPut();
				getTypleOfTourToCombobox();
				getStatusToComboBox();
			}
		});
		
		btn_luu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Long> locations = new ArrayList<>();				
				// save Tour
				Tour tour = new Tour();	
				tour.setId(Long.parseLong(lbl_maTour.getText()));
				tour.setName(txt_tenTour.getText());
				tour.setTypeId(typeRepository.getIdByName(cbb_loaiTour.getSelectedItem().toString()));
				tour.setDescription(txt_moTa.getText());
				tour.setStatus(cbb_trangThai.getSelectedItem().toString());
				tour.setImage(txt_hinhAnh.getText());
//				tour.setIdLocation(locations);
//				tour.setId(Long.parseLong(lbl_maTour.getText()));
				tourRepository.save(tour);
				//JOptionPane.showMessageDialog(rootPane, "Thêm thành công");
				//save TourCost
				TourCost tourCost = new TourCost();
				//String from_date = Convert(date_from.getDate().toString());
				//tourCost.setId(Long.parseLong(getWarningString()));
				tourCost.setId(tourCostRepository.getIdByTourId(Long.parseLong(lbl_maTour.getText())));
				tourCost.setTourId(Long.parseLong(lbl_maTour.getText()));
				tourCost.setPrice(Double.parseDouble(txt_giaTour.getText()));
				tourCost.setPriceFromTime(Convert(date_giaTuNgay.getDate()).toString());
				tourCost.setPriceToTime(Convert(date_giaDenNgay.getDate()).toString());
				tourCostRepository.save(tourCost);
				//save TourLocation
				tourLocationRepository.deleteAllById(Long.parseLong(lbl_maTour.getText()));
				for(int i = 0; i < tableModel2.getRowCount() ; i++)
				{
					//locations.add( (Long) tableModel2.getValueAt(i, 0));
					TourLocation tourLocation = new TourLocation();
					tourLocation.setTour_id(Long.parseLong(lbl_maTour.getText()));
					tourLocation.setLocation_id(Long.parseLong(tableModel2.getValueAt(i, 0).toString()));
					tourLocationRepository.save(tourLocation);
					
				}
				
				dispose();
			}
		});
	}
}
