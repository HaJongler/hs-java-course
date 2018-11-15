import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ATMTest extends TestCase {
    ATM atm;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String[] containers = {"50", "20", "10"};
        atm = new ATM(containers);
    }

    @Test
    public void testFirstDeposit() {
        atm.deposit(70);
        assertEquals((Integer) 70, atm.getBalance());
    }

    @Test
    public void testSecondDeposit() {
        atm.deposit(70);
        atm.deposit(113);
        assertEquals((Integer) 183, atm.getBalance());
    }

    @Test
    public void testWithdrawl() {
        atm.deposit(70);
        atm.deposit(113);
        atm.withdrawl(100);
        assertEquals((Integer) 83, atm.getBalance());
    }

    @Test
    public void testImpossibleWithdrawl() {
        atm.deposit(70);
        atm.deposit(113);
        atm.withdrawl(167);
        assertEquals((Integer) 23, atm.getBalance());
    }
}