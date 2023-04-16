package diff;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;
import java.util.Arrays;
import java.util.List;

public class DiffTest {

    public static void main(String[] args) {
        DiffObject a = new DiffObject("a", "1");
        DiffObject b = new DiffObject("b", "1");
        b.setAge(1);

        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .inlineDiffByWord(true)
                .oldTag(f -> "~")
                .newTag(f -> "**")
                .build();
        List<DiffRow> rows = generator.generateDiffRows(
                Arrays.asList(a.toString()),
                Arrays.asList(b.toString()));

        System.out.println("|original|new|");
        System.out.println("|--------|---|");
        for (DiffRow row : rows) {
            System.out.println("|" + row.getOldLine() + "|" + row.getNewLine() + "|");
            System.out.println(row.getTag());
        }
    }
}
