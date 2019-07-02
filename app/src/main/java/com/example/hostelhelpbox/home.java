package com.example.hostelhelpbox;

        import android.support.annotation.NonNull;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.widget.Toast;

public class home extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawLay;
    private ActionBarDrawerToggle mToggle;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        Toast.makeText(this,"Welcome " + UserInfo.fullname,Toast.LENGTH_SHORT).show();

        mDrawLay = findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawLay, R.string.open, R.string.close);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawLay.addDrawerListener(mToggle);
        mToggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentProfile()).commit();
            navigationView.setCheckedItem(R.id.nav_account);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_account:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentProfile()).commit();
                break;
            case R.id.nav_AddSecy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentAddSecy()).commit();
                break;

            case R.id.nav_DeleteSecy:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentDelSecy()).commit();
                break;

            case R.id.nav_logout:
                UserInfo.logout();
//                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
//                startActivity(intent);
                sharedPreferenceConfig.writeLoginStatus(false);
                this.finish();
        }
        mDrawLay.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
