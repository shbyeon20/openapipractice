

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class WifiLocator {
    public static void main(String[] args) throws IOException {
        // Stringbuilder을 사용하여 URL에다 ?를 붙이고 필요한 parameter key와 value를 붙임
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088/494b797669627368343674784e4d63/xml/TbPublicWifiInfo/1/5/"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("KEY","UTF-8") +"=494b797669627368343674784e4d63"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("TYPE","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); //데이터 타입
        urlBuilder.append("&" + URLEncoder.encode("SERVICE","UTF-8") + "=" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); //서비스명
        urlBuilder.append("&" + URLEncoder.encode("START_INDEX","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("END_INDEX","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        //urlBuilder.append("&" + URLEncoder.encode("X_SWIFI_WRDOFC","UTF-8") + "=" + URLEncoder.encode("서대문구", "UTF-8")); /*06시 발표(정시단위) */
        //urlBuilder.append("&" + URLEncoder.encode("X_SWIFI_ADRES1","UTF-8") + "=" + URLEncoder.encode("서소문로", "UTF-8")); /*예보지점의 X 좌표값*/
        System.out.println(urlBuilder.toString());

        // 완성된 String을 URL class를 사용하여 url개체를 만듬
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
        System.out.println(sb.toString());
    }
}