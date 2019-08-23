package node;

import com.google.gson.Gson;
import message.Message;
import message.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Node implements NodeInterface {

    private String protocolID;
    String nodeID;
    private NodeAddress address;
    private static final Logger logger = LoggerFactory.getLogger(Node.class.getName());

    private MessageListener messageListener;


    public Node(String nodeID, String nodeAddress, int port,  String protocolID) {
        this.nodeID = nodeID;
        this.address = new NodeAddress(nodeAddress, port);
        this.protocolID = protocolID;
    }

    public String getProtocolType() {
        return null;
    }

    public byte[] encode(Message message) {
        Gson gson = new Gson();
        byte[] messageBytes = gson.toJson(message).getBytes();
        return messageBytes;
    }


    public Message decode(byte[] messageBytes) {
        Gson gson = new Gson();
        Message message = gson.fromJson(new String(messageBytes), Message.class);
        return message;
    }

    public void send(Message message) {
        /*
            For bluetooth, create a similar class implementing NodeInterface
            send method should contain logic to send message to another device.
         */
        Socket socket;
        try {
            socket = new Socket(this.address.nodeAddress, this.address.port);
            System.out.println("Sending message... to " + this.address.nodeAddress + ":" + this.address.port);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(new String(this.encode(message)));

        } catch (Exception e) {
          //  logger.error("Failed to send the message", e);
            System.out.println("Failed to send the message " +  e);
        }

    }

    public void startReceiver(MessageListener messageListener) {
        /*
            startReceiver here starts a server to receive messages
            For Bluetooth: Implement the logic in the method to receive messages from nodes.
         */

        Runnable server = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(address.port);
                   // logger.info("Server started", "Port", address.port, "NodeID", nodeID);
                    System.out.println("Server Started");
                    while (true) {
                        Socket socket = serverSocket.accept();
                        DataInputStream dis = new DataInputStream(socket.getInputStream());
                        String messageString = dis.readUTF();
                        Message message = decode(messageString.getBytes());
                        System.out.println("Got message");
                        messageListener.onMessageEvent(message);
                    }
                } catch(Exception e) {
                //    logger.error("Failed to start server", e);
                    System.out.println("Failed to start server");
                }

            }
        };

        Thread t = new Thread(server);
        t.start();

    }

    /*
        This inner class is very specific to Java Sockets.
        While implementing the Bluetooth node, create a similar inner class to container
        Bluetooth related addresses.
     */
    public static class NodeAddress {

        NodeAddress(String nodeAddress, int port) {
            this.port = port;
            this.nodeAddress = nodeAddress;
        }

        String nodeAddress;
        int port;
    }

}