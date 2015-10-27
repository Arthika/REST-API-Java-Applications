import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;

class ArthikaHFTPriceListenerImp12 implements ArthikaHFTPriceListener {

	@Override
	public void timestampEvent(String timestamp) {
		System.out.println("Response timestamp: " + timestamp + " Contents:");
	}
	
	@Override
	public void heartbeatEvent() {
		System.out.println("Heartbeat!");
	}
	
	@Override
	public void messageEvent(String message) {
		System.out.println("Message from server: " + message);
	}

	@Override
	public void priceEvent(List<ArthikaHFT.priceTick> priceTickList) {
		for (ArthikaHFT.priceTick tick : priceTickList){
			System.out.println("Security: " + tick.security + " Price: " + tick.price + " Side: " + tick.side + " Liquidity: " + tick.liquidity + " TI: " + tick.tinterface);
			if (tick.side.equals("ask")){
				if (tick.tinterface.equals("Cantor_CNX_3")){
					Example12.bestcanask = tick.price;
					Example12.bestcanaskliquidity = tick.liquidity;
				}
				if (tick.tinterface.equals("Baxter_CNX")){
					Example12.bestbaxask = tick.price;
					Example12.bestbaxaskliquidity = tick.liquidity;
				}
			
			}
			if (tick.side.equals("bid")){
				if (tick.tinterface.equals("Cantor_CNX_3")){
					Example12.bestcanbid = tick.price;
					Example12.bestcanbidliquidity = tick.liquidity;
				}
				if (tick.tinterface.equals("Baxter_CNX")){
					Example12.bestbaxbid = tick.price;
					Example12.bestbaxbidliquidity = tick.liquidity;
				}
				Example12.checkPrices();
			}
		}
	}
	
	@Override
    public void accountingEvent(ArthikaHFT.accountingTick accountingTick) {
		System.out.println("StrategyPL: " + accountingTick.strategyPL + " TotalEquity: " + accountingTick.totalequity + " UsedMargin: " + accountingTick.usedmargin + " FreeMargin: " + accountingTick.freemargin);
    }

	@Override
	public void assetPositionEvent(List<ArthikaHFT.assetPositionTick> assetPositionTickList) {
		for (ArthikaHFT.assetPositionTick tick : assetPositionTickList){
			System.out.println("Asset: " + tick.asset + " Account: " + tick.account + " Exposure: " + tick.exposure + " TotalRisk: " + tick.totalrisk);
		}
	}

	@Override
	public void securityPositionEvent(List<ArthikaHFT.securityPositionTick> securityPositionTickList) {
		for (ArthikaHFT.securityPositionTick tick : securityPositionTickList){
			System.out.println("Security: " + tick.security + " Account: " + tick.account + " Equity: " + tick.equity + " Exposure: " + tick.exposure + " Price: " + tick.price + " Pips: " + tick.pips);
		}
	}

	@Override
	public void positionHeartbeatEvent(ArthikaHFT.positionHeartbeat positionHeartbeatList) {
		System.out.print("Asset: " );
		for (int i=0; i<positionHeartbeatList.asset.size(); i++){
			System.out.print(positionHeartbeatList.asset.get(i));
			if (i<positionHeartbeatList.asset.size()-1){
				System.out.print(",");
			}
		}
		System.out.print(" Security: " );
		for (int i=0; i<positionHeartbeatList.security.size(); i++){
			System.out.print(positionHeartbeatList.security.get(i));
			if (i<positionHeartbeatList.security.size()-1){
				System.out.print(", ");
			}
		}
		System.out.print(" Account: " );
		for (int i=0; i<positionHeartbeatList.account.size(); i++){
			System.out.print(positionHeartbeatList.account.get(i));
			if (i<positionHeartbeatList.account.size()-1){
				System.out.print(",");
			}
		}
		System.out.println();
	}

	@Override
	public void orderEvent(List<ArthikaHFT.orderTick> orderTickList) {
		for (ArthikaHFT.orderTick tick : orderTickList){
			System.out.println("TempId: " + tick.tempid + " OrderId: " + tick.orderid + " Security: " + tick.security + " Account: " + tick.account + " Quantity: " + tick.quantity + " Type: " + tick.type + " Side: " + tick.side + " Status: " + tick.status);
		}
	}

