package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.ItemProduct;

public class ClientSideApp {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {

		ItemProduct ip = new ItemProduct();
		ip.setName("Spark Plug BKRE");
		ip.setPrice(100);
		
		ItemProduct ip2 = new ItemProduct();
		ip2.setName("Clutch Plate");
		ip2.setPrice(200);
		
		ItemProduct ip3 = new ItemProduct();
		ip3.setName("Engine Oil");
		ip3.setPrice(300);
		
		List<ItemProduct> itemProduct = new ArrayList<ItemProduct>();
		itemProduct.add(ip);
		itemProduct.add(ip2);
		itemProduct.add(ip3);
		
		try {
			
			// Data to establish connection to server
			int portNo = 4228;
			InetAddress serverAddress = InetAddress.getLocalHost();
			
			// Connect to the server at local host, port 4228
			Socket socket = new Socket(serverAddress, portNo);
			
			// Open stream to send object
			ObjectOutputStream objectOS = new ObjectOutputStream(socket.getOutputStream());
			
			//Send request to server
			System.out.println("Send object to server: " + itemProduct);
			objectOS.writeUnshared(itemProduct);
			objectOS.flush();
			
			//Open stream to receive object
			ObjectInputStream objectIS = new ObjectInputStream(socket.getInputStream());
			
			// Get object from stream, cast and display details
			itemProduct = (ArrayList<ItemProduct>) objectIS.readObject();
			
			for(ItemProduct itemproduct: itemProduct) {
				System.out.println ("Id product =  " + itemproduct.getItemProductId() + "\n"
						+ "Name product =  " + itemproduct.getName() + "\n"
						+ "Price product =  " + itemproduct.getPrice() + "\n");
			}		
			
			Thread.sleep(1000);
			
			// Close all closable objects
			objectOS.close();			
			objectIS.close();
			socket.close();			
			
		} catch (IOException | ClassNotFoundException e) {			
			e.printStackTrace();				
		} 
		
		System.out.println("\nClientSideApp: End of application.\n");			
	}
}
