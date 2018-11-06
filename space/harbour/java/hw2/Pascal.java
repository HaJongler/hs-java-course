package space.harbour.java.hw2;


public class Pascal {

    public static int[] getNextPascal(int[] previous) {
        int[] next = new int[(previous.length + 1)];
        next[0] = 1;
        next[previous.length] = 1;
        for (int i = 0; i < previous.length - 1; i++) {
            next[i + 1] = previous[i] + previous[i + 1];
        }
        return next;
    }

    public static void main(String[] args) {
        int level = Integer.parseInt(args[0]);
        int[] current = {1};
        while (current.length <= level) {
            for (int i = 0; i < current.length; i++) {
                System.out.print(current[i] + " ");
            }
            if (current.length < level)
                System.out.print("\n");
            current = getNextPascal(current);
        }
    }
}