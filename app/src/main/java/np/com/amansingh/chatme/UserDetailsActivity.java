package np.com.amansingh.chatme;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashSet;

import np.com.amansingh.chatme.databinding.ActivityUserDetailsBinding;
import np.com.amansingh.chatme.model.user;

public class UserDetailsActivity extends AppCompatActivity {

    private ActivityUserDetailsBinding binding;
    private EditText userName;
    private Button nextButton;
    private ImageView imageView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private FirebaseStorage mStorage;
    private  StorageReference mStorageReference;

    private int IMAGE=1;

    ActivityResultLauncher<String> mGetContent;

    private Uri ImageUri;

    private ActivityResultLauncher<String> resultLauncher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupUI();




    }
    protected void setupUI()
    {
        userName=binding.userName;
        nextButton=binding.buttonNext;
        imageView=binding.profile;
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mStorage=FirebaseStorage.getInstance();
        mStorageReference=mStorage.getReference().child("profile Photos");
        imageView.setImageResource(R.drawable.ic_profile_foreground);

        resultLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(),isGranted->{
            if(isGranted)
            {

            }else
            {
                CharSequence text="permisson not Granted";
                new AlertDialog.Builder(getApplicationContext()).setMessage(text).show();
            }

        });
         mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                imageView.setImageURI(result);

                final StorageReference ref=mStorageReference.child(mAuth.getCurrentUser().getPhoneNumber());
                UploadTask uploadTask=ref.putFile(result);

                Task<Uri> urlTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if(!task.isSuccessful())
                        {
                            CharSequence text="Error in Image uploading";
                            Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
                            throw task.getException();
                        }
                       return  ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            ImageUri=task.getResult();
                        }else
                        {

                        }
                    }
                });



            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    mGetContent.launch("image/*");
                }else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
                {

                }
                else
                {
                    resultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                }





            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userName.getText().toString().equals(""))
                {
                    CharSequence text ="Enter Name";
                    userName.isFocused();
                    Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();

                }else
                {
                    try{
                        mReference=mDatabase.getReference().child("Users");
                        user mUser=new user(userName.getText().toString(),mAuth.getCurrentUser().getPhoneNumber());
                        if(ImageUri!=null)
                            mUser.setImageUrl(ImageUri.toString());
                            mUser.setStatus("Hey There! I using Chat Me.");

                        mReference.child(mAuth.getCurrentUser().getPhoneNumber()).setValue(mUser);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }catch (Exception e)
                    {
                        Log.e("Click error",e.getMessage());
                    }

                }

            }
        });


    }

}