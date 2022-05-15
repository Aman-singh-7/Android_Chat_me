package np.com.amansingh.chatme.model;

import np.com.amansingh.chatme.model.mateModel.Mates;
import np.com.amansingh.chatme.model.user;

public class chatRoom {
    private  String chatRoomID;
    private String name;
    private  String phoneNumber;
    private  String photoUrl;
    private  String status;

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getStatus() {
        return status;
    }

    public  chatRoom()
    {

    }
    public  chatRoom(String chatRoomID, Mates Receiver)
    {
        this.chatRoomID=chatRoomID;
        name=Receiver.getName();
        phoneNumber=Receiver.getPhoneNumber();
        photoUrl=Receiver.getPhotoUrl();
        status= Receiver.getStatus();
    }

    public String getChatRoomID() {
        return chatRoomID;
    }

}

