import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;

public class Example0 {
	
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
	
	public Example0(){
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, DecoderException{
		
		// get properties from file
    	getProperties();

    	wrapper = new ArthikaHFT(domain, url_stream, url_polling, url_challenge, url_token, user, password, authentication_port, request_port);
		
		wrapper.doAuthentication();
		
		// GET ACCOUNTS AND TINTERFACES
		
		List<ArthikaHFT.accountTick> accountTickList = wrapper.getAccount();
		for (ArthikaHFT.accountTick tick : accountTickList){
			System.out.println("Name: " + tick.name + " Style: " + tick.style + " Leverage: " + tick.leverage);
		}
		
		List<ArthikaHFT.tinterfaceTick> tinterfaceTickList = wrapper.getInterface();
		for (ArthikaHFT.tinterfaceTick tick : tinterfaceTickList){
			System.out.println("Name: " + tick.name + " Account: " + tick.account + " Commissions: " + tick.commissions);
		}
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
