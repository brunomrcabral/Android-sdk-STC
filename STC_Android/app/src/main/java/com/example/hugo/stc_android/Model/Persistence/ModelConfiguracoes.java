package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelConfiguracoes {

    public ModelConfiguracoes() {}

    // Schema
    public static abstract class ModelConfiguracoesSchema implements BaseColumns {
        public static final String TABLE_NAME = "configuracoes";

        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_VALOR = "valor";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_EDITAVEL = "editavel";
        public static final String COLUMN_VISIVEL = "visivel";
        public static final String COLUMN_TIPO_VALOR = "tipo_valor";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelConfiguracoesSchema.TABLE_NAME + " (" +
                    ModelConfiguracoesSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelConfiguracoesSchema.COLUMN_NOME + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelConfiguracoesSchema.COLUMN_DESCRICAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelConfiguracoesSchema.COLUMN_VALOR + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelConfiguracoesSchema.COLUMN_TIPO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelConfiguracoesSchema.COLUMN_EDITAVEL + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelConfiguracoesSchema.COLUMN_VISIVEL + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelConfiguracoesSchema.COLUMN_TIPO_VALOR + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelConfiguracoesSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelConfiguracoesSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
