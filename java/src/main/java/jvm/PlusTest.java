package jvm;

/**
 * 测试++
 * 查看字节码
 * javap -v PlusTest.class > PlusTestByteCode.txt
 */
public class PlusTest {

    /**
     * byte var1 = 2;
     * byte var10000 = var1;
     * int var2 = var1 + 1;
     * var1 = var10000;
     * System.out.println(var1);
     *
     * @param args
     */
    public static void main(String[] args) {
        int i = 2;
        i = i++; // 2
//        i = ++i; // 3
        System.out.println(i);
    }
}
