package np.com.amansingh.chatme.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.function.ToLongBiFunction;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.model.mateModel.Mates;
import np.com.amansingh.chatme.ui.main.SectionsPagerAdapter;
import np.com.amansingh.chatme.databinding.ActivityMainBinding;
import np.com.amansingh.chatme.ui.main.fragments.PlaceholderFragment;
import np.com.amansingh.chatme.viewModals.friendViewModel;
import np.com.amansingh.chatme.viewModals.friendViewModelFactory;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mUser;
    private Toolbar toolbar;
    private  String LOG_TAG_MAIN="MAIN ACTIVITY ->";
    private FirebaseDatabase database;
    private friendViewModel viewModel;


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
        viewModel=new ViewModelProvider(this,new friendViewModelFactory(getApplication())).get(friendViewModel.class);
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
                      startActivity(new Intent(MainActivity.this, phoneNumberLogin.class));
                      return true;
                  case R.id.profile_edit:
                                  CharSequence t="profile edit";
                                  Toast.makeText(getApplicationContext(),t, Toast.LENGTH_SHORT).show();
                                  startActivity(new Intent(MainActivity.this,UserProfileActivity.class));
                                  return true;
                  case R.id.user_profile:
                        startActivity(new Intent(MainActivity.this,DetailsFillUpActivity.class));
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
        database=FirebaseDatabase.getInstance();
        database.getReference().child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Mates newMates=snapshot.getValue(Mates.class);
                if(newMates.getPhoneNumber()!=mFirebaseAuth.getCurrentUser().getPhoneNumber()) {
                    viewModel.insert(newMates, getContentResolver());
                    Log.v("NEW MATE", "INSERTED");
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
    }
}