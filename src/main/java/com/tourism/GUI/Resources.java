package com.tourism.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.tourism.GUI.util.IconUtil;

public class Resources {
	public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static Color PRIMARY = new Color(249, 247, 247);
	public static Color PRIMARY_DARK = new Color(219, 226, 239);
	public static Color SECONDARY_LIGHT = new Color(116, 180, 252);
	public static Color SECONDARY = new Color(63, 114, 175);
	public static Color SECONDARY_DARK = new Color(17, 45, 78);
	
	public static int SEARCH_PANEL_WIDTH = 1000;
	public static int SEARCH_PANEL_HEIGHT = 120;
	
	public static int MAIN_CONTENT_WIDTH = 1110;
	public static int MAIN_CONTENT_HEIGHT = 700;
	public static Dimension MAIN_CONTENT = new Dimension(MAIN_CONTENT_WIDTH, MAIN_CONTENT_HEIGHT);
	
	public static int SQUARE_EDGE_XXS = 34;
	public static int SQUARE_EDGE_XS = 40;
	public static int SQUARE_EDGE_S = 46;
	
	public static Dimension SQUARE_XXS = new Dimension(SQUARE_EDGE_XXS, SQUARE_EDGE_XXS);
	public static Dimension SQUARE_XXL= new Dimension(400, 400);
	
	public static Dimension RECTANGLE_XXS = new Dimension(100, SQUARE_EDGE_XXS);
	
	public static int INPUT_WIDTH_XS = 120;
	public static int INPUT_HEIGHT_S = 30;
	public static int INPUT_WIDTH_S = 160;
	public static int INPUT_HEIGHT_M = 34;
	public static int INPUT_WIDTH_M = 200;
	public static int INPUT_HEIGHT_L = 38;
	public static int INPUT_WIDTH_L = 240;
	public static int INPUT_HEIGHT_XL = 42;
	public static int INPUT_WIDTH_XL = 280;
	public static int INPUT_HEIGHT_XXL = 46;
	public static int INPUT_WIDTH_XXL = 320;
	public static Dimension INPUT_TYPE_DATE = new Dimension(INPUT_WIDTH_M, INPUT_HEIGHT_M);

	public static ImageIcon TOURISM_LOGO = IconUtil.loadIcon("src/images/Tourism Logo.png", 200, 50);
	public static ImageIcon CALENDAR_ICON = IconUtil.loadIcon("src/images/noun_Calendar.png", 28, 28);
	public static ImageIcon ADD_ICON = IconUtil.loadIcon("src/images/add.png", 24, 24);
	public static ImageIcon ADD_ICON_S = IconUtil.loadIcon("src/images/add.png", 18, 18);
	public static ImageIcon DASHBOARD = IconUtil.loadIcon("src/images/dashboard.png", 28, 28);
	public static ImageIcon TRAVEL = IconUtil.loadIcon("src/images/travel.png", 28, 28);
	public static ImageIcon NETWORK = IconUtil.loadIcon("src/images/network.png", 28, 28);
	public static ImageIcon LOGIN_BACKGROUND= IconUtil.loadIcon("src/images/login_background.png", 300, 500);
	public static Dimension CLENDAR_BUTTON = new Dimension(10,10);
	
	public static int DETAIL_WIDTH = 1000;
	public static int DETAIL_HEIGHT = 200;
	public static Dimension DETAIL_PANEL = new Dimension(DETAIL_WIDTH, DETAIL_HEIGHT);
	public static Dimension DETAIL_PANEL_S = new Dimension(DETAIL_WIDTH, SEARCH_PANEL_HEIGHT);
	
	public static Dimension SEARCH_PANEL = new Dimension(SEARCH_PANEL_WIDTH, SEARCH_PANEL_HEIGHT);
	public static Dimension INPUT_SEARCH_TEXTFIELD = new Dimension(100,22);
	public static Dimension INPUT_SEARCH_PANEL = new Dimension(200,50);
	
	public static Dimension MANAGER_TABLE = new Dimension(900,800);
	public static Dimension MANAGER_TABLE_SCROLLER = new Dimension(1000, 300);
	public static Dimension MANAGER_TABLE_PANEL = new Dimension(1000,300);
	
	public static String[] TOURIST_GROUP_STATUSES = new String[] {"Chưa đi", "Đang đi", "Đã hoàn thành"};
}
