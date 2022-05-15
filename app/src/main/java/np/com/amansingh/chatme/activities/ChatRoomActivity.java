package np.com.amansingh.chatme.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.adapters.chatAdapter;
import np.com.amansingh.chatme.viewModals.chatRoomViewModel;
import np.com.amansingh.chatme.databinding.ActivityChatRoomBinding;
import np.com.amansingh.chatme.model.chatRoom;
import np.com.amansingh.chatme.model.mateModel.Mates;
import np.com.amansingh.chatme.model.message;
import np.com.amansingh.chatme.viewModals.chatRoomVMFactory;

public class ChatRoomActivity extends AppCompatActivity {

    private ActivityChatRoomBinding binding;
    private RecyclerView mRecyclerView;
    private EditText messageEditText;
    private Button smileyButton;
    private  Button cameraButton;
    private  Button fileButton;
    private FloatingActionButton audioRecordingButton;

    private chatAdapter adapter;
    private boolean sentActive=false;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private np.com.amansingh.chatme.model.message message;
    private Mates sender;
    private np.com.amansingh.chatme.viewModals.chatRoomViewModel chatRoomViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupUI();



    }
    void setupUI()
    {
        mRecyclerView=binding.chatList;
        messageEditText=binding.chatBarMessage;
        smileyButton=binding.chatBarSmiley;
        cameraButton=binding.chatBarCamera;
        fileButton=binding.chatBarFile;
        audioRecordingButton=binding.chatBarMic;
        mAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        database.getReference().child("Users").child(mAuth.getCurrentUser().getPhoneNumber()).child("info").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isComplete()&&task.isSuccessful())
                {
                    sender=task.getResult().getValue(Mates.class);
                }
            }
        });
        Intent friendListIntent=getIntent();
        String matePhone,matename,chatRoomID;
        String userNumber=mAuth.getCurrentUser().getPhoneNumber();
        if(friendListIntent.getStringExtra("class").equals("main"))
        {
         matename= friendListIntent.getStringExtra("mateName");
         matePhone=friendListIntent.getStringExtra("mateID");
         chatRoomID=friendListIntent.getStringExtra("chatID");
        }else
        {
            matename= friendListIntent.getStringExtra("mateName");
            matePhone=friendListIntent.getStringExtra("mateID");
            chatRoomID=getChatRoomID(matePhone,userNumber);
        }

        chatRoomVMFactory factory=new chatRoomVMFactory(database,mAuth,matePhone);
        chatRoomViewModel= new ViewModelProvider(this,factory).get(chatRoomViewModel.class);


        adapter=chatRoomViewModel.getAdapter(chatRoomID);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        reference= database.getReference().child("ChatRooms").child(chatRoomID);


        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setTitle(matename);
            getSupportActionBar().setSubtitle(matePhone);
        }
        messageEditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
               sentActive=true;
               audioRecordingButton.setForeground(getDrawable(R.drawable.ic_baseline_send_24));
               smileyButton.setVisibility(View.GONE);
               fileButton.setVisibility(View.GONE);

           }
       });


        audioRecordingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sentActive)
                {
                   message=new message(userNumber,matePhone,System.currentTimeMillis());
                    message.setTextMessage(messageEditText.getText().toString());
                    reference.child("Messages").push().setValue(message);
                    reference.child("RecentMessages").setValue(message);
                    sentActive=false;
                    messageEditText.setText("");
                    smileyButton.setVisibility(View.VISIBLE);
                    fileButton.setVisibility(View.VISIBLE);
                    audioRecordingButton.setForeground(getDrawable(R.drawable.ic_baseline_mic_none_24));
                }
            }
        });

        database.getReference().child("ChatRooms").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               if( snapshot.getKey().equals(chatRoomID)) {

                   Task<DataSnapshot> task=database.getReference().child("Users").child(matePhone).child("info").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                       @Override
                       public void onComplete(@NonNull Task<DataSnapshot> task) {
                           Mates mate=task.getResult().getValue(Mates.class);
                           database.getReference().child("Users").child(userNumber).child("ChatList").child(chatRoomID).setValue(new chatRoom(chatRoomID,mate));
                           database.getReference().child("Users").child(matePhone).child("ChatList").child(chatRoomID).setValue(new chatRoom(chatRoomID,sender));
                       }
                   });
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


    private  String  getChatRoomID(String p1,String p2)
    {
        p1=p1.substring(1);
        p2=p2.substring(1);
        return String.valueOf( Long.valueOf(p1)+Long.valueOf(p2));

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}