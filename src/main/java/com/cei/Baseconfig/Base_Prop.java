package com.cei.Baseconfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cei.reporter.BaseReporter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

public class Base_Prop {

	public static String env;

	final static String configfolderpath = ".//TestData/";
	static String propvalue;

	public static JSONObject loadEnv() {

		JSONObject jsonObject_env = null;
		String path =  ".//Env.json";
		JSONParser parser = new JSONParser();
		Object obj;
		try {
			obj = parser.parse(new FileReader(path));
			jsonObject_env = (JSONObject) obj;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonObject_env;
	}

	public static String getEnv() {

			if (System.getProperty("ENV") != null)
			return System.getProperty("ENV");
			else
			return (String) loadEnv().get("env");
	}

	public static String getReportDir() {
		return (String) loadEnv().get("reportPath");
	}

	public static String getBrowserDir() {
		return (String) loadEnv().get("browserPath");
	}

	public static String getArtifactsDir() {
		String path = System.getProperty("user.home") + (String) loadEnv().get("artifactsPath");
		return path;
	}

	public static int getNumberofAttempt() {
		return Integer.parseInt((String) loadEnv().get("failTry"));
	}

	public static long getWaitForRerun() {
		return Long.parseLong((String) loadEnv().get("waitForRerun"));
	}

	public static boolean getAssertCondition() {
		return Boolean.parseBoolean((String) loadEnv().get("Assert"));
	}

	public  String getUsername() {
		return (String) loadEnv().get("username");
	}

	public  String getUrl() {
		return (String) loadEnv().get("appurl");
	}

	public  String getPassword() {

		String encryptedPW =  (String) loadEnv().get("password");

		byte[] decodePW = Base64.getDecoder().decode(encryptedPW.getBytes());

		String password =new String(decodePW);
		return password;
	}

	private static JSONObject getModule(String jsonName) {

		JSONObject jsonObj = null;
		String path;
		JSONParser parser = new JSONParser();
		Object obj;
		env = getEnv();
		path = configfolderpath + jsonName + "/" + jsonName + "." + env + ".json";
		try {
			if (new File(path).exists()) {
				obj = parser.parse(new FileReader(path));
				jsonObj = (JSONObject) obj;
			} else {
				jsonObj = getDefaultModule(jsonName);
			}
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
		return jsonObj;
	}

	private static JSONObject getDefaultModule(String jsonName) {

		JSONObject jsonDefaultObj = null;
		String path;
		JSONParser parser = new JSONParser();
		Object obj;
		path = configfolderpath + jsonName + "/" + jsonName + ".json";
		try {
			if (new File(path).exists()) {
				obj = parser.parse(new FileReader(path));
				jsonDefaultObj = (JSONObject) obj;
			} else {
				BaseReporter.logFailForConf(jsonName, "json");
			}
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
		return jsonDefaultObj;
	}

	protected static List<JSONObject> getJsonObject(String module, String objectname) {
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		JSONObject jsonObj = null, jsonDefaultObj = null;
		try {
			 if((JSONObject) getModule(module).get(objectname) != null)
				 jsonObj = (JSONObject) getModule(module).get(objectname);
			 if((JSONObject) getDefaultModule(module).get(objectname) != null)
				 jsonDefaultObj = (JSONObject) getDefaultModule(module).get(objectname);
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
		jsonList.add(jsonObj);
		jsonList.add(jsonDefaultObj);
		return jsonList;
	}

	protected static List<JSONObject> getJsonObject(List<JSONObject> jsonList, String objectname) {
		List<JSONObject> jsonobjList = new ArrayList<JSONObject>();
		try {
			if(jsonList.get(0).get(objectname) != null)
				jsonobjList.add((JSONObject) jsonList.get(0).get(objectname));
			if(jsonList.get(1).get(objectname) != null)
				jsonobjList.add((JSONObject) jsonList.get(1).get(objectname));
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
		return jsonobjList;
	}

	// for jsonvalue from key-value pair
	protected static String getValue(String module, String propname) {
		JSONObject jsonDefaultObj = getDefaultModule(module);
		JSONObject jsonObj = getModule(module);

		String jsonValue = (String) jsonObj.get(propname);
		String jsonDefaultValue = (String) jsonDefaultObj.get(propname);

		if (jsonValue != null)
			return jsonValue;
		else if (jsonDefaultValue != null)
			return jsonDefaultValue;
		else {
			BaseReporter.logFailForConf(propname, "property");
			return null;
		}
	}

	// for jsonvalue from objects
	protected static String getValue(List<JSONObject> jsonList, String propname) {
		String jsonValue = null, jsonDefaultValue = null;

		if(jsonList.get(0) != null)
			jsonValue = (String) jsonList.get(0).get(propname);
		if(jsonList.get(1) != null)
			jsonDefaultValue  = (String) jsonList.get(1).get(propname);

		if (jsonValue != null)
			return jsonValue;
		else if (jsonDefaultValue != null)
			return jsonDefaultValue;
		else {
			BaseReporter.logFailForConf(jsonList.get(1) + " : " + propname, "property");
			return null;
		}
	}

	protected static Object[][] getData(String module, String propname)
			throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		JsonObject jsonObj;
		String path = configfolderpath + module + "/" + module + "." + env + ".json";
		String defaultpath = configfolderpath + module + "/" + module + ".json";

		try {
			if (new File(path).exists())
				jsonObj = JsonParser.parseReader(new FileReader(path)).getAsJsonObject();
			else
				jsonObj = JsonParser.parseReader(new FileReader(defaultpath)).getAsJsonObject();

			JsonArray the_json_array = jsonObj.getAsJsonArray(propname);
			if (the_json_array == null) {
				jsonObj = JsonParser.parseReader(new FileReader(defaultpath)).getAsJsonObject();
				the_json_array = jsonObj.getAsJsonArray(propname);
			}

			int size = the_json_array.size();
			Set<String> keySet = (the_json_array.get(0)).getAsJsonObject().keySet();
			Object[][] returnValue = new Object[the_json_array.size()][keySet.size()];

			for (int i = 0; i < size; i++) {
				Set<Entry<String, JsonElement>> subdata = (the_json_array.get(i)).getAsJsonObject().entrySet();
				Object[] tmp = subdata.toArray();
				for (int j = 0; j < keySet.size(); j++) {
					returnValue[i][j] = ((JsonPrimitive) ((Entry<?, ?>) tmp[j]).getValue()).getAsString();
				}
			}
			return returnValue;
		} catch (Exception e) {
			BaseReporter.logFailForConf("array : " + propname, "property");
		}

		return null;
	}

	public String getEnvSpecificValue(String env, String propname, String module) {
		JSONObject jsonObj = null, jsonDefaultObj = null;
		String path, defaultpath;
		String jsonValue = null, jsonDefaultValue = null;
		JSONParser parser = new JSONParser();
		Object obj;
		path = configfolderpath + module + "/" + module + "." + env + ".json";
		defaultpath = configfolderpath + module + "/" + module + ".json";
		try {
			if (new File(path).exists()) {
				obj = parser.parse(new FileReader(path));
				jsonObj = (JSONObject) obj;
			}
			if (new File(defaultpath).exists()) {
				obj = parser.parse(new FileReader(defaultpath));
				jsonDefaultObj = (JSONObject) obj;
			}
			jsonValue = (String) jsonObj.get(propname);
			jsonDefaultValue = (String) jsonDefaultObj.get(propname);
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}

		if (jsonValue != null)
			return jsonValue;
		else if (jsonDefaultValue != null)
			return jsonDefaultValue;
		else {
			BaseReporter.logFailForConf(propname, "property");
			return null;
		}
	}

}
