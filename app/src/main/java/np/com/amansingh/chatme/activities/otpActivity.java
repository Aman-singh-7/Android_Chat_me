package np.com.amansingh.chatme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import np.com.amansingh.chatme.databinding.ActivityOtpBinding;

public class otpActivity extends AppCompatActivity {

    private ActivityOtpBinding otpBinding;
    private EditText otpEditText;
    private Button verifyButton;
    private LinearLayout resendLayout;
    private  LinearLayout callMeLayout;
    private TextView resendOtp;
    private TextView callme;
    private  TextView resendTimer;
    private TextView callTimer;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private  PhoneAuthProvider.ForceResendingToken resendingToken;
    private  String mVerificationId;
    private  ProgressBar mProgressBar;


    private String phoneNumber;
    private String otp;
    private boolean resendTimerEnabled;
    private boolean callMeTimerEnabled;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpBinding=ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(otpBinding.getRoot());

        setupUI();



        Intent phoneNumberLoginIntent=getIntent();

        phoneNumber=phoneNumberLoginIntent.getStringExtra("phoneNumber");
        Log.v("phoneNumber",phoneNumber);
        mAuth=FirebaseAuth.getInstance();

        mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInPhoneAuthCredential(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.v("verification failed",""+e.getMessage());


            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                resendingToken=forceResendingToken;
                mVerificationId=s;



            }
        };


        PhoneAuthOptions options= PhoneAuthOptions.newBuilder(mAuth)
                .setActivity(otpActivity.this)
                .setCallbacks(mCallbacks)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }



    void setupUI()
    {
        otpEditText=otpBinding.otpEt;
        verifyButton=otpBinding.verifyOtp;
        resendLayout=otpBinding.resendLayout;
        callMeLayout=otpBinding.callmeLayout;
         resendOtp=otpBinding.resendOTPTV;
         callme= otpBinding.callmeTV;
         resendTimer=otpBinding.resendTimer;
         callTimer=otpBinding.callTimer;
         mProgressBar=otpBinding.progressBar;


         verifyButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 otp=otpEditText.getText().toString();
               if(otpEditText.getText().toString().length()==6&&mVerificationId!=null&&otp!=null)
               {
                   PhoneAuthCredential credential=PhoneAuthProvider.getCredential(mVerificationId,otp);
                   signInPhoneAuthCredential(credential);


               }else if (mVerificationId==null)
               {
                   CharSequence text="null VerificationId";
                   Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
               }
               else
               {
                   CharSequence text="OTP is not Correct..";
                   Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
                   otpEditText.setText("");

               }




             }
         });

         resendTimer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(!resendTimerEnabled)
                 {
                     resendTimer.setVisibility(View.VISIBLE);
                     timerCountDown(resendTimer);


                 }

             }
         });
         callTimer.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(!callMeTimerEnabled)
                 {
                     callTimer.setVisibility(View.VISIBLE);
                     timerCountDown(callTimer);
                 }
             }
         });

    }

    void signInPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    startActivity(new Intent(getApplicationContext(), DetailsFillUpActivity.class));


                }


            }

        });

    }
    void timerCountDown(TextView textView)
    {

        if(textView.equals(resendTimer))
        {
            resendTimerEnabled=true;
            resendOtp.setVisibility(View.INVISIBLE);
        }else
        {
            callMeTimerEnabled=true;
            callme.setVisibility(View.INVISIBLE);
        }
        CountDownTimer Timer=new CountDownTimer(120000,1000) {
            @Override
            public void onTick(long l) {
                CharSequence min=""+((120000-l)/60000)+":"+((120000-l)%60000);

                textView.setText(min);

            }

            @Override
            public void onFinish() {
                if(textView.equals(resendTimer))
                {
                    resendTimerEnabled=false;
                    resendOtp.setVisibility(View.VISIBLE);



                }else
                {
                    callMeTimerEnabled=false;
                    callme.setVisibility(View.VISIBLE);
                }
                textView.setVisibility(View.INVISIBLE);
            }
        };
    }

}