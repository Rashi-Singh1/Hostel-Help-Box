package com.example.hostelhelpbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AdminHome2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.admin_home2, menu);
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

        if (id == R.id.nav_account) {
            getSupportFragmentManager().beginTransaction().replace(R.id.screen_area, new FragmentProfile()).commit();
        } else if (id == R.id.nav_AddSecy) {
            getSupportFragmentManager().beginTransaction().replace(R.id.screen_area, new FragmentAddSecy()).commit();
        } else if (id == R.id.nav_ViewSecy) {
            getSupportFragmentManager().beginTransaction().replace(R.id.screen_area, new FragmentViewSecy()).commit();
        } else if (id == R.id.nav_DeleteSecy) {
            getSupportFragmentManager().beginTransaction().replace(R.id.screen_area, new FragmentDelSecy()).commit();

        } else if (id == R.id.nav_logout) {
            SharedPreferenceConfig sharedPreferenceConfig;
            sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());
            UserInfo.logout();
            sharedPreferenceConfig.writeLoginStatus(false);
            Intent intent;
            intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            this.finish();

        } else if (id == R.id.nav_share) {

        }  else if (id == R.id.nav_message) {
            getSupportFragmentManager().beginTransaction().replace(R.id.screen_area, new FragmentViewMessage()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
