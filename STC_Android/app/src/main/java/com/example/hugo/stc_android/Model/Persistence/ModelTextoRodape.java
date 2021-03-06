package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelTextoRodape {

    public ModelTextoRodape() {}

    // Schema
    public static abstract class ModelTextoRodapeSchema implements BaseColumns {
        public static final String TABLE_NAME = "textorodape";

        public static final String COLUMN_CODIGO = "codigo";
        public static final String COLUMN_TEXTO = "texto";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelTextoRodapeSchema.TABLE_NAME + " (" +
                    ModelTextoRodapeSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelTextoRodapeSchema.COLUMN_CODIGO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTextoRodapeSchema.COLUMN_TEXTO + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelTextoRodapeSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelTextoRodapeSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
