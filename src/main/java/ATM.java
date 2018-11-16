import java.util.LinkedList;

public class ATM {

    private Integer id;
    private Integer[] bills;
    private Integer[] stocks;
    private LinkedList<Container> containers = new LinkedList<>();
    public LinkedList<Bank> observers = new LinkedList<>();

    public ATM(Integer[] bills, Integer[] stocks) {
        for (int i = 0; i < bills.length; i++) {
            this.containers.add(new Container(bills[i], stocks[i]));
            this.containers.get(i).registerObserver(this);
        }
        /**
         * Set the next container foreach container.
         */
        for (int i = 0; i < this.containers.size() - 1; i++) {
            this.containers.get(i).setNext(this.containers.get(i + 1));
        }
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getBalance() {
        Integer balance = 0;
        for (Container c : containers) {
            balance += c.getBalance();
        }
        return balance;
    }

    public void withdrawl(Integer amount) {
        Integer currentBalance = getBalance();
        if (amount > currentBalance) {
            System.out.println("Can not withdraw " + amount.toString() +
                    " from ATM with balance " + currentBalance.toString());
        } else {
            this.containers.get(0).handle(amount);
        }
    }

    public ATM clone() {
        return new ATM(this.bills, this.stocks);
    }

    public void registerObserver(Bank b) {
        this.observers.add(b);
    }

    public void notifyObservers(Integer con) {
        for (Bank b : observers) {
            b.update(this, con);
        }
    }

    public void update(Container c) {
        notifyObservers(c.getCash());
    }
}