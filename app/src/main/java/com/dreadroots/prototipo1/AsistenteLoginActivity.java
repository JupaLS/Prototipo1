package com.dreadroots.prototipo1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AsistenteLoginActivity extends AppCompatActivity {
    private EditText mEmail, mPass;
    private Button mIngreso, mRegistro;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistente_login);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(AsistenteLoginActivity.this,MapActivity.class);
                    startActivity(intent);

                    return;
                }
            }
        };

        mEmail = (EditText) findViewById(R.id.email);
        mPass = (EditText) findViewById(R.id.pass);

        mIngreso = (Button) findViewById(R.id.login);
        mRegistro = (Button) findViewById(R.id.registro);

        mRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = mEmail.getText().toString();
                final String Pass = mPass.getText().toString();
                mAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(AsistenteLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(AsistenteLoginActivity.this, "Error de registro", Toast.LENGTH_SHORT).show();


                        }else{
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Asistentes").child(user_id);
                            current_user_db.setValue(true);

                        }
                    }
                });
            }
        });

        mIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = mEmail.getText().toString();
                final String Pass = mPass.getText().toString();
                mAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(AsistenteLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(AsistenteLoginActivity.this, "Error de ingreso", Toast.LENGTH_SHORT).show();


                        }
                    }
                });
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}

