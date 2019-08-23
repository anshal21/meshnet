package device;

import message.Message;
import node.NodeInterface;

public interface DeviceInterface {

    public String getDeviceID();

    public void addNeighbour(NodeInterface node);

    public void removeNeighbour(NodeInterface node);

    public void startReceiver();

    public void broadCast(Message message);

    public boolean isTerminal();
}

