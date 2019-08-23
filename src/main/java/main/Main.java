package main;

import device.Device;
import message.Message;
import node.Node;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("DeviceID: ");
        String deviceID = sc.next();

        System.out.print("NodeID: ");
        String nodeID = sc.next();

        System.out.print("Port: ");
        int port = sc.nextInt();

        System.out.println("IsTerminal(0/1): ");
        int val = sc.nextInt();

        boolean isTerminal = (val==1);

        String protocolID = "JavaSockets";

        /*
            While using BT, instead of Node, create an instance of BTNode ( to be implmented ) and
            initialize device with that.
         */
        Node node = new Node(nodeID, "localhost", port, protocolID);
        Device device = new Device(deviceID, node, isTerminal);

        System.out.print("Neighbour count: ");
        int neighbourCount = sc.nextInt();

        for(int i=0; i<neighbourCount; i++) {
            System.out.print("NodeID: ");
            String neighbourNodeID = sc.next();

            System.out.print("Address: ");
            String neighbourAddress = sc.next();

            System.out.print("Port: ");
            int neighbourPort = sc.nextInt();

            Node neighbourNode = new Node(neighbourNodeID, neighbourAddress, neighbourPort, protocolID);
            device.addNeighbour(neighbourNode);
        }

        device.startReceiver();

        while(true) {
            System.out.println("Message: ");
            String messageString = sc.next();
            Message message = new Message(Message.Header.MESSAGE_TYPE_REQUEST);
            message.payload = messageString;
            message.header.setHops(0);
            message.header.setSource(nodeID);
            device.onMessageEvent(message);
        }

    }
}
