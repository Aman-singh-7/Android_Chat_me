package np.com.amansingh.chatme.ui.main.viewmodals;

import androidx.lifecycle.ViewModel;

import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import np.com.amansingh.chatme.model.chatRoom;
import np.com.amansingh.chatme.ui.main.adapters.rvChatAdapter;

public class ChatListViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private  rvChatAdapter adapter;
    ChatListViewModel(rvChatAdapter.itemClickListener listener)
    {
        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
    }
    public  rvChatAdapter getAdapter()
    {
        return  adapter;
    }
}