import java.util.UUID;

/** bot that simply replies to messages */
public class BotClient extends Client implements ReplyDelegate {

  /**for scripted reply to messages */
  MessageHandler msgHandler = new MessageHandler();

  public BotClient(String address, int port) {
    println("ChatBot activated");
    ID = UUID.randomUUID().toString() + ",ChatBot";
    openSocket(address, port);
  }

  /**send scripted messages */
  @Override
  public void replyToMessage(String msg) {
    try {
      //simulate typing...|
      Thread.sleep(2000);
      //send scripted message using MessageHandler
      serverOut.println(msgHandler.getReply(msg));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void connect() {
    super.connect();
    //inform what type of connection
    serverOut.println("CHAT");
    //give the client connection a unique identifier
    serverOut.println(ID);
    //start new server connection (not a thread)
    BotListener bot = new BotListener(socket, ID);
    bot.replyDelegate = this;
    bot.listen();
  }
}
