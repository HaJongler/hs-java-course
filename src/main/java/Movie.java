import javax.json.*;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Movie implements Jsonable {

    public String title;
    public String released;
    public String plot;
    public String awards;
    public String poster;
    public Integer year;
    public Integer runtime;
    public String[] genres;
    public String[] languages;
    public String[] countries;
    public Person director;
    public Person[] writers;
    public Person[] actors;
    public Rating[] ratings;

    public Movie(String json) {
        fromJson(json);
    }

    @Override
    public String toJsonString() {
        return toJsonObject().toString();
    }

    @Override
    public JsonObject toJsonObject() {
        JsonArrayBuilder genres_b = Json.createArrayBuilder();
        for (String genre : this.genres) genres_b.add(genre);

        JsonArrayBuilder languages_b = Json.createArrayBuilder();
        for (String language : this.languages) languages_b.add(language);

        JsonArrayBuilder countries_b = Json.createArrayBuilder();
        for (String country : this.countries) countries_b.add(country);

        JsonArrayBuilder writers_b = Json.createArrayBuilder();
        for (Person writer : this.writers) writers_b.add(writer.toJsonObject());

        JsonArrayBuilder actors_b = Json.createArrayBuilder();
        for (Person actor : this.actors) actors_b.add(actor.toJsonObject());

        JsonArrayBuilder ratings_b = Json.createArrayBuilder();
        for (Rating rating : this.ratings) ratings_b.add(rating.toJsonObject());

        JsonObject model = Json.createObjectBuilder()
                .add("Title", title)
                .add("Released", released)
                .add("Plot", plot)
                .add("Awards", awards)
                .add("Poster", poster)
                .add("Year", year)
                .add("Runtime", runtime)
                .add("Genres", genres_b.build())
                .add("Languages", languages_b.build())
                .add("Countries", countries_b.build())
                .add("Writers", writers_b.build())
                .add("Actors", actors_b.build())
                .add("Ratings", ratings_b.build())
                .add("Director", director.toJsonObject())
                .build();
        return model;
    }

    public void fromJson(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject jObject = reader.readObject();
        this.title = jObject.getString("Title");
        this.released = jObject.getString("Released");
        this.plot = jObject.getString("Plot");
        this.awards = jObject.getString("Awards");
        this.poster = jObject.getString("Poster");
        this.year = jObject.getInt("Year");
        this.runtime = jObject.getInt("Runtime");

        JsonArray genres_a = jObject.getJsonArray("Genres");
        this.genres = new String[genres_a.size()];
        for (int i = 0; i < genres_a.size(); i++)
            this.genres[i] = genres_a.get(i).toString();

        JsonArray languages_a = jObject.getJsonArray("Genres");
        this.languages = new String[languages_a.size()];
        for (int i = 0; i < languages_a.size(); i++)
            this.languages[i] = languages_a.get(i).toString();

        JsonArray countries_a = jObject.getJsonArray("Countries");
        this.countries = new String[countries_a.size()];
        for (int i = 0; i < countries_a.size(); i++)
            this.countries[i] = countries_a.get(i).toString();

        JsonArray writers_a = jObject.getJsonArray("Writers");
        this.writers = new Person[writers_a.size()];
        for (int i = 0; i < writers_a.size(); i++)
            this.writers[i] = new Person().fromJson(writers_a.get(i).toString());

        JsonArray actors_a = jObject.getJsonArray("Actors");
        this.actors = new Person[actors_a.size()];
        for (int i = 0; i < actors_a.size(); i++)
            this.actors[i] = new Person().fromJson(actors_a.get(i).toString());

        JsonArray ratings_a = jObject.getJsonArray("Ratings");
        this.ratings = new Rating[ratings_a.size()];
        for (int i = 0; i < ratings_a.size(); i++)
            this.ratings[i] = new Rating().fromJson(ratings_a.get(i).toString());

        this.director = new Person().fromJson(jObject.getJsonObject("Director").toString());
    }

    public static List<String> getNames(List<Movie> movies) {
        List<String> names = movies.stream()
                .map(x -> x.title)
                .collect(Collectors.toList());
        return names;
    }

    public static List<Movie> containActors(List<Movie> movies, String actor) {
        List<Movie> goodMovies = movies.stream()
                .filter(x -> Arrays.asList(x.actors).stream().anyMatch(y -> y.name.equals(actor)))
                .collect(Collectors.toList());
        return goodMovies;
    }

    public static List<Movie> containGenre(List<Movie> movies, String genre) {
        List<Movie> goodMovies = movies.stream()
                .filter(x -> Arrays.asList(x.genres).stream().anyMatch(y -> y.contains(genre)))
                .collect(Collectors.toList());
        return goodMovies;
    }

    public static List<Movie> containDirector(List<Movie> movies, String director) {
        List<Movie> goodMovies = movies.stream()
                .filter(x -> x.director.name.equals(director))
                .collect(Collectors.toList());
        return goodMovies;
    }

    public static List<String> sortMoviesNames(List<Movie> movies, String by) {
        if (by == "year") {
            movies.sort((m1, m2) -> m1.year - m2.year);
            return getNames(movies);
        } else if (by == "runtime") {
            movies.sort((m1, m2) -> m1.runtime - m2.runtime);
            return getNames(movies);
        } else {
            // Sort by ratings
            movies.sort((m1, m2) -> m1.ratings.length - m2.ratings.length);
            return getNames(movies);
        }
    }

    public class Person implements Jsonable {

        public String name;
        public String type;
        public String as;

        @Override
        public String toJsonString() {
            return toJsonObject().toString();
        }

        @Override
        public JsonObject toJsonObject() {
            JsonObjectBuilder model = Json.createObjectBuilder();
            if (this.name != null) model.add("Name", name);
            if (this.type != null) model.add("Type", type);
            if (this.as != null) model.add("As", as);
            return model.build();
        }

        public Person fromJson(String json) {
            JsonReader reader = Json.createReader(new StringReader(json));
            JsonObject jObject = reader.readObject();
            if (jObject.containsKey("Name")) this.name = jObject.getString("Name");
            if (jObject.containsKey("Type")) this.type = jObject.getString("Type");
            if (jObject.containsKey("As")) this.as = jObject.getString("As");
            return this;
        }
    }

    public class Rating implements Jsonable {

        public String source;
        public String value;
        public Integer votes;

        @Override
        public String toJsonString() {
            return toJsonObject().toString();
        }

        @Override
        public JsonObject toJsonObject() {
            JsonObjectBuilder model = Json.createObjectBuilder()
                    .add("Source", source)
                    .add("Value", value);
            if (this.votes != null) model.add("Votes", votes);
            return model.build();
        }

        public Rating fromJson(String json) {
            JsonReader reader = Json.createReader(new StringReader(json));
            JsonObject jObject = reader.readObject();
            this.source = jObject.getString("Source");
            this.value = jObject.getString("Value");
            if (jObject.containsKey("Votes")) this.votes = jObject.getInt("Votes");
            return this;
        }
    }

}