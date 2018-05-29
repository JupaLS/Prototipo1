package com.dreadroots.prototipo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mAsistente, mPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAsistente = (Button) findViewById(R.id.asistente);
        mPaciente = (Button) findViewById(R.id.paciente);

        mAsistente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AsistenteLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mPaciente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PacienteLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


    }


}
