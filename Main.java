import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args)
    {

        

        long multiThreadTime = multiThreading(4,1000000);
        System.out.println("multithread time (ms) - " + multiThreadTime);

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
