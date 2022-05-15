package np.com.amansingh.chatme.viewModals;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailsFillUpVmFactory implements ViewModelProvider.Factory {


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsFillUpViewModal();
    }
}
