package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelParametrosPDA {

    public ModelParametrosPDA() {}

    // Schema
    public static abstract class ModelParametrosPDASchema implements BaseColumns {
        public static final String TABLE_NAME = "parametrospda";

        public static final String COLUMN_PAR_COD = "par_cod";
        public static final String COLUMN_PAR_VALOR = "par_valor";
        public static final String COLUMN_PAR_DESIG = "par_desig";
        public static final String COLUMN_PAR_ALTERAVEL = "par_alteravel";
        public static final String COLUMN_PAR_VISIVEL = "par_visivel";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelParametrosPDASchema.TABLE_NAME + " (" +
                    ModelParametrosPDASchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelParametrosPDASchema.COLUMN_PAR_COD + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelParametrosPDASchema.COLUMN_PAR_VALOR + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelParametrosPDASchema.COLUMN_PAR_DESIG + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelParametrosPDASchema.COLUMN_PAR_ALTERAVEL + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelParametrosPDASchema.COLUMN_PAR_VISIVEL + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelParametrosPDASchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelParametrosPDASchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
