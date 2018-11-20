import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    public ChatClient(String name, String server, int port) {
        try (Socket socket = new Socket(server, port);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            out.writeUTF(name);
            out.flush();

            while (true) {
                if (in.available() > 0) System.out.println(in.readUTF());
                if (console.ready()) {
                    out.writeUTF(console.readLine());
                    out.flush();
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Server " + server + " not found");
        } catch (IOException e) {
            System.out.println("Lost connection to server " + server);
        }
    }

    public static void main(String... args) {
        ChatClient client = new ChatClient(args[0], "127.0.0.1", 8008);
    }
}
