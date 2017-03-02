package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelRuas {

    public ModelRuas() {}

    // Schema
    public static abstract class ModelRuasSchema implements BaseColumns {
        public static final String TABLE_NAME = "ruas";

        public static final String COLUMN_SEQUENCIA = "sequencia";
        public static final String COLUMN_CODIGO_ZONA = "codigo_zona";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_CODIGO = "codigo";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelRuasSchema.TABLE_NAME + " (" +
                    ModelRuasSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelRuasSchema.COLUMN_SEQUENCIA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelRuasSchema.COLUMN_CODIGO_ZONA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelRuasSchema.COLUMN_DESCRICAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelRuasSchema.COLUMN_CODIGO + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelRuasSchema.TABLE_NAME;

    public static String sqlInsertQuery(List<String> recordValues) {
        StringBuilder builder = new StringBuilder();

        if(recordValues.size() > 2) {
            for (int i = 1; i < recordValues.size() - 1; i++) {
                builder.append(DBUtils.STRING_DELIMITER)
                        .append(recordValues.get(i).contains("'") ? recordValues.get(i).replaceAll("'", "''") : recordValues.get(i))
                        .append(DBUtils.STRING_DELIMITER).append(DBUtils.COMMA_SEP);
            }
        }
        builder.append(DBUtils.STRING_DELIMITER)
                .append(recordValues.get(recordValues.size() - 1).contains("'") ? recordValues.get(recordValues.size() - 1).replaceAll("'", "''") : recordValues.get(recordValues.size() - 1))
                .append(DBUtils.STRING_DELIMITER);

        return "INSERT INTO " + ModelRuasSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
