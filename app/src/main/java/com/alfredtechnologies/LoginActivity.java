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
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @BindView(R.id.username)
    EditText editUsername;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @OnClick(R.id.signup)
    void open() {
        startActivity(new Intent(this, SignupActivity.class));
    }

    UserSession userSession;

    private static final String TAG = LoginActivity.class.getSimpleName();

    @OnClick(R.id.login)
    void loginUser() {
        if (TextUtils.isEmpty(editUsername.getText().toString())) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        } else {
            loginBtn.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(editUsername.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                loginBtn.setEnabled(true);
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                userSession.createUserLoginSession(user.getEmail());
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            } else {
                                progressBar.setVisibility(View.GONE);
                                loginBtn.setEnabled(true);
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

        }

    }

    @BindView(R.id.login)
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        userSession = new UserSession(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userSession.createUserLoginSession(currentUser.getEmail());
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}
