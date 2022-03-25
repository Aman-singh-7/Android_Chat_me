package np.com.amansingh.chatme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import np.com.amansingh.chatme.databinding.ActivityPhoneNumberLoginBinding;

public class phoneNumberLogin extends AppCompatActivity {

    private ActivityPhoneNumberLoginBinding phoneNumberLoginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNumberLoginBinding=ActivityPhoneNumberLoginBinding.inflate(getLayoutInflater());

        setContentView(phoneNumberLoginBinding.getRoot());

        Button loginButton=phoneNumberLoginBinding.loginSignupButton;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(phoneNumberLogin.this,MainActivity.class));
            }
        });




    }
}