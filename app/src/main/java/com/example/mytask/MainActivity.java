package com.example.mytask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytask.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String question;
    private int rightAnswer,rightAnswerPosition;
    private boolean isPositive;
    private int min = 5;
    private int max = 30;
     private TextView textView0,textView1,textView2,textView3;
     private ArrayList<TextView> options = new ArrayList<>();
     private int countOfQuestion = 0;
     private int countOfRightAnswer = 0;
     private boolean gameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    /*//    preferences.edit().putString("name","Nursultsan").apply();
     //   Toast.makeText(this, preferences.getString("name","unknow"), Toast.LENGTH_SHORT).show();*/
    /*    textViewTimer = findViewById(R.id.textViewTimer);
        CountDownTimer  timer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds =(int) (millisUntilFinished/1000);
                textViewTimer.setText(Integer.toString(seconds)); }
            @Override
            public void onFinish() {
            }
        };
 timer.start();*/
        textView0 = findViewById(R.id.textView0);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        options.add(textView0);
        options.add(textView1);
        options.add(textView2);
        options.add(textView3);

  playNext();
  CountDownTimer timer = new CountDownTimer(60000,1000) {
      @Override
      public void onTick(long millisUntilFinished) {
         binding.textViewTimer.setText(getTime(millisUntilFinished));
         if (millisUntilFinished  <= 10000){
             binding.textViewTimer.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
          }
      }

      @Override
      public void onFinish() {
          SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
          int max = preferences.getInt("max",0);
          if (countOfRightAnswer >= max){
              preferences.edit().putInt("max",countOfRightAnswer).apply();
          }
        gameOver = true;
          Intent intent = new Intent(MainActivity.this,ScoreActivity.class);
             intent.putExtra("result",countOfRightAnswer);
             intent.putExtra("countOfQuestion",countOfQuestion);
             startActivity(intent);
      }
  };
  timer.start();
    }
    private void playNext(){
        generateQuestion();
        for (int i = 0;i<options.size();i++){
            if (i == rightAnswerPosition){
                options.get(i).setText(Integer.toString(rightAnswer));
            } else{
                options.get(i).setText(Integer.toString(generateWrongAnswer()));
            }
        }
        String score = String.format("%s/%s",countOfQuestion,countOfRightAnswer);
        binding.textViewScore.setText(score);
    }
 private void generateQuestion(){
        int a = (int)(Math.random()*(max-min+1)+min);
        int b = (int)(Math.random()*(max-min+1)+min);
        int mark = (int)(Math.random()*2);
        isPositive = mark ==1;
        if (isPositive){
            rightAnswer = a + b;
            question = String.format("%s + %s",a,b);
        }else{
            rightAnswer = a - b;
            question = String.format("%s - %s",a,b);
        }
        binding.textViewQuestion.setText(question);
   rightAnswerPosition = (int) (Math.random()*4);
 }
 private int generateWrongAnswer(){
     int result;
        do {
             result = (int)(Math.random()*max*2+1)-(max - min);
        }while (result == rightAnswer);
         return result;
 }
  private String getTime(long millis){
int seconds = (int) (millis/1000);
int minutes =seconds/60;
int second = seconds%60;
return String.format(Locale.getDefault(),"%02d:%02d",minutes,second);
  }
    public void onClickAnswer(View view) {
         if (!gameOver){
        TextView textView = (TextView) view;
        String answer = textView.getText().toString();
        int chooseAnswer = Integer.parseInt(answer);
        if (chooseAnswer == rightAnswer){
            Toast.makeText(this, "Правильно", Toast.LENGTH_SHORT).show();
            countOfRightAnswer++;
        } else {
            Toast.makeText(this, "Не правильно", Toast.LENGTH_SHORT).show();
        }
        countOfQuestion++;
        playNext();
    }
    }
}