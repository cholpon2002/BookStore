package com.example.bookcave;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.bookcave.extras.LogoutDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeCustomer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_customer);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Передача идентификатора каждого меню как набора идентификаторов,
        // потому что каждое меню должны рассматриваться как пункты пропуска верхнего уровня.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbarmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutButtonHeader:
//                openDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
//    public void openDialog(){
//        LogoutDialog logoutdialog=new LogoutDialog();
//        logoutdialog.show(getSupportFragmentManager(),"Log out Dialog");
//    }
}
