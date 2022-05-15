package np.com.amansingh.chatme.viewModals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import np.com.amansingh.chatme.viewModals.friendViewModel;

public class friendViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
     public friendViewModelFactory(Application application)
    {
        mApplication=application;

    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
       if(modelClass== friendViewModel.class)
       {
           return (T) new friendViewModel(mApplication);
       }

       return  null;
    }
}
