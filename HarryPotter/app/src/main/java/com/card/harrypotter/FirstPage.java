package com.card.harrypotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class FirstPage extends AppCompatActivity {
    private Switch switchMusic, switchSound;
    private MediaPlayer mediaPlayer1;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mRefererence;
    private HashMap<String, Object> mData;
    String email, password, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        switchMusic = (Switch) findViewById(R.id.switchMusic);
        switchSound = (Switch) findViewById(R.id.switchSound);
        mediaPlayer1 = MediaPlayer.create(getApplicationContext(), R.raw.music);
        switchMusic.setChecked(true);
        switchSound.setChecked(true);

        Paper.init(this);
        mAuth = FirebaseAuth.getInstance();
        RegisterAllCard r = new RegisterAllCard();
        r.registerAll();
        r.registerCardBack();
//SWİTCH MEDIA PLAYER CONTROL
        if (switchSound.isChecked() == true)
            SongSettings.setMediaPlayerSoundOnOff(true);
        else
            SongSettings.setMediaPlayerSoundOnOff(false);
        if (switchMusic.isChecked() == true) {
            mediaPlayer1.start();
            SongSettings.setMediaPlayerMusicOnOff(true);
            mediaPlayer1.setLooping(true);
        }
        switchMusic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SongSettings.setMediaPlayerMusicOnOff(true);
                    mediaPlayer1.start();
                    mediaPlayer1.setLooping(true);
                } else {
                    mediaPlayer1.pause();
                    SongSettings.setMediaPlayerMusicOnOff(false);
                }
            }
        });
        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SongSettings.setMediaPlayerSoundOnOff(true);
                } else {
                    SongSettings.setMediaPlayerSoundOnOff(false);
                }
            }
        });

//remember me check
        String UserEmailKey = Paper.book().read(Prevalent.emailKey);
        String UserPasswordKey = Paper.book().read(Prevalent.passwordKey);
        if (UserEmailKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                allowAccess(UserEmailKey, UserPasswordKey);
            }
        }
    }

    private void allowAccess(String userEmailKey, String userPasswordKey) {
        email = userEmailKey;
        password = userPasswordKey;
        mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(FirstPage.this, new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                mUser = mAuth.getCurrentUser();
                mData = new HashMap<>();

                mRefererence = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());
                mRefererence.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot shot : snapshot.getChildren()) {
                            System.out.println(shot.getKey() + shot.getValue());
                            if (shot.getKey().equalsIgnoreCase("userName")) {
                                userName = shot.getValue().toString();
                            }
                /* if(shot.getKey().equalsIgnoreCase("password")){
                     password= shot.getValue().toString();
                     mData.put("password",password);
                 }*/

                        }

                        mData.put("password", password);
                        mRefererence.updateChildren(mData);
                        Toast.makeText(FirstPage.this, "Already Logged in ", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(FirstPage.this, SelectPlayerActivity.class);
                        intent.putExtra("userName", userName);
                      //  finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(FirstPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(FirstPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void signUp(View v) {
        if (connectionCheck()) {
            Intent intent = new Intent(FirstPage.this, SignUpActivity.class);//butona tıkladığında diğer sayfaya aktarıyor
            // intent.putExtra();//burada key ve value gönderiyoruz
            //finish();//eğer finish dersen bu aktivite kapanır geri tuşuna basınca geri gitmez eğer geri gelsin dersen sıfırdan çağırdığın aktiviteden intent yapman gerekir.
            startActivity(intent);
        } else {
            Toast.makeText(FirstPage.this, "Connection Failed! Please check Network", Toast.LENGTH_SHORT).show();
        }

    }

    public void signIn(View v) {
        if (connectionCheck()) {
            Intent intent = new Intent(FirstPage.this, SignInActivity.class);//butona tıkladığında diğer sayfaya aktarıyor
            // intent.putExtra();//burada key ve value gönderiyoruz
            //finish();//eğer finish dersen bu aktivite kapanır geri tuşuna basınca geri gitmez eğer geri gelsin dersen sıfırdan çağırdığın aktiviteden intent yapman gerekir.
            startActivity(intent);
        } else {
            Toast.makeText(FirstPage.this, "Connection Failed! Please check Network (Wifi ,gsm network )", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(" Harry Potter Card Game ");
        alert.setMessage("Are you sure exit?");
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

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);

            }
        });
        alert.show();


    }

    public boolean connectionCheck() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else

            return false;

    }
/*
    @Override
    public void onPause() {
        super.onPause();
        if (SongSettings.isMediaPlayerSoundOnOff()) {
            mediaPlayer1.stop();
            SongSettings.setMediaPlayerMusicOnOff(false);
        } else
            return;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (SongSettings.isMediaPlayerSoundOnOff()) {
            mediaPlayer1.stop();
            SongSettings.setMediaPlayerMusicOnOff(false);
        } else
            return;
    }*/

}