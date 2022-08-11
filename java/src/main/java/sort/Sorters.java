package sort;

import java.util.Arrays;
import java.util.LinkedList;

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
        //Sorters.quickSort(arrays, 0, arrays.length - 1);
        //Sorters.mergeSort(arrays,0 ,  arrays.length - 1, new int[4]);
        //Sorters.countingSort(arrays, 10);
        //Sorters.radixSort(arrays, 10, 2);
        //Sorters.bucketSort(arrays, 10, 1);

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
     * @param parent 父节点下标
     * @param last 数组最大下标
     */
    private static void minHeapFixDown(int[] arrays, int parent, int last) {
        // 获得左子节点
        int child = 2 * parent + 1;
        // 保存当前父节点
        int tmp = arrays[parent];
        while (child < last) {
            boolean hasRight = child + 1 < last;
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
     * @param low 最低位下标
     * @param high 最高位下标
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

    /**
     * 二路归并排序
     * 将arrays[first...middle]和arrays[middle+1...end]合并
     * 例如：A[] 和 B[] 合并成C[]
     * @param first 开始下标
     * @param middle 中间下标
     * @param last 结束下标
     * @param tmp 临时存储合并数组
     */
    private static void mergeArray(int arrays[], int first, int middle, int last, int tmp[]) {
        // A[]开始下标
        int i = first;
        int m = middle;
        // B[]开始下标
        int j = middle + 1;
        int n = last;
        // C[]开始下标,这里为tmp[]
        int k = 0;
        while (i <= m && j <= n) {
            // 判断谁小，然后放入，k++，i/j++
            // >:asc, <:desc
            if (arrays[j] > arrays[i]) {
                tmp[k] = arrays[i];
                k++;
                i++;
            } else {
                tmp[k] = arrays[j];
                k++;
                j++;
            }
        }
        // 如果B[]已经分配完，而A[]没有分完，那剩下的就都是A直接放入到C[]中
        while (i <= m) {
            tmp[k] = arrays[i];
            k++;
            i++;
        }
        // 如果A[]已经分配完，而B[]没有分完，那剩下的就都是B直接放入到C[]中
        while (j <= n) {
            tmp[k] = arrays[j];
            k++;
            j++;
        }

        // 将tmp[]的值赋给arrays[]，这样arrays[]得到的就是排序好的
        for (i = 0; i < k; i++) {
            arrays[first + i] = tmp[i];
        }
    }

    /**
     * 二路归并排序
     * 描述：归并排序属于比较类非线性时间排序，号称比较类排序中性能最佳者，在数据中应用中较广。
     * 归并排序是分治法(Divide and Conquer)的一个典型的应用。将已有序的子序列合并，得到完全有序的序列。
     * 即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为二路归并。
     * @param first 开始下标
     * @param last 结束下标
     * @param tmp 临时存储合并数组
     */
    public static void mergeSort(int[] arrays, int first, int last, int tmp[]) {
        if (first < last) {
            int middle = (first + last) >> 1;
            // 左半部分排好序
            mergeSort(arrays, first, middle, tmp);
            // 右半部分排好序
            mergeSort(arrays, middle + 1, last, tmp);
            //合并左右部分
            mergeArray(arrays, first, middle, last, tmp);
        }
    }

    /**
     * 计数排序
     * 描述：在待排序序列中，对于给定的输入序列中的每一个元素x，确定该序列中值小于x的元素的个数
     * (此处并非比较各元素的大小，而是通过对元素值的计数和计数值的累加来确定)，这样就知道了该元素的正确位置。
     * 例如，对于待排序序列{10,5,3,1,9,3}，有5个数比10小，那么10就应该放在第6个位置。
     * @param max    待排序数组中最大的数
     */
    public static void countingSort(int[] arrays, int max) {
        // 保留结果
        int[] result = new int[arrays.length];
        // 初始化数组，默认元素为0
        int[] tmp = new int[max + 1];

        for (int i = 0; i < arrays.length; i++) {
            // 遍历待排序的数组，计算其中的每一个元素出现的次数，比如一个key为i的元素出现了3次，那么A[i]=3
            int value = arrays[i];
            //tmp[]初始化为0，之后1，2...
            tmp[value] += 1;
        }

        // 对所有计数累加
        for (int i = 1; i < tmp.length; i++) {
            tmp[i] += tmp[i - 1];
        }

        // 逆向遍历源数组（保证稳定性），根据计数数组中对应的值填充到先的数组中
        for (int i = arrays.length - 1; i >= 0; i--) {
            // 记录原数组的每一个数
            int value = arrays[i];
            // 由于下标从0开始，所以这里减1
            int position = tmp[value] - 1;
            // 放入适当的位置
            result[position] = value;
            // 放了一个就减一个
            tmp[value] -= 1;
        }

        // 将结果赋给arrays数组
        for (int i = 0; i < arrays.length; i++) {
            arrays[i] = result[i];
        }
    }

    /**
     * 基数排序
     * 描述：
     * 1. 首先根据个位数的数值，将它们分配至编号0到9的桶子中接下来将这些桶子中的数值按顺序重新串接起来
     * 2. 根据十位数的数值，将它们分配至编号0到9的桶子中接下来将这些桶子中的数值按顺序重新串接起来，以此类推...直到最高位结束。
     * 3. 根据最高位数的数值，将它们分配至编号0到9的桶子中，得到排序。
     * @param arrays   待排序数组
     * @param radix    基数 10 (任何一个阿拉伯数，它的各个位数上的基数都是以0~9来表示的。所以我们不妨把0~9视为10个桶)
     * @param distance 待排序中的数最大的位数
     */
    public static void radixSort(int[] arrays, int radix, int distance) {
        int length = arrays.length;
        // 临时数组，用来暂存元素
        int[] tmp = new int[length];
        // radix: 10 用于计数排序 盒子 0~9
        int[] count = new int[radix];
        // 1  10 100
        int divide = 1;

        for (int i = 0; i < distance; i++) {
            System.arraycopy(arrays, 0, tmp, 0, length);
            // 盒子清空
            Arrays.fill(count, 0);

            // 用来把每个数的 <个十百千万...> 分开，并且使相对应号数的桶的个数增加1
            for (int j = 0; j < length; j++) {
                // divide：1 10 100
                // radix： 基数 10
                // 取出每一个数的位。例如123：divide:1时，取出 3。divide:10时，取出2
                int tmpKey = (tmp[j] / divide) % radix;
                // 盒子中的<个 十 百...>的个数
                count[tmpKey]++;
            }

            // 对所有计数累加
            // radix：基数 10
            for (int j = 1; j < radix; j++) {
                count[j] += count[j - 1];
            }
            // 逆向遍历源数组（保证稳定性），根据计数数组中对应的值填充到先的数组中
            for (int j = length - 1; j >= 0; j--) {
                int tmpKey = (tmp[j] / divide) % radix;
                // 由于下标从0开始，所以这里减1
                count[tmpKey]--;
                arrays[count[tmpKey]] = tmp[j];
            }

            // (tmp[j]/divide) % radix
            // divide：1 10 100,通过distance控制
            divide = divide * radix;
        }
    }

    /**
     * 桶排序(BucketSort)
     * 描述：桶排序，顾名思义就是运用桶的思想来将数据放到相应的桶内，再将每一个桶内的数据进行排序，
     * 最后把所有桶内数据按照顺序取出来，得到的就是我们需要的有序数据。
     * @param arrays 待排序数组
     * @param max    最大的数
     * @param min    最小的数
     */
    public static void bucketSort(int[] arrays, int max, int min) {
        // 桶数，比如10个桶
        int bucketNum = (max - min) / arrays.length + 1;
        // 创建链表(桶)集合并初始化，集合中的链表用于存放相应的元素
        // 存桶，桶集合
        LinkedList<LinkedList<Integer>> buckets = new LinkedList<>();
        for (int i = 0; i < bucketNum; i++) {
            // 存元素，桶
            LinkedList<Integer> bucket = new LinkedList<>();
            buckets.add(bucket);
        }

        // 把元素放进相对应的桶中
        for (int i = 0; i < arrays.length; i++) {
            // 把元素放进相对应的桶中
            int index = (arrays[i] - min) / arrays.length;
            buckets.get(index).add(arrays[i]);
        }

        // 对每个桶中的元素排序，并放进arrays[]
        // 用于排序后从每个桶中依次取出，arrays的下标
        int index = 0;
        for (LinkedList<Integer> linkedList : buckets) {
            // 每个桶的长度
            int size = linkedList.size();
            if (size == 0) {
                continue;
            }
            int[] tmp = new int[size];
            for (int i = 0; i < tmp.length; i++) {
                // 将桶的数取出放进入tmp[]中
                tmp[i] = linkedList.get(i);
            }
            // QAQ 对桶里面的数进行排序，选择一种排序方式
            Arrays.sort(tmp);

            // 排序后从每个桶中依次取出
            for (int i = 0; i < tmp.length; i++) {
                arrays[index] = tmp[i];
                index++;
            }
        }
    }
}
