package np.com.amansingh.chatme.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

public class friends  {
    private String name;
    private String phoneNumber;


    public friends(@NonNull String Name, @NonNull String phoneNumber)
    {
        name=Name;
        this.phoneNumber=phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    }
