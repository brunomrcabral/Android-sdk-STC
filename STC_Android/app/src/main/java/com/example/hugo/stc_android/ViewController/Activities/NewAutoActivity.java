package com.example.hugo.stc_android.ViewController.Activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hugo.stc_android.Model.Persistence.ModelMarcas;
import com.example.hugo.stc_android.Model.Persistence.ModelZonas;
import com.example.hugo.stc_android.Model.Utils.DatabaseHelper;
import com.example.hugo.stc_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 19-08-2015.
 */
public class NewAutoActivity extends FragmentActivity {
    Button mCancel;

    Spinner mMarcas;

    @Override
    public void onBackPressed(){    // botão default do menu passa a voltar atras
        Intent agentActiv = new Intent(getBaseContext(), AgentMenuActivity.class);
        startActivity(agentActiv);
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_auto);

        mMarcas = (Spinner) findViewById(R.id.autoVBrand);

        loadSpinners();
    }

    private void loadSpinners() {
        DatabaseHelper mDatabase = DatabaseHelper.getInstance(this);

        Cursor cMarcas = mDatabase.getOrderedRecordsByFilter(
                ModelMarcas.ModelMarcasSchema.TABLE_NAME,
                new String[]{ModelMarcas.ModelMarcasSchema.COLUMN_DESCRICAO}, null, null, null);

        List<String> marcas = new ArrayList<>();
        if (cMarcas.moveToFirst()) {
            do {
                marcas.add(cMarcas.getString(0));
            } while(cMarcas.moveToNext());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, marcas);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mMarcas.setAdapter(dataAdapter);
    }
}
