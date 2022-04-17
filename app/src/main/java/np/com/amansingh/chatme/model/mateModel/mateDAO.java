package np.com.amansingh.chatme.model.mateModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Dao
public interface mateDAO {
  @Insert
    void  insert(Mates... mates);

  @Delete
    void delete(Mates mates);

  @Query("SELECT * FROM Mates")
   LiveData<List<Mates>> getAll();
  @Query("SELECT * FROM Mates WHERE  phoneNumber=:phoneNumber")
   Mates getMate(String phoneNumber);
  @Query("select * from Mates where phoneNumber=:phoneNumber")
  boolean isFriend(String phoneNumber);


}
