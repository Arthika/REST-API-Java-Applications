import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;

public class Example7 {
	
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
	
	public Example7(){
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, DecoderException{
		
		// get properties from file
    	getProperties();

    	wrapper = new ArthikaHFT(domain, url_stream, url_polling, url_challenge, url_token, user, password, authentication_port, request_port);
		
		wrapper.doAuthentication();
		
		// ORDER CREATION
		
		// get tinterfaces
		List<ArthikaHFT.tinterfaceTick> tinterfaceTickList = wrapper.getInterface();
		
		String tinterface;
		if (tinterfaceTickList.size()>1){
			tinterface = tinterfaceTickList.get(1).name;
		}
		else{
			tinterface = tinterfaceTickList.get(0).name;
		}
		
		ArthikaHFT.orderRequest order1 = new ArthikaHFT.orderRequest();
		order1.security = "EUR_USD";
		order1.tinterface = tinterface;
		order1.quantity = 500000;
		order1.side = ArthikaHFT.SIDE_BUY;
		order1.type = ArthikaHFT.TYPE_MARKET;

		System.out.println("Starting Polling1");
		List<ArthikaHFT.orderTick> orderTickList1 = wrapper.getOrder(Arrays.asList("EUR_USD"), Arrays.asList(tinterface), null);
		for (ArthikaHFT.orderTick tick : orderTickList1){
			System.out.println("TempId: " + tick.tempid + " OrderId: " + tick.orderid + " Security: " + tick.security + " Account: " + tick.account + " Quantity: " + tick.quantity + " Type: " + tick.type + " Side: " + tick.side + " Status: " + tick.status);
		}
		System.out.println("Polling1 Finished");
		Thread.sleep(2000);
		
		System.out.println("Sending order");
		List<ArthikaHFT.orderRequest> orderList = wrapper.setOrder(Arrays.asList(order1));
		for (int i=0; i< orderList.size(); i++){
			ArthikaHFT.orderRequest orderresponse = orderList.get(i);
			System.out.println("Id: " + orderresponse.tempid + " Security: " + orderresponse.security + " Side: " + orderresponse.side + " Quantity: " + orderresponse.quantity + " Price: " + orderresponse.price + " Type: " + orderresponse.type);
		}
		System.out.println("Order sended");
		Thread.sleep(2000);
		
		System.out.println("Starting Polling2");
		List<ArthikaHFT.orderTick> orderTickList2 = wrapper.getOrder(Arrays.asList("EUR_USD"), Arrays.asList(tinterface), null);
		for (ArthikaHFT.orderTick tick : orderTickList2){
			System.out.println("TempId: " + tick.tempid + " OrderId: " + tick.orderid + " Security: " + tick.security + " Account: " + tick.account + " Quantity: " + tick.quantity + " Type: " + tick.type + " Side: " + tick.side + " Status: " + tick.status);
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
