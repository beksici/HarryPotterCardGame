package com.card.harrypotter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdatePasswordActivity extends AppCompatActivity {
    private EditText edtTxtEMail,edtTxtPassword ,edtTxtPasswordNew;
    private TextView textNotif;
    String email,password,newPassword;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mRefererence;
    private HashMap<String, Object> mData ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        edtTxtEMail= (EditText)findViewById(R.id.editTextEMaillSignIn);
        edtTxtPassword= (EditText)findViewById(R.id.editTextPasswordSignIn);
        edtTxtPasswordNew=(EditText)findViewById(R.id.editTextPasswordUpdateNew);
        textNotif= (TextView) findViewById(R.id.textViewNotif);
        textNotif.setVisibility(View.INVISIBLE);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
    }
    public void ButtonUpdatePassword(View v){
        email=edtTxtEMail.getText().toString();
        password=edtTxtPassword.getText().toString();
        newPassword=edtTxtPasswordNew.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()&& !newPassword.isEmpty()  )
        { mData=new HashMap<>();
            mData.put("password",newPassword);
            mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    mUser=mAuth.getCurrentUser();
                    mRefererence = FirebaseDatabase.getInstance().getReference("users").child(mUser.getUid());
                    mRefererence.updateChildren(mData).addOnCompleteListener(UpdatePasswordActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(UpdatePasswordActivity.this, "Update is Successful", Toast.LENGTH_SHORT).show();
                                textNotif.setText("Successful!!");
                                textNotif.setVisibility(View.VISIBLE);
                                //Intent intent= new Intent(UpdatePasswordActivity.this,SignInActivity.class);
                                finish();
                                //startActivity(intent);
                            }
                        }
                    });
                }
            }).addOnFailureListener(UpdatePasswordActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdatePasswordActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    textNotif.setText(" Opps! Something wrong!!");
                    textNotif.setVisibility(View.VISIBLE);
                }
            });

        }
        else {
            textNotif.setText(" Opps! Something wrong!!");
            textNotif.setVisibility(View.VISIBLE);
            Toast.makeText(UpdatePasswordActivity.this, "Fields do not be blank!!", Toast.LENGTH_SHORT).show();
        }

    }
}