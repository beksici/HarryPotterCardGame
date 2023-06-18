package com.card.harrypotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class ProgressBarActivity extends AppCompatActivity {
    String house, imageString1, imageString2,score,userName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myReference = database.getReference();
    ProgressBar pb;
    int counter = 0;
    String fromCameActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        Intent comeIntent = getIntent();
        userName=comeIntent.getStringExtra("userName");
        fromCameActivity=comeIntent.getStringExtra("whichSelectedActivity");
        getBackImageInFirebase();


for(int i=101;i<145;i++){
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myReference = database.getReference();
    if( 101<=i&&i<=111) {
        house = "gryffindor";
        getImageStrings(i, house,myReference);
    }else if(112 <= i && i <= 122){
        house = "ravenclaw";
        getImageStrings(i, house,myReference);
    }else if(123 <= i && i <= 133){
        house = "slytherin";
        getImageStrings(i, house,myReference);
    }else if(134 <= i && i <= 144){
        house = "hufflepuff";
        getImageStrings(i, house,myReference);
    }

}


       progressBar();
    }

    private synchronized void  getBackImageInFirebase(){
        myReference.child("cards").child("back").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    if (shot.getKey().equalsIgnoreCase("imageString1")) {
                        //System.out.println( shot.getKey()+ shot.getValue());
                        imageString1 = shot.getValue().toString();
                        //System.out.println(imageString1.length());
                    }
                    if (shot.getKey().equalsIgnoreCase("imageString2")) {
                      //  System.out.println( shot.getKey()+ shot.getValue());
                        imageString2 = shot.getValue().toString();
                       // System.out.println(imageString2.length());
                    }}
                writeToFile("back.txt",(imageString1+imageString2));

            }
        });
    }
    private synchronized void getImageStrings(int id, String house,DatabaseReference myReference) {
        myReference.child("cards").child(house).child(String.valueOf(id)).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    if (shot.getKey().equalsIgnoreCase("imageString1")) {
                       // System.out.println(id);
                      //  System.out.println( shot.getKey()+ shot.getValue());
                        imageString1 = shot.getValue().toString();
                     //   System.out.println(imageString1.length());
                    }
                    if (shot.getKey().equalsIgnoreCase("imageString2")) {
                      //  System.out.println( shot.getKey()+ shot.getValue());
                       imageString2 = shot.getValue().toString();
                      //  System.out.println(imageString2.length());
                    }
                    if(shot.getKey().equalsIgnoreCase("score")){
                       score=shot.getValue().toString();
                    }
                }
                writeToFile(id+".txt",(imageString1+imageString2));
                writeToFile(id+"score.txt",score);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
           Toast.makeText(ProgressBarActivity.this,"fail",Toast.LENGTH_SHORT).show();
                System.out.println("fail oldu");
            }
        });

    }


    public void writeToFile(String fileName, String content){
        File path =getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path,fileName));
            writer.write(content.getBytes(StandardCharsets.UTF_8));
            //Toast.makeText(this,getApplicationContext().getFilesDir().toString(),Toast.LENGTH_SHORT).show();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public String readToFile(String fileName){
        File path = getApplicationContext().getFilesDir();
        File readFrom= new File(path,fileName);
        byte[] content = new byte[(int)readFrom.length()];
        try  {
            FileInputStream stream = new FileInputStream(readFrom);
            stream.read(content);
            return new String(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }


    }


    public void progressBar() {
        pb = (ProgressBar) findViewById(R.id.progressBar);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter += 4;
                pb.setProgress(counter);

                if (counter > 100) {
                    t.cancel();
                    if (fromCameActivity.equalsIgnoreCase("1")) {
                        Intent intent = new Intent(ProgressBarActivity.this, Single2x2Activity.class);
                        intent.putExtra("userName", userName);
                        finish();
                        startActivity(intent);
                    } else if (fromCameActivity.equalsIgnoreCase("2")) {
                        Intent intent = new Intent(ProgressBarActivity.this, Single4x4Activity.class);
                        intent.putExtra("userName", userName);
                        finish();
                        startActivity(intent);
                    }else if(fromCameActivity.equalsIgnoreCase("3")){
                        Intent intent = new Intent(ProgressBarActivity.this, Single6x6Activity.class);
                        intent.putExtra("userName", userName);
                        finish();
                        startActivity(intent);
                    }else if (fromCameActivity.equalsIgnoreCase("4")) {
                        Intent intent = new Intent(ProgressBarActivity.this, Two4x4Activity.class);
                        intent.putExtra("userName", userName);
                        finish();
                        startActivity(intent);
                    }else if(fromCameActivity.equalsIgnoreCase("5")){
                        Intent intent = new Intent(ProgressBarActivity.this, Two6x6Activity.class);
                        intent.putExtra("userName", userName);
                        finish();
                        startActivity(intent);
                    }
                }
            }
        };

        t.schedule(tt, 0, 100);


    }



}