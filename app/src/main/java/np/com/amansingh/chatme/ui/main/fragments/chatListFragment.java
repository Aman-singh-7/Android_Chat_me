package np.com.amansingh.chatme.ui.main.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.activities.ChatRoomActivity;
import np.com.amansingh.chatme.databinding.ChatListFragmentBinding;
import np.com.amansingh.chatme.model.chatRoom;
import np.com.amansingh.chatme.ui.main.adapters.rvChatAdapter;
import np.com.amansingh.chatme.ui.main.util.customLinearLayoutManager;
import np.com.amansingh.chatme.ui.main.viewmodals.ChatListViewModel;
import np.com.amansingh.chatme.ui.main.viewmodals.pageViewModalFactory;

public class chatListFragment extends Fragment implements rvChatAdapter.itemClickListener {

    private ChatListViewModel mViewModel;
    private ChatListFragmentBinding binding;
    private RecyclerView mRecyclerView;
    private rvChatAdapter adapter;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    public static chatListFragment newInstance() {
        return new chatListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding=ChatListFragmentBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView=binding.chatListRV;
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        FirebaseRecyclerOptions<chatRoom> options=new FirebaseRecyclerOptions.Builder<chatRoom>().setQuery(database.getReference().child("Users").child(mAuth.getCurrentUser().getPhoneNumber()).child("ChatList"),chatRoom.class).build();
        adapter=new rvChatAdapter(options,database,this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new customLinearLayoutManager(getContext()));

    }


    @Override
    public void onClickItem(chatRoom c) {
        if(c!=null)
        {
            try {
                Intent crIntent = new Intent(getActivity(), ChatRoomActivity.class);
                crIntent.putExtra("class", "main");
                crIntent.putExtra("mateID", c.getPhoneNumber());
                crIntent.putExtra("mateName", c.getName());
                crIntent.putExtra("chatID", c.getChatRoomID());
                if (crIntent != null)
                    startActivity(crIntent);
            }catch ( Exception e)
            {
                Log.e("fragmentChatList",e.getMessage());
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();


    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}