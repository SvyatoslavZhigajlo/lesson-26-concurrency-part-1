public class DeadlockResourceA {
    DeadlockResourceB resourceB;

    public synchronized int getI(){
        resourceB.returnI();
        Thread.yield();
        return  resourceB.getI();
    }

    public synchronized int returnI(){
        return 1;
    }
}


