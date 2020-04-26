package com.alfredtechnologies;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.group_choices)
    RadioGroup group;

    @BindView(R.id.choice_a)
    RadioButton toggleA;

    @BindView(R.id.choice_b)
    RadioButton toggleB;

    @BindView(R.id.choice_c)
    RadioButton toggleC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.choice_a:
                        intent.putExtra("level", toggleA.getText().toString().trim());
                        startActivity(intent);
                        finish();
                        toggleA.setBackground(getDrawable(R.drawable.checked_grade_background1));
                        toggleA.setTextColor(Color.WHITE);
                        toggleB.setBackground(getDrawable(R.drawable.checked_grade_background));
                        toggleB.setTextColor(Color.BLACK);
                        toggleC.setBackground(getDrawable(R.drawable.checked_grade_background));
                        toggleC.setTextColor(Color.BLACK);
                        break;
                    case R.id.choice_b:
                        intent.putExtra("level", toggleB.getText().toString().trim());
                        startActivity(intent);
                        finish();
                        toggleA.setBackground(getDrawable(R.drawable.checked_grade_background));
                        toggleA.setTextColor(Color.BLACK);
                        toggleC.setBackground(getDrawable(R.drawable.checked_grade_background));
                        toggleC.setTextColor(Color.BLACK);
                        toggleB.setBackground(getDrawable(R.drawable.checked_grade_background1));
                        toggleB.setTextColor(Color.WHITE);
                        break;
                    case R.id.choice_c:
                        intent.putExtra("level", toggleC.getText().toString().trim());
                        startActivity(intent);
                        finish();
                        toggleA.setBackground(getDrawable(R.drawable.checked_grade_background));
                        toggleA.setTextColor(Color.BLACK);
                        toggleC.setBackground(getDrawable(R.drawable.checked_grade_background1));
                        toggleC.setTextColor(Color.WHITE);
                        toggleB.setBackground(getDrawable(R.drawable.checked_grade_background));
                        toggleB.setTextColor(Color.BLACK);
                        break;

                }
            }
        });
    }
}
