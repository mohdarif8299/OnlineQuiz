package com.alfredtechnologies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase, firebaseDatabase1;
    DatabaseReference databaseReference, databaseReference1;
    static LinkedHashMap<String, String> arrayList;
    int i = 0;
    TextView time, setName;
    int questions = 0;
    ProgressBar progressBar;
    String answer;
    Vibrator vibe;
    int timer;
    String uid, username;
    TextView questionNo;
    Exam examination;
    Button submit;
    String date;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    FirebaseUser user;
    boolean isFinished = false;
    String level;
    int score = 0;
    TextView question, option_a, option_b, option_c, option_d;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        level = intent.getStringExtra("level");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Android Developer");
        question = findViewById(R.id.question);
        option_a = findViewById(R.id.option_a);
        option_b = findViewById(R.id.option_b);
        option_c = findViewById(R.id.option_c);
        option_d = findViewById(R.id.option_d);

        time = findViewById(R.id.time);
        questionNo = findViewById(R.id.question_no);
        progressBar = findViewById(R.id.progress_timer);
        submit = findViewById(R.id.submit);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();
        username = user.getEmail();
        arrayList = new LinkedHashMap<>();
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        progressDialog = new ProgressDialog(this, R.style.MyDialogTheme);
        progressDialog.setMessage("Loading Question, please wait...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        nextQuestion();
        option_a.setOnClickListener(v -> {
            timer = 0;
            progressDialog.show();
            if (questions == 10) {
                if (option_a.getText().equals(answer)) {
                    option_a.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                    score++;
                } else {
                    vibe.vibrate(500);
                    option_a.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));
                }
                completeQuestion();
            } else {
                if (option_a.getText().equals(answer)) {
                    option_a.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                    score++;
                    nextQuestion();
                } else {
                    vibe.vibrate(500);
                    option_a.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));
                    nextQuestion();
                }
            }
        });
        option_b.setOnClickListener(v -> {
            timer = 0;
            progressDialog.show();
            if (questions == 10) {
                option_b.setEnabled(false);
                if (option_b.getText().equals(answer)) {
                    option_b.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                    score++;
                } else {
                    vibe.vibrate(500);
                    option_b.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));
                }
                completeQuestion();
            } else {
                if (option_b.getText().equals(answer)) {
                    option_b.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                    score++;
                    nextQuestion();

                } else {
                    vibe.vibrate(500);
                    option_b.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));
                    nextQuestion();

                }
            }
        });
        option_c.setOnClickListener(v -> {
            timer = 0;
            progressDialog.show();
            if (questions == 10) {
                if (option_c.getText().equals(answer)) {
                    option_c.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                    score++;
                } else {
                    vibe.vibrate(500);
                    option_c.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));
                }
                completeQuestion();
            } else {
                if (option_c.getText().equals(answer)) {
                    option_c.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                    score++;
                    nextQuestion();
                } else {
                    vibe.vibrate(500);
                    option_c.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));
                    nextQuestion();
                }
            }
        });
        option_d.setOnClickListener(v -> {
            timer = 0;
            progressDialog.show();
            if (questions == 10) {
                option_d.setEnabled(false);
                if (option_d.getText().equals(answer)) {
                    option_d.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                    score++;
                } else {
                    vibe.vibrate(500);
                    option_d.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));
                }
                completeQuestion();
            } else {
                if (option_d.getText().equals(answer)) {
                    option_d.setBackground(getResources().getDrawable(R.drawable.correct_answer));
                    score++;
                    nextQuestion();
                } else {
                    vibe.vibrate(500);
                    option_d.setBackground(getResources().getDrawable(R.drawable.incorrect_answer));
                    nextQuestion();
                }
            }
        });
        new CountDownTimer(200000, 1000) {
            @Override
            public void onTick(long l) {
                int pro = (int) l / 1000;
                time.setText(pro + "");
                progressBar.setProgress(pro);
            }

            @Override
            public void onFinish() {
                option_a.setEnabled(false);
                option_b.setEnabled(false);
                option_c.setEnabled(false);
                option_d.setEnabled(false);
                if (!isFinished) {
                    //   uploadResult();
                } else
                    Toast.makeText(getApplicationContext(), "Exam Ended Submit", Toast.LENGTH_SHORT).show();
            }
        }.start();
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        submit.setOnClickListener(v -> {
            move();
        });
    }

    public void nextQuestion() {
        new Handler().postDelayed(() -> {
            option_a.setBackgroundColor(Color.WHITE);
            option_b.setBackgroundColor(Color.WHITE);
            option_c.setBackgroundColor(Color.WHITE);
            option_d.setBackgroundColor(Color.WHITE);
            databaseReference = firebaseDatabase.getReference("Questions").child(level).child("Question" + i);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    examination = dataSnapshot.getValue(Exam.class);

                    if (examination == null) {
                        progressDialog.dismiss();
                        move();
                        return;
                    }
                    progressDialog.dismiss();
                    if (questions < 10)
                        questionNo.setText("0" + questions);
                    else
                        questionNo.setText(questions + "");
                    question.setText(examination.question.toString());
                    option_a.setText(examination.a.toString());
                    option_b.setText(examination.b.toString());
                    option_c.setText(examination.c.toString());
                    option_d.setText(examination.d.toString());
                    answer = examination.answer;
                    arrayList.put(examination.question, examination.answer);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                    Toast.makeText(QuizActivity.this, "Question Completed", Toast.LENGTH_SHORT).show();
                }

            });
        }, 1000);
        i++;
        questions++;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        alertDialog.setTitle("Exit Quiz");
        alertDialog.setMessage("Do you want to exit the quiz");
        alertDialog.setPositiveButton("Yes", (d, i) -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }).setNegativeButton("No", (d, i) -> d.cancel());
        alertDialog.show();
    }

    public void completeQuestion() {
        isFinished = true;
        Handler h = new Handler();
        h.postDelayed(() -> {
            progressDialog.dismiss();
            option_a.setEnabled(false);
            option_b.setEnabled(false);
            option_c.setEnabled(false);
            option_d.setEnabled(false);
            move();
        }, 1000);
    }

    public void move() {
        // uploadResult();
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", score + "");
        startActivity(intent);
        finish();
    }

//    public void uploadResult() {
//        firebaseDatabase1 = FirebaseDatabase.getInstance();
//        databaseReference1 = firebaseDatabase1.getReference("Result").child(uid);
//        databaseReference1.push().setValue(setResult);
//        SendMail sm = new SendMail(this, username, "Engineers Zone", "Thanks for Playing the Quiz. Your Score for chapter " + chapter_name + " is " + score + " Date " + date + " Play again for better result");
//        sm.execute();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
                break;
        }
        return true;
    }

}
