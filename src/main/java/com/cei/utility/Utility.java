package com.cei.utility;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import org.junit.Assert;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.cei.pages.Page;
import com.cei.reporter.BaseReporter;

public class Utility {

	JSONParser parse;
	JSONObject obj;
	JSONArray values;
	JSONObject jobj;

	int attempt = 5;
	long wait = 10000;
	
	final static String API_GETINBOX = "https://getnada.com/api/v1/inboxes/";
	final static String API_GETMSGCONTENT = "https://getnada.com/api/v1/messages/html/";
	final static String API_GETMSG = "https://getnada.com/api/v1/messages/";

	public static String getTimeStamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_yyyy_hh_mm_ss_a");
		LocalDateTime now = LocalDateTime.now();
		// System.out.println(dtf.format(now));
		return dtf.format(now);
	}
	
	public static String getTimeStamp(String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static void createFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			if (file.mkdir())
				System.out.println("folder created successfully !!");
			else
				System.out.println("Failed to create folder");
		}
	}

	public boolean checkResponse(URL url, int responseCode, String mailSummary) {

		boolean flag = false;
		String inline = "";
		String last;
		Long l;
		int stalecount = 0;
		outer: while (stalecount < 2) {
			try {
				for (int count = 0; count < attempt; count++) {
					if (responseCode == 200) {
						Scanner sc = new Scanner(url.openStream());
						while (sc.hasNext()) {
							inline += sc.nextLine();
						}
						sc.close();
						parse = new JSONParser();
						obj = (JSONObject) parse.parse(inline);
						l = (Long) obj.get("last");
						last = Long.toString(l);
						if (!last.equals("0")) {
							values = (JSONArray) obj.get("msgs");
							for (int i = 0; i < values.size(); i++) {
								jobj = (JSONObject) values.get(i);
								if (jobj.get("s").toString().equalsIgnoreCase(mailSummary)) {
									BaseReporter.logPass("Mail is received");
									flag = true;
									break outer;
								}
							}

						}
						System.out.println("Mail is not received.Retrying...");
						Page.callForWait(wait);
						inline = "";
					} else {
						BaseReporter.logFail("Response code error :" + responseCode);
					}
				}
			} catch (Exception e) {
				stalecount++;
				if (stalecount == 2) {
					BaseReporter.logException(null, e);
					break;
				}
				Page.callForWait(5000);
				System.out.println(e);
			}
			break;
		}
		Page.callForWait(5000);
		return flag;
	}

	public boolean checkMailNotReceived(String mailid, String mailSummary){
		boolean flag = true;
		try {
			URL url = new URL(API_GETINBOX + mailid.toLowerCase());
			int resp = apiresponse(url, "GET");
			flag = checkResponse(url, resp, mailSummary);
		}catch(Exception e) {
			BaseReporter.logException(null, e);
		}
		return flag;
	}

	public String ReadAPI(String sURL, String sType, String mailSummary) {
		String inline = "";
		try {
			URL url = new URL(sURL);
			int resp = apiresponse(url, "GET");
			boolean response = checkResponse(url, resp, mailSummary);		
			if (response) {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
			}
			else
				BaseReporter.logFail("No mail received", null);
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
		return inline;
	}

	public String getMailuid(String mailid, String mailSummary) throws Exception {

		String uid = null;
		String inline = ReadAPI(API_GETINBOX + mailid.toLowerCase(), "GET", mailSummary);
		parse = new JSONParser();
		obj = (JSONObject) parse.parse(inline);
		values = (JSONArray) obj.get("msgs");
		for (int i = 0; i < values.size(); i++) {
			jobj = (JSONObject) values.get(i);
			if (jobj.get("s").toString().equalsIgnoreCase(mailSummary)) {
				System.out.println("UID: " + jobj.get("uid"));
				uid = (String) jobj.get("uid");
				break;
			}
		}
		if(uid == null) {
			BaseReporter.logFail("Unable to find mail with summary: "+mailSummary);
			Assert.fail();
		}
		return uid;
	}

	public void verifyMailDocumentExist(String uid, String docname) {

		try {
			String inline = "";
			URL url = new URL(API_GETMSG + uid);
			int responseCode;
			responseCode = apiresponse(url, "GET");
			if (responseCode == 200) {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
			}
			parse = new JSONParser();
			boolean flag = false;
			obj = (JSONObject) parse.parse(inline);
			values = (JSONArray) obj.get("at");
			for (int i = 0; i < values.size(); i++) {
				jobj = (JSONObject) values.get(i);
				if (jobj.get("name").toString().equalsIgnoreCase(docname)) {
					BaseReporter.logPass(docname + " is received in mail");
					flag = true;
					break;
				}
			}
			if (!flag)
				BaseReporter.logFail("unable to receive " + docname, null);
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
	}
	
	public List<String> checkMailUidExistance(String mailid, String mailSummary) {

		String inline = "";
		boolean received = false;
		List<String> uids = new ArrayList<String>();
		try {
			URL url = new URL(API_GETINBOX + mailid.toLowerCase());
			int responseCode;
			responseCode = apiresponse(url, "GET");
			if (responseCode == 200) {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();
				parse = new JSONParser();
				obj = (JSONObject) parse.parse(inline);
				Long l = (Long) obj.get("last");
				String last = Long.toString(l);
				if (!last.equals("0")) {
					values = (JSONArray) obj.get("msgs");
					for (int i = 0; i < values.size(); i++) {
						jobj = (JSONObject) values.get(i);
						if ((jobj.get("s") != null)) {
							if (jobj.get("s").toString().equalsIgnoreCase(mailSummary)) {
								System.out.println("UID: " + jobj.get("uid"));
								uids.add((String) jobj.get("uid"));
								received = true;
							}
						}
					}
				}
				if (!received) {
					System.out.println("No Past OTP Mail....");
				}
			} else {
				BaseReporter.logFail("Response code error :" + responseCode);
			}
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
		return uids;
	}
	
	int apiresponse(URL apiurl, String request) {
		int code = 0;
		int retry = 0;
		while (retry < 2) {
			try {
				HttpURLConnection conn = (HttpURLConnection) apiurl.openConnection();
				conn.setRequestMethod(request);
				conn.connect();
				code = conn.getResponseCode();
				System.out.println(code);
			} catch (Exception e) {
				BaseReporter.logException(null, e);
			}
			if (code != 502) {
				break;
			}
			retry++;
			System.out.println("code = "+code+ "..retrying..");
			Page.callForWait(3000);
		}
		return code;
	}

	public void deleteMail(String uid) throws Exception {
		try {
			URL url = new URL(API_GETMSG + uid);
			int responseCode = apiresponse(url, "DELETE");
			if(responseCode == 201) {
				BaseReporter.logPass("Mail is deleted");
			}
			else
				BaseReporter.logFail("No mail received", null);
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
	}
	
	public void deleteInbox(String mailid) throws Exception {
		try {
			URL url = new URL(API_GETINBOX + mailid.toLowerCase());
			int responseCode = apiresponse(url, "DELETE");
			if(responseCode == 201) {
				BaseReporter.logPass("Inbox is Empty");
			}
			else
				BaseReporter.logFail("Error Response code: "+responseCode, null);
		} catch (Exception e) {
			BaseReporter.logException(null, e);
		}
	}

	public String[] readMail(String uid) throws Exception {
		String inline = "";
		URL url = new URL(API_GETMSGCONTENT + uid);
		int responseCode = apiresponse(url,"GET");
		if (responseCode != 200)
			BaseReporter.logFail("Failed Response code : " + responseCode, null);
		else {
			Scanner sc = new Scanner(url.openStream());
			while (sc.hasNext()) {
				inline += sc.nextLine();
			}
			sc.close();
		}
//		parse = new JSONParser();
//		obj = (JSONObject) parse.parse(inline);
//		String html = (String) obj.get("html");
		Document doc = Jsoup.parse(inline);
		Elements body = doc.getElementsByTag("body");
		String[] arr = body.get(0).toString().split("<br>");
		return arr;
	}

	public static String getFilePath(String path) {
		File file = new File(path);
		return file.getAbsolutePath();
	}

	public static String lineBreak(String line) {

		if (line != null && !line.equals("")) {
			line = line.trim().replaceAll("\n", "").replaceAll("\r", "").replaceAll("<br>", "").replaceAll("\\<.*?\\>",
					"");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < line.length(); i++) {
				if (i > 0 && (i % 80 == 0)) {
					sb.append("<br>");
				}
				sb.append(line.charAt(i));
			}
			return sb.toString();
		}
		// return line.replaceAll("(.{70})", "$1<br>");
		return line;
	}

	public static String dateConverter(String testdata, String format) throws ParseException {
		DateFormat originalFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat(format);
		Date date = originalFormat.parse(testdata);
		String formattedDate = targetFormat.format(date);
		return formattedDate;
	}
	


	public static String pdfReader(String path) {
		PDDocument document;
		File file;
		String text = null;
		try {
			file = new File(path);
			document = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			text = pdfStripper.getText(document);
			document.close();
		} catch (IOException e) {
			BaseReporter.logException(null, e);
		}
		return text;
	}

	public static String getDownloadPath() {
		return System.getProperty("user.home") + "/Downloads/";
	}

	public static void matchText() {

	}

	public static void matchPartialText() {

	}

}
