package routing;

import message.Message;

public abstract class WorkingPathBasedRouter implements Router{

    public abstract String getNodeId();

    @Override
    public boolean shouldBroadCast(Message message) {
        return !(message.inWorkingPath(this.getNodeId()));
    }

    @Override
    public String getNextHop(Message message) {
        return null;
    }

    @Override
    public Message getNextHopMessage(Message message) {
        return null;
    }
}
