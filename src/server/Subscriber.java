package server;

import common.Message;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author sumit
 * 
 * Class: Subscriber
 * 
 * Class to hold details of an individual subscriber
 */
public class Subscriber {

    public String IP;
    public int port;

    public Subscriber(String IP, int port) {
        this.IP = IP;
        this.port = port;
    }

    /**
     * Publish message to the subscriber
     * @param m - Message object to be published
     */
    public void publish(Message m) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName(IP);
            byte[] messageBytes = new byte[1024];
            messageBytes = m.toString().getBytes();
            DatagramPacket sendPacket = new DatagramPacket(messageBytes, messageBytes.length, IPAddress, port);
            clientSocket.send(sendPacket);
            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Error publishing message to subscriber " + IP);
            e.printStackTrace();
        }
    }
}
