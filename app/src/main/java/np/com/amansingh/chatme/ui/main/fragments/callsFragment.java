package np.com.amansingh.chatme.ui.main.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import np.com.amansingh.chatme.databinding.CallsFragmentBinding;
import np.com.amansingh.chatme.ui.main.viewmodals.CallsViewModel;

public class callsFragment extends Fragment {

    private CallsViewModel mViewModel;
    private CallsFragmentBinding callsFragmentBinding;

    public static callsFragment newInstance() {
        return new callsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        callsFragmentBinding=CallsFragmentBinding.inflate(inflater,container,false);
        View root =callsFragmentBinding.getRoot();
        return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CallsViewModel.class);
        // TODO: Use the ViewModel
    }

}