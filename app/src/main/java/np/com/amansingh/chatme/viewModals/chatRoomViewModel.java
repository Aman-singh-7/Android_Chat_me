package np.com.amansingh.chatme.viewModals;

import androidx.lifecycle.ViewModel;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import np.com.amansingh.chatme.adapters.chatAdapter;
import np.com.amansingh.chatme.model.message;

public class chatRoomViewModel extends ViewModel {
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private chatAdapter adapter;
    private   String ReceiverPhone;


    public chatRoomViewModel(FirebaseDatabase database,FirebaseAuth auth,String phoneNumber)
    {
        this.database=database;
        mAuth=auth;
        ReceiverPhone=phoneNumber;
    }

    public  chatAdapter getAdapter(String chatRoomID)
    {
        if(adapter==null)
        {
            FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<message>().setQuery(database.getReference().child("ChatRooms").child(chatRoomID).child("Messages"),message.class).build();
            adapter=new chatAdapter(options,mAuth.getCurrentUser().getPhoneNumber());
        }
        return  adapter;
    }


 }


