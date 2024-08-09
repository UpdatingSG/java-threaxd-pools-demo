package com.example.mergeSortMultithreaded;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import com.example.mergeSortMultithreaded.MergerSorter; // Added this import

public class Main {
    public static void main(String[] args) {
        List<Integer> listToShort = Arrays.asList(3, 2, 1, 4, 6, 8, 7, 5);
        
        ExecutorService executorService = Executors.newCachedThreadPool();
        MergerSorter mergerSorter = new MergerSorter(listToShort, executorService);
        Future<List<Integer>> sortedListFuture = executorService.submit(mergerSorter);
        try {
            List<Integer> sortedList = sortedListFuture.get();
            System.out.println(sortedList);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}