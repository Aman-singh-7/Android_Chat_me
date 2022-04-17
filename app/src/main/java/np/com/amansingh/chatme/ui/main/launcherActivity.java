package np.com.amansingh.chatme.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import np.com.amansingh.chatme.MainActivity;
import np.com.amansingh.chatme.R;
import np.com.amansingh.chatme.phoneNumberLogin;


public class launcherActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFirebaseAuth=FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser()==null)
        {
            startActivity(new Intent(this, agreementActivity.class));

        }else
        {
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}