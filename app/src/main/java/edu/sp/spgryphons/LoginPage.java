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

public class LoginPage extends AppCompatActivity {
    EditText emailId, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button);
        tvSignIn = findViewById(R.id.textView);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
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
                                Toast.makeText(LoginPage.this,"Fields are empty!",Toast.LENGTH_SHORT).show();

                            }
                            else if(!(email.isEmpty() && pwd.isEmpty())){
                                mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(!task.isSuccessful()){
                                            Toast.makeText(LoginPage.this,"SignUp Unsuccessul , Please try again!",Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            startActivity(new Intent(LoginPage.this,events.class));
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(LoginPage.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                            }

            }
        });
            tvSignIn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(LoginPage.this,RealLoginPage.class);
                    startActivity(i);
                }
            });
    }
}
