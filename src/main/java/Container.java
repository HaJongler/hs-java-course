public class Container {

    private Container next;
    private Integer cash;

    public Container(Integer cash) {
        this.cash = cash;
    }

    public Integer handle(Integer money) {
        Integer quotient = 0;
        if (getNext() == null) {
            if (money < this.cash) {
                System.out.println("ERROR: The ATM can not out put remaining amount: " + money.toString());
                return 0;
            }
            else {
                quotient = money / this.cash;
                System.out.println(this.cash.toString() + " x " + quotient.toString());
                System.out.println("ERROR: The ATM can not out put remaining amount: " + (money - (quotient * this.cash)));
                return quotient * this.cash;
            }
        }
        if (money < this.cash) {
            return getNext().handle(money);
        }
        else {
            quotient = money / this.cash;
            System.out.println(this.cash.toString() + " x " + quotient.toString());
            return quotient * this.cash + getNext().handle(money - (quotient * this.cash));
        }
    }

    private Container getNext() {
        return this.next;
    }

    public void setNext(Container next) {
        this.next = next;
    }
}
