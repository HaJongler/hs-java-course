import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ATM {

    private List<Container> containers;
    private LogList<Integer> balanceLog = new LogList<>(0);

    public ATM(String[] containers) {
        this.containers = Arrays.stream(containers)
                .map(Integer::parseInt)
                .sorted((c1, c2) -> c2 - c1)
                .map(Container::new)
                .collect(Collectors.toList());
        /**
         * Set the next container foreach container.
         */
        for (int i = 0; i < this.containers.size() - 1; i++) {
            this.containers.get(i).setNext(this.containers.get(i + 1));
        }
    }

    public Integer getBalance() {
        Iterator<Integer> iterator = this.balanceLog.getIterator();
        Integer currentBalance = 0;
        while (iterator.hasNext()) {
            currentBalance = iterator.next();
        }
        return currentBalance;
    }

    public void deposit(Integer amount) {
        Integer currentBalance = getBalance();
        this.balanceLog.addLog(currentBalance + amount);
    }

    public void withdrawl(Integer amount) {
        Integer currentBalance = getBalance();
        if (amount > currentBalance) {
            System.out.println("Can not withdraw " + amount.toString() +
                    " from ATM with balance " + currentBalance.toString());
        } else {
            Integer actualWithdrawl = this.containers.get(0).handle(amount);
            this.balanceLog.addLog(currentBalance - actualWithdrawl);
        }
    }
}
