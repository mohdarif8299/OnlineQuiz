package com.alfredtechnologies;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText editUsername;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.cpassword)
    EditText c_password;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.signup)
    Button signupBtn;

    private FirebaseAuth mAuth;
    private static final String TAG = SignupActivity.class.getSimpleName();

    @OnClick(R.id.signup)
    void signupUser() {
        if (TextUtils.isEmpty(editUsername.getText().toString())) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.getText().toString().trim().equals(c_password.getText().toString().trim())) {
            Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show();
            return;
        } else {
            progressBar.setVisibility(View.VISIBLE);
            signupBtn.setEnabled(false);
            signupBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(editUsername.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                signupBtn.setEnabled(true);
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                mAuth.signOut();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                signupBtn.setEnabled(true);
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignupActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
    }
}
