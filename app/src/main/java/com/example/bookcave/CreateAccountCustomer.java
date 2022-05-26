package com.example.bookcave;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAccountCustomer extends AppCompatActivity {

    private EditText email,password,firstname,lastname,phno,address,pincode;
    private String semail,spassword,sfirstname,slastname,sphno,saddress,spincode,userid;
    private Button createaccButton;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_customer);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        phno = findViewById(R.id.phno);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        createaccButton = findViewById(R.id.createaccButton);

        createaccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrive the strings
                semail= email.getText().toString().trim();
                spassword= password.getText().toString().trim();
                sfirstname= firstname.getText().toString().trim();
                slastname= lastname.getText().toString().trim();
                sphno= phno.getText().toString().trim();
                saddress= address.getText().toString().trim();
                spincode= pincode.getText().toString().trim();

                //Проверка на введённость данных
                if(TextUtils.isEmpty(semail)){
                    Toast.makeText(getApplicationContext(),"Пожалуйста введите почту",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(spassword)){
                    Toast.makeText(getApplicationContext(),"Пожалуйста введите пароль",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(sfirstname)){
                    Toast.makeText(getApplicationContext(),"Пожалуйста введите имя",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(slastname)){
                    Toast.makeText(getApplicationContext(),"Пожалуйста введите фамилию",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(sphno)){
                    Toast.makeText(getApplicationContext(),"Пожалуйста введите номер телефона",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(saddress)){
                    Toast.makeText(getApplicationContext(),"Пожалуйста введите адрес",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(spincode)){
                    Toast.makeText(getApplicationContext(),"Пожалуйста введите пинкод",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sphno.length()!=10){
                    phno.setError("У номера должно быть 10 значений");
                    return;
                }
                if(spincode.length()!=6){
                    pincode.setError("у пинкода долнжо быть 10 значений");
                    return;
                }

                //Save Data to Firestore
                //saveData(semail,spassword,sfirstname,slastname,sphno,saddress,spincode);
                fAuth = FirebaseAuth.getInstance();
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                createaccButton.setText("Создание ...");
                fAuth.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            userid = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("userid", userid);
                            user.put("email", semail);
                            user.put("firstname", sfirstname);
                            user.put("lastname", slastname);
                            user.put("phno", sphno);
                            user.put("address", saddress);
                            user.put("pincode", spincode);
                            user.put("usertype", "Customer");

                            //NEW
                            db.collection("Users").document(userid).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        createaccButton.setText("Создать аккаунт");
                                        Toast.makeText(CreateAccountCustomer.this,"Регистрация прошла успешно! <3", Toast.LENGTH_LONG).show();
                                        FirebaseAuth.getInstance().signOut();
                                        Intent i = new Intent(CreateAccountCustomer.this, CustomerLogin.class);
                                        startActivity(i);
                                    } else{
                                        createaccButton.setText("Создать аккаунт");
                                        String errorMessage = Objects.requireNonNull(task.getException()).getMessage();
                                        Toast.makeText(CreateAccountCustomer.this, "Ошибка: " + errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            //OLD
                            //Users info = new Users(fullName,email,phone,userid); class with getter setter

                        }else {
                            Toast.makeText(CreateAccountCustomer.this, "Ошибка! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    /*
    private void saveData(String semail,String spassword,String sfirstname,String slastname,String sphno,String saddress,String spincode){


        //New User
        Map<String, Object> user = new HashMap<>();
        user.put("first", semail);
        user.put("last", ");
        user.put("born", 1815);
    } */
}
