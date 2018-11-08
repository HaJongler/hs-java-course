import javax.json.*;
import java.io.StringReader;

public class BladeRunner implements Jsonable {
    private String title = "Blade Runner";
    private String released = "25 Jun 1982";
    private String plot = "A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.";
    private String awards = "Nominated for 2 Oscars. Another 11 wins & 16 nominations.";
    private String poster = "https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg";
    private Integer year = 1982;
    private Integer runtime = 117;
    private String[] genres = {"Sci-Fi", "Thriller"};
    private String[] languages = {"English", "German", "Cantonese", "Japanese", "Hungarian", "Arabic"};
    private String[] countries = {"USA", "Hong Kong"};
    private Person director = new Person().fromJson("{\"Name\": \"Ridley Scott\"}");
    private Person[] writers = {
            new Person().fromJson("{\"Name\": \"Hampton Fancher\", \"Type\": \"screenplay\"}"),
            new Person().fromJson("{\"Name\": \"David Webb Peoples\", \"Type\": \"screenplay\"}"),
            new Person().fromJson("{\"Name\": \"Philip K. Dick\", \"Type\": \"novel\"}")
    };
    private Person[] actors = {
            new Person().fromJson("{\"Name\": \"Harrison Ford\", \"As\": \"Rick Deckard\"}"),
            new Person().fromJson("{\"Name\": \"Rutger Hauer\", \"As\": \"Roy Batty\"}"),
            new Person().fromJson("{\"Name\": \"Sean Young\", \"As\": \"Rachael\"}"),
            new Person().fromJson("{\"Name\": \"Edward James Olmos\", \"As\": \"Gaff\"}")
    };
    private Rating[] ratings = {
            new Rating().fromJson("{\"Source\":\"Internet Movie Database\",\"Value\":\"8.2/10\",\"Votes\":602079}"),
            new Rating().fromJson("{\"Source\":\"Rotten Tomatoes\",\"Value\":\"90%\"}"),
            new Rating().fromJson("{\"Source\":\"Metacritic\",\"Value\":\"89/100\"}")
    };

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

    private class Person implements Jsonable {

        private String name;
        private String type;
        private String as;

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

    private class Rating implements Jsonable {

        private String source;
        private String value;
        private Integer votes;

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
