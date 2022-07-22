package exception;

/**
 * 异常 示例
 */
public class ExceptionTest {

    public static void main(String[] args) {
        int i = tryAndFinallyReturnTest();
        // 0
        System.out.println("i: " + i);

        // Exception in thread "main" java.lang.RuntimeException
        // catchAndFinallyThrowExceptionTest();

        try {
            catchOriginExceptionTest();
        } catch (Exception e) {
            //    java.lang.RuntimeException
            //    at exception.ExceptionDemo.catchOriginExceptionTest(ExceptionDemo.java:33)
            //    at exception.ExceptionDemo.main(ExceptionDemo.java:15)
            //    Suppressed: java.lang.ArithmeticException: / by zero
            //    at exception.ExceptionDemo.catchOriginExceptionTest(ExceptionDemo.java:26)
            //... 1 more
            System.out.println("getCause: " + e.getCause());
            System.out.println("suppressed: " + e.getSuppressed());
            e.printStackTrace();
        }
    }

    /**
     * Suppressed 示例
     *
     * @return
     */
    private static int catchOriginExceptionTest() {
        Exception origin = null;
        try {
            int i = 10 / 0;
            return 1;
        } catch (ArithmeticException e) {
            System.out.println("catch: ");
            origin = e;
            throw e;
        } finally {
            RuntimeException e = new RuntimeException();
            System.out.println("finally: ");
            if (origin != null) {
                e.addSuppressed(origin);
            }
            throw e;
        }
    }

    /**
     * catch和finally中都抛异常示例
     * <p>
     * 说明，最终会返回的是finally中的异常，因为只能抛出一个异常。
     * 没有被抛出的异常称为“被屏蔽”的异常（Suppressed Exception）
     *
     * @return
     */
    private static int catchAndFinallyThrowExceptionTest() {
        try {
            int i = 10 / 0;
            return 1;
        } catch (ArithmeticException e) {
            System.out.println("catch: ");
            throw new ArithmeticException();
        } finally {
            System.out.println("finally: ");
            throw new RuntimeException();
        }
    }

    /**
     * try和finally 中都有 return 示例
     * <p>
     * 说明，最终会返回的是finally中的return，说明finally会被try或catch里面的异常覆盖掉
     *
     * @return
     */
    private static int tryAndFinallyReturnTest() {
        try {
            int i = 10 / 0;
            return 1;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 2;
        } finally {
            return 0;
        }
    }
}
