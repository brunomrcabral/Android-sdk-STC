package com.example.hugo.stc_android.Model.AsyncOperations;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.example.hugo.stc_android.Model.Persistence.ModelConfiguracoes;
import com.example.hugo.stc_android.Model.Persistence.ModelControloSincronizacao;
import com.example.hugo.stc_android.Model.Utils.DatabaseHelper;
import com.example.hugo.stc_android.Model.Utils.ResponseDataObject;
import com.example.hugo.stc_android.Model.Utils.WebserviceSoapClient;
import com.example.hugo.stc_android.Model.Utils.WebserviceUtils;
import com.example.hugo.stc_android.R;
import com.example.hugo.stc_android.ViewController.Interfaces.DialogListeners;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Hugo
 */
public class SynchronizeInfo extends AsyncTask<Object, String, String> {

    private final Context mContext;

    private ProgressDialog progressDialog;

    public SynchronizeInfo(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        // Inicializa dialog de progresso da task
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(mContext.getResources().getString(R.string.title_sync_dialog));
        progressDialog.setMessage(mContext.getResources().getString(R.string.initial_message_sync_dialog));
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Object... params) {
        DatabaseHelper mDbHelper = DatabaseHelper.getInstance(mContext);

        String serviceUrl = mDbHelper.getSingleColumnValueFomTable(
                ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME,
                new String[]{ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR},
                ModelConfiguracoes.ModelConfiguracoesSchema._ID + "=?", new String[]{"0"});

        if(serviceUrl == null || serviceUrl.equals(""))
            return mContext.getResources().getString(R.string.error_empty_service_address_parameter);

        String operationTimeout = mDbHelper.getSingleColumnValueFomTable(
                ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME,
                new String[]{ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR},
                ModelConfiguracoes.ModelConfiguracoesSchema._ID + "=?", new String[]{"3"});

        if(operationTimeout == null || serviceUrl.equals(""))
            operationTimeout = "60000";

        String terminal = mDbHelper.getSingleColumnValueFomTable(
                ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME,
                new String[]{ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR},
                ModelConfiguracoes.ModelConfiguracoesSchema._ID + "=?", new String[]{"1"});

        if(terminal == null || terminal.equals(""))
            return mContext.getResources().getString(R.string.error_empty_pda_number_parameter);

        String accessCode = mDbHelper.getSingleColumnValueFomTable(
                ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME,
                new String[]{ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR},
                ModelConfiguracoes.ModelConfiguracoesSchema._ID + "=?", new String[]{"2"});

        if(accessCode == null || accessCode.equals(""))
            return mContext.getResources().getString(R.string.error_empty_pda_code_parameter);

        WebserviceSoapClient soapClient = new WebserviceSoapClient(serviceUrl, operationTimeout);

        // Numero de tentativas a executar cada acao com o webservice. Objetivo de minimizar perda de dados derivadas de falhas de ligacao temporarias
        int numberAttempts = 3;

        publishProgress(mContext.getResources().getString(R.string.message_validating_connection));

        // Valida estado da conexao da internet
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo == null) {
            return mContext.getString(R.string.error_internet_connection);
        }

        publishProgress(mContext.getResources().getString(R.string.message_validating_pda));

        // Valida terminal
        int validTerminal = soapClient.validaTerminal(terminal, accessCode, numberAttempts);
        if(validTerminal != 0) {
            switch(validTerminal) {
                case 1:
                    return mContext.getString(R.string.error_validate_pda);
                case 2:
                    return mContext.getString(R.string.error_connecting_webservice);
                default:
                    return mContext.getString(R.string.error_general_operation);
            }
        }

        // TODO: ENVIO DOS DADOS, INCLUINDO A MENSAGEM DE PROGRESSO E REGISTO EM FICHEIRO DE LOG

        publishProgress(mContext.getResources().getString(R.string.message_validating_sync_dates));

        // Valida tabela de controlo de datas de sincronização
        ResponseDataObject validData = soapClient.validaDadosTabelas(terminal, accessCode, numberAttempts, mDbHelper);
        if(validData.getStatusCode() != 0) {
            switch (validData.getStatusCode()) {
                case 1:
                    return mContext.getString(R.string.error_validate_control_table);
                case 2:
                    return mContext.getString(R.string.error_create_sql_table);
                case 3:
                    return mContext.getString(R.string.error_connecting_webservice);
                default:
                    return mContext.getString(R.string.error_general_operation);
            }
        }

        // Descarrega dados base que nao existam ou nao esteja atualizados
        if(!validData.getResponseData().isEmpty()) {
            publishProgress(mContext.getResources().getString(R.string.message_downloading_information));
            int downloadData = soapClient.recebeDados(terminal, accessCode, validData.getResponseData(), numberAttempts, mDbHelper);
            if(downloadData != 0) {
                switch (downloadData) {
                    case 1:
                        return mContext.getString(R.string.error_processing_download_info);
                    case 2:
                        return mContext.getString(R.string.error_delete_sql_table);
                    case 3:
                        return mContext.getString(R.string.error_create_sql_table);
                    case 4:
                        return mContext.getString(R.string.error_insert_in_sql_table);
                    case 5:
                        return mContext.getString(R.string.error_connecting_webservice);
                    default:
                        return mContext.getString(R.string.error_general_operation);
                }
            }

            // Atualiza tabela de controlo com as ultimas datas
            if(mDbHelper.checkTableExists(ModelControloSincronizacao.ModelControloSincronizacaoSchema.TABLE_NAME))
                if(!mDbHelper.executeSqlStatement(ModelControloSincronizacao.SQL_DELETE_QUERY))
                    return mContext.getString(R.string.error_delete_sql_table);

            if(!mDbHelper.executeSqlStatement(ModelControloSincronizacao.SQL_CREATE_QUERY))
                return mContext.getString(R.string.error_create_sql_table);

            int idRecord = 1;
            List<String> insertControlQueries = new ArrayList<>();
            for(LinkedHashMap<String, String> record : validData.getRecords()) {
                insertControlQueries.add(ModelControloSincronizacao.sqlInsertQuery(WebserviceUtils.getListWithId(record, idRecord)));
                idRecord++;
            }

            if(!mDbHelper.executeBatchInserts(insertControlQueries))
                return mContext.getString(R.string.error_insert_in_sql_table);

            // Retorna mensagem de sucesso de descarregamento
            return mContext.getResources().getString(R.string.message_download_successful);
        } else {
            // Dados estao atualizados e envia mensagem a informar disso
            return mContext.getResources().getString(R.string.message_information_updated);
        }
    }

    @Override
    protected void onProgressUpdate(String... msg) {
        progressDialog.setMessage(msg[0]);
    }

    @Override
    protected void onPostExecute(String response) {
        // Fecha modal de progresso de sincronizacao
        if(progressDialog.isShowing())
            progressDialog.dismiss();

        // Caso devolva mensagem de erro, mostra popup com a informacao
        DialogListeners listener = (DialogListeners) mContext;
        if(!response.equals(mContext.getResources().getString(R.string.message_download_successful))
                && !response.equals(mContext.getResources().getString(R.string.message_information_updated)))
            listener.showDialogMessage(mContext.getString(R.string.title_error_sync), response);
        else
            listener.showDialogMessage(mContext.getString(R.string.title_sync_dialog), response);
    }
}
