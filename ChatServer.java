import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ChatServer {

  private ServerSocket socket;
  private boolean running = false;

  public ChatServer(int port) {
    openSocket(port);
  }

  /**
   * open the server socket connection
   * @param port
   */
  private void openSocket(int port) {
    try {
      socket = new ServerSocket(port);
      running = true;
      println("Server started");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * closes server socket connection
   */
  private void stopServer() { //synchronized?
    running = false;
    try {
      socket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void startServer() {
    try {
      //continuously get client connections
      while(running) {
        //accept client connection
        Socket clientSocket = socket.accept();
        println("Connection on: " + socket.getLocalPort() + " ; " + clientSocket.getPort());

        //create and start new thread for this client
        ClientHandler client = new ClientHandler(clientSocket);
        Thread clientThread = new Thread(client);
        clientThread.start();
      }

    } catch (IOException e) {
      e.printStackTrace();

    } finally {
      //close if stop running requested
      stopServer();
    }
  }

  public static void main(String[] args) {
    ChatServer chatServer = new ChatServer(PortHelper.getPort(args));
    chatServer.startServer();
  }

  //helper functions
  public static void println(String msg) {
    System.out.println(msg);
  }
}