package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ClientesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClientesAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        recyclerView = findViewById(R.id.list_recycler_cliente);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ClientesAdapter(this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoyt2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.update();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void buttonAddClick2(View view) {
        Intent intent = new Intent(this, EditActivityCliente.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }
}

class ClientesAdapter extends RecyclerView.Adapter<ClientesViewHolder> {
    private Context context;
    private ArrayList<Cliente> clientes;
    ClienteDAO clienteDAO;

    public ClientesAdapter(Context context) {
        this.context = context;
        this.clienteDAO = new ClienteDAO(this.context);
        update();
    }

    public void update() {
        clientes = clienteDAO.getList();
    }

    public ClientesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ClientesViewHolder ih = new ClientesViewHolder(v, context);
        return ih;
    }

    public void onBindViewHolder(ClientesViewHolder holder, int position) {
        holder.clientId.setText(String.valueOf(clientes.get(position).getId()));
        holder.nome.setText(clientes.get(position).getNome());
        holder.imovel_id.setText(String.valueOf(clientes.get(position).getImovel_id()));
        holder.idade.setText(String.valueOf(clientes.get(position).getIdade()));
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }
}

class ClientesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Context context;
    public TextView clientId, nome, imovel_id, idade;
    public int id;

    public ClientesViewHolder(ConstraintLayout v, Context context) {
        super(v);
        this.context = context;
        clientId = v.findViewById(R.id.itemId);
        nome = v.findViewById(R.id.itemName);
        imovel_id = v.findViewById(R.id.imovelId);
        idade = v.findViewById(R.id.itemAge);
        v.setOnClickListener(this);
    }

    public void onClick(View v) {
        //Toast.makeText(context, "Cliente: " + this.nome.getText().toString() + "\nId: " + this.clientId.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, EditActivityCliente.class);
        intent.putExtra("clientId", Integer.parseInt(this.clientId.getText().toString()));
        context.startActivity(intent);
    }

}