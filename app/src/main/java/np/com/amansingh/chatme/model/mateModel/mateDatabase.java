package np.com.amansingh.chatme.model.mateModel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Mates.class},version =1)
public abstract class mateDatabase extends RoomDatabase {
    private  static  mateDatabase instance;
    public  abstract mateDAO matedao();
    public static  synchronized  mateDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context.getApplicationContext(),mateDatabase.class,"mate_database").fallbackToDestructiveMigration().build();
        }
        return  instance;

    }

}
