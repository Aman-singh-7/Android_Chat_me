package np.com.amansingh.chatme.viewModals;

import android.net.Uri;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.ViewModel;

import java.net.URI;
import java.nio.channels.AcceptPendingException;

public class userProfileViewModal extends ViewModel {
    private Uri photourl;
    private  String name;
    private  String about;
    public Uri getPhotourl() {
        return photourl;
    }

    public void setPhotourl(Uri photourl) {
        this.photourl = photourl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
