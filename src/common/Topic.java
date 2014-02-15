package common;

import java.util.ArrayList;
import server.Subscriber;

/**
 *
 * @author sumit
 * 
 * Class: Topic
 * 
 * Class to store the name and list of subscribers to a topic.
 * 
 */
public class Topic {
    
    public String name;
    public ArrayList<Subscriber> subscribers;
    
    public Topic(String name) {
        this.name = name;
        subscribers = new ArrayList<Subscriber>();
    }
    
    /**
     * 
     * @param m - Message object to be published
     * 
     * Publishes content of the given message to list of subscribers
     */
    public void publishMessageToSubscribers(Message m) {
        for (Subscriber s : subscribers) {
            s.publish(m);
        }
    }
}
