package thread.character9;

import org.openjdk.jol.info.ClassLayout;
import thread.character9.model.User;

/**
 * 对象头示例
 */
public class ObjectHeaderDemo {

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("Akane");

        // 测试大小端存储
        System.out.println("测试大小端存储:" + Integer.toBinaryString(2));

        // 打印hashcode 的 二进制
        System.out.println("hashcode二进制: " + Integer.toBinaryString(user.hashCode()));
        System.out.println("hashcode十六进制: " + Integer.toHexString(user.hashCode()));
        System.out.println(ClassLayout.parseInstance(user).toPrintable());

//        对象头源码详解：https://blog.csdn.net/baidu_28523317/article/details/104453927

//        hashcode二进制: 1000 10000000 01111110 00100101
//        hashcode十六进制: 8 80 7e 25
//        thread.character9.model.User object internals:
//        OFFSET  SIZE               TYPE DESCRIPTION                               VALUE
//        0     4                    (object header)                           01 25 7e 80 (00000001 00100101 01111110 10000000) (-2139216639)
//        4     4                    (object header)                           08 00 00 00 (00001000 00000000 00000000 00000000) (8)
//        8     4                    (object header)                           92 c3 00 f8 (10010010 11000011 00000000 11111000) (-134167662)
//        12     4   java.lang.String User.username                             (object)
//        Instance size: 16 bytes
//        Space losses: 0 bytes internal + 0 bytes external = 0 bytes total


        /**
         * 分析
         * 1. 对象头大小： (4+4+4) * 8bit = 96 bit
         * 说明Java8及以后默认开启指针压缩，可通过-XX:+UseCompressedOops设置
         *
         * 2. 前面64bit是MarkWord，后32位是类的元数据指针(开启指针压缩)
         *
         * 3. 无锁时，存放的是Hashcode
         *
         *
         */
    }
}
