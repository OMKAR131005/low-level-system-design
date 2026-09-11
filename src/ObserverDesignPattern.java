
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

interface Subscriber {
    void update(String channelName,String message);
}
interface Channel  {
    void add(Subscriber s);
    void remove(Subscriber s);
    void notifySubscribers(String message);
}
class  SubscriberImpl implements Subscriber {
    SubscriberImpl(String subscriberName) {
        this.subscriberName = subscriberName;
    }
    String subscriberName;
    @Override
    public void update(String channel,String message) {
        System.out.println("hey "+subscriberName+" "+message+" from "+channel);
    }
    @Override
    public boolean equals(Object obj) {
        subscriberName = ((SubscriberImpl)obj).subscriberName;
        return subscriberName.equals(subscriberName);
    }

    @Override
    public int hashCode() {
       return Objects.hash(subscriberName);
    }
}
class  ChannelImpl implements Channel {
    String channelName;
    ChannelImpl(String channelName) {
        this.channelName = channelName;
    }
    Set<Subscriber> subscribers=new HashSet<>();

    @Override
    public void add(Subscriber subscriber) {
        subscribers.add(subscriber);
    }
    @Override
    public void remove(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }
    @Override
    public void notifySubscribers(String message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(channelName,message);
        }
    }
    public void uploadVideo (String message) {
        System.out.println("Video uploaded "+"with title"+message);
        notifySubscribers(message);
    }


}
public class ObserverDesignPattern {
    public static void main(String[] args) {
        ChannelImpl ogCode=new ChannelImpl("ogCode");
        ogCode.add(new SubscriberImpl("Omkar"));
        ogCode.add(new SubscriberImpl("Omya"));
        ogCode.add(new SubscriberImpl("ajay"));
        ogCode.remove(new SubscriberImpl("Omkar"));
        ogCode.uploadVideo("Observer design pattern");
    }

}
