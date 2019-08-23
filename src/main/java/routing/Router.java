package routing;


import message.Message;

public interface Router {
    public boolean shouldBroadCast(Message message);
    public String getNextHop(Message message);
    public Message getNextHopMessage(Message message);
}
