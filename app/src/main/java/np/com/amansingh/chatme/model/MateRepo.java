package np.com.amansingh.chatme.model;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import np.com.amansingh.chatme.model.mateModel.Mates;
import np.com.amansingh.chatme.model.mateModel.mateDAO;
import np.com.amansingh.chatme.model.mateModel.mateDatabase;

public class MateRepo
{
    private  ArrayList<friends> friendsList;
    private mateDAO mateDao;
    private ExecutorService service;

    private LiveData<List<Mates>> getMateList;

    public MateRepo(Application application) {
        mateDatabase database = mateDatabase.getInstance(application);
        mateDao = database.matedao();
        service= Executors.newSingleThreadExecutor();
        getMateList=mateDao.getAll();
        friendsList=new ArrayList<>();
    }

    public  interface  fetchContactCallback<T>{
        void onComplete(ArrayList<friends> list);

    }



    public void fetchContact(ContentResolver cr,fetchContactCallback callback)
    {


        service.execute(new Runnable() {
            @Override
            public void run() {

                String select = "((" + ContactsContract.Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                        + ContactsContract.Contacts.HAS_PHONE_NUMBER + "=1) AND ("
                        + ContactsContract.Contacts.DISPLAY_NAME + " != '' ))";

                String projection[] =new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,projection,select,null,null);
                while (!cursor.isClosed()&&cursor.moveToNext())
                {
                    friendsList.add(new friends(cursor.getString(0),cursor.getString(1)));
                }

                cursor.close();
            }

        });
        callback.onComplete(friendsList);
        
    }



    public Mates findFriend(@NonNull String key)
    {
        final Mates[] mates = new Mates[1];
        Runnable findFriendRunnable=new Runnable() {
            @Override
            public void run() {
                mates[0] =mateDao.getMate(key);
            }
        };

        new ThreadPerExecutor().execute(findFriendRunnable);

      return  mates[0];

    }


    public void insertMates(Mates mate)
    {
      Runnable insertRunnable=new Runnable() {
          @Override
          public void run() {
              mateDao.insert(mate);
          }
      };
      new ThreadPerExecutor().execute(insertRunnable);
    }



    public boolean isFriend(String number)
    {
        final boolean[] res = {true};
      Runnable isFriendRunnable=new Runnable() {
          @Override
          public void run() {
             res[0] = mateDao.isFriend(number);
          }
      };
        new ThreadPerExecutor().execute(isFriendRunnable);
      return res[0];
    }











   public interface mateCallback{
        public void onComplete();
        public  void  onStart();
    }

    public LiveData<List<Mates>> getGetMateList() {
        return getMateList;
    }

    public void updateMates(ArrayList<friends>list, DatabaseReference reference)
    {
        Runnable updateMatesRunnable=new Runnable() {
            @Override
            public void run() {
                for(friends f:list)
                {
                    if(!isFriend(f.getPhoneNumber()))
                    {
                        reference.runTransaction(new Transaction.Handler() {
                            @NonNull
                            @Override
                            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                                Mates mates=currentData.getValue(Mates.class);
                                if(mates.getPhoneNumber()==f.getPhoneNumber())
                                {
                                    insertMates(mates);
                                }
                                return Transaction.success(currentData);
                            }

                            @Override
                            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                                Log.v("Mates :","Local Mates updated");
                            }
                        });
                    }
                }
            }
        };
    }

     static class  ThreadPerExecutor implements Executor {
       public void  execute(Runnable r)
       {
           new Thread(r).start();
       }
    }



}



