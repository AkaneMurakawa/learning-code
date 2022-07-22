package jvm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义类加载器
 */
public class CustomerClassLoaderTest extends ClassLoader {

    public CustomerClassLoaderTest() {
    }

    /**
     * 自定义父类
     *
     * @param parent
     */
    public CustomerClassLoaderTest(ClassLoader parent) {
        super(parent);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File f = new File("D:\\learnig-code\\java\\src\\main\\java\\jvm\\", name.replace(".", "/").concat(".class"));
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(f);
            baos = new ByteArrayOutputStream();
            int b = 0;

            while ((b = fis.read()) != 0) {
                baos.write(b);
            }
            byte[] bytes = baos.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.findClass(name);
    }

//    @Override
//    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
//        return super.loadClass(name, resolve);
//    }

    public static void main(String[] args) throws Exception {
        CustomerClassLoaderTest classLoader = new CustomerClassLoaderTest();
        Class<?> loadClass = classLoader.loadClass("jvm.ClassLoaderObject");
        // @Deprecated
        ClassLoaderObject instance = (ClassLoaderObject) loadClass.newInstance();
        instance.print();

        System.out.println(classLoader.getClass().getClassLoader());
        System.out.println(classLoader.getParent());
    }
}
