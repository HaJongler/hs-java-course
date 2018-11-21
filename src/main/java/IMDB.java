import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import spark.ModelAndView;
import spark.Request;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.*;
import java.util.stream.Collectors;

import static spark.Spark.*;
import static spark.Spark.delete;
import static spark.Spark.get;

public class IMDB {

    public static void main(String[] args) {
        final Gson gson = new Gson();
        final Random random = new Random();
        final MovieDBManager imdb = new MovieDBManager();

        staticFileLocation("public");

        // Creates a new movie resource, will return the ID to the created resource
        // Movie data sent in the post body as JSON.

        post("/movies", (request, response) -> {
            String movieJson = request.body();
            Movie newMovie = new Movie(movieJson);
            String id = imdb.addMovieToDB(newMovie);
            response.status(201); // 201 Created
            return id;
        });

        // Gets the movie resource for the provided id
        get("/movies/:id", (request, response) -> {
            String movieId = request.params(":id");
            Movie movie = imdb.findMovies("_id", new ObjectId(movieId)).get(0);
            if (movie == null) {
                response.status(404); // 404 Not found
                return "Movie not found";
            }
            if (clientAcceptsHtml(request)) {
                Map<String, Object> movieMap = new HashMap<>();
                movieMap.put("movie", movie);
                return render(movieMap, "movie.ftl");
            } else if (clientAcceptsJson(request))
                return movie.toJsonString();

            return null;
        });

        // Updates the movie resource for the provided id with new information
        // data is sent in the request body as JSON.
        // Due to time I only allow updating the Year, runtime and plot
        put("/movies/:id", (request, response) -> {
            String movieId = request.params(":id");
            Movie movie = imdb.findMovies("_id", new ObjectId(movieId)).get(0);
            if (movie == null) {
                response.status(404); // 404 Not found
                return "Movie not found";
            }
            Integer newYear = Integer.valueOf(request.queryParams("year"));
            Integer newRuntime = Integer.valueOf(request.queryParams("runtime"));
            String newPlot = request.queryParams("plot");
            BasicDBObject updateQuery = new BasicDBObject();
            BasicDBObject toSet = new BasicDBObject();

            if (newYear != null) {
                toSet.append("Year", newYear);
            }
            if (newRuntime != null) {
                toSet.append("Runtime", newRuntime);
            }
            if (newPlot != null) {
                toSet.append("Plot", newPlot);
            }
            updateQuery.append("$set", toSet);

            // Update in mongo
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.append("_id", new ObjectId(movieId));
            imdb.updateMovie(searchQuery, updateQuery);
            return "Movie with id '" + movieId + "' updated";
        });

        // Deletes the movie resource for the provided id
        delete("/movies/:id", (request, response) -> {
            String movieId = request.params(":id");
            Movie movie = imdb.findMovies("_id", new ObjectId(movieId)).get(0);
            if (movie == null) {
                response.status(404); // 404 Not found
                return "Movie not found";
            }
            imdb.deleteMovie(new ObjectId(movieId));
            return "Movie with id '" + movieId + "' deleted";
        });

        // Gets all available book resources
        get("/movies", (request, response) -> {
            List<String> movies = new ArrayList<>();
            if (clientAcceptsHtml(request)) {
                movies = imdb.getAll().stream().map(Movie::toJsonString).collect(Collectors.toList());
                Map<String, Object> moviesMap = new HashMap<>();
                moviesMap.put("movies", movies);
                return render(moviesMap, "movies.ftl");
            } else if (clientAcceptsJson(request))
                return gson.toJson(movies);

            return null;
        });

        // Delete all
        get("/delete", (request, response) -> {
            imdb.deleteAll();
            return "ALL MOVIES DELETED! >:(";
        });
    }

    public static String render(Map values, String template) {
        return new FreeMarkerEngine().render(new ModelAndView(values, template));
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }

    public static boolean clientAcceptsJson(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("application/json");
    }

}
