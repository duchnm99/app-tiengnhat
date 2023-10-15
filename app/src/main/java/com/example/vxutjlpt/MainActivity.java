package com.example.vxutjlpt;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.vxutjlpt.db.DbQuery;
import com.example.vxutjlpt.fragment.AccountFragment;
import com.example.vxutjlpt.fragment.HomeFragment;
import com.example.vxutjlpt.fragment.LeaderboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vxutjlpt.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final int  FRAGMENT_HOME = 1;
    public static final int  FRAGMENT_ACCOUNT = 2;
    public static final int  FRAGMENT_LEADERBOARD = 3;

    private int current_fragment = FRAGMENT_HOME;

    private NavigationView mNavigationView;
    private BottomNavigationView mBottomNavigationView;
    private DrawerLayout drawerLayout;
    private TextView profileImage, profileName, profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Home");
        
        drawerLayout= findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // set profile
        profileImage = mNavigationView.getHeaderView(0).findViewById(R.id.nav_drawer_imgText);
        profileName = mNavigationView.getHeaderView(0).findViewById(R.id.nav_drawer_name);
        profileEmail = mNavigationView.getHeaderView(0).findViewById(R.id.nav_drawer_email);

        String nameP = DbQuery.mProfile.getName();
        profileName.setText(nameP);
        String emailP = DbQuery.mProfile.getEmail();
        profileEmail.setText(emailP);
        profileImage.setText(nameP.toUpperCase().substring(0,1));

        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        replaceFragment(new HomeFragment());
                        mNavigationView.setCheckedItem(R.id.nav_home);
                        mBottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case R.id.action_leaderboard:
                        replaceFragment(new LeaderboardFragment());
                        mNavigationView.setCheckedItem(R.id.nav_leaderboard);
                        mBottomNavigationView.getMenu().findItem(R.id.action_leaderboard).setChecked(true);
                        break;
                    case R.id.action_account:
                        replaceFragment(new AccountFragment());
                        mNavigationView.setCheckedItem(R.id.nav_account);
                        mBottomNavigationView.getMenu().findItem(R.id.action_account).setChecked(true);
                        break;
                }
//                setTitleToolbar();
                return false;
            }
        });

        replaceFragment(new HomeFragment());
        mNavigationView.setCheckedItem(R.id.nav_home);
        mBottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
//        setTitleToolbar();

    }// End onCreate

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){
            replaceFragment(new HomeFragment());
            mNavigationView.setCheckedItem(R.id.nav_home);
            mBottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
        }else if(id == R.id.nav_leaderboard){
            replaceFragment(new LeaderboardFragment());
            mNavigationView.setCheckedItem(R.id.nav_leaderboard);
            mBottomNavigationView.getMenu().findItem(R.id.action_leaderboard).setChecked(true);
        }else if(id == R.id.nav_account){
            replaceFragment(new AccountFragment());
            mNavigationView.setCheckedItem(R.id.nav_account);
            mBottomNavigationView.getMenu().findItem(R.id.action_account).setChecked(true);
        }
//        setTitleToolbar();
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    //replace fragment
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    //set title toolbar
    private void setTitleToolbar(){
        String title = "";
        switch (current_fragment){
            case FRAGMENT_HOME:
                title = getString(R.string.nav_home);
                break;
            case FRAGMENT_LEADERBOARD:
                title = getString(R.string.nav_leaderboard);
                break;
            case FRAGMENT_ACCOUNT:
                title = getString(R.string.nav_account);
                break;
        }
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    //--------*********--------
}//End MainActivity