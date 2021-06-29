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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ImoveisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImoveisAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    private boolean clicado = false;

    private ExtendedFloatingActionButton botao_acao;
    private FloatingActionButton botao_add_imovel, botao_relatorio_imovel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imoveis);
        setTitle("Imóveis");

        recyclerView = findViewById(R.id.list_recycler_imovel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ImoveisAdapter(this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoyt);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.update();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottrom_anim);

        botao_add_imovel = findViewById(R.id.addImovel);
        botao_relatorio_imovel = findViewById(R.id.relatorioImovel);

        botao_acao = findViewById(R.id.extended_imoveis);
        botao_acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarBotoes();
            }
        });
    }

    public boolean retornaClicado() {
        return clicado;
    }

    public void mostrarBotoes() {
        mostrar(clicado);
        animar(clicado);
        clicado = !clicado;
    }


    public void mostrar(boolean clicado){
        if(!clicado){
            botao_add_imovel.setVisibility(View.VISIBLE);
            botao_add_imovel.setEnabled(true);
            botao_relatorio_imovel.setVisibility(View.VISIBLE);
            botao_relatorio_imovel.setEnabled(true);
        }
        else{
            botao_add_imovel.setVisibility(View.INVISIBLE);
            botao_add_imovel.setEnabled(false);
            botao_relatorio_imovel.setVisibility(View.INVISIBLE);
            botao_relatorio_imovel.setEnabled(false);
        }
    }

    public void animar(boolean clicado) {
        if(!clicado) {
            botao_acao.startAnimation(rotateClose);
            botao_add_imovel.startAnimation(fromBottom);
            botao_relatorio_imovel.startAnimation(fromBottom);
        }
        else {
            botao_acao.startAnimation(rotateClose);
            botao_add_imovel.startAnimation(toBottom);
            botao_relatorio_imovel.startAnimation(toBottom);
        }
    }

    public void buttonAddClick(View view) {
        if(clicado){
            mostrarBotoes();
        }
        Intent intent = new Intent(this, EditActivityImovel.class);
        startActivity(intent);
    }

    public void buttonRelatorioClick(View view) {
        if(clicado){
            mostrarBotoes();
        }
        Intent intent = new Intent(this, RelatorioImoveis.class);
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
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item2, parent, false);
        ImoveisViewHolder ih = new ImoveisViewHolder(v, context);
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
        //Toast.makeText(context, "Imóvel: " + this.categoria.getText().toString() + "\nId: " + this.item_id.getText().toString(), Toast.LENGTH_SHORT).show();

        try {
            if(((ImoveisActivity) v.getContext()).retornaClicado()) {
                ((ImoveisActivity) v.getContext()).mostrarBotoes();
            }
        } catch (Exception e) {
            // ignore
        }

        Intent intent = new Intent(context, EditActivityImovel.class);
        intent.putExtra("imovelId", Integer.parseInt(this.item_id.getText().toString()));
        context.startActivity(intent);
    }

}