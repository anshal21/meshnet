package node;

import message.Message;
import message.MessageListener;


/*
    Bluetooth communication class should implement this interface.
 */
public interface NodeInterface {

    public String getProtocolType();

    public byte[] encode(Message message);

    public Message decode(byte[] nodeMessage);

    public void send(Message message);

    public void startReceiver(MessageListener messageListener);
}
