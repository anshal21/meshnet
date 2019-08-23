package routing;

import message.Message;

public class HopBasedBroadcastRouter implements Router{

    private static final int maxHops = 10;

    @Override
    public boolean shouldBroadCast(Message message) {
        return message.header.getHops() <= maxHops;
    }

    @Override
    public String getNextHop(Message message) {
        return null;
    }

    @Override
    public Message getNextHopMessage(Message message) {
        message.incermentHops();
        return message;
    }

}
