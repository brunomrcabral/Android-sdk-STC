package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelMenu {

    public ModelMenu() {}

    // Schema
    public static abstract class ModelMenuSchema implements BaseColumns {
        public static final String TABLE_NAME = "menu_total";

        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_IMAGEM = "imagem";
        public static final String COLUMN_VIEW = "view";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelMenuSchema.TABLE_NAME + " (" +
                    ModelMenuSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelMenuSchema.COLUMN_NOME + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelMenuSchema.COLUMN_TIPO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelMenuSchema.COLUMN_IMAGEM + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelMenuSchema.COLUMN_VIEW + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelMenuSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelMenuSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
