package com.card.harrypotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class SignInActivity extends AppCompatActivity {
    private EditText edtTxtEMail, edtTxtPassword;
    private TextView textNotif;
    String email, password;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mRefererence;
    private String userName;
    private HashMap<String, Object> mData;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtTxtEMail = (EditText) findViewById(R.id.editTextEMaillSignIn);
        edtTxtPassword = (EditText) findViewById(R.id.editTextPasswordSignIn);
        textNotif = (TextView) findViewById(R.id.textViewNotif);
        textNotif.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        rememberMe = (CheckBox) findViewById(R.id.rememberMe);
        Paper.init(this);
    }

    public void btnSignIn(View view) {
        email = edtTxtEMail.getText().toString().trim();
        password = edtTxtPassword.getText().toString();


        if (!email.isEmpty() && !password.isEmpty()) {

            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(SignInActivity.this, new OnSuccessListener<AuthResult>() {
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
                            if (rememberMe.isChecked()) {
                                Paper.book().write(Prevalent.emailKey, email);
                                Paper.book().write(Prevalent.passwordKey, password);
                            }
                            mData.put("password", password);
                            mRefererence.updateChildren(mData);

                            textNotif.setText("Successful!!");
                            textNotif.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(SignInActivity.this, SelectPlayerActivity.class);
                            intent.putExtra("userName", userName);
                            finish();
                            startActivity(intent);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(SignInActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            textNotif.setText(" Opps! Something wrong!!");
                            textNotif.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    textNotif.setText(" Opps! Something wrong!!");
                    textNotif.setVisibility(View.VISIBLE);
                }
            });
        } else {
            textNotif.setText(" Opps! Something wrong!!");
            textNotif.setVisibility(View.VISIBLE);
            Toast.makeText(SignInActivity.this, "Fields do not be blank!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonUpdatePassword(View view) {
/*Intent intent1 = new Intent(SignInActivity.this,UpdatePasswordActivity.class);
finish();
startActivity(intent1);
  */
        email = edtTxtEMail.getText().toString().trim();
        password = edtTxtPassword.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(SignInActivity.this, "please enter e mail address", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(SignInActivity.this, "please enter your password to send mail for reset password", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignInActivity.this, "Your information checked", Toast.LENGTH_SHORT).show();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignInActivity.this, "Email sent successfully to reset your password", Toast.LENGTH_LONG).show();
                            }
                            //finish();
                        }
                    }).addOnFailureListener(SignInActivity.this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignInActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignInActivity.this, "Your information wrong! Please Check it", Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(" Harry Potter Card Game ");
        alert.setMessage("Are you sure to go main menu?");
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