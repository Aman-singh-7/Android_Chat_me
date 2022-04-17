package np.com.amansingh.chatme.friendList;

import android.app.Application;
import android.content.ContentResolver;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import np.com.amansingh.chatme.model.MateRepo;
import np.com.amansingh.chatme.model.friends;
import np.com.amansingh.chatme.model.mateModel.Mates;

public class friendViewModel extends ViewModel {

    private MutableLiveData<ArrayList<friends>> friendData;
    private LiveData<List<Mates>> matesLiveData;


    private MateRepo repo;
    private  boolean flag;
    friendViewModel(@NonNull Application application)
    {

        repo=new MateRepo(application);
        friendData=new MutableLiveData<>();
        matesLiveData=repo.getGetMateList();
        flag=true;
        Log.v("Viewmodel:","View model created");
    }
    public  MutableLiveData<ArrayList<friends>> getContacts(ContentResolver contentResolver)
    {

        if(flag)
        repo.fetchContact(contentResolver, new MateRepo.fetchContactCallback() {
            @Override
            public void onComplete(ArrayList list) {
                  friendData.setValue(list);
                  flag=false;


            }
        });
        return friendData;
    }
    public void loadContacts()
    {
        flag=true;
    }


    public  void insert(Mates mates)
    {
        if(!isMate(mates.getPhoneNumber()))
          repo.insertMates(mates);
        else
        {
            ;
        }
          Log.v("Insert-> :",mates.toString()+" Inserted");
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
