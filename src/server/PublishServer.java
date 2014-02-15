package server;

import common.Message;
import common.Topic;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author sumit
 *
 * Class PublishServer
 *
 * Class that runs on the server. Handles listening for incoming subscriptions.
 * Also responsible to take as input new messages and publish it to the
 * subscribed clients.
 */
public class PublishServer {

    static Topic[] topics;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter topics separated by spaces:");
        String line = sc.nextLine();
        String[] temp = line.split(" ");

        //Create a HashMap of topics with topic name as key.
        HashMap<String, Topic> topics = new HashMap<String, Topic>();
        for (String t : temp) {
            topics.put(t, new Topic(t));
        }
        System.out.println("Enter port for running server: ");
        int port = sc.nextInt();

        //Listen for new subscriptions in the background.
        new SubscriptionThread(topics, port).start();
        System.out.println("To publish message,");
        System.out.println("<topic>: <message> - <author>");

        //Take new mssages as input and publish them
        while (true) {
            String inputMessage = sc.nextLine();
            if (inputMessage.trim().equals("")) {
                continue;
            }
            temp = inputMessage.split(":");
            String messageTopic = temp[0];
            String message = temp[1].trim();
            temp = message.split("-");

            Message m = new Message(temp[0].trim(), temp[1].trim());
            topics.get(messageTopic).publishMessageToSubscribers(m);
        }
    }
}
