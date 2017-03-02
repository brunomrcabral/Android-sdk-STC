package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelFiscais {

    public ModelFiscais() {}

    // Schema
    public static abstract class ModelFiscaisSchema implements BaseColumns {
        public static final String TABLE_NAME = "fiscais";

        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_STC = "stc";
        public static final String COLUMN_SGBR = "sgbr";
        public static final String COLUMN_CODIGO_PDA = "codigo_pda";
        public static final String COLUMN_SGVFV = "sgvfv";
        public static final String COLUMN_FIS_AGENTE_QUALIFICADO = "fis_agente_qualificado";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelFiscaisSchema.TABLE_NAME + " (" +
                    ModelFiscaisSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelFiscaisSchema.COLUMN_USERNAME + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelFiscaisSchema.COLUMN_NOME + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelFiscaisSchema.COLUMN_PASSWORD + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelFiscaisSchema.COLUMN_STC + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelFiscaisSchema.COLUMN_SGBR + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelFiscaisSchema.COLUMN_CODIGO_PDA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelFiscaisSchema.COLUMN_SGVFV + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelFiscaisSchema.COLUMN_FIS_AGENTE_QUALIFICADO + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelFiscaisSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelFiscaisSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
