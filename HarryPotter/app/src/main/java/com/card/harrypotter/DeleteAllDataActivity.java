package com.card.harrypotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class DeleteAllDataActivity extends AppCompatActivity {
    ProgressBar pb;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_all_data);
        deleteAllData();
        progressBar();
    }

    public void deleteData(String fileName) {
        File path = getFilesDir();
        File file = new File(path, fileName);
        file.delete();

    }

    public void deleteAllData() {
        for (int i = 101; i < 145; i++) {

            deleteData(i + ".txt");
            deleteData(i + "score.txt");
        }
        deleteData("back.txt");

    }

    public void progressBar() {
        pb = (ProgressBar) findViewById(R.id.progressBar);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter += 10;
                pb.setProgress(counter);

                if (counter == 100) {
                    t.cancel();
                    finish();
                }
            }
        };

        t.schedule(tt, 0, 100);


    }
}