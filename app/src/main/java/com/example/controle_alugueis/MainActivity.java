package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Controle-Alugueis-V2.0");
    }

    public void entra_imoveis(View view) {
        Intent intent = new Intent(this, ImoveisActivity.class);
        startActivity(intent);
    }

    public void entra_clientes(View view) {
        Intent intent = new Intent(this, ClientesActivity.class);
        startActivity(intent);
    }

    public void entra_alugueis(View view) {
        Intent intent = new Intent(this, AlugueisActivity.class);
        startActivity(intent);
    }
}