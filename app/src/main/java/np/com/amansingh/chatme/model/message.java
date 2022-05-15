package np.com.amansingh.chatme.model;

import java.security.Timestamp;

public class message {
    private  String textMessage;
    private  String photoUrl;
    private  String videoUrl;
    private long timestamp;

    public long getTime() {
        return timestamp;
    }

    public String getSentTo() {
        return sentTo;
    }

    public String getSentBy() {
        return sentBy;
    }

    private  String sentTo;
    private  String sentBy;

    message()
    {

    }
    public message(String sentBy, String sentTo, long t)
    {
        this.sentBy=sentBy;
        this.sentTo=sentTo;
        timestamp=t;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
