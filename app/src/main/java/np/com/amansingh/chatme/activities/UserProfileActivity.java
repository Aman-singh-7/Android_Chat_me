package np.com.amansingh.chatme.activities;

import static android.os.Build.VERSION_CODES.R;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import np.com.amansingh.chatme.databinding.ActivityUserProfileBinding;
import np.com.amansingh.chatme.viewModals.userProfileViewModal;

public class UserProfileActivity extends AppCompatActivity {

    private ActivityUserProfileBinding binding;
    private ImageView profilepic;
    private CardView cardView;
    private FloatingActionButton picChangeButton;
    private RelativeLayout nameLayout;
    private RelativeLayout aboutLayout;
    private ActivityResultLauncher<String> permissionLauncher;
    private  ActivityResultLauncher<String> mGetContent;
    private  Uri ImageUri;
    private userProfileViewModal userProfileViewModal;
    private FirebaseStorage mStorage;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private StorageReference mStorageReference;
    private Uri ImageUploadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserProfileBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        mAuth=FirebaseAuth.getInstance();
        mStorage=FirebaseStorage.getInstance();
        mStorageReference=mStorage.getReference().child("Users").child(mAuth.getCurrentUser().getPhoneNumber());
        database=FirebaseDatabase.getInstance();



        Log.v("USER PROFILE-> ","ACTIVITY CREATED");
        userProfileViewModal=new ViewModelProvider(this).get(userProfileViewModal.class);

        mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), result -> {
            if(result!=null) {
                profilepic.setImageURI(result);
                userProfileViewModal.setPhotourl(result);
                Log.v("IMAGE URI -> ",result.toString());
               uploadImage(userProfileViewModal.getPhotourl());

            }
            else
            {
                Log.e("USER PROFILE ->","ERROR WHILE RETRIVING THE IMAGE");
            }
        }) ;

        permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->
        {
            if(isGranted)
            {
                Log.e("PERMISSION ->","GRANTED");
            }else
            {
                CharSequence text="permisson not Granted";
                new AlertDialog.Builder(getApplicationContext()).setMessage(text).show();
                Log.e("PERMISSION-> ","NOT GRANTED");
            }

        });




        setupUI();


    }
    void setupUI()
    {
        profilepic=binding.profilePic;
        picChangeButton=binding.ppChangeButton;
        nameLayout=binding.nameLayout;
        aboutLayout=binding.aboutLayout;

        picChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    Log.v("LAUNCHING ->","IMAGE RETERVING ACTIVITY");
                    mGetContent.launch("image/*");
                }else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                {
                 Log.v("ASDF->","Unknown thing");
                }else
                {
                    Log.v("Permission launcher ->","permission launched");
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
        });

        if(userProfileViewModal.getPhotourl()!=null) {

            profilepic.setImageURI(userProfileViewModal.getPhotourl());
            Log.v("IMAGE URI->  ",userProfileViewModal.getPhotourl().toString());
        }else
        {
            Log.v("Image URI->","Null Image URI");
        }



    }


    public  void   uploadImage(Uri ImageUri)
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