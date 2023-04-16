package diff.diff.converters;

import diff.diff.DiffRow;

public class ListConverter implements Converter<Object> {

    public Object convert(DiffRow diffRow, Object obj) {
        if (DiffRow.Tag.INSERT.is(diffRow.getTag())){
            return "添加了" + diffRow.getFieldName();
        }
        if (DiffRow.Tag.DELETE.is(diffRow.getTag())){
            return "删除了" + diffRow.getFieldName();
        }

        if (DiffRow.Tag.CHANGE.is(diffRow.getTag())){
            return "更新了" + diffRow.getFieldName();
        }

        return "";
    }
}
