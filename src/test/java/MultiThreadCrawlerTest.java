import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultiThreadCrawlerTest extends TestCase {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testGetContect() {
        String[] args = {"https://vasart.github.io/harbour-java-course/page3.html"};
        MultiThreadCrawler crawler = new MultiThreadCrawler(args);
        MultiThreadCrawler.Webpage w = crawler.toVisit.getFirst();
        String text = w.getContentOfWebPage(w.url);
        assertEquals("<!DOCTYPE html><html><body><h1>Goodbye</h1></body></html>", text);
    }
}