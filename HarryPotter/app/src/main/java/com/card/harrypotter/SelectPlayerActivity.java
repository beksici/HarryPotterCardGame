package com.card.harrypotter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.paperdb.Paper;

public class SelectPlayerActivity extends AppCompatActivity {
private String userName;
private TextView txtUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);
        txtUserName=(TextView)findViewById(R.id.textViewUserNameSelectField);
        Intent ComeIntent=getIntent();
        userName=ComeIntent.getStringExtra("userName");
        txtUserName.setText(userName);

    }

    public void buttonSignOut(View v){

        Paper.book().destroy();
        Intent intent = new Intent(SelectPlayerActivity.this,DeleteAllDataActivity.class);
        startActivity(intent);
        finish();
    }

    public void buttonSinglePlayer(View v){

        Intent intent1= new Intent(SelectPlayerActivity.this,LevelsSinglePlayerActivity.class);
        intent1.putExtra("userName", userName);
        //finish();
        startActivity(intent1);


    }
public void  buttonTwoPlayer(View v){
    Intent intent2= new Intent(SelectPlayerActivity.this,LevelsTwoPlayerActivity.class);
    intent2.putExtra("userName", userName);
   // finish();
    startActivity(intent2);
}

    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(" Harry Potter Card Game ");
        alert.setMessage("Are you sure to go main menu?");
        alert.setCancelable(false);//kapatılamaz
        alert.setIcon(R.mipmap.ic_launcher);//iconlarımız mipmap klasörü içindedir
        alert.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();;

            }
        });
        alert.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(SelectPlayerActivity.this, DeleteAllDataActivity.class);
                startActivity(intent);
                finish();
            }
        });
        alert.show();

    }
}