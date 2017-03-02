package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelTurnos {

    public ModelTurnos() {}

    // Schema
    public static abstract class ModelTurnosSchema implements BaseColumns {
        public static final String TABLE_NAME = "turnos";

        public static final String COLUMN_NUMERO_FISCAL = "numero_fiscal";
        public static final String COLUMN_NOME_FISCAL = "nome_fiscal";
        public static final String COLUMN_DATA_INICIO = "data_inicio";
        public static final String COLUMN_HORA_INICIO = "hora_inicio";
        public static final String COLUMN_DATA_FECHO = "data_fecho";
        public static final String COLUMN_HORA_FECHO = "hora_fecho";
        public static final String COLUMN_ENVIADO = "enviado";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelTurnosSchema.TABLE_NAME + " (" +
                    ModelTurnosSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.AUTO_INCREMENT + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelTurnosSchema.COLUMN_NUMERO_FISCAL + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTurnosSchema.COLUMN_NOME_FISCAL + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTurnosSchema.COLUMN_DATA_INICIO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTurnosSchema.COLUMN_HORA_INICIO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTurnosSchema.COLUMN_DATA_FECHO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTurnosSchema.COLUMN_HORA_FECHO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTurnosSchema.COLUMN_ENVIADO + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelTurnosSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelTurnosSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
