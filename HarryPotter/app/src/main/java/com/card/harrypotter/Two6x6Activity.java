package com.card.harrypotter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Two6x6Activity extends AppCompatActivity {
    private String userName;
    TextView tv_username, tv_p1, tv_p2, tv_p1_score, tv_p2_score, tv_p1_time, tv_p2_time;
    ImageView iv_11, iv_12, iv_13, iv_14, iv_15, iv_16, iv_21, iv_22, iv_23, iv_24, iv_25, iv_26, iv_31, iv_32, iv_33, iv_34, iv_35, iv_36, iv_41, iv_42, iv_43, iv_44, iv_45, iv_46, iv_51, iv_52, iv_53, iv_54, iv_55, iv_56, iv_61, iv_62, iv_63, iv_64, iv_65, iv_66;
    ArrayList<Integer> houseFactor = new ArrayList<>();
    ArrayList<String> housesOfCards = new ArrayList<>();
    ArrayList<Integer> powerOfCards = new ArrayList<>();
    Integer[] cardsArrayGroup1 = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111};
    Integer[] cardsArrayGroup2 = {112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    Integer[] cardsArrayGroup3 = {123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133};
    Integer[] cardsArrayGroup4 = {134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144};
    ArrayList<Bitmap> imagesBitmap = new ArrayList<>();
    Bitmap bitmap;
    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;
    int turn = 1;
    double p1 = 0, p2 = 0;
    String house;
    CountDownTimer timerP1;
    CountDownTimer timerP2;
    private MediaPlayer victory;
    private MediaPlayer timeFinish;
    private MediaPlayer congratulations;
    long durationP1 = 60001;
    long durationP2 = 60001;
    boolean isTimerFinishedP1 = false;
    boolean isTimerFinishedP2 = false;
    long currentTimeMillisP1 = 60001;
    long currentTimeMillisP2 = 60001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two6x6);
        Intent comeIntent = getIntent();
        userName = comeIntent.getStringExtra("userName");
        tv_username = (TextView) findViewById(R.id.textViewUserNameTwo6x6);
        tv_username.setText("USERNAME: " + userName);
        tv_p1 = (TextView) findViewById(R.id.tv_p1);
        tv_p2 = (TextView) findViewById(R.id.tv_p2);
        tv_p1_score = (TextView) findViewById(R.id.tv_p1_score);
        tv_p2_score = (TextView) findViewById(R.id.tv_p2_score);
        tv_p1_time = (TextView) findViewById(R.id.tv_p1_time);
        tv_p2_time = (TextView) findViewById(R.id.tv_p2_time);

        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_12 = (ImageView) findViewById(R.id.iv_12);
        iv_13 = (ImageView) findViewById(R.id.iv_13);
        iv_14 = (ImageView) findViewById(R.id.iv_14);
        iv_15 = (ImageView) findViewById(R.id.iv_15);
        iv_16 = (ImageView) findViewById(R.id.iv_16);
        iv_21 = (ImageView) findViewById(R.id.iv_21);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_23 = (ImageView) findViewById(R.id.iv_23);
        iv_24 = (ImageView) findViewById(R.id.iv_24);
        iv_25 = (ImageView) findViewById(R.id.iv_25);
        iv_26 = (ImageView) findViewById(R.id.iv_26);
        iv_31 = (ImageView) findViewById(R.id.iv_31);
        iv_32 = (ImageView) findViewById(R.id.iv_32);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_34 = (ImageView) findViewById(R.id.iv_34);
        iv_35 = (ImageView) findViewById(R.id.iv_35);
        iv_36 = (ImageView) findViewById(R.id.iv_36);
        iv_41 = (ImageView) findViewById(R.id.iv_41);
        iv_42 = (ImageView) findViewById(R.id.iv_42);
        iv_43 = (ImageView) findViewById(R.id.iv_43);
        iv_44 = (ImageView) findViewById(R.id.iv_44);
        iv_45 = (ImageView) findViewById(R.id.iv_45);
        iv_46 = (ImageView) findViewById(R.id.iv_46);
        iv_51 = (ImageView) findViewById(R.id.iv_51);
        iv_52 = (ImageView) findViewById(R.id.iv_52);
        iv_53 = (ImageView) findViewById(R.id.iv_53);
        iv_54 = (ImageView) findViewById(R.id.iv_54);
        iv_55 = (ImageView) findViewById(R.id.iv_55);
        iv_56 = (ImageView) findViewById(R.id.iv_56);
        iv_61 = (ImageView) findViewById(R.id.iv_61);
        iv_62 = (ImageView) findViewById(R.id.iv_62);
        iv_63 = (ImageView) findViewById(R.id.iv_63);
        iv_64 = (ImageView) findViewById(R.id.iv_64);
        iv_65 = (ImageView) findViewById(R.id.iv_65);
        iv_66 = (ImageView) findViewById(R.id.iv_66);

        tv_p2.setTextColor(Color.GRAY);
        tv_p2_time.setTextColor(Color.GRAY);
        tv_p2_score.setTextColor(Color.GRAY);
        tv_p1.setTextColor(Color.BLACK);
        tv_p1_time.setTextColor(Color.BLACK);
        tv_p1_score.setTextColor(Color.BLACK);

        victory = MediaPlayer.create(getApplicationContext(), R.raw.victory);
        timeFinish = MediaPlayer.create(getApplicationContext(), R.raw.timefinish);
        congratulations = MediaPlayer.create(getApplicationContext(), R.raw.congratulations);

        tv_p1_time.setText("01:00");
        tv_p2_time.setText("01:00");
        startAlertDiaolg();
        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_15.setTag("4");
        iv_16.setTag("5");
        iv_21.setTag("6");
        iv_22.setTag("7");
        iv_23.setTag("8");
        iv_24.setTag("9");
        iv_25.setTag("10");
        iv_26.setTag("11");
        iv_31.setTag("12");
        iv_32.setTag("13");
        iv_33.setTag("14");
        iv_34.setTag("15");
        iv_35.setTag("16");
        iv_36.setTag("17");
        iv_41.setTag("18");
        iv_42.setTag("19");
        iv_43.setTag("20");
        iv_44.setTag("21");
        iv_45.setTag("22");
        iv_46.setTag("23");
        iv_51.setTag("24");
        iv_52.setTag("25");
        iv_53.setTag("26");
        iv_54.setTag("27");
        iv_55.setTag("28");
        iv_56.setTag("29");
        iv_61.setTag("30");
        iv_62.setTag("31");
        iv_63.setTag("32");
        iv_64.setTag("33");
        iv_65.setTag("34");
        iv_66.setTag("35");
        Collections.shuffle(Arrays.asList(cardsArrayGroup1));
        Collections.shuffle(Arrays.asList(cardsArrayGroup2));
        Collections.shuffle(Arrays.asList(cardsArrayGroup3));
        Collections.shuffle(Arrays.asList(cardsArrayGroup4));
        Integer[] cardArrayShuffled = new Integer[36];
        //4 4 5 5
        for (int i = 0; i < 11; i++) {
            if (i < 4)
                cardArrayShuffled[i] = cardsArrayGroup1[i];
            else if (i < 8)
                cardArrayShuffled[i] = cardsArrayGroup2[i];
            else
                cardArrayShuffled[i] = cardsArrayGroup3[i];

        }
        for (int i=1;i<8;i++){
            if(i<3)
                cardArrayShuffled[i+10] = cardsArrayGroup3[i];
            else
                cardArrayShuffled[i+10] = cardsArrayGroup4[i];

        }
        for (int i = 0; i < 18; i++) {
            cardArrayShuffled[i + 18] = cardArrayShuffled[i] + 100;
        }
        Collections.shuffle(Arrays.asList(cardArrayShuffled));

        //Write check
        System.out.println("--------------");
        for (int i = 0; i < cardArrayShuffled.length; i++) {
            System.out.println(cardArrayShuffled[i]);
        }

        getCardInformation(cardArrayShuffled);
        bitmap = decodeImage(readToFile("back.txt"));

        setImageBack();
        tv_p1_score.setText("0");
        tv_p2_score.setText("0");

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
            if(isTimerFinishedP1 &&isTimerFinishedP2)
            {
                showResult();
                setImageEnabled(false);
                setImageBack();
            } else

            {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_11, theCard, cardArrayShuffled);
            }


        }
    });


        iv_12.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_12, theCard, cardArrayShuffled);
        }
    }
    });

        iv_13.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_13, theCard, cardArrayShuffled);
        }
    }
    });

        iv_14.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_14, theCard, cardArrayShuffled);
        }
    }
    });

        iv_15.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_15, theCard, cardArrayShuffled);
        }

    }
    });

        iv_16.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_16, theCard, cardArrayShuffled);
        }
    }
    });

        iv_21.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_21, theCard, cardArrayShuffled);
        }
    }
    });
        iv_22.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_22, theCard, cardArrayShuffled);
        }
    }
    });
        iv_23.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_23, theCard, cardArrayShuffled);
        }

    }
    });

        iv_24.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_24, theCard, cardArrayShuffled);
        }

    }
    });

        iv_25.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_25, theCard, cardArrayShuffled);
        }

    }
    });

        iv_26.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_26, theCard, cardArrayShuffled);
        }

    }
    });

        iv_31.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_31, theCard, cardArrayShuffled);
        }
    }
    });

        iv_32.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_32, theCard, cardArrayShuffled);
        }

    }
    });

        iv_33.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_33, theCard, cardArrayShuffled);
        }

    }
    });

        iv_34.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_34, theCard, cardArrayShuffled);
        }


    }
    });

        iv_35.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_35, theCard, cardArrayShuffled);
        }

    }
    });

        iv_36.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_36, theCard, cardArrayShuffled);
        }

    }
    });

        iv_41.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_41, theCard, cardArrayShuffled);
        }
    }
    });

        iv_42.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_42, theCard, cardArrayShuffled);
        }

    }
    });

        iv_43.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_43, theCard, cardArrayShuffled);
        }
    }
    });

        iv_44.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_44, theCard, cardArrayShuffled);
        }
    }
    });

        iv_45.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_45, theCard, cardArrayShuffled);
        }
    }
    });

        iv_46.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_46, theCard, cardArrayShuffled);
        }
    }
    });

        iv_51.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_51, theCard, cardArrayShuffled);
        }

    }
    });

        iv_52.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_52, theCard, cardArrayShuffled);
        }

    }
    });

        iv_53.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){


        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_53, theCard, cardArrayShuffled);
        }
    }
    });

        iv_54.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_54, theCard, cardArrayShuffled);
        }
    }
    });

        iv_55.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_55, theCard, cardArrayShuffled);
        }
    }
    });

        iv_56.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_56, theCard, cardArrayShuffled);
        }
    }
    });

        iv_61.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_61, theCard, cardArrayShuffled);
        }

    }
    });

        iv_62.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){

        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_62, theCard, cardArrayShuffled);
        }

    }
    });

        iv_63.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){


        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_63, theCard, cardArrayShuffled);
        }
    }
    });

        iv_64.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_64, theCard, cardArrayShuffled);
        }
    }
    });

        iv_65.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_65, theCard, cardArrayShuffled);
        }
    }
    });

        iv_66.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        if (isTimerFinishedP1 && isTimerFinishedP2) {
            showResult();
            setImageEnabled(false);
            setImageBack();
        } else {
            int theCard = Integer.parseInt((String) view.getTag());
            doStuff(iv_66, theCard, cardArrayShuffled);
        }
    }
    });

}

    public void startTimerP1(long durationP1) {
        timerP1 = new CountDownTimer(durationP1, 1000) {
            @Override
            public void onTick(long l) {
                //convert milisecond to minute and second
                // System.out.println(l);
                String sDuration = String.format(Locale.ENGLISH, "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(l), TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                currentTimeMillisP1 = l;
                //   System.out.println(currentTimeMillisP1);
                tv_p1_time.setText(sDuration);

            }

            @Override
            public void onFinish() {
                if (SongSettings.isMediaPlayerSoundOnOff())
                    timeFinish.start();
                isTimerFinishedP1 = true;
                if (isTimerFinishedP2) {
                    showResult();
                } else
                    Toast.makeText(Two6x6Activity.this, "LAST CHANCE P1!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    public void startTimerP2(long durationP2) {

        timerP2 = new CountDownTimer(durationP2, 1000) {
            @Override
            public void onTick(long l) {
                //convert milisecond to minute and second

                String sDuration = String.format(Locale.ENGLISH, "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(l), TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                currentTimeMillisP2 = l;
                tv_p2_time.setText(sDuration);

            }

            @Override
            public void onFinish() {
                if (SongSettings.isMediaPlayerSoundOnOff())
                    timeFinish.start();
                isTimerFinishedP2 = true;

                if (isTimerFinishedP1) {
                    showResult();
                } else {
                    Toast.makeText(Two6x6Activity.this, "LAST CHANCE P2!", Toast.LENGTH_SHORT).show();
                }

            }
        }.start();
    }


    public void startAlertDiaolg() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Two6x6Activity.this);
        alertDialogBuilder.setTitle("READY!");
        alertDialogBuilder.setMessage("first time out player take a last chance!");
        alertDialogBuilder.setCancelable(false).setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                startTimerP1(durationP1);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void showResult() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Two6x6Activity.this);
        alertDialogBuilder.setTitle("GAME OVER");
        alertDialogBuilder.setMessage("\nSCORE P1: " + String.format("%.2f", p1) + "\nRemaining Time : " + tv_p1_time.getText() + "\n\n" + "SCORE P2: " + String.format("%.2f", p2) + "\nRemaining Time : " + tv_p2_time.getText()).setCancelable(false).setPositiveButton(" NEW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Two6x6Activity.this, Two6x6Activity.class);
                intent.putExtra("userName",userName);
                startActivity(intent);
                finish();
            }
        }).setNegativeButton("EXİT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                congratulations.stop();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private synchronized void doStuff(ImageView iv, int card, Integer[] cardArrayShuffled) {
        //set the correct image to the imageview
        iv.setImageBitmap(imagesBitmap.get(card));
        //check which image is selected and save it to temporary variable
        if (cardNumber == 1) {
            cardNumber = 2;
            clickedFirst = card;
            iv.setEnabled(false);
            firstCard = cardArrayShuffled[card];
            if (firstCard > 200) {
                firstCard = firstCard - 100;
            }


        } else if (cardNumber == 2) {
            setImageEnabled(false);
            cardNumber = 1;
            secondCard = cardArrayShuffled[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }

            clickedSecond = card;


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // check if the selected images are equal
                    //  setImageEnabled();
                    calculate();
                }
            }, 500);

        }

    }


    private synchronized void calculate() {
        //if images are equal remove  them and add point
        if (firstCard == secondCard) {
            if (SongSettings.isMediaPlayerSoundOnOff())
                victory.start();

            if (clickedFirst == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4) {
                iv_15.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                iv_16.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10){
                iv_25.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11){
                iv_26.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 12){
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 13){
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 14){
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 15){
                iv_34.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 16) {
                iv_35.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 17) {
                iv_36.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 18) {
                iv_41.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 19) {
                iv_42.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 20) {
                iv_43.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 21) {
                iv_44.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 22) {
                iv_45.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 23) {
                iv_46.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 24) {
                iv_51.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 25) {
                iv_52.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 26) {
                iv_53.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 27) {
                iv_54.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 28) {
                iv_55.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 29) {
                iv_56.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 30) {
                iv_61.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 31) {
                iv_62.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 32) {
                iv_63.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 33) {
                iv_64.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 34) {
                iv_65.setVisibility(View.INVISIBLE);
            }else if (clickedFirst == 35) {
                iv_66.setVisibility(View.INVISIBLE);
            }


            if (clickedSecond == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4) {
                iv_15.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                iv_16.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9) {
                iv_24.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 10) {
                iv_25.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 11) {
                iv_26.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 12) {
                iv_31.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 13) {
                iv_32.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 14) {
                iv_33.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 15) {
                iv_34.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 16) {
                iv_35.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 17) {
                iv_36.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 18) {
                iv_41.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 19) {
                iv_42.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 20) {
                iv_43.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 21) {
                iv_44.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 22) {
                iv_45.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 23) {
                iv_46.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 24) {
                iv_51.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 25) {
                iv_52.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 26) {
                iv_53.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 27) {
                iv_54.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 28) {
                iv_55.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 29) {
                iv_56.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 30) {
                iv_61.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 31) {
                iv_62.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 32) {
                iv_63.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 33) {
                iv_64.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 34) {
                iv_65.setVisibility(View.INVISIBLE);
            }else if (clickedSecond == 35) {
                iv_66.setVisibility(View.INVISIBLE);
            }


            // add points to the correct player
            if (turn == 1) {
                p1 = p1 + (2 * powerOfCards.get(clickedFirst) * houseFactor.get(clickedFirst));
                tv_p1_score.setText(String.format("%.2f", p1));
            } else if (turn == 2) {
                p2 = p2 + (2 * powerOfCards.get(clickedFirst) * houseFactor.get(clickedFirst));
                tv_p2_score.setText(String.format("%.2f", p2));
            }


        } else {
            //Not same card but we look at house
            //chance the player turn
            if (turn == 1) {
                if (housesOfCards.get(clickedFirst).equalsIgnoreCase(housesOfCards.get(clickedSecond))) {
                    p1 = p1 - ((powerOfCards.get(clickedFirst) + powerOfCards.get(clickedSecond)) / houseFactor.get(clickedFirst));
                    tv_p1_score.setText(String.format("%.2f", p1));
                } else if (!housesOfCards.get(clickedFirst).equalsIgnoreCase(housesOfCards.get(clickedSecond))) {
                    p1 = p1 - ((((double) (powerOfCards.get(clickedFirst) + powerOfCards.get(clickedSecond)) )/ 2) * houseFactor.get(clickedFirst) * houseFactor.get(clickedSecond));
                    tv_p1_score.setText(String.format("%.2f", p1));
                }
                //System.out.println("durationp1" +durationP1+"currentp1"+currentTimeMillisP1);
                durationP1 = currentTimeMillisP1;
                // System.out.println( " new durap1"+durationP1);

                if (!isTimerFinishedP2) {
                    timerP1.cancel();
                    turn = 2;
                    durationP2 = currentTimeMillisP2;
                    startTimerP2(durationP2);
                    tv_p1.setTextColor(Color.GRAY);
                    tv_p1_time.setTextColor(Color.GRAY);
                    tv_p1_score.setTextColor(Color.GRAY);
                    tv_p2.setTextColor(Color.BLACK);
                    tv_p2_time.setTextColor(Color.BLACK);
                    tv_p2_score.setTextColor(Color.BLACK);
                }
            } else if (turn == 2) {
                if (housesOfCards.get(clickedFirst).equalsIgnoreCase(housesOfCards.get(clickedSecond))) {
                    p2 = p2 - ((powerOfCards.get(clickedFirst) + powerOfCards.get(clickedSecond)) / houseFactor.get(clickedFirst));
                    tv_p2_score.setText(String.format("%.2f", p2));
                } else if (!housesOfCards.get(clickedFirst).equalsIgnoreCase(housesOfCards.get(clickedSecond))) {
                    p2 = p2 - ((((double) (powerOfCards.get(clickedFirst) + powerOfCards.get(clickedSecond)) / 2) * houseFactor.get(clickedFirst) * houseFactor.get(clickedSecond)));
                    tv_p2_score.setText(String.format("%.2f", p2));
                }

                durationP2 = currentTimeMillisP2;

                if (!isTimerFinishedP1) {
                    timerP2.cancel();
                    turn = 1;
                    durationP1 = currentTimeMillisP1;
                    startTimerP1(durationP1);
                    tv_p2.setTextColor(Color.GRAY);
                    tv_p2_time.setTextColor(Color.GRAY);
                    tv_p2_score.setTextColor(Color.GRAY);
                    tv_p1.setTextColor(Color.BLACK);
                    tv_p1_time.setTextColor(Color.BLACK);
                    tv_p1_score.setTextColor(Color.BLACK);
                }
            }


            setImageBack();


        }
        setImageEnabled(true);

        //check if the game is over
        if (isTimerFinishedP2 && isTimerFinishedP1)
            showResult();

        checkEnd();
    }

    private void setImageEnabled(Boolean b) {
        iv_11.setEnabled(b);
        iv_12.setEnabled(b);
        iv_13.setEnabled(b);
        iv_14.setEnabled(b);
        iv_15.setEnabled(b);
        iv_16.setEnabled(b);
        iv_21.setEnabled(b);
        iv_22.setEnabled(b);
        iv_23.setEnabled(b);
        iv_24.setEnabled(b);
        iv_25.setEnabled(b);
        iv_26.setEnabled(b);
        iv_31.setEnabled(b);
        iv_32.setEnabled(b);
        iv_33.setEnabled(b);
        iv_34.setEnabled(b);
        iv_35.setEnabled(b);
        iv_36.setEnabled(b);
        iv_41.setEnabled(b);
        iv_42.setEnabled(b);
        iv_43.setEnabled(b);
        iv_44.setEnabled(b);
        iv_45.setEnabled(b);
        iv_46.setEnabled(b);
        iv_51.setEnabled(b);
        iv_52.setEnabled(b);
        iv_53.setEnabled(b);
        iv_54.setEnabled(b);
        iv_55.setEnabled(b);
        iv_56.setEnabled(b);
        iv_61.setEnabled(b);
        iv_62.setEnabled(b);
        iv_63.setEnabled(b);
        iv_64.setEnabled(b);
        iv_65.setEnabled(b);
        iv_66.setEnabled(b);
    }

    private void setImageBack() {
        iv_11.setImageBitmap(bitmap);
        iv_12.setImageBitmap(bitmap);
        iv_13.setImageBitmap(bitmap);
        iv_14.setImageBitmap(bitmap);
        iv_15.setImageBitmap(bitmap);
        iv_16.setImageBitmap(bitmap);
        iv_21.setImageBitmap(bitmap);
        iv_22.setImageBitmap(bitmap);
        iv_23.setImageBitmap(bitmap);
        iv_24.setImageBitmap(bitmap);
        iv_25.setImageBitmap(bitmap);
        iv_26.setImageBitmap(bitmap);
        iv_31.setImageBitmap(bitmap);
        iv_32.setImageBitmap(bitmap);
        iv_33.setImageBitmap(bitmap);
        iv_34.setImageBitmap(bitmap);
        iv_35.setImageBitmap(bitmap);
        iv_36.setImageBitmap(bitmap);
        iv_41.setImageBitmap(bitmap);
        iv_42.setImageBitmap(bitmap);
        iv_43.setImageBitmap(bitmap);
        iv_44.setImageBitmap(bitmap);
        iv_45.setImageBitmap(bitmap);
        iv_46.setImageBitmap(bitmap);
        iv_51.setImageBitmap(bitmap);
        iv_52.setImageBitmap(bitmap);
        iv_53.setImageBitmap(bitmap);
        iv_54.setImageBitmap(bitmap);
        iv_55.setImageBitmap(bitmap);
        iv_56.setImageBitmap(bitmap);
        iv_61.setImageBitmap(bitmap);
        iv_62.setImageBitmap(bitmap);
        iv_63.setImageBitmap(bitmap);
        iv_64.setImageBitmap(bitmap);
        iv_65.setImageBitmap(bitmap);
        iv_66.setImageBitmap(bitmap);
    }


    private synchronized void checkEnd() {
        if (iv_11.getVisibility() == View.INVISIBLE && iv_12.getVisibility() == View.INVISIBLE && iv_13.getVisibility() == View.INVISIBLE && iv_14.getVisibility() == View.INVISIBLE
                && iv_21.getVisibility() == View.INVISIBLE && iv_22.getVisibility() == View.INVISIBLE && iv_23.getVisibility() == View.INVISIBLE && iv_24.getVisibility() == View.INVISIBLE
                && iv_31.getVisibility() == View.INVISIBLE && iv_32.getVisibility() == View.INVISIBLE && iv_33.getVisibility() == View.INVISIBLE && iv_34.getVisibility() == View.INVISIBLE
                && iv_41.getVisibility() == View.INVISIBLE && iv_42.getVisibility() == View.INVISIBLE && iv_43.getVisibility() == View.INVISIBLE && iv_44.getVisibility() == View.INVISIBLE) {
            if (SongSettings.isMediaPlayerSoundOnOff()) {
                victory.stop();
                congratulations.start();
            }
            timerP1.cancel();
            timerP2.cancel();
            showResult();
        }
    }

    private void getCardInformation(Integer[] cardArrayShuffled) {
        for (int i = 0; i < cardArrayShuffled.length; i++) {
            int id = cardArrayShuffled[i];
            if ((101 <= id && id <= 111) || (201 <= id && id <= 211)) {
                house = "gryffindor";
                if (id > 200) id = id - 100;
                imagesBitmap.add(getImageBitmap(id));
                houseFactor.add(2);
                housesOfCards.add(house);
                powerOfCards.add(getPowerRead(id));
            } else if ((112 <= id && id <= 122) || (212 <= id && id <= 222)) {
                house = "ravenclaw";
                if (id > 200) id = id - 100;
                imagesBitmap.add(getImageBitmap(id));
                houseFactor.add(1);
                housesOfCards.add(house);
                powerOfCards.add(getPowerRead(id));
            } else if ((123 <= id && id <= 133) || (223 <= id && id <= 233)) {
                house = "slytherin";
                if (id > 200) id = id - 100;
                imagesBitmap.add(getImageBitmap(id));
                houseFactor.add(2);
                housesOfCards.add(house);
                powerOfCards.add(getPowerRead(id));
            } else if ((134 <= id && id <= 144) || (234 <= id && id <= 244)) {
                house = "hufflepuff";
                if (id > 200) id = id - 100;
                imagesBitmap.add(getImageBitmap(id));
                houseFactor.add(1);
                housesOfCards.add(house);
                powerOfCards.add(getPowerRead(id));
            }
        }

    }

    private Bitmap getImageBitmap(int id) {
        String imageBase64 = readToFile(id + ".txt");

        return decodeImage(imageBase64);
    }

    private int getPowerRead(int id) {
        String score = readToFile(id + "score.txt");
        return Integer.parseInt(score);
    }


    public String readToFile(String fileName) {
        File path = getApplicationContext().getFilesDir();
        File readFrom = new File(path, fileName);
        byte[] content = new byte[(int) readFrom.length()];
        try {
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

    public Bitmap decodeImage(String base64) {
        byte[] imageBytes = Base64.decode((base64), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        //System.out.println(decodedImage);
        return decodedImage;

    }

    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Confirm Exit ");
        alert.setMessage("Do you want to quit the game?");
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
                finish();
            }
        });
        alert.show();

    }



}