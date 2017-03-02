package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelTipoInfraccoes {

    public ModelTipoInfraccoes() {}

    // Schema
    public static abstract class ModelTipoInfraccoesSchema implements BaseColumns {
        public static final String TABLE_NAME = "tipinfraccoes";

        public static final String COLUMN_CODIGO_PDA = "codigo_pda";
        public static final String COLUMN_DESCRICAO = "descricao";
        public static final String COLUMN_LEGISLACAO = "legislacao";
        public static final String COLUMN_VALOR_MINIMO_COIMA = "valor_minimo_coima";
        public static final String COLUMN_VALOR_MAXIMO_COIMA = "valor_maximo_coima";
        public static final String COLUMN_MOEDA = "moeda";
        public static final String COLUMN_DESCRICAO_LONGA = "descricao_longa";
        public static final String COLUMN_NUMERO_INFORMATICO = "numero_informatico";
        public static final String COLUMN_LEGISLACAO_COIMA = "legislacao_coima";
        public static final String COLUMN_GRAVIDADE = "gravidade";
        public static final String COLUMN_CODIGO_ENTIDADE = "codigo_entidade";
        public static final String COLUMN_TIN_PERMITE_TALOES_TMAX = "tin_permite_taloes_tmax";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelTipoInfraccoesSchema.TABLE_NAME + " (" +
                    ModelTipoInfraccoesSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_CODIGO_PDA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_DESCRICAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_LEGISLACAO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_VALOR_MINIMO_COIMA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_VALOR_MAXIMO_COIMA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_MOEDA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_DESCRICAO_LONGA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_NUMERO_INFORMATICO + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_LEGISLACAO_COIMA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_GRAVIDADE + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_CODIGO_ENTIDADE + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTipoInfraccoesSchema.COLUMN_TIN_PERMITE_TALOES_TMAX + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelTipoInfraccoesSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelTipoInfraccoesSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
