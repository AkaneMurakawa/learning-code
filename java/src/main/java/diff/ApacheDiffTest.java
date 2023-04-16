package diff;

import diff.diff.DiffUtils;

public class ApacheDiffTest {

    public static void main(String[] args) {
        DiffObject a = new DiffObject("a", "1");
        DiffObject b = new DiffObject("b", "1");
        b.setAge(1);
        b.setBio("");

        String diff = DiffUtils.diff(a, b);
        System.out.println(diff);

        diff = DiffUtils.diff(a, null);
        System.out.println(diff);

        diff = DiffUtils.diff(null, b);
        System.out.println(diff);

    }
}
