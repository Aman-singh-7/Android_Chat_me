package np.com.amansingh.chatme.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.databinding.StatusFragmentBinding;

public class statusFragment extends Fragment {

    private StatusViewModel mViewModel;
    private StatusFragmentBinding statusFragmentBinding;

    public static statusFragment newInstance() {
        return new statusFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        statusFragmentBinding=StatusFragmentBinding.inflate(inflater, container, false);
        View root=statusFragmentBinding.getRoot();

        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        // TODO: Use the ViewModel


    }


}