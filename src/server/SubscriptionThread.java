package server;

import common.Topic;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import org.json.JSONObject;

/**
 *
 * @author sumit
 * 
 * Class: SubscriptionThread
 * 
 * Thread that runs in the background and listens for clients subscribing to
 * topics.
 * It then adds those subscribers to the respective topic in the HashMap of
 * topics.
 */
public class SubscriptionThread extends Thread {

    HashMap<String, Topic> topics = new HashMap<String, Topic>();
    int port;

    public SubscriptionThread(HashMap<String, Topic> topics, int port) {
        this.topics = topics;
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
                JSONObject message = new JSONObject(json);
                String topic = message.getString("topic");
                String subscripberIP = message.getString("ipaddress");
                int subscriberPort = message.getInt("port");
                Subscriber newSubscriber = new Subscriber(subscripberIP, subscriberPort);
                topics.get(topic).subscribers.add(newSubscriber);
                System.out.printf("**Subscriber %s with port %d subscribed to topic %s%n", subscripberIP, subscriberPort, topic);
            }
        } catch (Exception e) {
            System.err.println("Error in Subscrption Thread");
            e.printStackTrace();
        }
    }
}
