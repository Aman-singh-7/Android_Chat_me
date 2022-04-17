package np.com.amansingh.chatme;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.textclassifier.TextSelection;
import android.widget.Toast;

import np.com.amansingh.chatme.friendList.friendListActivity;
import np.com.amansingh.chatme.ui.main.SectionsPagerAdapter;
import np.com.amansingh.chatme.databinding.ActivityMainBinding;
import np.com.amansingh.chatme.ui.main.agreementActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth=FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
       toolbar=binding.toolbar;
      toolbar.addMenuProvider(new MenuProvider() {
          @Override
          public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
              menuInflater.inflate(R.menu.main_page_menus,menu);

          }

          @Override
          public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
              switch (menuItem.getItemId())
              {
                  case R.id.logout:
                      CharSequence text="Logged out";
                      Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
                      mFirebaseAuth.signOut();
                      startActivity(new Intent(getApplicationContext(),phoneNumberLogin.class));

                      return true;
              }

              return false;
          }
      });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, friendListActivity.class));
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();


    }
}