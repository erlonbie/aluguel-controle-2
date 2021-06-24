package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ImoveisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImoveisAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imoveis);

        recyclerView = findViewById(R.id.list_recycler_imovel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ImoveisAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    public void buttonAddClick(View view) {
        Intent intent = new Intent(this, EditActivityImovel.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }
}

class ImoveisAdapter extends RecyclerView.Adapter<ImoveisViewHolder> {
    private Context context;
    private ArrayList<Imovel> imoveis;
    ImovelDAO imovelDAO;

    public ImoveisAdapter(Context context) {
        this.context = context;
        this.imovelDAO = new ImovelDAO(this.context);
        update();
    }

    public void update() {
        imoveis = imovelDAO.getList();
    }

    public ImoveisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ImoveisViewHolder ih = new ImoveisViewHolder(v, this.context);
        return ih;
    }

    public void onBindViewHolder(ImoveisViewHolder holder, int position) {
        holder.categoria.setText(imoveis.get(position).getCategoria());
        holder.item_id.setText(String.valueOf(imoveis.get(position).getId()));
        holder.endereco.setText(imoveis.get(position).getEndereco());
    }

    @Override
    public int getItemCount() {
        return imoveis.size();
    }
}

class ImoveisViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Context context;
    public TextView categoria, item_id, endereco;
    public int id;

    public ImoveisViewHolder(ConstraintLayout v, Context context) {
        super(v);
        this.context = context;
        categoria = v.findViewById(R.id.itemCategory);
        item_id = v.findViewById(R.id.itemId);
        endereco = v.findViewById(R.id.itemAdress);
        v.setOnClickListener(this);
    }

    public void onClick(View v) {
        //Toast.makeText(this.context, "Imóvel: " + this.categoria.getText().toString() + "\nId: " + this.item_id.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, EditActivityImovel.class);
        intent.putExtra("imovelId", this.id);
        context.startActivity(intent);
    }

}