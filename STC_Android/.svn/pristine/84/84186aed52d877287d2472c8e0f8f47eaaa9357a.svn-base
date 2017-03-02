package com.example.hugo.stc_android.ViewController.Activities;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hugo.stc_android.R;
import com.example.hugo.stc_android.ViewController.NavigationOptions.NavDrawerItem;
import com.example.hugo.stc_android.ViewController.NavigationOptions.NavDrawerListAdapter;

import java.util.ArrayList;

/**
 * Created by Hugo on 28-07-2015.
 */
public class AgentMenuActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_agent_menu);

        String[] navMenuTitles = getResources().getStringArray(R.array.nav_options_items); // load titles from strings.xml
        TypedArray navMenuIcons = getResources().obtainTypedArray(R.array.nav_options_icons);//load icons from strings.xml
        set(navMenuTitles,navMenuIcons);

        String[] menuOptionsTitles = getResources().getStringArray(R.array.agent_menu_options_items); // load titles from strings.xml
        TypedArray menuOptionsIcons = getResources().obtainTypedArray(R.array.agent_menu_options_icons);//load icons from strings.xml
        setListMenu(menuOptionsTitles, menuOptionsIcons);
    }

    private void setListMenu(String[] navMenuTitles, TypedArray navMenuIcons) {
        ListView menuList = (ListView) findViewById(R.id.menuAgentOptions);

        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<>();
        if (navMenuIcons == null) {
            for (String navMenuTitle : navMenuTitles) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitle));
            }
        } else {
            for (int i = 0; i < navMenuTitles.length; i++) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
            }
        }

        menuList.setOnItemClickListener(new OptionMenuClickListener());

        NavDrawerListAdapter adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        menuList.setAdapter(adapter);
    }

    private class OptionMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    Intent newAuto = new Intent(getBaseContext(), NewAutoActivity.class);
                    startActivity(newAuto);
                    finish();
                    break;
                case 1:
                    Log.i("SELECAO:","OPCAO 1");
                    break;
                case 2:
                    Log.i("SELECAO:","OPCAO 2");
                    break;
                default:
                    break;
            }
        }
    }
}
