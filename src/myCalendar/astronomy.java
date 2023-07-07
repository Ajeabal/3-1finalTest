package myCalendar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class astronomy {

    public static void main(String[] args) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/AstroEventInfoService/getAstroEventInfo?"
                + "ServiceKey=0mrJccWYe2NqI%2FNKD1L3Bpi9R6Qmlwz%2BDFJVcD31jlpGVphejOPILwtCgXHC95ocdN%2BfTuWZ0FAvghA%2FbL3xew%3D%3D&solYear=2024&solMonth=01");
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
        System.out.println(xmlResponse);

        try {
            // XML 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

            // 데이터베이스 연결 설정
            String urlDB = "jdbc:mysql://localhost:3306/app?serverTimezone=Asia/Seoul";
            String username = "root";
            String password = "1234";

            Connection connection = DriverManager.getConnection(urlDB, username, password);

            // 데이터 저장
            NodeList nodeList = document.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String locdate = element.getElementsByTagName("locdate").item(0).getTextContent();
                    String astroTime = element.getElementsByTagName("astroTime").item(0).getTextContent();
                    String astroTitle = element.getElementsByTagName("astroTitle").item(0).getTextContent();
                    String astroEvent = element.getElementsByTagName("astroEvent").item(0).getTextContent();

                    // SQL 쿼리 실행
                    String sql = "INSERT INTO event (locdate, astroTime, astroTitle, astroEvent) VALUES (?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, locdate);
                    statement.setString(2, astroTime);
                    statement.setString(3, astroTitle);
                    statement.setString(4, astroEvent);

                    statement.executeUpdate();
                }
            }

            System.out.println("데이터가 성공적으로 저장되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
