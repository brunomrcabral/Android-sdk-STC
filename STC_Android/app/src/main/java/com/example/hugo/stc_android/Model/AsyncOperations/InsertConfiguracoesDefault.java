package com.example.hugo.stc_android.Model.AsyncOperations;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.hugo.stc_android.Model.Persistence.ModelConfiguracoes;
import com.example.hugo.stc_android.Model.Utils.DatabaseHelper;
import com.example.hugo.stc_android.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hugo
 */
public class InsertConfiguracoesDefault extends AsyncTask<Object, Boolean, Void> {

    private final Context mContext;

    public InsertConfiguracoesDefault(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Object... params) {

        DatabaseHelper mDbHelper = DatabaseHelper.getInstance(mContext);

        try {
            // Inicializa o stream do ficheiro JSON de configurações
            InputStream in = mContext.getResources().openRawResource(R.raw.configuracoes);

            // Inicializa reader de JSON para interpretar o ficheiro
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            reader.beginArray();

            while (reader.hasNext()) {
                Map<String, String> configuracao = new HashMap<>();
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals(ModelConfiguracoes.ModelConfiguracoesSchema._ID.replace("_", ""))) {
                        configuracao.put(ModelConfiguracoes.ModelConfiguracoesSchema._ID, reader.nextString());
                    } else if (name.equals(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_NOME)) {
                        configuracao.put(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_NOME, reader.nextString());
                    } else if (name.equals(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_DESCRICAO)) {
                        configuracao.put(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_DESCRICAO, reader.nextString());
                    } else if (name.equals(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR)) {
                        configuracao.put(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR, reader.nextString());
                    } else if (name.equals(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_TIPO)) {
                        configuracao.put(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_TIPO, reader.nextString());
                    } else if (name.equals(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_EDITAVEL)) {
                        configuracao.put(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_EDITAVEL, reader.nextString());
                    } else if (name.equals(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VISIVEL)) {
                        configuracao.put(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VISIVEL, reader.nextString());
                    } else if (name.equals(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_TIPO_VALOR)) {
                        configuracao.put(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_TIPO_VALOR, reader.nextString());
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();

                // Insere os dados na base de dados
                mDbHelper.insertRecord(ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME, configuracao);
            }
            reader.close();
        } catch (Exception e) {
            Log.e("ERRO", "Ocorreu um erro ao popular as configuracoes iniciais: " + e.getMessage());
        }
        return null;
    }
}
