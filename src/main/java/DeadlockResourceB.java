public class DeadlockResourceB {
    DeadlockResourceA resourceA;

    protected synchronized int getI(){
        return  resourceA.returnI();
    }

    public synchronized int returnI(){
        return 2;
    }
}
