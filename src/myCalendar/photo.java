package myCalendar;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class photo {
	public ResultSet moon(String key) {
		api(key); // api 메서드 호출
		return null;
	}

	public void api(String locdate) {
		try {
			StringBuilder urlBuilder = new StringBuilder(
					"http://apis.data.go.kr/1360000/SatlitImgInfoService/getInsightSatlit");
			urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8")
					+ "=0mrJccWYe2NqI%2FNKD1L3Bpi9R6Qmlwz%2BDFJVcD31jlpGVphejOPILwtCgXHC95ocdN%2BfTuWZ0FAvghA%2FbL3xew%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("sat", "UTF-8") + "=" + URLEncoder.encode("G2", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("data", "UTF-8") + "=" + URLEncoder.encode("ir105", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("area", "UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8"));
			urlBuilder.append("&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(locdate, "UTF-8"));

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/xml");

			System.out.println("Response code: " + conn.getResponseCode());
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
			conn.disconnect();

			String xmlResponse = sb.toString();
			String latestURL = "";
			int lastIndex = xmlResponse.lastIndexOf("<satImgC-file>");//문자열의 뒤부터<satImgC-file>
			int endIndex = xmlResponse.indexOf("</satImgC-file>", lastIndex);//</satImgC-file> 찾기
			if (lastIndex != -1 && endIndex != -1) {
				latestURL = xmlResponse.substring(lastIndex + "<satImgC-file>".length(), endIndex);
			}

			System.out.println("Latest URL: " + latestURL);

			
			JFrame frame = new JFrame("Image Viewer");//이미지 띄워줄 프레임
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());

			if (!latestURL.isEmpty()) {
				ImageIcon imageIcon = new ImageIcon(new URL(latestURL));
				JLabel imageLabel = new JLabel(imageIcon);
				panel.add(imageLabel, BorderLayout.CENTER); 
			} else {
				JLabel errorLabel = new JLabel("Failed to retrieve the image.");
				panel.add(errorLabel, BorderLayout.CENTER);
			}

			frame.getContentPane().add(panel);
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	}

}