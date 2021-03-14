package com.tourism.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JsonAddress {	
	public List<Object> getAddressLV1() {
		Object obj;
		List<Object> address = new ArrayList<>();
		try {
			obj = new JSONParser().parse(new FileReader("src\\main\\java\\com\\tourism\\service\\address.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray listAddress = (JSONArray) jsonObject.get("data");
			Iterator itr = listAddress.iterator();
			
			while(itr.hasNext()) {
				JSONObject nameCity = (JSONObject) itr.next();
                address.add(nameCity.get("name"));                
				}
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return address;
	}
	
	public List<Object> getAddressLv2(String nameCity){
		Object obj;
		List<Object> address = new ArrayList<>();
		try {
			obj = new JSONParser().parse(new FileReader("src\\main\\java\\com\\tourism\\service\\address.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray listAddress = (JSONArray) jsonObject.get("data");
			Iterator itr = listAddress.iterator();
			Iterator itr2 = null;
			String NameCity = "";
			while(!nameCity.equals(NameCity)) {				
				JSONObject name= (JSONObject) itr.next();
				JSONArray  listDistric = (JSONArray) name.get("level2s");
				itr2 = listDistric.iterator();
				NameCity = (String) name.get("name");
				}
			while(itr2.hasNext()) {
				JSONObject nameDistric = (JSONObject) itr2.next();
				address.add(nameDistric.get("name"));
			}
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return address;
	}
	
	public List<Object> getAddressLV3(String nameCity,String nameDistrict){
		Object obj;
		List<Object> address = new ArrayList<>();
		try {
			obj = new JSONParser().parse(new FileReader("src\\main\\java\\com\\tourism\\service\\address.json"));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray listAddress = (JSONArray) jsonObject.get("data");
			Iterator itr = listAddress.iterator();
			Iterator itr2 = null;
			String NameCity = "";
			while(!nameCity.equals(NameCity)) {				
				JSONObject name= (JSONObject) itr.next();
				JSONArray  listDistric = (JSONArray) name.get("level2s");
				itr2 = listDistric.iterator();
				NameCity = (String) name.get("name");
				}
			Iterator itr3 =null;
			String NameDistrict = "";
			while(!nameDistrict.equals(NameDistrict)) {
				JSONObject name= (JSONObject) itr2.next();
				JSONArray listWard = (JSONArray) name.get("level3s");
				itr3 = listWard.iterator();
				NameDistrict = (String) name.get("name");
			}
			while(itr3.hasNext()) {
				JSONObject nameWard = (JSONObject) itr3.next();
				address.add(nameWard.get("name"));
			}
			
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		return address;
	}
	
}
