package sort;

/**
 * 排序
 * 默认实现为升序asc
 */
public class Sortors {

    public static void main(String[] args) {
        int[] arrays = {1, 4, 10, 5};
        //Sortors.straightInsertionSort(arrays);
        Sortors.binaryInsertionSort(arrays);
        for (int value : arrays) {
            System.out.print(value);
            System.out.print("\t");
        }
        System.out.println();
    }

    /**
     * 直接插入排序
     * 描述：直接插入排序也称为简单插入排序，先将序列的第1个记录看成是一个有序的子序列，
     * 然后从第2个记录逐个进行插入，直至整个序列有序为止。
     */
    public static void straightInsertionSort(int[] arrays) {
        // 外层循环从第二个开始，因为默认第一个已经排序好
        for (int i = 1; i < arrays.length; i++) {
            // 从第二个开始和前面的逐个比较交换
            for (int j = i; j > 0; j--) {
                // >:asc, <:desc
                if (arrays[j - 1] > arrays[j]) {
                    int tmp = arrays[j];
                    arrays[j] = arrays[j - 1];
                    arrays[j - 1] = tmp;
                } else {
                    // 不需要交换则直接退出循环，进行下一个记录比较
                    break;
                }
            }
        }
    }

    /**
     * 折半插入排序
     * 描述：折半插入排序是对直接插入排序的改进，由于前面已排序好，我们就不用按照顺序依次寻找插入点，
     * 可以采用折半查找的方法加快寻找插入点的速度。
     * 同时依然默认第一个已经排序好，当找到插入点时，将插入点和当前记录前一个节点数据后移，然后插入数据。
     */
    public static void binaryInsertionSort(int[] arrays) {
        // 外层循环从第二个开始，因为默认第一个已经排序好
        for (int i = 1; i < arrays.length; i++) {
            // 要插入的第i个记录
            int tmp = arrays[i];
            // 标记排序好的头部
            int begin = 0;
            // 标记排序好的尾部，第i个记录位插入的数据，所以标记好的尾部为i-1
            int end = i - 1;
            while (begin <= end) {
                int mid = (begin + end) / 2;
                if (tmp == arrays[mid]) {
                    continue;
                }
                // >:asc, <:desc
                if (tmp > arrays[mid]) {
                    // 在右边
                    begin = mid + 1;
                } else {
                    // 在左边
                    end = mid - 1;
                }
            }
            // 当找到位置的时候，此时begin的位置就是插入点位置，begin后的数往后移动
            for (int j = i; j > begin; j--) {
                arrays[j] = arrays[j - 1];
            }
            // 插入i
            arrays[begin] = tmp;
        }
    }
}
