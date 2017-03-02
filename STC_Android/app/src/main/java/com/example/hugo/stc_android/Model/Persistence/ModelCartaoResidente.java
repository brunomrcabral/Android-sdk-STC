package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelCartaoResidente {

    public ModelCartaoResidente() {}

    // Schema
    public static abstract class ModelCartaoResidenteSchema implements BaseColumns {
        public static final String TABLE_NAME = "cartao_residente";

        public static final String COLUMN_MATRICULA = "matricula";
        public static final String COLUMN_ZONA = "zona";
        public static final String COLUMN_DATA_VALIDADE = "data_validade";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelCartaoResidenteSchema.TABLE_NAME + " (" +
                    ModelCartaoResidenteSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelCartaoResidenteSchema.COLUMN_MATRICULA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelCartaoResidenteSchema.COLUMN_ZONA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelCartaoResidenteSchema.COLUMN_DATA_VALIDADE + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelCartaoResidenteSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelCartaoResidenteSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
