package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AlugueisActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//    private RecyclerView recyclerView;
//    private ClientesAdapter adapter;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    Spinner spinnerImoveis, spinnerClientes;
//    ArrayList<String> imoveis = new ArrayList<>();
//    ArrayList<String> clientes = new ArrayList<>();

    Spinner spinnerImoveis, spinnerClientes;
    int id_imovel, id_cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugueis);

        spinnerImoveis = (Spinner) findViewById(R.id.spinnerImoveis);
        spinnerClientes = (Spinner) findViewById(R.id.spinnerClientes);

        spinnerImoveis.setOnItemSelectedListener(this);
        spinnerClientes.setOnItemSelectedListener(this);


        spinnerImoveis.setPrompt("Escolha uma imóvel");
        loadSpinners();

    }

    private void loadSpinners() {
//        SQLiteDatabase db = new Database(this).getWritableDatabase();
//        Cursor cursor = db.rawQuery()
        ImovelDAO imovelDAO = new ImovelDAO(this);
        ClienteDAO clienteDAO = new ClienteDAO(this);

        ArrayList<String> imoveis = imovelDAO.getListNames();
        ArrayList<String> clientes = clienteDAO.getListNames();

        ArrayAdapter<String> imoveisAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, imoveis);
        ArrayAdapter<String> clientesAdatper = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, clientes);

        spinnerImoveis.setAdapter(imoveisAdapter);
        spinnerClientes.setAdapter(clientesAdatper);
    }


    public int pegaId(String object) {
        int fim = 4;
        for (int i = fim; object.charAt(i) != '|'; i++) {
            fim++;
        }
        String id_acumulado = object.substring(4, fim);
        int id = Integer.parseInt(id_acumulado);
        return id;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinnerImoveis) {
            id_imovel = pegaId(parent.getSelectedItem().toString());
            Toast.makeText(this, "teste" + id_imovel, Toast.LENGTH_SHORT).show();
        }
        else if(parent.getId() == R.id.spinnerClientes) {
            id_cliente = pegaId(parent.getSelectedItem().toString());
            Toast.makeText(this, "teste" + id_cliente, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

