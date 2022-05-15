package np.com.amansingh.chatme.ui.main.viewmodals;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import np.com.amansingh.chatme.model.chatRoom;
import np.com.amansingh.chatme.ui.main.adapters.rvChatAdapter;

public class PageViewModel extends ViewModel {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private rvChatAdapter adapter;
    private  rvChatAdapter.itemClickListener listener;

    public PageViewModel(rvChatAdapter.itemClickListener listener)
    {
       mAuth=FirebaseAuth.getInstance();
       database=FirebaseDatabase.getInstance();
       this.listener=listener;
        Log.v("ChatFragments","VM CREATED");
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<chatRoom>().setQuery(database.getReference().child("Users").child(mAuth.getCurrentUser().getPhoneNumber()).child("ChatList"),chatRoom.class).build();
        adapter=new rvChatAdapter(options,database,listener);

    }
    public  rvChatAdapter getAdapter()
    {
        return adapter;
    }


}