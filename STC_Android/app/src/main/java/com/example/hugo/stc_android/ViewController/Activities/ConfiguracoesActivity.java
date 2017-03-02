package com.example.hugo.stc_android.ViewController.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.hugo.stc_android.Model.Persistence.ModelConfiguracoes;
import com.example.hugo.stc_android.Model.Utils.DatabaseHelper;
import com.example.hugo.stc_android.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hugo
 */
public class ConfiguracoesActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction().replace(android.R.id.content, new ConfigurationsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class ConfigurationsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setPreferenceScreen(createPreferenceHierarchy());
        }

        public PreferenceScreen createPreferenceHierarchy() {
            PreferenceScreen root = getPreferenceManager().createPreferenceScreen(getActivity());

            DatabaseHelper mDbHelper = DatabaseHelper.getInstance(getActivity());

            //Lista os grupos possiveis de ModelConfiguracoes
            List<String> listaTiposModelConfiguracoes =
                    mDbHelper.getDistinctColumnRecordsFromTable(
                            ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME,
                            new String[]{ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_TIPO},
                            ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VISIVEL + "=?",
                            new String[]{"S"}, "DESC");

            for (String tipoConfiguracao : listaTiposModelConfiguracoes) {
                Cursor configuracoes =
                        mDbHelper.getOrderedRecordsByFilter(
                                ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME, null,
                                ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VISIVEL + "=? and "
                                        + ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_TIPO + "=?",
                                new String[]{"S", tipoConfiguracao},
                                ModelConfiguracoes.ModelConfiguracoesSchema._ID + " ASC");

                if (configuracoes.moveToFirst()) {
                    // Se tiver mais que "limite" valores, abre restantes opcoes em nova janela
                    int pos = 0;
                    int limite = 2;

                    PreferenceCategory categoria = new PreferenceCategory(getActivity());
                    categoria.setTitle(tipoConfiguracao);
                    root.addPreference(categoria);

                    PreferenceScreen opcaoSubNivel = getPreferenceManager().createPreferenceScreen(getActivity());
                    if (configuracoes.getCount() > limite) {
                        opcaoSubNivel.setTitle(R.string.more_options);
                        root.addPreference(opcaoSubNivel);
                    }

                    do {
                        String tipoComponente = configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_TIPO_VALOR));
                        switch (tipoComponente) {
                            case "0":
                                CheckBoxPreference checkBox = new CheckBoxPreference(getActivity());
                                checkBox.setKey(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema._ID)));
                                checkBox.setTitle(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_NOME)));
                                checkBox.setSummary(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_DESCRICAO)));
                                checkBox.setEnabled(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_EDITAVEL)).equals("S"));
                                checkBox.setDefaultValue(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR)).equals("1"));
                                checkBox.setOnPreferenceChangeListener(preferenceListener);
                                if (pos < limite)
                                    categoria.addPreference(checkBox);
                                else
                                    opcaoSubNivel.addPreference(checkBox);
                                break;
                            case "1":
                                EditTextPreference editorTexto = new EditTextPreference(getActivity());
                                editorTexto.setKey(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema._ID)));
                                editorTexto.setTitle(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_NOME)));
                                editorTexto.setEnabled(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_EDITAVEL)).equals("S"));
                                editorTexto.setDialogTitle(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_NOME)));
                                editorTexto.setDialogMessage(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_DESCRICAO)));
                                if(editorTexto.getText() == null) {
                                    editorTexto.setText(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR)));
                                }
                                editorTexto.setSummary(editorTexto.getText());
                                editorTexto.setOnPreferenceChangeListener(preferenceListener);
                                if (pos < limite)
                                    categoria.addPreference(editorTexto);
                                else
                                    opcaoSubNivel.addPreference(editorTexto);
                                break;
                            case "2":
                                ListPreference listaOpcoes = new ListPreference(getActivity());
                                listaOpcoes.setKey(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema._ID)));
                                listaOpcoes.setTitle(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_NOME)));
                                listaOpcoes.setEnabled(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_EDITAVEL)).equals("S"));
                                listaOpcoes.setDialogTitle(configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_NOME)));
                                setValoresLista(listaOpcoes, configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema._ID)),
                                        configuracoes.getString(configuracoes.getColumnIndex(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR)));
                                if (pos < limite)
                                    categoria.addPreference(listaOpcoes);
                                else
                                    opcaoSubNivel.addPreference(listaOpcoes);
                                break;
                            //Implementar novos elementos antes do default se necessario
                            default:
                                Log.e("Aviso", "Tipo de Configuracao nao encontrado");
                                break;
                        }
                        pos++;
                    } while (configuracoes.moveToNext());
                }
                configuracoes.close();
                //mDbHelper.close();
            }
            return root;
        }

        private void setValoresLista(ListPreference listaOpcoes, String id, String defaultValue) {
            switch (id) {
                // Se for 'Pais'.
                case "8":
                    listaOpcoes.setEntries(R.array.countrys_entrys);
                    listaOpcoes.setEntryValues(R.array.countrys_values);
                    if (listaOpcoes.getValue() == null) {
                        listaOpcoes.setValue(defaultValue);
                    }
                    break;

                // Se for 'Envia Dados'.
                case "18":
                    listaOpcoes.setEntries(R.array.photos_sending_entries);
                    listaOpcoes.setEntryValues(R.array.photos_sending_values);
                    if (listaOpcoes.getValue() == null) {
                        listaOpcoes.setValue(defaultValue);
                    }
                    break;

                // Se for 'Qualidade Foto'.
                case "36":
                    listaOpcoes.setEntries(R.array.photos_quality_entries);
                    listaOpcoes.setEntryValues(R.array.photos_quality_values);
                    if (listaOpcoes.getValue() == null) {
                        listaOpcoes.setValue(defaultValue);
                    }
                    break;

                // Se for 'Modelo da Impressora'.
                case "39":
                    listaOpcoes.setEntries(R.array.printer_entries);
                    listaOpcoes.setEntryValues(R.array.printer_values);
                    if (listaOpcoes.getValue() == null) {
                        listaOpcoes.setValue(defaultValue);
                    }
                    break;
            }
            listaOpcoes.setSummary(listaOpcoes.getEntry());
            listaOpcoes.setOnPreferenceChangeListener(preferenceListener);
        }

        private Preference.OnPreferenceChangeListener preferenceListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
            // Altera os valores visualizados
            if(preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                listPreference.setValue(newValue.toString());
                listPreference.setSummary(listPreference.getEntry());
            } else if(preference instanceof EditTextPreference) {
                preference.setSummary(newValue.toString());
            }

            // Guarda novo valor na BD
            DatabaseHelper mDbHelper = new DatabaseHelper(getActivity());

            Map<String,String> valueToUpdate = new HashMap<>();
            valueToUpdate.put(ModelConfiguracoes.ModelConfiguracoesSchema.COLUMN_VALOR, newValue.toString());

            mDbHelper.updateRecord(
                        ModelConfiguracoes.ModelConfiguracoesSchema.TABLE_NAME,
                        valueToUpdate,
                        ModelConfiguracoes.ModelConfiguracoesSchema._ID + "=?",
                        new String[]{preference.getKey()});

            return true;
            }
        };
    }
}
