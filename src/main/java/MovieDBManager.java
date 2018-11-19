import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoQueryException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.LinkedList;
import java.util.concurrent.Executor;

public class MovieDBManager implements Executor {

    private LinkedList<Movie> movies;
    private MongoClient mongoClient = new MongoClient("localhost", 27017);
    private MongoDatabase database = mongoClient.getDatabase("testdb");
    private MongoCollection collection = database.getCollection("jmdb"); // Jonathan's Movie DataBase

    public MovieDBManager() {
        new MovieDBManager();
    }

    public boolean addMovieToDB(Movie movie) {
        Document doc = Document.parse(movie.toJsonString());
        collection.insertOne(doc);
        return true;
    }

    public void executeCommand(String jsoncommand) {
        MongoQuery mongoQuery = new MongoQuery(jsoncommand);
        execute(mongoQuery);
    }

    public class MongoQuery implements Runnable {

        private BasicDBObject query;

        public MongoQuery(String query) {
            this.query = BasicDBObject.parse(query);
        }

        @Override
        public void run() {
            database.runCommand(this.query);
        }
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
