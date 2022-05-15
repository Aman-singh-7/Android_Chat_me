package np.com.amansingh.chatme.viewModals;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import np.com.amansingh.chatme.activities.DetailsFillUpActivity;

public class DetailsFillUpViewModal extends ViewModel {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseStorage mStorage;
    private StorageReference mStorageReference;
    private Uri localImageUri;
   DetailsFillUpViewModal()
   {
       mAuth=FirebaseAuth.getInstance();
       database=FirebaseDatabase.getInstance();
       mStorage=FirebaseStorage.getInstance();
       mStorageReference=mStorage.getReference().child("profile Photos").child(mAuth.getCurrentUser().getPhoneNumber());
       Log.v("DetailsFill VM","VM Created");
   }

   public void setLocalImageUri(Uri localImageUri)
   {
       this.localImageUri=localImageUri;
   }

    public Uri getLocalImageUri() {
        return localImageUri;
    }

    public void uploadImage(Uri ImageUri)
   {
       if(ImageUri!=null){
           try {
               UploadTask task =mStorageReference.putFile(ImageUri);
               Task<Uri> uriTask=task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                   @Override
                   public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if(!task.isSuccessful())
                       {
                           throw task.getException();
                       }
                       return mStorageReference.getDownloadUrl();
                   }
               }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                   @Override
                   public void onComplete(@NonNull Task<Uri> task) {
                       database.getReference().child("Users").child(mAuth.getCurrentUser().getPhoneNumber()).child("info").child("photoUrl").setValue(task.getResult().toString());

                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Log.v("Upload Error", e.getMessage());
                   }
               });
           }catch (Exception e)
           {
               Log.v("UPLOAD ERROR ->",e.getMessage());
           }

       }
   }
}
