package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AlugueisActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private AlugueisAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    //private Button aluga;

    private Spinner spinnerImoveis, spinnerClientes;
    private int id_imovel, id_cliente;
    private String alugado;
    private  AluguelDAO aluguelDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugueis);

        recyclerView = findViewById(R.id.list_recycler_alugueis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        aluguelDAO = new AluguelDAO(this);

        adapter = new AlugueisAdapter(this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoyt3);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.update();
                adapter.notifyDataSetChanged();
                loadSpinners();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        spinnerImoveis = (Spinner) findViewById(R.id.spinnerImoveis);
        spinnerClientes = (Spinner) findViewById(R.id.spinnerClientes);

        spinnerImoveis.setOnItemSelectedListener(this);
        spinnerClientes.setOnItemSelectedListener(this);


        spinnerImoveis.setPrompt("Escolha uma imóvel");
        loadSpinners();

        //aluga = findViewById(R.id.alugaImovel);

    }

    public void alugaImovel(View view) {
        if(alugado.equals("sim")) {
            Toast.makeText(this, "Imóvel já está alugado!", Toast.LENGTH_SHORT).show();
            return;
        }
//        String inicio = SimpleDateFormat.getDateInstance().format("dd/mm/yy");
//        String termino = SimpleDateFormat.getDateInstance().format("dd/mm/yy");

        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");

        Aluguel aluguel = new Aluguel(0, id_imovel, id_cliente, sdf.format(cal.getTime()) , sdf.format(cal.getTime()) , 0, 0, 0);
        Toast.makeText(this, "Id do cliente: " + id_cliente, Toast.LENGTH_SHORT).show();
        aluguelDAO.add(aluguel);
        ImovelDAO imovelDAO = new ImovelDAO(this);
        Imovel imovel = imovelDAO.get(id_imovel);
        imovelDAO.alugaImovel(imovel);
        adapter.update();
        adapter.notifyDataSetChanged();
        loadSpinners();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
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
            alugado = parent.getSelectedItem().toString().substring(parent.getSelectedItem().toString().length() -3);
            Toast.makeText(this, "Id do imóvel: " + id_imovel, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Alugado: " + alugado, Toast.LENGTH_SHORT).show();

        }
        else if(parent.getId() == R.id.spinnerClientes) {
            id_cliente = pegaId(parent.getSelectedItem().toString());
            Toast.makeText(this, "Id do cliente: " + id_cliente, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

class AlugueisAdapter extends RecyclerView.Adapter<AlugueisViewHolder> {
    private Context context;
    private ArrayList<Aluguel> alugueis;
    private ArrayList<Imovel> imoveis;
    private ArrayList<Cliente> clientes;
    AluguelDAO aluguelDAO;
    ImovelDAO imovelDAO;
    ClienteDAO clienteDAO;

    public AlugueisAdapter(Context context) {
        this.context = context;
        this.aluguelDAO = new AluguelDAO(this.context);
        this.imovelDAO = new ImovelDAO(this.context);
        this.clienteDAO = new ClienteDAO(this.context);
        update();
    }

    public void update() {
        alugueis = aluguelDAO.getList();
        imoveis =  imovelDAO.getList();
        clientes = clienteDAO.getList();
    }

    public AlugueisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item3, parent, false);
        AlugueisViewHolder ah = new AlugueisViewHolder(v, context);
        return ah;
    }

    public void onBindViewHolder(AlugueisViewHolder holder, int position) {
        holder.aluguel_id.setText(String.valueOf(alugueis.get(position).getId()));
        //holder.categoria.setText(imoveis.get(position).getCategoria());

        holder.imovel_id.setText(String.valueOf(alugueis.get(position).getImovel_id()));
        //holder.clientName.setText(String.valueOf(clientes.get(position).getNome()));
        AluguelDAO aDAO = new AluguelDAO(context);
        //holder.clientName.setText(aDAO.nomeCliente(position));
        holder.categoria.setText(aDAO.categoriaImovel((alugueis.get(position).getId())));
        holder.clientName.setText(aDAO.nomeCliente(alugueis.get(position).getId()));
        update();
    }

    @Override
    public int getItemCount() {
        return alugueis.size();
    }
}

class AlugueisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Context context;
    public TextView aluguel_id, categoria, imovel_id, clientName;
    public int id;

    public AlugueisViewHolder(ConstraintLayout v, Context context) {
        super(v);
        this.context = context;
        aluguel_id = v.findViewById(R.id.aluguelId);
        categoria = v.findViewById(R.id.itemCategory);
        imovel_id = v.findViewById(R.id.itemId);
        clientName = v.findViewById(R.id.clienteName);
        v.setOnClickListener(this);
    }

    public void onClick(View v) {
        Toast.makeText(context, "Cliente: " + this.aluguel_id.getText().toString() + "\nId: " + this.imovel_id.getText().toString(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(context, EditActivityAluguel.class);
//        intent.putExtra("aluguelId", Integer.parseInt(this.aluguel_id.getText().toString()));
//        context.startActivity(intent);
    }

}