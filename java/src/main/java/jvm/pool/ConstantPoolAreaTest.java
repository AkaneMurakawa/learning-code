package jvm.pool;

/**
 * 字符串常量池 存储区域测试
 */
public class ConstantPoolAreaTest {

    public static void main(String[] args) {
        String var0 = "varvarvarvarvarvarvarvarvarvarvarvarvarvarvarvarvar" +
                "varvarvarvarvarvarvarvarvarvarvarvarvarvarvarvarvarvarvarvar" +
                "varvarvarvar0";
        int i = 0;
        while (true) {
            // 存放在堆中
            var0 += i++;
        }
    }
}

