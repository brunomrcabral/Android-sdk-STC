package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelInfractor {

    public ModelInfractor() {}

    // Schema
    public static abstract class ModelInfractorSchema implements BaseColumns {
        public static final String TABLE_NAME = "infractor";

        public static final String COLUMN_CODIGO = "codigo";
        public static final String COLUMN_PROPRIETARIO = "proprietario";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_MORADA = "morada";
        public static final String COLUMN_DATA_NASCIMENTO = "data_nascimento";
        public static final String COLUMN_NUMERO_CONTRIBUINTE = "numero_contribuinte";
        public static final String COLUMN_TIPO_DOCUMENTO_LICENCA = "tipo_documento_licenca";
        public static final String COLUMN_NUMERO_LICENCA = "numero_licenca";
        public static final String COLUMN_DATA_LICENCA = "data_licenca";
        public static final String COLUMN_ENTIDADE_LICENCA = "entidade_licenca";
        public static final String COLUMN_TIPO_IDENTIFICACAO = "tipo_identificacao";
        public static final String COLUMN_NUMERO_IDENTIFICACAO = "numero_identificacao";
        public static final String COLUMN_DATA_IDENTIFICACAO = "data_identificacao";
        public static final String COLUMN_ENTIDADE_IDENTIFICACAO = "entidade_identificacao";
        public static final String COLUMN_ENVIADO = "enviado";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelInfractorSchema.TABLE_NAME + " (" +
                    ModelInfractorSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_CODIGO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_PROPRIETARIO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_NOME + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_MORADA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_DATA_NASCIMENTO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_NUMERO_CONTRIBUINTE + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_TIPO_DOCUMENTO_LICENCA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_NUMERO_LICENCA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_DATA_LICENCA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_ENTIDADE_LICENCA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_TIPO_IDENTIFICACAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_NUMERO_IDENTIFICACAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_DATA_IDENTIFICACAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_ENTIDADE_IDENTIFICACAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelInfractorSchema.COLUMN_ENVIADO + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelInfractorSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelInfractorSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
