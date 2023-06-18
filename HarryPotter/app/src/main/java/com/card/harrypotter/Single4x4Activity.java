package com.card.harrypotter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Single4x4Activity extends AppCompatActivity {
    private String userName;
    TextView tv_username, tv_score, tv_time;
    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34, iv_41, iv_42, iv_43, iv_44;
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
    double playerPoints = 0;
    String house;
    CountDownTimer timer;
    int currentTime = 0;
    int getSecond = 45;
    private MediaPlayer victory;
    private MediaPlayer timeFinish;
    private MediaPlayer congratulations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single4x4);
        Intent comeIntent = getIntent();
        userName = comeIntent.getStringExtra("userName");
        tv_username = (TextView) findViewById(R.id.textViewUserNameS4x4);
        tv_username.setText("USERNAME: " + userName);
        tv_score = (TextView) findViewById(R.id.textViewScoreS4x4);
        tv_time = (TextView) findViewById(R.id.textViewTimeS4x4Field);

        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_12 = (ImageView) findViewById(R.id.iv_12);
        iv_13 = (ImageView) findViewById(R.id.iv_13);
        iv_14 = (ImageView) findViewById(R.id.iv_14);
        iv_21 = (ImageView) findViewById(R.id.iv_21);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_23 = (ImageView) findViewById(R.id.iv_23);
        iv_24 = (ImageView) findViewById(R.id.iv_24);
        iv_31 = (ImageView) findViewById(R.id.iv_31);
        iv_32 = (ImageView) findViewById(R.id.iv_32);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_34 = (ImageView) findViewById(R.id.iv_34);
        iv_41 = (ImageView) findViewById(R.id.iv_41);
        iv_42 = (ImageView) findViewById(R.id.iv_42);
        iv_43 = (ImageView) findViewById(R.id.iv_43);
        iv_44 = (ImageView) findViewById(R.id.iv_44);
        victory = MediaPlayer.create(getApplicationContext(), R.raw.victory);
        timeFinish = MediaPlayer.create(getApplicationContext(), R.raw.timefinish);
        congratulations = MediaPlayer.create(getApplicationContext(), R.raw.congratulations);
        long duration = 45001;
        tv_time.setText("00:45");
        startAlertDiaolg();
        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");
        iv_41.setTag("12");
        iv_42.setTag("13");
        iv_43.setTag("14");
        iv_44.setTag("15");
        Collections.shuffle(Arrays.asList(cardsArrayGroup1));
        Collections.shuffle(Arrays.asList(cardsArrayGroup2));
        Collections.shuffle(Arrays.asList(cardsArrayGroup3));
        Collections.shuffle(Arrays.asList(cardsArrayGroup4));
        Integer[] cardArrayShuffled = new Integer[16];
        for (int i = 0; i < 8; i++) {
            if (i < 2)
                cardArrayShuffled[i] = cardsArrayGroup1[i];
            else if (i < 4)
                cardArrayShuffled[i] = cardsArrayGroup2[i];
            else if (i < 6)
                cardArrayShuffled[i] = cardsArrayGroup3[i];
            else cardArrayShuffled[i] = cardsArrayGroup4[i];
        }
        for (int i = 0; i < 8; i++) {
            cardArrayShuffled[i + 8] = cardArrayShuffled[i] + 100;
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
        tv_score.setText("SCORE: 0");


        timer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                //convert milisecond to minute and second

                String sDuration = String.format(Locale.ENGLISH, "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(l), TimeUnit.MILLISECONDS.toSeconds(l) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                tv_time.setText(sDuration);
                getSecond--;
                currentTime++;

            }

            @Override
            public void onFinish() {
                if (SongSettings.isMediaPlayerSoundOnOff())
                    timeFinish.start();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Single4x4Activity.this);
                alertDialogBuilder.setTitle("GAME OVER");
                alertDialogBuilder.setMessage("\nSCORE: " + String.format("%.2f", playerPoints) + "\nRemaining Time : " + tv_time.getText() + "\nTime :" + currentTime).setCancelable(false).setPositiveButton(" NEW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Single4x4Activity.this, Single4x4Activity.class);
                        intent.putExtra("userName", userName);
                        finish();
                        startActivity(intent);

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
        };

        // image click listener


        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_11, theCard, cardArrayShuffled);

            }
        });

        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_12, theCard, cardArrayShuffled);

            }
        });

        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_13, theCard, cardArrayShuffled);

            }
        });

        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_14, theCard, cardArrayShuffled);

            }
        });

        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_21, theCard, cardArrayShuffled);

            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_22, theCard, cardArrayShuffled);

            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_23, theCard, cardArrayShuffled);

            }
        });

        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_24, theCard, cardArrayShuffled);

            }
        });

        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_31, theCard, cardArrayShuffled);

            }
        });

        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_32, theCard, cardArrayShuffled);

            }
        });

        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_33, theCard, cardArrayShuffled);

            }
        });

        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_34, theCard, cardArrayShuffled);

            }
        });

        iv_41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_41, theCard, cardArrayShuffled);

            }
        });

        iv_42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_42, theCard, cardArrayShuffled);

            }
        });

        iv_43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_43, theCard, cardArrayShuffled);
            }
        });

        iv_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_44, theCard, cardArrayShuffled);
            }
        });


    }


    public void startAlertDiaolg() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Single4x4Activity.this);
        alertDialogBuilder.setTitle("READY!");
        alertDialogBuilder.setCancelable(false).setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                timer.start();
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
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 12) {
                iv_41.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 13) {
                iv_42.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 14) {
                iv_43.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 15) {
                iv_44.setVisibility(View.INVISIBLE);
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
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 12) {
                iv_41.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 13) {
                iv_42.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 14) {
                iv_43.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 15) {
                iv_44.setVisibility(View.INVISIBLE);
            }


            // add points to the correct player
            // System.out.println(Integer.parseInt(tv_time.getText().toString()));
            System.out.println(getSecond);
            playerPoints = playerPoints + (2 * powerOfCards.get(clickedFirst) * houseFactor.get(clickedFirst)) * (getSecond / 10.0);
            tv_score.setText("SCORE: " + String.format("%.2f", playerPoints));


        } else {
            //Not same card but we look at house
            if (housesOfCards.get(clickedFirst).equalsIgnoreCase(housesOfCards.get(clickedSecond))) {
                playerPoints = playerPoints - ((powerOfCards.get(clickedFirst) + powerOfCards.get(clickedSecond)) / houseFactor.get(clickedFirst)) * (currentTime / 10.0);
                tv_score.setText("SCORE: " + String.format("%.2f", playerPoints));
            } else if (!housesOfCards.get(clickedFirst).equalsIgnoreCase(housesOfCards.get(clickedSecond))) {
                playerPoints = playerPoints - (((((double) (powerOfCards.get(clickedFirst) + powerOfCards.get(clickedSecond))) / 2) * houseFactor.get(clickedFirst) * houseFactor.get(clickedSecond)) * (currentTime / 10.0));
                tv_score.setText("SCORE: " + String.format("%.2f", playerPoints));
            }


            setImageBack();
            //chance the player turn
         /*   if (turn == 1) {
                turn = 2;
                tv_p1.setTextColor(Color.GRAY);
                tv_p2.setTextColor(Color.BLACK);
            } else if (turn == 2) {
                turn = 1;
                tv_p2.setTextColor(Color.GRAY);
                tv_p1.setTextColor(Color.BLACK);
            }*/
        }
        setImageEnabled(true);

        //check if the game is over
        checkEnd();
    }

    private void setImageEnabled(Boolean b) {
        iv_11.setEnabled(b);
        iv_12.setEnabled(b);
        iv_13.setEnabled(b);
        iv_14.setEnabled(b);
        iv_21.setEnabled(b);
        iv_22.setEnabled(b);
        iv_23.setEnabled(b);
        iv_24.setEnabled(b);
        iv_31.setEnabled(b);
        iv_32.setEnabled(b);
        iv_33.setEnabled(b);
        iv_34.setEnabled(b);
        iv_41.setEnabled(b);
        iv_42.setEnabled(b);
        iv_43.setEnabled(b);
        iv_44.setEnabled(b);
    }

    private void setImageBack() {
        iv_11.setImageBitmap(bitmap);
        iv_12.setImageBitmap(bitmap);
        iv_13.setImageBitmap(bitmap);
        iv_14.setImageBitmap(bitmap);
        iv_21.setImageBitmap(bitmap);
        iv_22.setImageBitmap(bitmap);
        iv_23.setImageBitmap(bitmap);
        iv_24.setImageBitmap(bitmap);
        iv_31.setImageBitmap(bitmap);
        iv_32.setImageBitmap(bitmap);
        iv_33.setImageBitmap(bitmap);
        iv_34.setImageBitmap(bitmap);
        iv_41.setImageBitmap(bitmap);
        iv_42.setImageBitmap(bitmap);
        iv_43.setImageBitmap(bitmap);
        iv_44.setImageBitmap(bitmap);
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
            timer.cancel();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Single4x4Activity.this);
            alertDialogBuilder.setTitle("GAME OVER");
            alertDialogBuilder.setMessage("\nSCORE: " + String.format("%.2f", playerPoints) + "\nRemaining Time : " + tv_time.getText() + "\nTime :" + currentTime).setCancelable(false).setPositiveButton(" NEW", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Single4x4Activity.this, Single4x4Activity.class);
                    intent.putExtra("userName", userName);
                    finish();
                    startActivity(intent);

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
                dialogInterface.dismiss();
                ;

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


