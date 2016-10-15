package com.sarp.test.adminService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainRest {

	public static void main(String[] args) {
		StringBuilder resultado = new StringBuilder();
		try {
			URL url = new URL("http://52.52.100.160:8080/SARPService/adminService/displays");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("user-rol","Responsableector");
			
			if (conn.getResponseCode() != 200) {
				System.out.println("erorr ");
				resultado.append("Error");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));
	
			String output;
			System.out.println("RestClient GET con " + "" );
			while ((output = br.readLine()) != null) {
				resultado.append(output);
			}
	
			conn.disconnect();
			
	
		} catch (MalformedURLException e) {
	
			e.printStackTrace();
	
		} catch (Exception e) {
			System.out.println("hice print aca "); 
			
			e.printStackTrace();
	
		}	

	}

}
