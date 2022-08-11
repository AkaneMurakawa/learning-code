package sort;

/**
 * 排序
 * 默认实现为升序asc
 */
public class Sorters {

    public static void main(String[] args) {
        int[] arrays = {1, 4, 10, 5};
        //Sorters.straightInsertionSort(arrays);
        //Sorters.binaryInsertionSort(arrays);
        //Sorters.shellSort(arrays);
        //Sorters.selectionSort(arrays);
        //Sorters.heapSort(arrays);
        //Sorters.bubbleSort(arrays);
        Sorters.quickSort(arrays, 0, arrays.length - 1);
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

    /**
     * 希尔排序
     * 描述：希尔排序又称缩小增量排序, 由D. L. Shell在1959年提出，是对直接插入排序的改进。
     * 相邻指定距离(称为增量)的元素进行比较，并不断把增量缩小至1，完成排序。将需要排序的序列划分为若干个较小的序列，
     * 对这些序列进行直接插入排序，通过这样若干次操作可使需要排序的数据基本有序，最后再使用一次直接插入排序。
     */
    public static void shellSort(int[] arrays) {
        int gap = arrays.length;
        // 增量如果为1就结束
        while (gap > 1) {
            // 增量逐次减半
            for (gap = arrays.length / 2; gap > 0; gap /= 2) {
                // i表示从gap开始到length的数, gap gap+1 ... length-1
                for (int i = gap; i < arrays.length; i++) {
                    // j = i - gap：表示gap开始前面的数, 0 1 ... gap-1
                    for (int j = i - gap; j >= 0; j -= gap) {
                        // gap前面的数 和 gap后面的数 比较大小，j为gap前面的数
                        // >:asc, <:desc
                        if (arrays[j] > arrays[j + gap]) {
                            int tmp = arrays[j];
                            arrays[j] = arrays[j + gap];
                            arrays[j + gap] = tmp;
                        } else {
                            // 不需要交换则直接退出循环，进行下一个记录比较
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 直接选择排序
     * 描述：直接选择排序也叫简单选择排序。从记录中找到最小的一个数和第一个位置交换，
     * 然后在剩下的记录中找到最小的和第二个位置交换，循环直到剩下最后一个数。
     */
    public static void selectionSort(int[] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            // index为每次找到的最小/大的值的下标
            int index = i;
            // 找出每一次数据中最小/大值
            for (int j = i + 1; j < arrays.length; j++) {
                // >:asc, <:desc
                if (arrays[index] > arrays[j]) {
                    index = j;
                }
            }
            // 如果index和当前的第i个不同则交换，这样第i个位置就是最小/大的
            if (index != i) {
                int tmp = arrays[i];
                arrays[i] = arrays[index];
                arrays[index] = tmp;
            }
        }
    }

    /**
     * 堆排序
     * 调整大顶堆，从i节点开始调整，n为总节点数。
     * i的子节点为：2*i+1 和 2*i+2
     * <p>
     * 一个父节点的子节点数的取值范围为[0,2]，子节点数为零的父节点常被称为叶子节点。
     * 每一个父节点又可以看成是其子树分支的根节点。
     * 2*i、2*i+1、2*i+2
     * 0 1 2
     */
    private static void minHeapFixDown(int[] arrays, int parent, int length) {
        // 获得左子节点
        int child = 2 * parent + 1;
        // 保存当前父节点
        int tmp = arrays[parent];
        while (child < length) {
            boolean hasRight = child + 1 < length;
            // 如果有右子节点，child+1为右子节点，并且右子节点大与左子节点,则选取右子节点
            // >:asc, <:desc
            if (hasRight && arrays[child + 1] > arrays[child]) {
                child++;
            }
            // 如果父节点的值 > 子节点的值，结束
            // >=:asc, <=:desc
            if (tmp >= arrays[child]) {
                break;
            }
            // 把子节点的值赋给父节点
            arrays[parent] = arrays[child];
            // 选取左节点，继续向下筛选
            parent = child;
            child = 2 * child + 1;
        }
        arrays[parent] = tmp;
    }

    /**
     * 堆排序
     * 循环建立初始堆
     */
    private static void makeMinHeap(int[] arrays) {
        for (int parent = arrays.length / 2; parent >= 0; parent--) {
            minHeapFixDown(arrays, parent, arrays.length - 1);
        }
    }

    /**
     * 堆排序
     * 描述：堆是一棵完全二叉树，所谓完全二叉树即叶节点只能出现在最下层和次下层，
     * 并且最下面一层的结点都集中在该层最左边的若干位置的二叉树。
     * <p>
     * 堆排序是一种树形选择排序，威洛母斯(J. Willioms)在1964年对直接选择排序的有效改进。
     * 堆排序是在排序过程中，将向量中存储的数据看成一颗完全二叉树，
     * 使用完成二叉树中双亲结点和孩子节点之间的内在关系来选择关键字最小的记录，
     * 即待排序记录仍采用向量数字方式存储，并非采用树的存储结构。第一个记录作为二叉树的根，每个节点表示一个记录。
     */
    public static void heapSort(int[] arrays) {
        makeMinHeap(arrays);
        // 进行length-1次循环，完成排序
        for (int i = arrays.length - 1; i > 0; i--) {
            // 最后一个元素和第一个元素交换
            int tmp = arrays[0];
            arrays[0] = arrays[i];
            arrays[i] = tmp;
            // 筛选arrays[0]节点，得到i-1个节点的堆
            minHeapFixDown(arrays, 0, i);
        }
    }

    /**
     * 冒泡排序
     * 描述：从数组中第一个数开始，依次遍历数组中的每一个数，通过相邻比较交换，
     * 每一轮循环下来找出剩余未排序数的中的最小/大数并”冒泡”至数列的顶端。
     */
    public static void bubbleSort(int[] arrays) {
        // 用于标识后续是否需要交换，因为每次排序后最右边都是有序的，true:无序需要交换，false:有序不需要
        boolean disorder;
        // 外层控制循环次数，只需要比较length-1次，因为最后一个无需再比较
        for (int i = 1; i < arrays.length; i++) {
            disorder = false;
            // 内层循环开始两两之间比较，每次结束后最右边找出最大/小值
            for (int j = 0; j < arrays.length - i; j++) {
                // >:asc, <:desc
                if (arrays[j] > arrays[j + 1]) {
                    int tmp = arrays[j];
                    arrays[j] = arrays[j + 1];
                    arrays[j + 1] = tmp;
                    disorder = true;
                }
            }
            // 如果没有交换就直接退出了，说明没有可以换了
            if (!disorder) {
                break;
            }
        }
    }

    /**
     * 快速排序
     * 描述：先从数列中取出一个数作为key值(通常选择第一个元素或者最后一个元素)，
     * 将比这个数小的数全部放在它的左边，大于或等于它的数全部放在它的右边，对左右两个小数列重复第二步，直至各区间只有1个数。
     */
    public static void quickSort(int[] arrays, int low, int high) {
        // 如果最低位的下标>=最高位的下标
        if (low >= high) {
            return;
        }
        int left = low;
        int right = high;
        // 选取第一个key进行左右分类，或者你可以选最后一个
        int key = arrays[left];
        while (left < right) {
            // >=:asc, <=:desc
            while (left < right && arrays[right] >= key) {
                // 从右边向左，找第一个值小/大于key的。左边<——右边
                right--;
            }
            // 找到arrays[left]后和arrays[right]交换
            arrays[left] = arrays[right];

            // <=:asc, >=:desc
            while (left < right && arrays[left] <= key) {
                // 从左边向右，找第一个大/小于key的值。左边——>右边
                left++;
            }
            // 找到arrays[right]后和arrays[left]交换
            arrays[right] = arrays[left];
        }
        // 当在当组内找完一边以后就把中间数key回归，就是说此时中间的值为key
        arrays[left] = key;
        // 对key左边的元素进行递归排序
        quickSort(arrays, low, left - 1);
        // 对key右边的元素进行递归排序
        quickSort(arrays, left + 1, high);
    }


}
