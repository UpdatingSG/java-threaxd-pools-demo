package com.example.mergeSortMultithreaded;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MergerSorter implements Callable<List<Integer>> {
    List<Integer> listToSort;
    ExecutorService executorService;

    public MergerSorter(List<Integer> listToSort, ExecutorService executorService) {
        this.listToSort = listToSort;
        this.executorService = executorService;
    }

    @Override
    public List<Integer> call() throws Exception {
        int n = listToSort.size();

        if (n <= 1) {
            return listToSort;
        }

        List<Integer> leftHalf = new ArrayList<>();
        List<Integer> rightHalf = new ArrayList<>();

        for (int i = 0; i < n / 2; i++) {
            leftHalf.add(listToSort.get(i));
        }

        for (int i = n / 2; i < n; i++) {
            rightHalf.add(listToSort.get(i));
        }

        MergerSorter leftMergerSorter = new MergerSorter(leftHalf, executorService);
        MergerSorter rightMergerSorter = new MergerSorter(rightHalf, executorService);

        Future<List<Integer>> leftFuture = executorService.submit(leftMergerSorter);
        Future<List<Integer>> rightFuture = executorService.submit(rightMergerSorter);

        List<Integer> leftSortedList = leftFuture.get();
        List<Integer> rightSortedList = rightFuture.get();

        int i = 0, j = 0;
        List<Integer> sortedList = new ArrayList<>();
        while (i < leftSortedList.size() && j < rightSortedList.size()) {
            if (leftSortedList.get(i) < rightSortedList.get(j)) {
                sortedList.add(leftSortedList.get(i));
                i++;
            } else {
                sortedList.add(rightSortedList.get(j));
                j++;
            }
        }

        while (i < leftSortedList.size()) {
            sortedList.add(leftSortedList.get(i));
            i++;
        }

        while (j < rightSortedList.size()) {
            sortedList.add(rightSortedList.get(j));
            j++;
        }

        return sortedList;
    }
}