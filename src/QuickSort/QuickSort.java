package QuickSort;

import java.util.Random;

public class QuickSort {

    // randomize the pivot and put it the end of the array
    private static void randomPivot(int[] arr, int low, int high) {
        Random rand = new Random();
        int r = rand.nextInt(high - low) + low;

        //swap pivot with the last element
        int temp = arr[r];
        arr[r] = arr[high];
        arr[high] = temp;
    }

    // put element smaller than the pivot to the left, greater to the right
    private static int partition(int[] arr, int low, int high) {
        randomPivot(arr, low, high);
        int pivot = arr[high];
        int i = low - 1; // smallest element index

        // compare the elements with the pivot
        for (int j = low; j < high; j++) {
            // if the element is smaller than the pivot, put it in the front of the array
            if (arr[j] < pivot) {
                i++;
                // swap arr[j] with arr[i]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // swap the arr[high] with arr[i+1]
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        // return pivot index;
        return i + 1;
    }

    // quicksort implementation
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high); // pivot index
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }
}
