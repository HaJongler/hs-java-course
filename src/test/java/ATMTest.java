import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ATMTest extends TestCase {
    ATM atm;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        Integer[] bills = {50, 20, 10};
        Integer[] stocks = {3, 2, 2};
        atm = new ATM(bills, stocks);
    }

    @Test
    public void testLegalWithdraw() {
        atm.withdrawl(200);
        assertEquals((Integer) 10, atm.getBalance());
    }

    @Test
    public void testImpossibleWithdraw() {
        atm.withdrawl(222);
        assertEquals((Integer) 210, atm.getBalance());
    }

    @Test
    public void testFinishHim() {
        atm.withdrawl(210);
        assertEquals((Integer) 0, atm.getBalance());
    }
}