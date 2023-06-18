package com.card.harrypotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LevelsSinglePlayerActivity extends AppCompatActivity {
private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels_single_player);
        Intent comeIntent = getIntent();
        userName=comeIntent.getStringExtra("userName");



    }
    public void buttonSingle2x2(View v){
        Intent intent = new Intent(LevelsSinglePlayerActivity.this,ProgressBarActivity.class);
        intent.putExtra("userName",userName);
        String t="1";
        intent.putExtra("whichSelectedActivity",t);
        finish();
        startActivity(intent);
    }
    public void buttonSingle4x4(View v){
        Intent intent = new Intent(LevelsSinglePlayerActivity.this,ProgressBarActivity.class);
        intent.putExtra("userName",userName);
        String t="2";
        intent.putExtra("whichSelectedActivity",t);
        finish();
        startActivity(intent);

    }
    public void buttonSingle6x6(View v){
        Intent intent = new Intent(LevelsSinglePlayerActivity.this,ProgressBarActivity.class);
        intent.putExtra("userName",userName);
        String t="3";
        intent.putExtra("whichSelectedActivity",t);
        finish();
        startActivity(intent);

    }
}