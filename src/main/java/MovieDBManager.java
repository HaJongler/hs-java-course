import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

import static com.mongodb.client.model.Filters.eq;

public class MovieDBManager implements Executor {

    private List<Movie> movies;
    private MongoClient mongoClient = new MongoClient("localhost", 27017);
    private MongoDatabase database = mongoClient.getDatabase("testdb");
    private MongoCollection collection = database.getCollection("jmdb"); // Jonathan's Movie DataBase

    public MovieDBManager() {
        String m1 = "{\"Title\":\"Blade Runner\",\"Released\":\"25 Jun 1982\",\"Plot\":\"A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.\",\"Awards\":\"Nominated for 2 Oscars. Another 11 wins & 16 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\"Year\":1981,\"Runtime\":118,\"Genres\":[\"Sci-Fi\",\"Thriller\"],\"Languages\":[\"English\",\"German\",\"Cantonese\",\"Japanese\",\"Hungarian\",\"Arabic\"],\"Countries\":[\"USA\",\"Hong Kong\"],\"Writers\":[{\"Name\":\"Hampton Fancher\",\"Type\":\"screenplay\"},{\"Name\":\"David Webb Peoples\",\"Type\":\"screenplay\"},{\"Name\":\"Philip K. Dick\",\"Type\":\"novel\"}],\"Actors\":[{\"Name\":\"Harrison Ford\",\"As\":\"Rick Deckard\"},{\"Name\":\"Rutger Hauer\",\"As\":\"Roy Batty\"},{\"Name\":\"Sean Young\",\"As\":\"Rachael\"},{\"Name\":\"Edward James Olmos\",\"As\":\"Gaff\"}],\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.2/10\",\"Votes\":602079},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"90%\"},{\"Source\":\"Metacritic\",\"Value\":\"89/100\"}],\"Director\":{\"Name\":\"Vasilii\"}}\n";
        String m2 = "{\"Title\":\"Blade Runner 2\",\"Released\":\"25 Jun 1982\",\"Plot\":\"A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.\",\"Awards\":\"Nominated for 2 Oscars. Another 11 wins & 16 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\"Year\":1983,\"Runtime\":117,\"Genres\":[\"Sci-Fi\",\"Thriller\",\"Action\"],\"Languages\":[\"English\",\"German\",\"Cantonese\",\"Japanese\",\"Hungarian\",\"Arabic\"],\"Countries\":[\"USA\",\"Hong Kong\"],\"Writers\":[{\"Name\":\"Hampton Fancher\",\"Type\":\"screenplay\"},{\"Name\":\"David Webb Peoples\",\"Type\":\"screenplay\"},{\"Name\":\"Philip K. Dick\",\"Type\":\"novel\"}],\"Actors\":[{\"Name\":\"Jonathan Harel\",\"As\":\"Rick Deckard\"},{\"Name\":\"Humpty Dumpty\",\"As\":\"Roy Batty\"},{\"Name\":\"Sean Young\",\"As\":\"Rachael\"},{\"Name\":\"Edward James Olmos\",\"As\":\"Gaff\"}],\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.3/10\",\"Votes\":6021079},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"80%\"}],\"Director\":{\"Name\":\"Vasilii\"}}\n";
        String m3 = "{\"Title\":\"Blade Runner 3\",\"Released\":\"25 Jun 1982\",\"Plot\":\"A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.\",\"Awards\":\"Nominated for 2 Oscars. Another 11 wins & 16 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\"Year\":1982,\"Runtime\":119,\"Genres\":[\"Sci-Fi\",\"Thriller\",\"Action\"],\"Languages\":[\"English\",\"German\",\"Cantonese\",\"Japanese\",\"Hungarian\",\"Arabic\"],\"Countries\":[\"USA\",\"Hong Kong\"],\"Writers\":[{\"Name\":\"Hampton Fancher\",\"Type\":\"screenplay\"},{\"Name\":\"David Webb Peoples\",\"Type\":\"screenplay\"},{\"Name\":\"Philip K. Dick\",\"Type\":\"novel\"}],\"Actors\":[{\"Name\":\"Humpty Dumpty\",\"As\":\"Rick Deckard\"},{\"Name\":\"Rutger Hauer\",\"As\":\"Roy Batty\"},{\"Name\":\"Sean Young\",\"As\":\"Rachael\"},{\"Name\":\"Edward James Olmos\",\"As\":\"Gaff\"}],\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.4/10\",\"Votes\":6022079}],\"Director\":{\"Name\":\"Vasilii\"}}\n";
        Movie[] ms = {new Movie(m1), new Movie(m2), new Movie(m3)};
        for (Movie movie: ms) addMovieToDB(movie);
        movies = Arrays.asList(ms);
    }

    public String addMovieToDB(Movie movie) {
        Document doc = Document.parse(movie.toJsonString());
        collection.insertOne(doc);
        return (String) doc.get("_id.$oid");
    }

    public List<Movie> findMovies(String key, Object value) {
        FindIterable<Document> result = collection.find(eq(key, value));
        List<Movie> movies = new ArrayList<>();
        for (Document document : result) {
            movies.add(new Movie(document.toJson()));
        }
        return movies;
    }

    public List<Movie> getAll() {
        FindIterable<Document> result = collection.find();
        List<Movie> movies = new ArrayList<>();
        for (Document document : result) {
            movies.add(new Movie(document.toJson()));
        }
        return movies;
    }

    public void updateMovie(BasicDBObject movieQuery, BasicDBObject updateQuery) {
        collection.updateMany(movieQuery, updateQuery);
    }

    public void deleteMovie(ObjectId movieId) {
        collection.deleteOne(new BasicDBObject("_id", movieId));
    }

    public void deleteAll() {
        collection.deleteMany(new BasicDBObject());
    }

    @Override
    public void execute(Runnable command) {
        command.run();
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
}
