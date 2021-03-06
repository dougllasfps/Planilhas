package io.github.dougllasfps.planilhas.datasource.manager;

import io.github.dougllasfps.planilhas.annotation.AnnotationConfig;
import io.github.dougllasfps.planilhas.datasource.SpreadSheetDataSourceManager;
import io.github.dougllasfps.planilhas.datasource.impl.AnnotationDataSource;
import io.github.dougllasfps.planilhas.model.Column;
import io.github.dougllasfps.planilhas.model.Row;
import io.github.dougllasfps.planilhas.reflection.ReflectionHelper;
import io.github.dougllasfps.planilhas.util.RowColumnOrganization;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Criado por dougllas.sousa em 16/01/2017.
 */
public class AnnotationDataSourceManager implements SpreadSheetDataSourceManager<AnnotationDataSource> {

    @Override
    public List<Row> populateRows(AnnotationDataSource dataSource) {
        Collection source = dataSource.getSource();

        List<Column> columns = populateColumns(dataSource);
        RowColumnOrganization.sortColumnsByIndex(columns);
        int fieldsInRow = RowColumnOrganization.getHigherIndex(columns) + 1;

        List<Row> list = new ArrayList<>();

        int index = 0;

        for (Object o : source) {

            Object[] tuple = new Object[fieldsInRow];

            for(Column column : columns) {
                String valueBinding = column.getValueBinding();

                Field field = ReflectionUtils.findField(o.getClass(), valueBinding);
                field.setAccessible(true);

                Object value = null;

                try {
                    value = field.get(o);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

                int columnIndex = column.getIndex();
                tuple[columnIndex] = value;
            }

            Row row = new Row();
            row.setIndex(index++);
            row.setData(tuple);
            list.add(row);
        }

        return list;
    }

    @Override
    public List<Column> populateColumns(AnnotationDataSource dataSource) {
        Class model = dataSource.getModel();

        List<AnnotationConfig> configs= ReflectionHelper.extractConfigurationfromClass(model);

        List<Column> list = new ArrayList<>();

        for(AnnotationConfig a : configs){
            Column c = new Column();
            c.setHeader(a.getHeader());
            c.setIndex(a.getIndex());
            c.setWidth(a.getWidth());
            c.setValueBinding(a.getFieldName());
            list.add(c);
        }

        return  list;
    }
}