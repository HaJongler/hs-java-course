import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GenderDetectorTest extends TestCase {
    GenderDetector gDetector;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        gDetector = new GenderDetector();
    }

    @After
    public void tearDown() throws Exception {
        gDetector = null;
    }

    @Test
    public void  testDetectMaleGenderByName() {
        String gender = gDetector.detectGenderByName("Jonathan");
        assertEquals("male", gender);
    }

    @Test
    public void  testDetectFemaleGenderByName() {
        String gender = gDetector.detectGenderByName("Sabrina");
        assertEquals("female", gender);
    }
}