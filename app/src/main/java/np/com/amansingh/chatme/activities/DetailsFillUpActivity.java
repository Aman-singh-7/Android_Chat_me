package np.com.amansingh.chatme.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.databinding.ActivityUserDetailsBinding;
import np.com.amansingh.chatme.model.mateModel.Mates;
import np.com.amansingh.chatme.viewModals.DetailsFillUpViewModal;
import np.com.amansingh.chatme.viewModals.DetailsFillUpVmFactory;

public class DetailsFillUpActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button nextButton;
    private EditText nameED;
    private ProgressBar progressBar;

    //Firebase
    private FirebaseStorage mStorage;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private StorageReference mStorageReference;
    private ActivityResultLauncher<String > permissionLauncher;
    private ActivityResultLauncher<String> mGetContent;
    private DetailsFillUpViewModal viewModal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        viewModal=new ViewModelProvider(DetailsFillUpActivity.this,new DetailsFillUpVmFactory()).get(DetailsFillUpViewModal.class);
        permissionLauncher=registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            CharSequence text="";
            if(result)
            {
                text="Permission granted";
            }else
            {
                text="Permission Denied";
            }
            Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
        });
        mGetContent=registerForActivityResult(new ActivityResultContracts.GetContent(),result ->
        {
            if(result!=null)
            {
                imageView.setImageURI(result);
                viewModal.setLocalImageUri(result);
                viewModal.uploadImage(result);
                Log.v("Image uri",result.toString());
            }else
            {
                Log.v("Error ","Null Image Uri");
            }
        });

        database=FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();

        setupUI();



    }

    public  void  setupUI()
    {
        imageView=findViewById(R.id.profile);
        nameED=findViewById(R.id.userName);
        nextButton=findViewById(R.id.button_next);
        progressBar=findViewById(R.id.progress_bar);
        if(viewModal.getLocalImageUri()!=null)
        {
            imageView.setImageURI(viewModal.getLocalImageUri());
        }
        imageView.setOnClickListener(view->{
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
            {
                mGetContent.launch("image/*");

            }else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE))
            {

            }else
            {
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });

        nextButton.setOnClickListener(view -> {
            if(!nameED.getText().toString().equals(""))
            {
                Mates user=new Mates();
                user.setPhoneNumber(mAuth.getCurrentUser().getPhoneNumber());
                user.setName(nameED.getText().toString());
                user.setStatus("Hey There!, I'm Using Chat Me");
                database.getReference().child("Users").child(mAuth.getCurrentUser().getPhoneNumber()).child("info").setValue(user);
                startActivity(new Intent(this,MainActivity.class));
            }else
            {
                CharSequence text="Enter The Name first";
                Toast.makeText(this,text,Toast.LENGTH_SHORT).show();

            }

        });


    }


}