	@Override
	public void orderHeartbeatEvent(ArthikaHFT.orderHeartbeat orderHeartbeat) {
		System.out.print("Security: " );
		for (int i=0; i<orderHeartbeat.security.size(); i++){
			System.out.print(orderHeartbeat.security.get(i));
			if (i<orderHeartbeat.security.size()-1){
				System.out.print(", ");
			}
		}
		System.out.print(" Interface: " );
		for (int i=0; i<orderHeartbeat.tinterface.size(); i++){
			System.out.print(orderHeartbeat.tinterface.get(i));
			if (i<orderHeartbeat.tinterface.size()-1){
				System.out.print(",");
			}
		}
		System.out.println();
	}    
}

public class Example12 {
	
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
	
	public static double bestcanask = 0.0;
	public static int bestcanaskliquidity = 0;
	public static String bestcanaskti = "";
	public static double bestcanbid = 0.0;
	public static int bestcanbidliquidity = 0;
	public static String bestcanbidti = "";
	
	public static double bestbaxask = 0.0;
	public static int bestbaxaskliquidity = 0;
	public static String bestbaxaskti = "";
	public static double bestbaxbid = 0.0;
	public static int bestbaxbidliquidity = 0;
	public static String bestbaxbidti = "";
	
	public Example12(){
	}
	
	public static void main(String[] args) throws InterruptedException, IOException, DecoderException{
		
		// get properties from file
    	getProperties();

    	wrapper = new ArthikaHFT(domain, url_stream, url_polling, url_challenge, url_token, user, password, authentication_port, request_port);
		
		wrapper.doAuthentication();
		
		// STRATEGY

		// Open price streaming
		long id1 = wrapper.getPriceBegin(Arrays.asList("EUR_USD"), null, "tob", 1, new ArthikaHFTPriceListenerImp12());
		Thread.sleep(10000);

		// Close price streaming
		wrapper.getPriceEnd(id1);
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
	
	public static void checkPrices(){
		if ((bestcanask<bestbaxbid && bestcanask>0.0) || (bestbaxask<bestcanbid && bestbaxask>0.0)){
			ArthikaHFT.orderRequest orderask = new ArthikaHFT.orderRequest();
			ArthikaHFT.orderRequest orderbid = new ArthikaHFT.orderRequest();
			int quantity = 0;
			if (bestcanask<bestbaxbid && bestcanask>0.0){
				quantity = bestcanaskliquidity;
				if (bestcanaskliquidity>bestbaxbidliquidity){
					quantity = bestbaxbidliquidity;
				}
				orderask.tinterface = "Cantor_CNX_3";
				orderbid.tinterface = "Baxter_CNX";
				orderask.price = bestcanask;
				orderbid.price = bestbaxbid;
			}
			if (bestbaxask<bestcanbid && bestbaxask>0.0){
				quantity = bestbaxaskliquidity;
				if (bestbaxaskliquidity>bestcanbidliquidity){
					quantity = bestcanbidliquidity;
				}
				orderask.tinterface = "Baxter_CNX";
				orderbid.tinterface = "Cantor_CNX_3";
				orderask.price = bestbaxask;
				orderbid.price = bestcanbid;
			}
			
			orderask.security = "EUR_USD";
			orderask.quantity = quantity;
			orderask.side = "buy";
			orderask.type = "limit";
			orderask.timeinforce = "fill or kill";
			
			orderbid.security = "EUR_USD";
			orderbid.quantity = quantity;
			orderbid.side = "sell";
			orderbid.type = "limit";
			orderbid.timeinforce = "fill or kill";
			
			try{
				List<ArthikaHFT.orderRequest> orderList1 = wrapper.setOrder(Arrays.asList(orderask, orderbid));
				for (int i=0; i< orderList1.size(); i++){
					ArthikaHFT.orderRequest orderresponse = orderList1.get(i);
					System.out.println("Id: " + orderresponse.tempid + " Security: " + orderresponse.security + " Side: " + orderresponse.side + " Quantity: " + orderresponse.quantity + " Price: " + orderresponse.price + " Type: " + orderresponse.type);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
