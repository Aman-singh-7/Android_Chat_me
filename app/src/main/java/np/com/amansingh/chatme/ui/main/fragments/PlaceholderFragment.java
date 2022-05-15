package np.com.amansingh.chatme.ui.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import np.com.amansingh.chatme.activities.ChatRoomActivity;
import np.com.amansingh.chatme.activities.MainActivity;
import np.com.amansingh.chatme.databinding.FragmentMainBinding;
import np.com.amansingh.chatme.model.chatRoom;
import np.com.amansingh.chatme.ui.main.adapters.rvChatAdapter;
import np.com.amansingh.chatme.ui.main.util.customLinearLayoutManager;
import np.com.amansingh.chatme.ui.main.viewmodals.PageViewModel;
import np.com.amansingh.chatme.ui.main.viewmodals.pageViewModalFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements rvChatAdapter.itemClickListener{


    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;
    private FirebaseDatabase database;
    private rvChatAdapter adapter;
    private RecyclerView recyclerView;
    private Context mContext;
    private  View mRootView;
    public static PlaceholderFragment newInstance() {
        PlaceholderFragment fragment = new PlaceholderFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pageViewModel=new ViewModelProvider(this,new pageViewModalFactory(this)).get(PageViewModel.class);

        Log.v("Fragment lyf","fragment onViewCreated");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;

        Log.v("fragment ","Fragment is attached");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

        Log.v("Fragment lyf","fragment onCreate");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView= binding.rvFriendsChats;
        Log.v("Fragment lyf","fragment onCreateView");
        mRootView=root;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageViewModel=new ViewModelProvider(this,new pageViewModalFactory(this)).get(PageViewModel.class);
        adapter= pageViewModel.getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new customLinearLayoutManager(getContext()));

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("Fragment lifecycle","Fragment Resumed");
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        Log.v("fragment lifecycle","fragment started");

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClickItem(chatRoom c) {
        if(c!=null)
        {
            if(isAdded())
            try {
                    Intent crIntent = new Intent(getActivity(), ChatRoomActivity.class);
                    crIntent.putExtra("class", "main");
                    crIntent.putExtra("mateID", c.getPhoneNumber());
                    crIntent.putExtra("mateName", c.getName());
                    crIntent.putExtra("chatID", c.getChatRoomID());
                    if (crIntent != null)
                        startActivity(crIntent);

            }catch (Exception e)
            {
                Log.e("Error In Starting Activity",e.getMessage());
            }
            else
            {
                Log.e("Fragment error","Fragment is not attached");
            }
        }else
        {
            Log.e("Clicked","null Chatroom");
        }
    }

}