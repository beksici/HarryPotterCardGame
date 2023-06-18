package com.card.harrypotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LevelsTwoPlayerActivity extends AppCompatActivity {
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_two_player);

        Intent comeIntent = getIntent();
        userName=comeIntent.getStringExtra("userName");
    }

    public void buttonTwo4x4(View v){
        Intent intent = new Intent(LevelsTwoPlayerActivity.this,ProgressBarActivity.class);
        intent.putExtra("userName",userName);
        String t="4";
        intent.putExtra("whichSelectedActivity",t);
        finish();
        startActivity(intent);

    }
    public void buttonTwo6x6(View v){
        Intent intent = new Intent(LevelsTwoPlayerActivity.this,ProgressBarActivity.class);
        intent.putExtra("userName",userName);
        String t="5";
        intent.putExtra("whichSelectedActivity",t);
        finish();
        startActivity(intent);

    }
}