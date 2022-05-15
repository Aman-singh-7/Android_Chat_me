package np.com.amansingh.chatme.ui.main.viewmodals;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import np.com.amansingh.chatme.ui.main.adapters.rvChatAdapter;

public class pageViewModalFactory implements ViewModelProvider.Factory {
    private rvChatAdapter.itemClickListener listener;

    public pageViewModalFactory(rvChatAdapter.itemClickListener listener)
    {
        this.listener=listener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatListViewModel(listener);
    }
}
