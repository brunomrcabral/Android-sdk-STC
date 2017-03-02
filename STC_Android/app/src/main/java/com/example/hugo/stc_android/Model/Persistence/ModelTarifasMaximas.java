package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelTarifasMaximas {

    public ModelTarifasMaximas() {}

    // Schema
    public static abstract class ModelTarifasMaximasSchema implements BaseColumns {
        public static final String TABLE_NAME = "tarifasmaximas";

        public static final String COLUMN_VALOR_TARIFA_MAX = "valor_tarifa_max";
        public static final String COLUMN_ZNA_COD = "zna_cod";
        public static final String COLUMN_DIGITO_INICIAL_REF_MB = "digito_inicial_ref_mb";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelTarifasMaximasSchema.TABLE_NAME + " (" +
                    ModelTarifasMaximasSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelTarifasMaximasSchema.COLUMN_VALOR_TARIFA_MAX + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTarifasMaximasSchema.COLUMN_ZNA_COD + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTarifasMaximasSchema.COLUMN_DIGITO_INICIAL_REF_MB + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelTarifasMaximasSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelTarifasMaximasSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
