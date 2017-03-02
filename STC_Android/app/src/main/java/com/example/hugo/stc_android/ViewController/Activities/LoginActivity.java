package com.example.hugo.stc_android.ViewController.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.hugo.stc_android.Model.AsyncOperations.InsertConfiguracoesDefault;
import com.example.hugo.stc_android.Model.Persistence.ModelConfiguracoes;
import com.example.hugo.stc_android.Model.Persistence.ModelFiscais;
import com.example.hugo.stc_android.Model.Persistence.ModelParametrosPDA;
import com.example.hugo.stc_android.Model.Persistence.ModelTurnos;
import com.example.hugo.stc_android.Model.Utils.DatabaseHelper;
import com.example.hugo.stc_android.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hugo
 */
public class LoginActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_options_items); // load titles from strings.xml
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_options_icons);//load icons from strings.xml
        set(navMenuTitles,navMenuIcons);

        // Cria e popula tabela de configurações se não existir
        DatabaseHelper mDbHelper = DatabaseHelper.getInstance(this);
        if(!mDbHelper.checkTableExists(ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME)) {
            mDbHelper.executeSqlStatement(ModelConfiguracoes.SQL_CREATE_QUERY);
            InsertConfiguracoesDefault asyncInsertConfig = new InsertConfiguracoesDefault(this);
            asyncInsertConfig.execute(this);
        }
    }

    // Acao do botao de login
    public void submitLogin(View view) {
        final DatabaseHelper mDbHelper = DatabaseHelper.getInstance(this);

        // Verifica a existencia da tabela de fiscais. Se nao existir necessita de sincronizar a aplicacao
        if(!mDbHelper.checkTableExists(ModelFiscais.ModelFiscaisSchema.TABLE_NAME)) {
            showDialogMessage(this.getResources().getString(R.string.title_error_login),this.getResources().getString(R.string.error_login_sync_needed));
        } else {
            // Antes de verificar as credenciais introduzidas, verifica se a aplicacao esta atualizada
            String minAppVersion = mDbHelper.getSingleColumnValueFomTable(
                    ModelParametrosPDA.ModelParametrosPDASchema.TABLE_NAME,
                    new String[]{ModelParametrosPDA.ModelParametrosPDASchema.COLUMN_PAR_VALOR},
                    ModelParametrosPDA.ModelParametrosPDASchema.COLUMN_PAR_COD + "=?",
                    new String[]{"916"});
            String actualAppVersion = mDbHelper.getSingleColumnValueFomTable(
                    ModelParametrosPDA.ModelParametrosPDASchema.TABLE_NAME,
                    new String[]{ModelParametrosPDA.ModelParametrosPDASchema.COLUMN_PAR_VALOR},
                    ModelParametrosPDA.ModelParametrosPDASchema.COLUMN_PAR_COD + "=?",
                    new String[]{"917"});

            if(actualAppVersion == null || (!minAppVersion.equals("1") && !actualAppVersion.equals("3.0.0.0"))) {
                showDialogMessage(this.getResources().getString(R.string.title_error_login),this.getResources().getString(R.string.error_login_app_need_update));
            } else {
                // Verifica se credenciais estao preenchidas no ecra
                final String username = ((EditText)findViewById(R.id.username)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                if(username.equals("") || password.equals("")) {
                    showDialogMessage(this.getResources().getString(R.string.title_error_login),this.getResources().getString(R.string.error_login_no_credentials));
                } else {
                    // Verifica se fiscal com o username existe
                    final Cursor cursorUser = mDbHelper.getOrderedRecordsByFilter(
                            ModelFiscais.ModelFiscaisSchema.TABLE_NAME, null,
                            ModelFiscais.ModelFiscaisSchema.COLUMN_USERNAME + "=?",
                            new String[]{username}, null);

                    if(!cursorUser.moveToFirst()) {
                        cursorUser.close();
                        cleanPasswordInput();
                        showDialogMessage(this.getResources().getString(R.string.title_error_login),this.getResources().getString(R.string.error_login_user_not_exist));
                    } else {
                        String userPassword = cursorUser.getString(cursorUser.getColumnIndex(ModelFiscais.ModelFiscaisSchema.COLUMN_PASSWORD));
                        String userEquipment = cursorUser.getString(cursorUser.getColumnIndex(ModelFiscais.ModelFiscaisSchema.COLUMN_CODIGO_PDA));
                        final String userFullName = cursorUser.getString(cursorUser.getColumnIndex(ModelFiscais.ModelFiscaisSchema.COLUMN_NOME));
                        cursorUser.close();

                        // Verifica se a senha introduzida esta correta
                        if (!userPassword.equals(password)) {
                            cleanPasswordInput();
                            showDialogMessage(this.getResources().getString(R.string.title_error_login), this.getResources().getString(R.string.error_invalid_passwd));
                        } else {
                            // Verifica se o fiscal tem permissoes para atuar no dispositivo
                            String equipment = mDbHelper.getSingleColumnValueFomTable(
                                    ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME,
                                    new String[]{ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR},
                                    ModelConfiguracoes.ModelConfiguracoesSchema._ID + "=?",
                                    new String[]{"1"});

                            if (!userEquipment.equals(equipment) && !userEquipment.equals("0")) {
                                cleanPasswordInput();
                                showDialogMessage(this.getResources().getString(R.string.title_error_login), this.getResources().getString(R.string.error_login_no_permission_to_device));
                            } else {
                                // TODO: Falta verificar datas das fotos e remover as que passarem 30 dias de armazenamento

                                // Verifica se existe tabela de turnos criada
                                if (!mDbHelper.checkTableExists(ModelTurnos.ModelTurnosSchema.TABLE_NAME))
                                    mDbHelper.executeSqlStatement(ModelTurnos.SQL_CREATE_QUERY);

                                // Verifica se existe algum turno aberto para o fiscal
                                if (mDbHelper.getCountRecordsFromTableByFilter(
                                        ModelTurnos.ModelTurnosSchema.TABLE_NAME,
                                        ModelTurnos.ModelTurnosSchema.COLUMN_NUMERO_FISCAL + "=? AND " +
                                                ModelTurnos.ModelTurnosSchema.COLUMN_DATA_FECHO + "=?", new String[]{username, "N"}) > 0) {

                                    // Encaminha para a página do fiscal
                                    redirectToMenu(this);
                                } else {
                                    // Possibilita a criação de novo turno
                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    builder.setTitle(R.string.dialog_turnos_title)
                                            .setMessage(R.string.dialog_turnos_message)
                                            .setPositiveButton(R.string.dialog_turnos_positive, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    // Calcula data atual
                                                    Calendar nowCal = Calendar.getInstance();
                                                    String nowDate = nowCal.get(Calendar.DAY_OF_MONTH) + "-" + nowCal.get(Calendar.MONTH) + "-" + nowCal.get(Calendar.YEAR);
                                                    Log.i("NOW DATE: ", nowDate);
                                                    String nowTime = nowCal.get(Calendar.HOUR_OF_DAY) + ":" + nowCal.get(Calendar.MINUTE) + ":" + nowCal.get(Calendar.SECOND);
                                                    Log.i("NOW TIME: ", nowTime);

                                                    // Cria novo turno
                                                    Map<String, String> newShift = new HashMap<>();
                                                    newShift.put(ModelTurnos.ModelTurnosSchema.COLUMN_NUMERO_FISCAL, username);
                                                    newShift.put(ModelTurnos.ModelTurnosSchema.COLUMN_NOME_FISCAL, userFullName);
                                                    newShift.put(ModelTurnos.ModelTurnosSchema.COLUMN_DATA_INICIO, nowDate);
                                                    newShift.put(ModelTurnos.ModelTurnosSchema.COLUMN_HORA_INICIO, nowTime);
                                                    newShift.put(ModelTurnos.ModelTurnosSchema.COLUMN_DATA_FECHO, "N");
                                                    newShift.put(ModelTurnos.ModelTurnosSchema.COLUMN_HORA_FECHO, "N");
                                                    newShift.put(ModelTurnos.ModelTurnosSchema.COLUMN_ENVIADO, "N");
                                                    mDbHelper.insertRecord(ModelTurnos.ModelTurnosSchema.TABLE_NAME, newShift);

                                                    // Encaminha para a página do fiscal
                                                    redirectToMenu(LoginActivity.this);
                                                }
                                            })
                                            .setNegativeButton(R.string.dialog_turnos_negative, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    cleanPasswordInput();
                                                    dialog.dismiss();
                                                }
                                            });
                                    AlertDialog turnoDialog = builder.create();
                                    turnoDialog.show();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void cleanPasswordInput() {
        ((EditText) findViewById(R.id.password)).setText("");
    }

    private void redirectToMenu(Activity mActivity) {
        Intent configurations = new Intent(mActivity, AgentMenuActivity.class);
        startActivity(configurations);
        mActivity.finish();
    }
}
