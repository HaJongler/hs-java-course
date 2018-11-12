public class MyThreadSubclass {

    static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println("1");
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run(){
            System.out.println("2");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread1();
        Thread t2 = new Thread2();

        t1.start();
        t2.start();
    }
}
