import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatHandler extends Thread {
    public final String name;
    private final ChatServer server;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public ChatHandler(ChatServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;

        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        name = in.readUTF();
        server.broadcast("New chat member " + name + " from " + socket.getInetAddress());
        server.sendAllMessages(this);
    }

    public void run() {
        try (socket; in; out) {
            while (true) {
                if (in.available() > 0)
                    server.broadcast(name + ": " + in.readUTF());
            }
        } catch (IOException e) {
            System.out.println("Connection to user is lost");
            server.chatDisconnected(this);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            System.out.println("Connection to user is lost");
            server.chatDisconnected(this);
        }
    }
}
