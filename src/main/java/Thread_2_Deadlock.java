public class Thread_2_Deadlock extends Thread{
    DeadlockResourceB resourceB;
    @Override
    public void run() {
        System.out.println(resourceB.getI());
    }
}
