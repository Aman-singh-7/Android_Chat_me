package np.com.amansingh.chatme.viewModals;

import android.app.Application;
import android.content.ContentResolver;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import np.com.amansingh.chatme.model.MateRepo;
import np.com.amansingh.chatme.model.friends;
import np.com.amansingh.chatme.model.mateModel.Mates;

public class friendViewModel extends ViewModel {

    private MutableLiveData<HashMap<String,String>> friendData;
    private LiveData<List<Mates>> matesLiveData;


    private MateRepo repo;
    private  boolean flag;
    private ArrayList<friends> friendsList;
    private HashMap<String,String> contactMap;
    friendViewModel(@NonNull Application application)
    {

        repo=new MateRepo(application);
        friendData=new MutableLiveData<>();
        matesLiveData=repo.getGetMateList();
        flag=true;
        Log.v("Viewmodel:","View model created");
        friendsList=new ArrayList<>();
        contactMap=new HashMap<>();
    }
    public  MutableLiveData<HashMap<String,String>> getContacts(ContentResolver contentResolver)
    {

        if(flag)
        repo.fetchContact(contentResolver, new MateRepo.fetchContactCallback() {
            @Override
            public void onComplete(HashMap list) {
                contactMap=list;
                friendData.setValue(list);
            }
        });
        return friendData;
    }
    public void loadContacts()
    {
        flag=true;
    }


    public  void insert(Mates mates,ContentResolver contentResolver)
    {
        if(!isMate(mates.getPhoneNumber()))
        {
            if(contactMap.size()<=0)
                getContacts(contentResolver);
            if(contactMap.containsKey(mates.getPhoneNumber()))
                   repo.insertMates(mates);
        }
        else
        {

        }
         // Log.v("Insert-> :",mates.toString()+" Inserted");
    }


    public LiveData<List<Mates>> getMateList()
    {
        return matesLiveData;
    }

    public  boolean isMate(String Key)
    {
        return repo.isFriend(Key);
    }

    public void updateMates(ArrayList<friends> list, DatabaseReference reference)
    {
        repo.updateMates(list,reference);
    }

}
