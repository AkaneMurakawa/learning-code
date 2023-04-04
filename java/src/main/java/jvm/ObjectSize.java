package jvm;

import org.openjdk.jol.info.ClassLayout;

class MyObject {

    String str = "str";
}

/**
 * 对象内存布局、对象头内存布局、markword内存布局
 * 1. markword
 * 2. class pointer
 */
public class ObjectSize {

    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        System.out.println(ClassLayout.parseInstance(myObject).toPrintable());

        MyObject[] arr = new MyObject[1];
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());

    }
}
