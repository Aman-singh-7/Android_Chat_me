package np.com.amansingh.chatme.friendList;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.databinding.ActivityFriendListBinding;
import np.com.amansingh.chatme.model.MateRepo;
import np.com.amansingh.chatme.model.friends;
import np.com.amansingh.chatme.model.mateModel.Mates;

public class friendListActivity extends AppCompatActivity {

    private ActivityFriendListBinding binding;
    private RecyclerView contactRV;

    private ArrayList<friends> list=new ArrayList<>();
    private List<Mates> matesList=new ArrayList<>();
    private ProgressBar progressBar;
    private ActivityResultLauncher<String> resultLauncher;

    private  friendViewModel viewModel;
    private friendViewModelFactory factory;

    private DatabaseReference reference;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFriendListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupUI();



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.home:
                     this.finish();
                     return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected  void setupUI()
    {
        contactRV =binding.friendListView;

        progressBar=binding.progressBar;
        mDatabase=FirebaseDatabase.getInstance();
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
               if(viewModel.isMate(snapshot.getKey())) {

                   Mates newMates = snapshot.getValue(Mates.class);
                   viewModel.insert(newMates);
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

        friendListAdapter adapter=new friendListAdapter();
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

        Observer<ArrayList<friends>> friendListObserver=new Observer<ArrayList<friends>>() {
            @Override
            public void onChanged(ArrayList<friends> list) {
                 viewModel.updateMates(list,reference);
            }
        };
        viewModel.getContacts(getContentResolver()).observe(this,friendListObserver);
        try {
             viewModel.getMateList().observe(this,matesListObserver);
        }catch (Exception e)
        {
            Log.v("Exception",e.getMessage());
            Log.v("observer:",viewModel==null?"Null":"Not Null");
        }




        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Select Contacts");

        }


    }




}