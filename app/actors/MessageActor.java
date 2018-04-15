package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

public class MessageActor extends UntypedActor {
    private final ActorRef out;

    //Self Reference the actor
    public MessageActor(ActorRef out) {
        this.out = out; //constructor
    }

    //PROPS
    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out); //out is the actor reference
        //props,reference and class
    }

    //object of newsagent service
    private NewsAgentService newsAgentService = new NewsAgentService();
    //object of feed service
    private FeedService feedService = new FeedService();

    private FeedResponse feedResponse = new FeedResponse();

    //define another actor reference (this.actor=some_actor)
    //dynamic object message
    @Override
    public void onReceive(Object message) throws Throwable {
        //send the response
        ObjectMapper mapper = new ObjectMapper();
        if (message instanceof String) {
            Message messageObject = new Message();
            messageObject.text = (String) message;
            messageObject.sender = Message.Sender.USER;
            out.tell(mapper.writeValueAsString(messageObject), self());
            //writeValueAsString sends JSON, self to say refer to this actor itself
            String query = newsAgentService.getNewsAgentResponse("Find " + messageObject.text, UUID.randomUUID()).query;
            feedResponse = feedService.getFeedByQuery(query);
            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + query;
            messageObject.feedResponse = feedResponse;
            messageObject.sender = Message.Sender.BOT;
            out.tell(mapper.writeValueAsString(messageObject), self());
        }
    }
}