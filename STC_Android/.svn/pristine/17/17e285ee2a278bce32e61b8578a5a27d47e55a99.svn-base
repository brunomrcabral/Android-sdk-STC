package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class Auto {

    public Auto() {}

    // Schema
    public static abstract class AutoSchema implements BaseColumns {
        public static final String TABLE_NAME = "Auto";

        public static final String COLUMN_AUTO_NUMERO = "AUTO_NUMERO";
        public static final String COLUMN_CO_NUMERO = "CO_NUMERO";
        public static final String COLUMN_EMITIDO = "EMITIDO";
        public static final String COLUMN_ENTIDADE_SIBS = "ENTIDADE_SIBS";
        public static final String COLUMN_REFERENCIA_SIBS = "REFERENCIA_SIBS";
        public static final String COLUMN_MONTANTE_SIBS = "MONTANTE_SIBS";
        public static final String COLUMN_DATA_PAGAMENTO_MIN = "DATA_PAGAMENTO_MIN";
        public static final String COLUMN_DATA_PAGAMENTO_MAX = "DATA_PAGAMENTO_MAX";
        public static final String COLUMN_ESTADO = "ESTADO";
        public static final String COLUMN_ENVIADO = "ENVIADO";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + AutoSchema.TABLE_NAME + " (" +
                    AutoSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_AUTO_NUMERO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_CO_NUMERO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_EMITIDO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_ENTIDADE_SIBS + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_REFERENCIA_SIBS + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_MONTANTE_SIBS + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_DATA_PAGAMENTO_MIN + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_DATA_PAGAMENTO_MAX + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_ESTADO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    AutoSchema.COLUMN_ENVIADO + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + AutoSchema.TABLE_NAME;

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

        return "INSERT INTO " + AutoSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
