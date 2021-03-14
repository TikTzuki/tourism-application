package com.tourism.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.tourism.DTO.TouristGroupCostItem;

public class JsonTouristCost {
	public static List<TouristGroupCostItem> parseToTouristGroupCostItems(String jsonArrayString) {
		List<TouristGroupCostItem> items = new ArrayList<TouristGroupCostItem>();
		try {
			Object obj = new JSONParser().parse(jsonArrayString);
			JSONArray array = (JSONArray) obj;
			for(Iterator itr = array.iterator(); itr.hasNext(); ) {
				JSONObject item = (JSONObject) itr.next();
				items.add(new TouristGroupCostItem(
						item.get("name").toString(),
						Double.parseDouble(item.get("cost").toString()),
						Integer.parseInt(item.get("quantity").toString())));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public static String formatToJsonArray(List<TouristGroupCostItem> items) {
		StringBuilder arrayString = new StringBuilder("[ ");
		items.forEach(item->{
			arrayString.append(item.toString() + ", ");
		});
		arrayString.substring(0, arrayString.lastIndexOf(","));
		arrayString.append("]");
		return arrayString.toString();
	}
	
	public static void main(String[] args) {
		System.out.println( JsonTouristCost.parseToTouristGroupCostItems("fe").toString());
	}
}
