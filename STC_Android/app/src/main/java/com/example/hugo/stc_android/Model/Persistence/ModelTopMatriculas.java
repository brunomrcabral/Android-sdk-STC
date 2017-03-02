package com.example.hugo.stc_android.Model.Persistence;

import android.provider.BaseColumns;

import com.example.hugo.stc_android.Model.Utils.DBUtils;

import java.util.List;

/**
 *
 * @author Hugo
 */

public final class ModelTopMatriculas {

    public ModelTopMatriculas() {}

    // Schema
    public static abstract class ModelTopMatriculasSchema implements BaseColumns {
        public static final String TABLE_NAME = "topmatriculas";

        public static final String COLUMN_MATRICULA = "matricula";
        public static final String COLUMN_CODIGO_PAIS = "codigo_pais";
        public static final String COLUMN_QUANTIDADE = "quantidade";
    }

    // SQL
    public static final String SQL_CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + ModelTopMatriculasSchema.TABLE_NAME + " (" +
                    ModelTopMatriculasSchema._ID + DBUtils.TYPE_INTEGER + DBUtils.PRIMARY_KEY + DBUtils.COMMA_SEP +
                    ModelTopMatriculasSchema.COLUMN_MATRICULA + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTopMatriculasSchema.COLUMN_CODIGO_PAIS + DBUtils.TYPE_TEXT + DBUtils.COMMA_SEP +
                    ModelTopMatriculasSchema.COLUMN_QUANTIDADE + DBUtils.TYPE_TEXT +
            " )";

    public static final String SQL_DELETE_QUERY =
            "DROP TABLE IF EXISTS " + ModelTopMatriculasSchema.TABLE_NAME;

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

        return "INSERT INTO " + ModelTopMatriculasSchema.TABLE_NAME +
                " VALUES(" +
                Integer.parseInt(recordValues.get(0)) + DBUtils.COMMA_SEP +
                builder.toString() + ")";
    }

}
