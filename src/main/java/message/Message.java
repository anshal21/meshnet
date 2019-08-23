package message;

import java.util.LinkedHashSet;
import java.util.Random;

public class Message {

     // transport layer

    public String payload;

    public String id;

    public Header header;



    public Message(String messageType) {
        Random r = new Random();
        this.id = "" + r.nextInt();
        this.header = new Header(messageType);
    }

    public Message(String id, String messageType) {
        this.id = id;
        this.header = new Header(messageType);
    }


    public void print() {
        System.out.println("***********Start***************");
        System.out.println("ID: " + id);
        System.out.println("MessageType: " + this.header.messageType);
        System.out.println("Source: " + this.header.source);
        System.out.println("Comment: " + payload);
        System.out.println("Hops: " + this.header.hops);
        System.out.println("***********End*****************");
        return;
    }

    public void addNodeToWorkingPath(String s) {
        this.header.workingPath.add(s);
    }

    public boolean inWorkingPath(String s) {
        return this.header.workingPath.contains(s);
    }



    public void incermentHops() {
        this.header.hops += 1;
    }


    public static class Header {

        public static final String MESSAGE_TYPE_REQUEST = "MESSAGE_TYPE_REQUEST";
        public static final String MESSAGE_TYPE_RESPONSE = "MESSAGE_TYPE_RESPONSE";

        public Header(String messageType) {
            this.messageType = messageType;
        }

        String messageType;
        LinkedHashSet<String> workingPath;
        int hops;
        String source;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public int getHops() {
            return hops;
        }

        public void setHops(int hops) {
            this.hops = hops;
        }


    }

}


