package common;
/**
 * 
 * @author sumit
 * 
 * Class: Message
 * 
 * Just a basic class to hold contents of a message
 */
public class Message {

    public String message;
    public String author;

    public Message(String message, String author) {
        this.message = message;
        this.author = author;
    }

    /**
     * Function to convert and return content of message in JSON format
     * 
     * @return json
     */
    @Override
    public String toString() {
        String json = String.format("{'message':'%s', 'author':'%s'}", message, author);
        return json;
    }
}
