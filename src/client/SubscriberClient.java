package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import org.json.JSONObject;
import utils.IPFunctions;

/**
 *
 * @author sumit
 * 
 * Class: SubscriberClient
 * 
 * Class that is used at the client side to subscribe and listen to published
 * messages.
 */
public class SubscriberClient {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter port to listen for messages: ");
        int port = sc.nextInt();
        //Start listening to messages being sent to this client
        new SubscriberListenerThread(port).start();
        System.out.println("To subscribe type: <publisher IP> <port> <topic>");
        
        //Infinte loop that subscribes this client to a particular topic
        while (true) {
            String inputMessage = sc.nextLine();
            if (inputMessage.trim().equals("")) {
                continue;
            }
            String[] temp = inputMessage.split(" ");
            JSONObject subscriptionRequest = new JSONObject();
            subscriptionRequest.put("ipaddress", IPFunctions.getFirstNonLoopbackAddress().toString().substring(1));
            subscriptionRequest.put("port", port);
            subscriptionRequest.put("topic", temp[2]);
            String message = subscriptionRequest.toString();
            
            int publisherPort = Integer.parseInt(temp[1]);
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(temp[0]);
            byte[] messageBytes = new byte[1024];
            messageBytes = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(messageBytes, messageBytes.length, IPAddress, publisherPort);
            clientSocket.send(sendPacket);
            clientSocket.close();
        }
    }
}
