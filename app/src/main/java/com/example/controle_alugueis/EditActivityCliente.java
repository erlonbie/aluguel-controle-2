package com.example.controle_alugueis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivityCliente extends AppCompatActivity {

    private ClienteDAO clienteDAO;
    private AluguelDAO aluguelDAO;
    private int clientId;
    private TextView editNome, editIdade;

    private EditText nome, idade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cliente2);
        setTitle("Cliente");

//        editNome = findViewById(R.id.nomeCliente);
//        editIdade = findViewById(R.id.idadeCliente);

        clienteDAO = new ClienteDAO(this);
        aluguelDAO = new AluguelDAO(this);
        nome = findViewById(R.id.nome);
        idade = findViewById(R.id.idade);

        Intent intent = getIntent();
        clientId = intent.getIntExtra("clientId", -1);

        if(clientId != -1) {
            Cliente cliente = clienteDAO.get(clientId);
            //Toast.makeText(this, clienteDAO.get(clientId).getNome(), Toast.LENGTH_SHORT).show();
            nome.setText(cliente.getNome());
            idade.setText(String.valueOf(cliente.getIdade()));
        }
    }

    public void salvarClicado2(View view) {
        Cliente cliente = null;

            if(nome.getText().toString().equals("") || idade.getText().toString().equals("")) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                cliente = new Cliente(clientId, 0, nome.getText().toString(), Integer.parseInt(idade.getText().toString()));
            }


        boolean result;
        result = clientId == -1 ? clienteDAO.add(cliente) : clienteDAO.update(cliente);
        if(clientId == -1) {
            Toast.makeText(this, "Cliente salvo!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Cliente atualizado!", Toast.LENGTH_SHORT).show();
        }

        if(result) finish();
    }

    public void apagarClicado2(View view) {
        AluguelDAO aluguelDAO = new AluguelDAO(this);
        if(aluguelDAO.temCliente(clientId)){
            Toast.makeText(this, "Esse Cliente está vinculado a um aluguel! Apague o aluguel antes", Toast.LENGTH_SHORT).show();
            return;
        }
        clienteDAO.delete(clientId);
        finish();
    }
}