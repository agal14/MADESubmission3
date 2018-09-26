package com.dicoding.associate.madesubmission3;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.dicoding.associate.madesubmission3.adapter.KamusAdapter;
import com.dicoding.associate.madesubmission3.database.KamusHelper;
import com.dicoding.associate.madesubmission3.fragment.BlankFragment;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.search)
    EditText textSearch;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefsKamus" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        textSearch = findViewById(R.id.search);
        //Default pertama kata inggris yang dicari
        textSearch.setHint("Masukkan kata Inggris yang dicari");
        textSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Bundle bundle = new Bundle();
                bundle.putString("queryget", textSearch.getText().toString());
                BlankFragment fragobj = new BlankFragment();
                fragobj.setArguments(bundle);
                replaceFragment(fragobj);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String kamusChoose="";

        if (id == R.id.nav_engindo) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            kamusChoose = "engindo";
            editor.putString("kamus", kamusChoose);
            editor.commit();
            String tes = sharedpreferences.getString("kamus",null);
            Log.d("prefs",tes);
            textSearch.setText("");
            textSearch.setHint("Masukkan kata Inggris yang dicari");
        } else if (id == R.id.nav_indoeng) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            kamusChoose = "indoeng";
            editor.putString("kamus", kamusChoose);
            editor.commit();
            String tes = sharedpreferences.getString("kamus",null);
            Log.d("prefs",tes);
            textSearch.setText("");
            textSearch.setHint("Masukkan kata Indonesia yang dicari");
        } else if (id == R.id.nav_exit) {
            exitApps();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * for replace fragment
     *
     * @param fragment {@link Fragment}
     */
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_list, fragment)
                .commit();
    }


    public void exitApps() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.exit_dialog);
        alertDialogBuilder
                .setMessage(R.string.click_yes)
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                Process.killProcess(Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
