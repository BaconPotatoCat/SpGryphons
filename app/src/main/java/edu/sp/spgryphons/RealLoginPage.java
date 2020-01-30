package edu.sp.spgryphons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RealLoginPage extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthenticateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_login_page);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignIn = findViewById(R.id.button);
        tvSignUp = findViewById(R.id.textView);

        mAuthenticateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null){
                    Toast.makeText(RealLoginPage.this,"You are logged in!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RealLoginPage.this, HomePage.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(RealLoginPage.this,"Please login!",Toast.LENGTH_SHORT).show();

                }
            }
        };
        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                    String email = emailId.getText().toString();
                    String pwd = password.getText().toString();
                    if(email.isEmpty()){
                        emailId.setError("Please enter a valid email!");
                        emailId.requestFocus();
                    }
                    else if(pwd.isEmpty()){
                        password.setError("Please enter a valid password!");
                        password.requestFocus();
                    }
                    else if(email.isEmpty() && pwd.isEmpty()){
                        Toast.makeText(RealLoginPage.this,"Fields are empty!",Toast.LENGTH_SHORT).show();

                    }
                    else if(!(email.isEmpty() && pwd.isEmpty())) {
                        mFirebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(RealLoginPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!(task.isSuccessful())){
                                    Toast.makeText(RealLoginPage.this,"Login Error!! Please try again!",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent intToHome = new Intent(RealLoginPage.this,HomePage.class);
                                    startActivity(intToHome);
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(RealLoginPage.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                    }
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(RealLoginPage.this,LoginPage.class);
                startActivity(intSignUp);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthenticateListener);
    }
}
