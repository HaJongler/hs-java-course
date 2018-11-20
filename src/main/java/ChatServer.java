import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection collection;
    private Document doc;
    private Set<ChatHandler> chats = ConcurrentHashMap.newKeySet();

    public ChatServer(int port) {
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("testdb");
        collection = database.getCollection("chat");

        try (ServerSocket s = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (true) {
                ChatHandler chat = new ChatHandler(this, s.accept());
                chats.add(chat);
                chat.start();
            }
        } catch (IOException e) {
            System.out.println("Server failed on port " + port);
        }
    }

    public static void main(String[] args) {
        new ChatServer(8008);
    }

    public void sendAllMessages(ChatHandler chat) {
        FindIterable<Document> cursor = collection.find();
        if (collection.count() > 0) {
            for (Document doc : cursor) {
                String name = doc.getString("name");
                String msg = doc.getString("message");
                chat.sendMessage(name + ": " + msg);
            }
        }
    }

    public synchronized void broadcast(String message) {
        System.out.println("New message -> " + message);
        doc = new Document("message", message);
        collection.insertOne(doc);
        for (ChatHandler chat : chats) {
            chat.sendMessage(message);
        }
    }

    public void chatDisconnected(ChatHandler chat) {
        chats.remove(chat);
        broadcast("Chat member " + chat.name + " left");
    }
}