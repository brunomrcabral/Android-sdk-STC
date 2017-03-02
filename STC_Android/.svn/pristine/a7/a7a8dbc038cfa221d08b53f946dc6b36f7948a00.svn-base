package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelContraOrdenacoes {

    public ModelContraOrdenacoes() {}

    // Schema
    public static abstract class ModelContraOrdenacoesSchema implements BaseColumns {
        public static final String TABLE_NAME = "contra_ordenacoes";

        public static final String COLUMN_FISCAL = "fiscal";
        public static final String COLUMN_TURNO = "turno";
        public static final String COLUMN_DATA = "data";
        public static final String COLUMN_ZONA = "zona";
        public static final String COLUMN_CODIGO_RUA = "codigo_rua";
        public static final String COLUMN_NOME_RUA = "nome_rua";
        public static final String COLUMN_NUMERO_POLICIA = "numero_policia";
        public static final String COLUMN_INFRACCAO = "infraccao";
        public static final String COLUMN_PAIS = "pais";
        public static final String COLUMN_MATRICULA = "matricula";
        public static final String COLUMN_CATEGORIA = "categoria";
        public static final String COLUMN_TIPO_VEICULO = "tipo_veiculo";
        public static final String COLUMN_MARCA = "marca";
        public static final String COLUMN_COR = "cor";
        public static final String COLUMN_TIPO = "tipo";
        public static final String COLUMN_ENVIADO = "enviado";
        public static final String COLUMN_ONLINE = "online";
        public static final String COLUMN_CAMPO_ID = "campo_id";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelContraOrdenacoesSchema.TABLE_NAME + " (" +
                    ModelContraOrdenacoesSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_FISCAL + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_TURNO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_DATA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_ZONA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_CODIGO_RUA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_NOME_RUA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_NUMERO_POLICIA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_INFRACCAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_PAIS + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_MATRICULA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_CATEGORIA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_TIPO_VEICULO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_MARCA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_COR + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_TIPO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_ENVIADO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_ONLINE + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelContraOrdenacoesSchema.COLUMN_CAMPO_ID + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelContraOrdenacoesSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelContraOrdenacoesSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
