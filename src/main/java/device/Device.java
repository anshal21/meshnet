package device;

import message.Message;
import message.MessageListener;
import node.Node;
import node.NodeInterface;
import routing.HopBasedBroadcastRouter;

import java.util.HashSet;
import java.util.Random;

import static java.util.Random.*;

/*
    Device class represents a device, it contains :-
    - An Embeded Node to enable communication
    - A list of Neighbour Nodes for routing
    - A MessageListener interface to specify processor for received message
 */
public class Device extends HopBasedBroadcastRouter implements  DeviceInterface, MessageListener {

    private String deviceID;
    private NodeInterface deviceNode;
    private HashSet<NodeInterface> neighbours;

    private boolean isTerminal;

    public Device(String deviceID, NodeInterface deviceNode, boolean isTerminal) {
        this.deviceID = deviceID;
        neighbours = new HashSet<>();
        // TODO: Add exception handling and type check here
        this.deviceNode = (Node)deviceNode;
        this.isTerminal = isTerminal;
    }

    @Override
    public String getDeviceID() {
        return this.deviceID;
    }

    @Override
    public void addNeighbour(NodeInterface node) {
        this.neighbours.add(node);
    }

    @Override
    public void removeNeighbour(NodeInterface node) {
        this.neighbours.remove(node);
    }

    @Override
    public void startReceiver() {
        this.deviceNode.startReceiver(this);
    }

    @Override
    public void broadCast(Message message) {
        neighbours.forEach(node -> {
            node.send(message);
        });
    }

    @Override
    public boolean isTerminal() {
        /*
            Here goes the check if device has internet or not.
         */
        return isTerminal;
    }

    @Override
    public void onMessageEvent(Message message) {
        message.print();
        this.processMessage(message);
    }

    private void processMessage(Message message) {

        if(message.header.getMessageType().equals(Message.Header.MESSAGE_TYPE_RESPONSE)) {
            System.out.println("INFO: Got response");
            /*
                Perform the response matching here to check if it belongs to some request
                initiated by this node.  
             */
            message.print();
        } else {
            if(this.isTerminal()) {
                System.out.println("INFO: Found Terminal Node!!!");
                System.out.println("INFO: Prepared Response Packet");
                Message newMessage = new Message(message.id, Message.Header.MESSAGE_TYPE_RESPONSE);
                newMessage.header.setSource(this.deviceID);
                message = newMessage;
            }
        }


        if (!this.shouldBroadCast(message)) {
            System.out.println("INFO: Stopping Broadcast");
            return;
        }

        message.incermentHops();
        System.out.println("INFO: Broadcasting Message");
        this.broadCast(message);
    }




}
