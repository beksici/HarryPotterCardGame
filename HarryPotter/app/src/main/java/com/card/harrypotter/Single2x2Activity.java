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
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Single2x2Activity extends AppCompatActivity {
    private String userName;
    TextView tv_username, tv_score, tv_time;
    ImageView iv_11, iv_12, iv_21, iv_22;
    ArrayList<Integer> houseFactor = new ArrayList<>();
    ArrayList<String> housesOfCards = new ArrayList<>();
    ArrayList<Integer> powerOfCards = new ArrayList<>();
    Integer[] cardsArrayAll = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144};
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
        setContentView(R.layout.activity_single2x2);
        Intent comeIntent = getIntent();
        userName = comeIntent.getStringExtra("userName");

        tv_username = (TextView) findViewById(R.id.textViewUserNameS2x2);
        tv_username.setText("USERNAME: " + userName);
        tv_score = (TextView) findViewById(R.id.textViewScoreS2x2);
        tv_time = (TextView) findViewById(R.id.textViewTimeS2x2Field);

        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_12 = (ImageView) findViewById(R.id.iv_12);
        iv_21 = (ImageView) findViewById(R.id.iv_21);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        victory= MediaPlayer.create(getApplicationContext(), R.raw.victory);
        timeFinish=MediaPlayer.create(getApplicationContext(), R.raw.timefinish);
        congratulations= MediaPlayer.create(getApplicationContext(), R.raw.congratulations);
        long duration = 45001;
        tv_time.setText("00:45");
        startAlertDiaolg();
        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_21.setTag("2");
        iv_22.setTag("3");


// shuffle image id
        Collections.shuffle(Arrays.asList(cardsArrayAll));
        Integer[] cardArrayShuffled = new Integer[4];
        for (int i = 0; i < 2; i++) {
            cardArrayShuffled[i] = cardsArrayAll[i];
        }
        for (int i = 0; i < 2; i++) {
            cardArrayShuffled[i + 2] = cardArrayShuffled[i] + 100;
            //  System.out.println(cardArrayShuffled[i]);
        }
        //burada öylesine yazdırdım
        for (int i = 0; i < 4; i++) {
            System.out.println(cardArrayShuffled[i]);
        }

        Collections.shuffle(Arrays.asList(cardArrayShuffled));
        System.out.println("--------------");
        for (int i = 0; i < 4; i++) {
            System.out.println(cardArrayShuffled[i]);
        }


        getCardInformation(cardArrayShuffled);
        bitmap = decodeImage(readToFile("back.txt"));
        iv_11.setImageBitmap(bitmap);
        iv_12.setImageBitmap(bitmap);
        iv_21.setImageBitmap(bitmap);
        iv_22.setImageBitmap(bitmap);
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
                if(SongSettings.isMediaPlayerSoundOnOff())
                    timeFinish.start();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Single2x2Activity.this);
                alertDialogBuilder.setTitle("GAME OVER");
                alertDialogBuilder.setMessage("\nSCORE: " + String.format("%.2f", playerPoints) + "\nRemaining Time : " + tv_time.getText() + "\nTime :" + currentTime).setCancelable(false).setPositiveButton(" NEW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Single2x2Activity.this, Single2x2Activity.class);
                        intent.putExtra("userName",userName);
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


    }

    public void startAlertDiaolg() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Single2x2Activity.this);
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
        getResourceImageFromFirebase(iv, card);
        //check which image is selected and save it to temporary variable
        if (cardNumber == 1) {
            firstCard = cardArrayShuffled[card];
            if (firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;
            iv.setEnabled(false);
        } else if (cardNumber == 2) {
            secondCard = cardArrayShuffled[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // check if the selected images are equal
                    calculate();
                }
            }, 1000);

        }


    }

    private synchronized void getResourceImageFromFirebase(ImageView iv, int card) {

        iv.setImageBitmap(imagesBitmap.get(card));
    }


    private synchronized void calculate() {
        //if images are equal remove  them and add point
        if (firstCard == secondCard) {
           if(SongSettings.isMediaPlayerSoundOnOff())
            victory.start();

            if (clickedFirst == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                iv_22.setVisibility(View.INVISIBLE);
            }

            if (clickedSecond == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                iv_22.setVisibility(View.INVISIBLE);
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
                playerPoints = playerPoints - (((((double) (powerOfCards.get(clickedFirst) + powerOfCards.get(clickedSecond)) )/ 2) * houseFactor.get(clickedFirst) * houseFactor.get(clickedSecond)) * (currentTime / 10.0));
                tv_score.setText("SCORE: " + String.format("%.2f", playerPoints));
            }
            iv_11.setImageBitmap(bitmap);
            iv_12.setImageBitmap(bitmap);
            iv_21.setImageBitmap(bitmap);
            iv_22.setImageBitmap(bitmap);
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

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);

        //check if the game is over
        checkEnd();
    }

    private synchronized void checkEnd() {
        if (iv_11.getVisibility() == View.INVISIBLE && iv_12.getVisibility() == View.INVISIBLE && iv_21.getVisibility() == View.INVISIBLE && iv_22.getVisibility() == View.INVISIBLE) {
            if(SongSettings.isMediaPlayerSoundOnOff()) {
                victory.stop();
                congratulations.start();
            }
            timer.cancel();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Single2x2Activity.this);
            alertDialogBuilder.setTitle("GAME OVER");
            alertDialogBuilder.setMessage("\nSCORE: " + String.format("%.2f", playerPoints) + "\nRemaining Time : " + tv_time.getText() + "\nTime :" + currentTime).setCancelable(false).setPositiveButton(" NEW", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Single2x2Activity.this, Single2x2Activity.class);
                    intent.putExtra("userName",userName);
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

    public void writeToFile(String fileName, String content) {
        File path = getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer = new FileOutputStream(new File(path, fileName));
            writer.write(content.getBytes(StandardCharsets.UTF_8));
            Toast.makeText(this, getApplicationContext().getFilesDir().toString(), Toast.LENGTH_SHORT).show();
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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