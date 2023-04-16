package diff.diff.converters;

import diff.diff.DiffRow;

/**
 * 转换器
 * @param <T>
 */
public interface Converter<T> {

    T convert(DiffRow diffRow, Object obj);
}
