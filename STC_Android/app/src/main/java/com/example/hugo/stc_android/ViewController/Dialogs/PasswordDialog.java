package com.example.hugo.stc_android.ViewController.Dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.hugo.stc_android.Model.Persistence.ModelConfiguracoes;
import com.example.hugo.stc_android.Model.Utils.DatabaseHelper;
import com.example.hugo.stc_android.R;
import com.example.hugo.stc_android.ViewController.Activities.ConfiguracoesActivity;
import com.example.hugo.stc_android.ViewController.Interfaces.DialogListeners;

/**
 * Created by Hugo
 */
public class PasswordDialog extends DialogFragment {
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Layout customizado
        LayoutInflater inflater = getActivity().getLayoutInflater();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_passwd_title)
               .setMessage(R.string.dialog_passwd_msg)
               .setView(inflater.inflate(R.layout.dialog_password, null))
               .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Verifica password e abre página de configurações se estiver correta
                       EditText mPassword = (EditText) getDialog().findViewById(R.id.passwd_dialog);
                       if (mPassword.getText() != null && !mPassword.getText().toString().equals("")) {
                           boolean passwordCerta = false;
                           DatabaseHelper mDbHelper = new DatabaseHelper(getActivity());
                           Cursor password =
                                   mDbHelper.getOrderedRecordsByFilter(ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME, null,
                                           ModelConfiguracoes.ModelConfiguracoesSchema._ID + "=?",
                                           new String[]{"22"},
                                           null);
                           if (password.moveToFirst()) {
                               if (password.getString(password.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR)).equals(mPassword.getText().toString()))
                                   passwordCerta = true;
                           }
                           password.close();

                           if (passwordCerta) {
                               Intent configurations = new Intent(getActivity(), ConfiguracoesActivity.class);
                               startActivity(configurations);
                               getActivity().finish();
                           } else {
                               // Erro de password errada
                               DialogListeners listener = (DialogListeners) getActivity();
                               listener.showDialogMessage(getActivity().getString(R.string.dialog_passwd_title), getActivity().getString(R.string.error_invalid_passwd));
                               dialog.dismiss();
                           }
                       } else {
                           // Erro de valor introduzido vazio
                           DialogListeners listener = (DialogListeners) getActivity();
                           listener.showDialogMessage(getActivity().getString(R.string.dialog_passwd_title), getActivity().getString(R.string.error_empty_passwd));
                           dialog.dismiss();
                       }
                   }
               })
               .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // Fecha a dialog sem efetuar qualquer acao
                       dialog.dismiss();
                   }
               });
        return builder.create();
    }
}