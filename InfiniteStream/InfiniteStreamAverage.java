import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

/**
 *  InfiniteStreamAverage: A class to find the average of a infinite Stream of Numbers
 */
class InfiniteStreamAverage{
    private int size;
    private Queue<Integer> window;
    private double sum;

    /**
     *
     * @param size - Size of the Window
     * - Constructor to initialize the window and the sum
     */
    public InfiniteStreamAverage(int size) {
        this.size = size;
        this.window = new LinkedList<>();
        this.sum = 0;
    }

    /**
     *
     * @param element - New Element
     */
    double computeRunningAverage(int element){
        if(window.size() == size){
            sum-=window.poll();
        }

        window.add(element);
        sum += element;

        return sum/window.size();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner (System.in);

        System.out.println("Enter the numbers");
        String[] str = sc.nextLine().split(" ");

        InfiniteStreamAverage obj = new InfiniteStreamAverage(str.length);

        for(String s:str){
            System.out.println(obj.computeRunningAverage(Integer.parseInt(s)));
        }

    }
}