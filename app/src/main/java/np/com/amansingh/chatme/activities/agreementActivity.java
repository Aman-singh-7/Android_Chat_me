package np.com.amansingh.chatme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import np.com.amansingh.chatme.databinding.ActivityAgreementBinding;

public class agreementActivity extends AppCompatActivity {
    private ActivityAgreementBinding activityAgreementBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       activityAgreementBinding= ActivityAgreementBinding.inflate(getLayoutInflater());
        setContentView(activityAgreementBinding.getRoot());
        getIntent();

        Button agreeButton=activityAgreementBinding.button2;
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent=new Intent(agreementActivity.this, phoneNumberLogin.class);
                startActivity(loginIntent);

            }
        });


    }
}