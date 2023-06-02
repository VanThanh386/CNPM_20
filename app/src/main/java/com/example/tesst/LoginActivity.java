package com.example.tesst;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUserName,editTextPassword;
    private Button btndangki,btndangnhap;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        btndangnhap = findViewById(R.id.btndangnhap);
        btndangki = findViewById(R.id.btndangki);

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangnhap();
            }
        });
        btndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangki();
            }
        });
    }

    private void dangki() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void dangnhap() {
        String email, pass;
        email = editTextUserName.getText().toString();
        pass = editTextPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Vui long nhap lai email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Vui long nhap lai pass",Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Dang nhap thanh cong ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Dang nhap khong thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    
}
