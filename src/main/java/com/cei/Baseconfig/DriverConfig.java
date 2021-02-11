package com.cei.Baseconfig;
import org.json.simple.JSONObject;

public class DriverConfig extends Base_Prop {

	String module = "DriverConfig";
	
	public JSONObject getWinAppConfig() {
		return getJsonObject(module, "windows.app.driver.config").get(1);
	}
	
	public JSONObject getLocalWinChromeConfig() {
		return getJsonObject(module, "local.win.chrome.config").get(1);
	}
	
	public JSONObject getLocalWinFirefoxConfig() {
		return getJsonObject(module, "local.win.firefox.config").get(1);
	}
	
	public JSONObject getLocalMacSafariConfig() {
		return getJsonObject(module, "local.mac.safari.config").get(1);
	}
	
	public JSONObject getLocalMacChromeConfig() {
		return getJsonObject(module, "local.mac.chrome.config").get(1);
	}
	
	public JSONObject getRemoteWinChromeConfig() {
		return getJsonObject(module, "remote.win.chrome.config").get(1);
	}
	
	public JSONObject getRemoteWinFirefoxConfig() {
		return getJsonObject(module, "remote.win.firefox.config").get(1);
	}
	
	public JSONObject getRemoteMacSafariConfig() {
		return getJsonObject(module, "remote.mac.safari.config").get(1);
	}
	
	public JSONObject getRemoteMacChromeConfig() {
		return getJsonObject(module, "remote.mac.chrome.config").get(1);
	}
	
	public JSONObject getIOSSafariConfig() {
		return getJsonObject(module, "ios.safari.config").get(1);
	}
	
	public JSONObject getIOSChromeConfig() {
		return getJsonObject(module, "ios.chrome.config").get(1);
	}
	
	public JSONObject getAndroidChromeConfig() {
		return getJsonObject(module, "android.chrome.config").get(1);
	}
}
