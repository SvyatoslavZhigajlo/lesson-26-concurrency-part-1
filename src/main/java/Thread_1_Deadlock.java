public class Thread_1_Deadlock extends Thread{

    DeadlockResourceA resourceA;
    @Override
    public void run() {
        System.out.println(resourceA.getI());
    }
}
