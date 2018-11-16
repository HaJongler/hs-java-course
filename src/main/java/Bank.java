import java.util.List;

public class Bank {

    private Integer id;
    private List<ATM> atms;

    public ATM createAnotherATM(Integer index) {
        return atms.get(index).clone();
    }

    public void update(ATM atm, Integer con) {
        System.out.println("(Bank " + id + ") ATM: " + atm.getId() + " ran out of " + con + " bills!");
    }
}
