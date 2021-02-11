package com.configPage;

import java.util.List;

import org.json.simple.JSONObject;

import com.cei.Baseconfig.Base_Prop;

public class Common_Prop extends Base_Prop {
	
	public String getFirstName(List<JSONObject> jobj) {
		return getValue(jobj, "firstname");
	}
	public String getLastName(List<JSONObject> jobj) {
		return getValue(jobj, "lastname");
	}
	
	public String getState(List<JSONObject> jobj) {
		return getValue(jobj, "state");
	}
	
	public String getStatus(List<JSONObject> jobj) {
		return getValue(jobj, "status");
	}
	
	public String getMailid(List<JSONObject> jobj) {
		return getValue(jobj, "mailid");
	}
	
	public String getPassword(List<JSONObject> jobj) {
		return getValue(jobj, "password");
	}
	
	public String getLendername(List<JSONObject> jobj) {
		return getValue(jobj, "lendername");
	}
	
	public String getStreet(List<JSONObject> jobj) {
		return getValue(jobj, "street");
	}
	
	public String getCity(List<JSONObject> jobj) {
		return getValue(jobj, "city");
	}
	
	public String getZip(List<JSONObject> jobj) {
		return getValue(jobj, "zip");
	}
	
	public String getSignerStreet(List<JSONObject> jobj) {
		return getValue(jobj, "signerstreet");
	}
	
	public String getSignerCity(List<JSONObject> jobj) {
		return getValue(jobj, "signercity");
	}
	
	public String getSignerState(List<JSONObject> jobj) {
		return getValue(jobj, "signerstate");
	}
	
	public String getSignerZip(List<JSONObject> jobj) {
		return getValue(jobj, "signerzip");
	}
	
	public String getSignerCellNum(List<JSONObject> jobj) {
		return getValue(jobj, "signercellnum");
	}
	
	public String getSignerWorkNum(List<JSONObject> jobj) {
		return getValue(jobj, "signerworknum");
	}
	
	public String getPath(List<JSONObject> jobj) {
		return getValue(jobj, "path");
	}
	
	public String getSignerFirstname(List<JSONObject> jobj) {
		return getValue(jobj, "signerfirstname");
	}
	
	public String getSigneremaildomain(List<JSONObject> jobj) {
		return getValue(jobj, "signeremaildomain");
	}
	
	public String getSigner2Firstname(List<JSONObject> jobj) {
		return getValue(jobj, "signer2firstname");
	}
	

}
