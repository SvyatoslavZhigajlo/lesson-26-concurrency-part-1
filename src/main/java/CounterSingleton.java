public class CounterSingleton {

    private static CounterSingleton counterSingleton;


    private CounterSingleton(){

    }

    public static CounterSingleton getInstance(){
        if(counterSingleton == null){
            counterSingleton = new CounterSingleton();
        }
        return counterSingleton;
    }


}
