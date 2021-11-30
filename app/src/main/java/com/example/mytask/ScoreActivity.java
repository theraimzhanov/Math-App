package com.example.mytask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.mytask.databinding.ActivityScoreBinding;

public class ScoreActivity extends AppCompatActivity {
private ActivityScoreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
      if (intent != null && intent.hasExtra("result")&&intent.hasExtra("countOfQuestion")){
        int  countQuestion =intent.getIntExtra("countOfQuestion",0);
         int result =intent.getIntExtra("result",0);
            String question = String.format("Общая количества вопросы : %s",countQuestion);
            String score = String.format("Ваш результат : %s",result);
            binding.textViewResult.setText(score);
            binding.textViewCountQuestion.setText(question);
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    int max =  preferences.getInt("max",0);
      String record = String.format("Ваш рекорд : %s",max);
      binding.textViewRecord.setText(record);
    }

    public void onClickNewGame(View view) {
         Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
         startActivity(intent);
         finish();
    }
}