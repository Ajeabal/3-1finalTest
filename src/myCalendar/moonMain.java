package myCalendar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class moonMain extends JFrame {

	JTextField jtf, in_jtf1;
	JTable jt;
	DefaultTableModel dtm;
	
	public ResultSet moon(String key) {
	    api(key); // api 메서드 호출
	    return null;
	}

	public void api(String locdate) {
	    try {
	        String serviceKey = "0mrJccWYe2NqI%2FNKD1L3Bpi9R6Qmlwz%2BDFJVcD31jlpGVphejOPILwtCgXHC95ocdN%2BfTuWZ0FAvghA%2FbL3xew%3D%3D";
	        String location = "청양";

	        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/RiseSetInfoService/getAreaRiseSetInfo");
	        urlBuilder.append("?serviceKey=" + serviceKey);
	        urlBuilder.append("&locdate=" + URLEncoder.encode(locdate, "UTF-8"));
	        urlBuilder.append("&location=" + URLEncoder.encode(location, "UTF-8"));

	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");

	        BufferedReader rd;
	        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }

	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();//api받는 부분 공공데이터 예제

	        String response = sb.toString();
	        String moonrise = response.substring(response.indexOf("<moonrise>") + 10, response.indexOf("</moonrise>"));
	        String moonset = response.substring(response.indexOf("<moonset>") + 9, response.indexOf("</moonset>"));//받아온 결과중 내가 필요한 부분만 자르기 위한 substring

	        dtm.setRowCount(0);
	        String[] info = new String[2];
	        info[0] = moonrise.substring(0, 2)+"시 "+moonrise.substring(2, 5)+"분";
	        info[1] = moonset.substring(0, 2)+"시 "+ moonset.substring(2, 5)+"분";
	        dtm.addRow(info);//창에 띄움
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public moonMain() {
		this.setTitle("월출몰 정보");
		this.setBounds(400, 200, 400, 100);
		this.setLayout(null);	
		Object[] title = {"월출", "월몰"};		
		Object[][] value = new Object[0][2];
		dtm = new DefaultTableModel(value, title);		
		jt = new JTable(dtm);
		dtm.setRowCount(0);
		JScrollPane jp3 = new JScrollPane(jt);	
		jp3.setBounds(5, 5, 375, 50);
		this.add(jp3);
		this.setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new moonMain();
		
	}

}
