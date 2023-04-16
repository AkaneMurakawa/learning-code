package diff.diff.example;

import diff.diff.DiffUtils;
import java.util.ArrayList;
import java.util.List;

public class ApacheDiffTest {

    public static void main(String[] args) {
        DiffObject a = DiffObject.builder()
                .s("a")
                .b(true)
                .i(23)
                .build();

        DiffObject b = DiffObject.builder()
                .s("b")
                .b(true)
                .i(23)
                .d(3.14)
                .build();

        List<String> l = new ArrayList<>();
        l.add("http://bing.com");
        DiffObject c = DiffObject.builder()
                .s("b")
                .b(true)
                .i(23)
                .d(3.14)
                .l(l)
                .build();

        // 更新
        String diff;
        diff = DiffUtils.diff(a, b);
        System.out.println(diff);

        diff = DiffUtils.diff(a, c);
        System.out.println(diff);

        diff = DiffUtils.diff(c, DiffObject.builder().build());
        System.out.println(diff);

        // 添加
        diff = DiffUtils.diff(null, b);
        System.out.println(diff);

        // 删除
        diff = DiffUtils.diff(a, null);
        System.out.println(diff);

        // 删除
        diff = DiffUtils.diff(b, null);
        System.out.println(diff);
    }
}
