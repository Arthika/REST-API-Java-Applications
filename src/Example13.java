import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;

public class Example13 {
	
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
	
	public Example13(){
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException, DecoderException{
		
		// get properties from file
    	getProperties();

    	wrapper = new ArthikaHFT(domain, url_stream, url_polling, url_challenge, url_token, user, password, authentication_port, request_port);
		
		wrapper.doAuthentication();
		
		// MULTIPLE ORDER CREATION
		
		ArthikaHFT.orderRequest order1 = new ArthikaHFT.orderRequest();
		order1.security = "EUR_USD";
		order1.tinterface = "Cantor_CNX_3";
		order1.quantity = 1000000;
		order1.side = "buy";
		order1.type = "market";
		order1.timeinforce = "day";
		
		ArthikaHFT.orderRequest order2 = new ArthikaHFT.orderRequest();
		order2.security = "EUR_USD";
		order2.tinterface = "Baxter_CNX";
		order2.quantity = 1000000;
		order2.side = "sell";
		order2.type = "market";
		order2.timeinforce = "day";
		
		ArthikaHFT.orderRequest order3 = new ArthikaHFT.orderRequest();
		order3.security = "EUR_USD";
		order3.tinterface = "Cantor_CNX_3";
		order3.quantity = 1000000;
		order3.side = "buy";
		order3.type = "market";
		order3.timeinforce = "day";
		
		ArthikaHFT.orderRequest order4 = new ArthikaHFT.orderRequest();
		order4.security = "EUR_USD";
		order4.tinterface = "Baxter_CNX";
		order4.quantity = 1000000;
		order4.side = "sell";
		order4.type = "market";
		order4.timeinforce = "day";
		
		ArthikaHFT.orderRequest order5 = new ArthikaHFT.orderRequest();
		order5.security = "EUR_USD";
		order5.tinterface = "Cantor_CNX_3";
		order5.quantity = 1000000;
		order5.side = "buy";
		order5.type = "market";
		order5.timeinforce = "day";
		
		ArthikaHFT.orderRequest order6 = new ArthikaHFT.orderRequest();
		order6.security = "EUR_USD";
		order6.tinterface = "Baxter_CNX";
		order6.quantity = 1000000;
		order6.side = "sell";
		order6.type = "market";
		order6.timeinforce = "day";

		System.out.println("Starting Polling1");
		List<ArthikaHFT.orderTick> orderTickList1 = wrapper.getOrder(Arrays.asList("EUR_USD"), null, null);
		for (ArthikaHFT.orderTick tick : orderTickList1){
			System.out.println("TempId: " + tick.tempid + " OrderId: " + tick.orderid + " Security: " + tick.security + " Account: " + tick.account + " Quantity: " + tick.quantity + " Type: " + tick.type + " Side: " + tick.side + " Status: " + tick.status);
		}
		System.out.println("Polling1 Finished");
		Thread.sleep(5000);
		
		System.out.println("Sending order");
		List<ArthikaHFT.orderRequest> orderList1 = wrapper.setOrder(Arrays.asList(order1, order2, order3, order4, order5, order6, order1, order2, order3, order4, order5, order6, order1, order2, order3, order4, order5, order6));
		for (int i=0; i< orderList1.size(); i++){
			ArthikaHFT.orderRequest orderresponse = orderList1.get(i);
			System.out.println("Id: " + orderresponse.tempid + " Security: " + orderresponse.security + " Side: " + orderresponse.side + " Quantity: " + orderresponse.quantity + " Price: " + orderresponse.price + " Type: " + orderresponse.type);
		}
		List<ArthikaHFT.orderRequest> orderList2 = wrapper.setOrder(Arrays.asList(order1, order2, order3, order4, order5, order6, order1, order2, order3, order4, order5, order6, order1, order2, order3, order4, order5, order6));
		for (int i=0; i< orderList2.size(); i++){
			ArthikaHFT.orderRequest orderresponse = orderList2.get(i);
			System.out.println("Id: " + orderresponse.tempid + " Security: " + orderresponse.security + " Side: " + orderresponse.side + " Quantity: " + orderresponse.quantity + " Price: " + orderresponse.price + " Type: " + orderresponse.type);
		}
		List<ArthikaHFT.orderRequest> orderList3 = wrapper.setOrder(Arrays.asList(order1, order2, order3, order4, order5, order6, order1, order2, order3, order4, order5, order6, order1, order2, order3, order4, order5, order6));
		for (int i=0; i< orderList3.size(); i++){
			ArthikaHFT.orderRequest orderresponse = orderList3.get(i);
			System.out.println("Id: " + orderresponse.tempid + " Security: " + orderresponse.security + " Side: " + orderresponse.side + " Quantity: " + orderresponse.quantity + " Price: " + orderresponse.price + " Type: " + orderresponse.type);
		}
		System.out.println("Order sended");
		Thread.sleep(5000);
		
		System.out.println("Starting Polling2");
		List<ArthikaHFT.orderTick> orderTickList2 = wrapper.getOrder(Arrays.asList("EUR_USD"), null, null);
		for (ArthikaHFT.orderTick tick : orderTickList2){
			System.out.println("TempId: " + tick.tempid + " OrderId: " + tick.orderid + " Security: " + tick.security + " Account: " + tick.account + " Quantity: " + tick.finishedquantity + " Type: " + tick.type + " Side: " + tick.side + " Status: " + tick.status);
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
