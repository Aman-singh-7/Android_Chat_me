package np.com.amansingh.chatme.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import np.com.amansingh.chatme.adapters.friendListAdapter;
import np.com.amansingh.chatme.databinding.ActivityFriendListBinding;
import np.com.amansingh.chatme.model.MateRepo;
import np.com.amansingh.chatme.model.friends;
import np.com.amansingh.chatme.model.mateModel.Mates;
import np.com.amansingh.chatme.viewModals.friendViewModel;
import np.com.amansingh.chatme.viewModals.friendViewModelFactory;

public class friendListActivity extends AppCompatActivity implements friendListAdapter.adapterClickListner {

    private ActivityFriendListBinding binding;
    private RecyclerView contactRV;

    private ArrayList<friends> list=new ArrayList<>();
    private List<Mates> matesList=new ArrayList<>();
    private ProgressBar progressBar;
    private ActivityResultLauncher<String> resultLauncher;

    private friendViewModel viewModel;
    private friendViewModelFactory factory;

    private DatabaseReference reference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;

    private  friendListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFriendListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupUI();



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    protected  void setupUI()
    {
        contactRV =binding.friendListView;

        progressBar=binding.progressBar;
        mDatabase=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        reference=mDatabase.getReference().child("Users");
        MateRepo mateRepo=new MateRepo(getApplication());

        resultLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->{
            if(isGranted)
            {

            }else
            {


            }
        });
        if(checkSelfPermission(Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
        {
            resultLauncher.launch(Manifest.permission.READ_CONTACTS);

        }


        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
              Mates newMates=snapshot.getValue(Mates.class);
              if(newMates.getPhoneNumber()!=mAuth.getCurrentUser().getPhoneNumber()) {
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

         adapter=new friendListAdapter(this);
         contactRV.setLayoutManager(new LinearLayoutManager(this));
         contactRV.setAdapter(adapter);

         factory =new friendViewModelFactory(getApplication());
         viewModel=new ViewModelProvider(friendListActivity.this,factory).get(friendViewModel.class);

        Observer<List<Mates>> matesListObserver=new Observer<List<Mates>>() {
            @Override
            public void onChanged(List<Mates> mates) {
                adapter.setList(mates);
                adapter.notifyDataSetChanged();
                Log.v("List length->  ",list.size()+"");
                if(getSupportActionBar()!=null)
                {
                    getSupportActionBar().setSubtitle(adapter.getItemCount()+" Contacts");
                }
            }
        };
        viewModel.getMateList().observe(this,matesListObserver);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Select Contacts");
        }


    }



    @Override
    public void onlistClick(int position) {
        Mates mates= adapter.getItem(position);
        Log.v("item clicked","->"+position);
        if(mates!=null)
        {
            Intent chatIntent=new Intent(this, ChatRoomActivity.class);
            chatIntent.putExtra("class","friendList");
            chatIntent.putExtra("mateID",mates.getPhoneNumber());
            chatIntent.putExtra("mateName",mates.getName());
            startActivity(chatIntent);
        }
    }
}