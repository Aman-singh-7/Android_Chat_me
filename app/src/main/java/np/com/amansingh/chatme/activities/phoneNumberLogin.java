package np.com.amansingh.chatme.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

import np.com.amansingh.chatme.activities.otpActivity;
import np.com.amansingh.chatme.databinding.ActivityPhoneNumberLoginBinding;

public class phoneNumberLogin extends AppCompatActivity {

    // View binding
    private ActivityPhoneNumberLoginBinding phoneNumberLoginBinding;

    //phone login views
    private EditText phoneNumber;
    private  EditText countryCode;

    private Button loginButton;
    private TextView mainNotice;
    private TextView noticeCarrier;
    private String phoneWithCC;
    private CountryCodePicker countryCodePicker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneNumberLoginBinding=ActivityPhoneNumberLoginBinding.inflate(getLayoutInflater());

        setContentView(phoneNumberLoginBinding.getRoot());

        setupUI(); // function to initialize the view in activity


    }


    private void setupUI()
    {
        //phone login views
        loginButton=phoneNumberLoginBinding.loginSignupButton;
        phoneNumber=phoneNumberLoginBinding.phoneNumber;
        countryCode=phoneNumberLoginBinding.countryCode;

        countryCodePicker=phoneNumberLoginBinding.ccp;
        countryCode.setText(countryCodePicker.getSelectedCountryCodeWithPlus().toString());
        CharSequence code[]=new CharSequence[1];
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                code[0] =countryCodePicker.getSelectedCountryCodeWithPlus();
                Log.v("code", "setupUI:"+ code[0]);
                countryCode.setText(code[0]);




            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cc=countryCode.getText().toString();

                String pn=phoneNumber.getText().toString();
                if(pn.length()<=0&&cc.length()<=0)
                {
                    CharSequence invalidPhoneNumberMessage="Enter the Valid Phone Number ";
                    Toast.makeText(getApplicationContext(),invalidPhoneNumberMessage,Toast.LENGTH_SHORT).show();

                }
              else
                {
                    Intent otpIntent=new Intent(getApplicationContext(), otpActivity.class);
                    otpIntent.putExtra("phoneNumber",cc+pn);
                    startActivity(otpIntent);

                }




            }
        });


    }



}