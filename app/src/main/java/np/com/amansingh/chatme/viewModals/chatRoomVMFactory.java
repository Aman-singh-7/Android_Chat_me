package np.com.amansingh.chatme.viewModals;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class chatRoomVMFactory implements ViewModelProvider.Factory {
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private  String phoneNumber;
    public chatRoomVMFactory(FirebaseDatabase d, FirebaseAuth auth, String num)
    {
        database=d;
        mAuth=auth;
        phoneNumber=num;

    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass== chatRoomViewModel.class)
        {
            return (T) new  chatRoomViewModel(database,mAuth,phoneNumber);
        }
        return  null;
    }
}
