import java.util.LinkedList;

public class Container {

    private Container next;
    private Integer cash;
    private Integer stock;
    private LinkedList<ATM> observers = new LinkedList<>();

    public Container(Integer cash, Integer stock) {
        this.cash = cash;
        this.stock = stock;
    }

    public Integer handle(Integer money) {
        Integer quotient;

        if (getNext() == null) {

            if (money < this.cash) {
                System.out.println("ERROR: The ATM can not out put remaining amount: " + money.toString());
                return 0;
            }
            else {
                quotient = money / this.cash;
                if (quotient >= stock) {
                    System.out.println(this.cash.toString() + " x " + stock.toString());
                    System.out.println("WARNING: The ATM ran out of " + this.cash + "'s");
                    notifyObservers();
                    Integer returned = stock * this.cash;
                    stock = 0;
                    if (money - returned > 0) System.out.println("ERROR: The ATM can not out put remaining amount: " + (money - returned));
                    return returned;
                }
                System.out.println(this.cash.toString() + " x " + quotient.toString());
                stock -= quotient;
                return quotient * this.cash;
            }

        }

        if (money < this.cash) {
            return getNext().handle(money);
        }

        else {
            quotient = money / this.cash;
            if (quotient >= stock) {
                System.out.println(this.cash.toString() + " x " + stock.toString());
                System.out.println("WARNING: The ATM ran out of " + this.cash + "'s");
                notifyObservers();
                Integer available = stock;
                stock = 0;
                return available * this.cash + getNext().handle(money - (available * this.cash));
            }
            System.out.println(this.cash.toString() + " x " + quotient.toString());
            stock -= quotient;
            return quotient * this.cash + getNext().handle(money - (quotient * this.cash));
        }
    }

    public Integer getBalance() { return this.stock * this.cash; }

    private Container getNext() {
        return this.next;
    }

    public void setNext(Container next) {
        this.next = next;
    }

    public Integer getCash() {
        return cash;
    }

    public void registerObserver(ATM atm) {
        this.observers.add(atm);
    }

    public void notifyObservers() {
        for (ATM atm : this.observers) {
            atm.update(this);
        }
    }
}
