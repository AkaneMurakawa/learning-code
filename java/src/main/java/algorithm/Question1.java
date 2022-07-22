package algorithm;

/**
 * Given an array of ints = [6, 4, -3, 5, -2, -1, 0, 1, -9], implement a function
 * to move all positive numbers to the left, and move all negative numbers to the right.
 * Try your best to make its time complexity to O(n), and space complexity to O(1).
 */
public class Question1 {

    public static void main(String[] args) {
        int[] ints = {6, 4, -3, 5, -2, -1, 0, 1, -9};
        move(ints, 0, ints.length - 1);
        sout(ints);
    }

    /**
     * @param ints 数组
     * @param low  最低位
     * @param high 最高位
     */
    public static void move(int[] ints, int low, int high) {
        if (low >= high) return;
        int left = low;
        int right = high;
        int key = ints[left];
        while (left < right) {
            while (left < right && ints[right] <= key) {
                right--;// 左<——右
            }
            ints[left] = ints[right];

            while (left < right && ints[left] >= key) {
                left++;//左——>右
            }
            ints[right] = ints[left];
        }
        ints[left] = key;
        if (0 == key) return;
        move(ints, low, left - 1);
        move(ints, left + 1, high);
    }

    public static void sout(int[] ints) {
        for (int i : ints) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
