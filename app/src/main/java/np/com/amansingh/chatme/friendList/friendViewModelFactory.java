package np.com.amansingh.chatme.friendList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class friendViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
     friendViewModelFactory(Application application)
    {
        mApplication=application;

    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
       if(modelClass==friendViewModel.class)
       {
           return (T) new friendViewModel(mApplication);
       }

       return  null;
    }
}
