import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        /*
        ДЗ 28 Задание 1-2 - Создать 2000 одновременных задач, которые увеличивают целочисленный счетчик на 1.
        Подтвердить проблему атомарности. Проверить ее решение с помощью volatile или Atomic классов.
        Выполнить ожидание завершения задач с помощью CountDownLatch.
         */
        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch doneSignal = new CountDownLatch(2000);
        final AtomicInteger containerCounter = new AtomicInteger();

        for (int i = 0; i < 2000; i++) {
            executor.execute(new CounterChange(containerCounter, doneSignal));
        }

        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        shutdownAndAwaitTermination(executor);
        System.out.println("Counter = " + containerCounter.get());

        /*ДЗ28 Задание 3 - Получить доступ к singleton-объекту с “ленивой” (lazy) инициализацией из множества
        потоков с использованием барьера инициализации при помощи класса CountDownLatch. Подтвердить проблему
        атомарности. Решить ее одним из известных способов.
        */
        singleton();

//        ДЗ28 Задание 4 - Воспроизвести проблему dead lock любым способом.
        deadlock();
    }

    void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!pool.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            pool.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }

    private void singleton() {
        ExecutorService executor = Executors.newCachedThreadPool();
//        CountDownLatch doneSignal = new CountDownLatch(2000);
    }

    private void deadlock() {
        Student lock1 = new Student();
        Student lock2 = new Student();

        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            synchronized (lock1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lock2) {
                }
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }, "Thread1");

        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " start");
            synchronized (lock2) {
                synchronized (lock1) {

                }
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }, "Thread2");

        thread1.start();
        thread2.start();

    }

}
