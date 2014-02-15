package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import org.json.JSONObject;

/**
 *
 * @author sumit
 * 
 * Class: SubscriberListenerThread
 * 
 * A thread that runs in the background listening to messages being sent
 * to this client.
 * 
 * When a message is received in JSON format, it parses and prints in to 
 * std output.
 */
public class SubscriberListenerThread extends Thread {

    int port;

    public SubscriberListenerThread(int port) {
        this.port = port;
    }

    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(port);
            byte[] receiveData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String json = new String(receivePacket.getData());
                JSONObject m = new JSONObject(json);
                String message = m.getString("message");
                String author = m.getString("author");
                System.out.printf("----%nReceived Message:%nMessage: %s%nAuthor: %s%n", message, author);
            }
        } catch (Exception e) {
            System.err.println("Error in Subscriber Listener Thread");
            e.printStackTrace();
        }
    }
}
