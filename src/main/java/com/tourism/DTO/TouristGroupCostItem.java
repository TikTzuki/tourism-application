package com.tourism.DTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TouristGroupCostItem {
	protected String name;
	protected Double cost;
	protected Integer quantity;

	@Override
	public String toString() {
		return "{ \"name\":\""+name+ "\", \"cost\":\"" + cost + "\", \"quantity\":\"" + quantity +"\" }";
	}

	public static List<TouristGroupCostItem> parseToTouristGroupCostItems(String jsonArrayString) {
		List<TouristGroupCostItem> items = new ArrayList<TouristGroupCostItem>();
		try {
			Object obj = new JSONParser().parse(jsonArrayString);
			JSONArray array = (JSONArray) obj;
			for(Iterator<JSONObject> itr = array.iterator(); itr.hasNext(); ) {
				JSONObject item = (JSONObject) itr.next();
				items.add(new TouristGroupCostItem(
						item.get("name").toString(),
						Double.parseDouble(item.get("cost").toString()),
						Integer.parseInt(item.get("quantity").toString())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
	public static String formatToJsonArray(List<TouristGroupCostItem> items) {
		StringBuilder arrayString = new StringBuilder("[ ");
		items.forEach(item->{
			arrayString.append(item.toString());
		});
		arrayString.append("]");
		return arrayString.toString();
	}
	public static void main(String[] args) {
	}
}
