package com.card.harrypotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtTxtEmail, edtTxtUserName, edtTxtPassword;
    private TextView textNotif;
    String editUserName, editEmail, editPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private HashMap<String, Object> mDataRegist;
    private FirebaseUser mUser;//id almak için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtTxtEmail = (EditText) findViewById(R.id.editTextEmail);
        edtTxtUserName = (EditText) findViewById(R.id.editTextUserName);
        edtTxtPassword = (EditText) findViewById(R.id.editTextPassword);
        textNotif = (TextView) findViewById(R.id.textViewNotif);
        textNotif.setVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference();// benim için root bölümünü getiriyor
        //getreference içine eğer root dışında path verebilirsin
    }

    public void btnSignUp(View v) {
        editEmail = edtTxtEmail.getText().toString().trim();
        editUserName = edtTxtUserName.getText().toString().trim();
        editPassword = edtTxtPassword.getText().toString();
        System.out.println(editEmail + editUserName + editPassword);
        //TODO Kontoller yapilmali eksik suanda
        if (!editEmail.isEmpty() && !editUserName.isEmpty() && !editPassword.isEmpty()) {
            if (!editEmail.endsWith(".com") || !editEmail.contains("@") ) {
                Toast.makeText(SignUpActivity.this, "Your e mail doesn't end with (.com) please check it !!", Toast.LENGTH_SHORT).show();
            }else if(editEmail.contains(" ")){
                Toast.makeText(SignUpActivity.this, "Your e mail field has space \" \" please check it !!", Toast.LENGTH_SHORT).show();

            }
            else {
                mAuth.createUserWithEmailAndPassword(editEmail, editPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mUser = mAuth.getCurrentUser();
                                    mDataRegist = new HashMap<>();
                                    mDataRegist.put("userName", editUserName);
                                    mDataRegist.put("password", editPassword);
                                    mDataRegist.put("email", editEmail);
                                    mDataRegist.put("id", mUser.getUid());
                                    // mReference= FirebaseDatabase.getInstance().getReference().child("users");
                                    mReference.child("users").child(mUser.getUid())
                                            .setValue(mDataRegist)
                                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        Toast.makeText(SignUpActivity.this, "Sign up Successfull !!", Toast.LENGTH_SHORT).show();
                                                        textNotif.setText("Successfull!!");
                                                        textNotif.setVisibility(View.VISIBLE);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


//veriler kayıt olurken key ve value şeklinde oluyor  bu yüzden hashmap kulllanmak mantıklı


                                    //Intent intent= new Intent(SignUpActivity.this,FirstPage.class);//butona tıkladığında diğer sayfaya aktarıyor
                                    ;//eğer finish dersen bu aktivite kapanır geri tuşuna basınca geri gitmez eğer geri gelsin dersen sıfırdan çağırdığın aktiviteden intent yapman gerekir.
                                    //startActivity(intent);
                                } else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        } else {
            textNotif.setText(" Opps! Something wrong!!");
            textNotif.setVisibility(View.VISIBLE);
        }
       /* Intent intent= new Intent(SignUpActivity.this,MainActivity2.class);//butona tıkladığında diğer sayfaya aktarıyor
        intent.putExtra("userName",editUserName);//burada key ve value gönderiyoruz
        finish();//eğer finish dersen bu aktivite kapanır geri tuşuna basınca geri gitmez eğer geri gelsin dersen sıfırdan çağırdığın aktiviteden intent yapman gerekir.
        startActivity(intent);*/
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}