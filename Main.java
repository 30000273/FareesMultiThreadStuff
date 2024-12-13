import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args)
    {

        /* prediction
        The singlethreaded should be quicker than the multithreaded because the overhead
        associated iwth making the multithreaded along with having multiple threads
        uses more CPU bandwidth than just using a single thread for 1-1billion, thus taking more time.
        There are a lot of context switches in the multithreaded solution, and for having that
        many threads, it will take a long time.

        // result
        My CPU is fast (Ryzen 5 5600X -- overclocked to 4.75GHZ boost) so it can handle a
        large amount of threads to do the calculations. Singlethread is faster to count
        until between 256-512 threads -- below this, using multithreading is faster.
        So yes the overhead with having a LOT of threads (1K+) just for something like this
        is unnecessary and isnt as efficient as having a single thread. But below a certain value
        using multithreading is beneficial to the time needed for the calculation.
        And another thing is, when you have a larger number (3 more zeros to 1 billion),
        multithreading even with 1024 threads is faster than single threading. So for
        more intensive workloads the overhead of having multiple threads is better than
        putting all the workload on a single thread.

         */
        long multiThreadTime = multiThreading(1024,1000000);
        System.out.println("multithread time (1024 threads) - " + multiThreadTime);

        multiThreadTime = multiThreading(512,1000000);
        System.out.println("multithread time (512 threads) - " + multiThreadTime);

        multiThreadTime = multiThreading(256,1000000);
        System.out.println("multithread time (256 threads) - " + multiThreadTime);

        multiThreadTime = multiThreading(128,1000000);
        System.out.println("multithread time (128 threads) - " + multiThreadTime);

        multiThreadTime = multiThreading(64,1000000);
        System.out.println("multithread time (64 threads) - " + multiThreadTime);

        multiThreadTime = multiThreading(32,1000000);
        System.out.println("multithread time (32 threads) - " + multiThreadTime);


        //single threading
        long startTime = System.nanoTime();
        long total = 0;
        for(int i = 0; i < 1000; i++){
            for(int j = 0; j < 1000000; j++)
            {
                total++;;
            }
        }
        long duration = (System.nanoTime() - startTime)/1000000;
        System.out.println("singlethread time(ms) - " + duration);

        multiThreadTime = multiThreading(1024,1000000000);
        System.out.println("multithread time to One Trillion (1024 threads) - " + multiThreadTime);

         startTime = System.nanoTime();
         total = 0;
        for(int i = 0; i < 1000; i++){
            for(int j = 0; j < 1000000000; j++)
            {
                total++;;
            }
        }
        duration = (System.nanoTime() - startTime)/1000000;
        System.out.println("singlethread time for One Trillion - " + duration);

    }

    public static long multiThreading(int numThreads, int countNum)
    {
        Callable<Integer> intSum = () ->{
            int x = 0;
            for(int i = 0; i < countNum; ++i)
            {
                x++;
            }
            return x;
        };

        try {
            ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
            List<Callable<Integer>> adding = new ArrayList<>();

            for(int i = 0; i<1000; i++)
            {
                adding.add(intSum);
            }
            long total = 0;
            long startTime = System.nanoTime();
            List<Future<Integer>> futures = executorService.invokeAll(adding);

            for(int i = 0; i < futures.size(); i++){
                total += futures.get(i).get();
            }
            long totalTime = System.nanoTime() - startTime;
            executorService.shutdown();
            return totalTime/1000000;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
