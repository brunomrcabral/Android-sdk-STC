package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelProprietario {

    public ModelProprietario() {}

    // Schema
    public static abstract class ModelProprietarioSchema implements BaseColumns {
        public static final String TABLE_NAME = "proprietario";

        public static final String COLUMN_CODIGO = "codigo";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_MORADA = "morada";
        public static final String COLUMN_TIPO_PROPRIETARIO = "tipo_proprietario";
        public static final String COLUMN_ENVIADO = "enviado";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelProprietarioSchema.TABLE_NAME + " (" +
                    ModelProprietarioSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelProprietarioSchema.COLUMN_CODIGO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelProprietarioSchema.COLUMN_NOME + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelProprietarioSchema.COLUMN_MORADA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelProprietarioSchema.COLUMN_TIPO_PROPRIETARIO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelProprietarioSchema.COLUMN_ENVIADO + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelProprietarioSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelProprietarioSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
