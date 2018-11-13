import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MovieTest extends TestCase {

    public List<Movie> movies;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String m1 = "{\"Title\":\"Blade Runner\",\"Released\":\"25 Jun 1982\",\"Plot\":\"A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.\",\"Awards\":\"Nominated for 2 Oscars. Another 11 wins & 16 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\"Year\":1981,\"Runtime\":118,\"Genres\":[\"Sci-Fi\",\"Thriller\"],\"Languages\":[\"English\",\"German\",\"Cantonese\",\"Japanese\",\"Hungarian\",\"Arabic\"],\"Countries\":[\"USA\",\"Hong Kong\"],\"Writers\":[{\"Name\":\"Hampton Fancher\",\"Type\":\"screenplay\"},{\"Name\":\"David Webb Peoples\",\"Type\":\"screenplay\"},{\"Name\":\"Philip K. Dick\",\"Type\":\"novel\"}],\"Actors\":[{\"Name\":\"Harrison Ford\",\"As\":\"Rick Deckard\"},{\"Name\":\"Rutger Hauer\",\"As\":\"Roy Batty\"},{\"Name\":\"Sean Young\",\"As\":\"Rachael\"},{\"Name\":\"Edward James Olmos\",\"As\":\"Gaff\"}],\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.2/10\",\"Votes\":602079},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"90%\"},{\"Source\":\"Metacritic\",\"Value\":\"89/100\"}],\"Director\":{\"Name\":\"Vasilii\"}}\n";
        String m2 = "{\"Title\":\"Blade Runner 2\",\"Released\":\"25 Jun 1982\",\"Plot\":\"A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.\",\"Awards\":\"Nominated for 2 Oscars. Another 11 wins & 16 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\"Year\":1983,\"Runtime\":117,\"Genres\":[\"Sci-Fi\",\"Thriller\",\"Action\"],\"Languages\":[\"English\",\"German\",\"Cantonese\",\"Japanese\",\"Hungarian\",\"Arabic\"],\"Countries\":[\"USA\",\"Hong Kong\"],\"Writers\":[{\"Name\":\"Hampton Fancher\",\"Type\":\"screenplay\"},{\"Name\":\"David Webb Peoples\",\"Type\":\"screenplay\"},{\"Name\":\"Philip K. Dick\",\"Type\":\"novel\"}],\"Actors\":[{\"Name\":\"Jonathan Harel\",\"As\":\"Rick Deckard\"},{\"Name\":\"Humpty Dumpty\",\"As\":\"Roy Batty\"},{\"Name\":\"Sean Young\",\"As\":\"Rachael\"},{\"Name\":\"Edward James Olmos\",\"As\":\"Gaff\"}],\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.3/10\",\"Votes\":6021079},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"80%\"}],\"Director\":{\"Name\":\"Vasilii\"}}\n";
        String m3 = "{\"Title\":\"Blade Runner 3\",\"Released\":\"25 Jun 1982\",\"Plot\":\"A blade runner must pursue and terminate four replicants who stole a ship in space, and have returned to Earth to find their creator.\",\"Awards\":\"Nominated for 2 Oscars. Another 11 wins & 16 nominations.\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNzQzMzJhZTEtOWM4NS00MTdhLTg0YjgtMjM4MDRkZjUwZDBlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\",\"Year\":1982,\"Runtime\":119,\"Genres\":[\"Sci-Fi\",\"Thriller\",\"Action\"],\"Languages\":[\"English\",\"German\",\"Cantonese\",\"Japanese\",\"Hungarian\",\"Arabic\"],\"Countries\":[\"USA\",\"Hong Kong\"],\"Writers\":[{\"Name\":\"Hampton Fancher\",\"Type\":\"screenplay\"},{\"Name\":\"David Webb Peoples\",\"Type\":\"screenplay\"},{\"Name\":\"Philip K. Dick\",\"Type\":\"novel\"}],\"Actors\":[{\"Name\":\"Humpty Dumpty\",\"As\":\"Rick Deckard\"},{\"Name\":\"Rutger Hauer\",\"As\":\"Roy Batty\"},{\"Name\":\"Sean Young\",\"As\":\"Rachael\"},{\"Name\":\"Edward James Olmos\",\"As\":\"Gaff\"}],\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.4/10\",\"Votes\":6022079}],\"Director\":{\"Name\":\"Vasilii\"}}\n";
        Movie[] ms = {new Movie(m1), new Movie(m2), new Movie(m3)};
        this.movies = Arrays.asList(ms);
    }

    @Test
    public void testSortMoviesNamesYear() {
        List<String> s = Movie.sortMoviesNames(this.movies, "year");
        assertEquals("Blade Runner", s.get(0));
    }

    @Test
    public void testSortMoviesNamesRuntime() {
        List<String> s = Movie.sortMoviesNames(this.movies, "runtime");
        assertEquals("Blade Runner 2", s.get(0));
    }

    @Test
    public void testSortMoviesNamesRatings() {
        List<String> s = Movie.sortMoviesNames(this.movies, "ratings");
        assertEquals("Blade Runner 3", s.get(0));
    }

    @Test
    public void testContainActors() {
        List<Movie> containsActorHumptyDumpty = Movie.containActors(movies, "Humpty Dumpty");
        assertEquals(2, containsActorHumptyDumpty.size());
    }

    @Test
    public void testContainGenre() {
        List<Movie> containsGenreAction = Movie.containGenre(movies, "Action");
        assertEquals(2, containsGenreAction.size());
    }

    @Test
    public void testContainDirector() {
        List<Movie> containsDirector = Movie.containDirector(movies, "Vasilii");
        assertEquals(3, containsDirector.size());
    }
}