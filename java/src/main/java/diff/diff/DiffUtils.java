package diff.diff;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * 对比工具
 */
public class DiffUtils {

    private static final String DEFAULT_SEPARATOR = ",";

    /**
     * 对比
     * @param source
     * @param target
     * @return
     */
    public static String diff(Object source, Object target){
        return diff(source, target, DiffUtils::defaultFormat, DEFAULT_SEPARATOR);
    }

    /**
     * 对比
     * @param source
     * @param target
     * @param format
     * @param separator
     * @return
     */
    public static String diff(Object source, Object target,
                              Function<Diff, String> format,
                              String separator){
        if (Objects.isNull(source) && Objects.isNull(target)) {
            throw new IllegalArgumentException("source target");
        }

        StringBuilder sb = new StringBuilder();
        // add
        if (Objects.isNull(source)){
            List<DiffRow> diffRows = getDiffRows(source, target);
            for (int i = 0; i < diffRows.size(); i++) {
                DiffRow diffRow = diffRows.get(i);
                sb.append(String.format("[%s：%s]", diffRow.getFieldName(), DiffUtils.format(diffRow.getRhs())));
                if (i != diffRows.size() - 1){
                    sb.append(separator);
                }
            }
            return sb.toString();
        }
        // delete
        if (Objects.isNull(target)){
            List<DiffRow> diffRows = getDiffRows(source, target);
            for (int i = 0; i < diffRows.size(); i++) {
                DiffRow diffRow = diffRows.get(i);
                sb.append(String.format("[%s：%s]", diffRow.getFieldName(), DiffUtils.format(diffRow.getLhs())));
                if (i != diffRows.size() - 1){
                    sb.append(separator);
                }
            }
            return sb.toString();
        }

        DiffResult diff = DiffUtils.diffBuilder(source, target);
        List<Diff<?>> diffs = diff.getDiffs();
        for (int i = 0; i < diffs.size(); i++) {
            sb.append(format.apply(diffs.get(i)));
            if (i != diffs.size() - 1){
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    private static String defaultFormat(Diff<?> d){
        return String.format("[%s：%s -> %s]",
                d.getFieldName(),
                DiffUtils.format(d.getLeft()),
                DiffUtils.format(d.getRight())
        );
    }

    private static Object format(Object obj){
        if (Objects.isNull(obj) || StringUtils.isBlank(obj.toString())) {
            return "空";
        }
        return obj;
    }

    @SneakyThrows
    private static DiffResult diffBuilder(Object source, Object target) {
        DiffBuilder diffBuilder = new DiffBuilder(source, target, ToStringStyle.SHORT_PREFIX_STYLE);
        List<DiffRow> diffRows = getDiffRows(source, target);
        if (CollectionUtils.isEmpty(diffRows)){
            return diffBuilder.build();
        }
        diffRows.forEach(v -> diffBuilder.append(v.getFieldName(), v.getLhs(), v.getRhs()));
        return diffBuilder.build();
    }

    @SneakyThrows
    private static List<DiffRow> getDiffRows(Object source, Object target){
        Class<?> clazz = Objects.nonNull(source) ? source.getClass() : target.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<DiffRow> diffRows = new ArrayList<>(fields.length);
        for (Field field : fields) {
            DiffNode diffNode = field.getAnnotation(DiffNode.class);
            if (null == diffNode){
                continue;
            }
            int order = diffNode.order();
            field.setAccessible(true);
            diffRows.add(new DiffRow(diffNode.value(),
                    Objects.nonNull(source) ? field.get(source) : null,
                    Objects.nonNull(target) ? field.get(target) : null,
                    order));
        }
        // sort
        diffRows.sort(Comparator.comparing(DiffRow::getOrder));
        return diffRows;
    }
}
