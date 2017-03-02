package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelPDAs {

    public ModelPDAs() {}

    // Schema
    public static abstract class ModelPDAsSchema implements BaseColumns {
        public static final String TABLE_NAME = "pdas";

        public static final String COLUMN_CODIGO = "codigo";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_STC = "stc";
        public static final String COLUMN_SGBR = "sgbr";
        public static final String COLUMN_CONTACTO = "contacto";
        public static final String COLUMN_SGVFV = "sgvfv";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelPDAsSchema.TABLE_NAME + " (" +
                    ModelPDAsSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelPDAsSchema.COLUMN_CODIGO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelPDAsSchema.COLUMN_DESCRICAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelPDAsSchema.COLUMN_STC + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelPDAsSchema.COLUMN_SGBR + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelPDAsSchema.COLUMN_CONTACTO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelPDAsSchema.COLUMN_SGVFV + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelPDAsSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelPDAsSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
