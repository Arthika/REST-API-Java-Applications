import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;

public class Example4 {
	
	private static ArthikaHFT wrapper;
	private static String domain;
	private static String url_stream;
	private static String url_polling;
	private static String url_challenge;
	private static String url_token;
	private static String user;
	private static String password;
	private static String authentication_port;
	private static String request_port;
	
	public Example4(){
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, DecoderException{
		
		// get properties from file
    	getProperties();

    	wrapper = new ArthikaHFT(domain, url_stream, url_polling, url_challenge, url_token, user, password, authentication_port, request_port);
		
		wrapper.doAuthentication();
		
		// POSITION POLLING

		System.out.println("Starting Polling1");
		ArthikaHFT.positionTick priceTickList1 = wrapper.getPosition(null, Arrays.asList("EUR_USD", "GBP_JPY", "GBP_USD"), null);
		for (ArthikaHFT.assetPositionTick tick : priceTickList1.assetPositionTickList){
			System.out.println("Asset: " + tick.asset + " Account: " + tick.account + " Exposure: " + tick.exposure + " TotalRisk: " + tick.totalrisk);
		}
		for (ArthikaHFT.securityPositionTick tick : priceTickList1.securityPositionTickList){
			System.out.println("Security: " + tick.security + " Account: " + tick.account + " Equity: " + tick.equity + " Exposure: " + tick.exposure + " Price: " + tick.price + " Pips: " + tick.pips);
		}
		System.out.println("Polling1 Finished");
		
		System.out.println("Starting Polling2");
		ArthikaHFT.positionTick priceTickList2 = wrapper.getPosition(Arrays.asList("EUR", "GBP", "JPY", "USD"), null, Arrays.asList("Cantor", "Baxter"));
		for (ArthikaHFT.assetPositionTick tick : priceTickList2.assetPositionTickList){
			System.out.println("Asset: " + tick.asset + " Account: " + tick.account + " Exposure: " + tick.exposure + " TotalRisk: " + tick.totalrisk);
		}
		for (ArthikaHFT.securityPositionTick tick : priceTickList2.securityPositionTickList){
			System.out.println("Security: " + tick.security + " Account: " + tick.account + " Equity: " + tick.equity + " Exposure: " + tick.exposure + " Price: " + tick.price + " Pips: " + tick.pips);
		}
		System.out.println("Polling2 Finished");
	}
	
	public static void getProperties(){
    	Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			domain = prop.getProperty("domain");
			url_stream = prop.getProperty("url-stream");
			url_polling = prop.getProperty("url-polling");
			url_challenge = prop.getProperty("url-challenge");
			url_token = prop.getProperty("url-token");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
			authentication_port = prop.getProperty("authentication-port");
			request_port = prop.getProperty("request-port");
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		finally {
			if (input != null) {
				try {
					input.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }

}