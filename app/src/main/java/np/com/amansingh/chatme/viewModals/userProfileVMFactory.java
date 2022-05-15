package np.com.amansingh.chatme.viewModals;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class userProfileVMFactory implements ViewModelProvider.Factory {
  public userProfileVMFactory()
    {

    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return null;
    }
}
