package com.example.controle_alugueis;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public class AlugueisActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private RecyclerView recyclerView;
    private AlugueisAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshLayout.OnRefreshListener refresher;
    //private Button aluga;

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;

    private boolean clicado = false;

    private Spinner spinnerImoveis, spinnerClientes;
    private int id_imovel, id_cliente;
    private String alugado;
    private  AluguelDAO aluguelDAO;
    private EditText aluguel_inicio, aluguel_termino;
    private Button botao_inicio, botao_termino;
    private Calendar calendar;
    private DatePickerDialog dpd;
    private Button extras;
    private List<String> lista_extras;
    private int[] valores_extras = new int[3];
    private String data_ini, data_ter;
    private int[] datas_inicio = new int[3];
    private int[] datas_termino = new int[3];
    private DecimalFormat dec = new DecimalFormat("#0.00");
    //private ArrayList<Integer> valores_extras2 = new ArrayList<>();
    private ExtendedFloatingActionButton botao_acao;
    private FloatingActionButton botao_add_aluguel, botao_relatorio_aluguel;
    private TextView texto_aluga, texto_relatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alugueis);
        setTitle("  Aluguéis");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_baseline_assignment_24);

        recyclerView = findViewById(R.id.list_recycler_alugueis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        aluguelDAO = new AluguelDAO(this);

        adapter = new AlugueisAdapter(this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayoyt3);
//        refresher = new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(true);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        };
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.update();
                adapter.notifyDataSetChanged();
                loadSpinners();
                swipeRefreshLayout.setRefreshing(false);
                if(clicado){
                    mostrarBotoes();
                }
            }
        });
        //swipeRefreshLayout.setOnRefreshListener(refresher);



//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });


        spinnerImoveis = (Spinner) findViewById(R.id.spinnerImoveis);
        spinnerClientes = (Spinner) findViewById(R.id.spinnerClientes);

        spinnerImoveis.setOnItemSelectedListener(this);
        spinnerClientes.setOnItemSelectedListener(this);


        spinnerImoveis.setPrompt("Escolha uma imóvel");
        loadSpinners();
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setRefreshing(false);
        //refresher.onRefresh();

        //aluga = findViewById(R.id.alugaImovel);

        aluguel_inicio = findViewById(R.id.data_inicio);
        aluguel_termino = findViewById(R.id.data_termino);

        botao_inicio = findViewById(R.id.button); //Tentei usar refactor mas não mudava o nome, então deixei esse button. Deveria ser 'inicio_aluguel'
        botao_termino = findViewById(R.id.termino_aluguel);

        botao_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicado){
                    mostrarBotoes();
                }
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(AlugueisActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if(month+1 < 10) {
                            if(dayOfMonth < 10) {
                                data_ini = year + "-0" + (month+1) + "-0" + dayOfMonth;
                            }
                            else {
                                data_ini = year + "-0" + (month+1) + "-" + dayOfMonth;
                            }
                        }
                        else{
                            if(dayOfMonth < 10) {
                                data_ini = year + "-" + (month+1) + "-0" + dayOfMonth;
                            }
                            else{
                                data_ini = year + "-" + (month+1) + "-" + dayOfMonth;
                            }
                        }
                        aluguel_inicio.setText(data_ini);
                        datas_inicio[0] = year;
                        datas_inicio[1] = month;
                        datas_inicio[2] = dayOfMonth;
                    }
                }, ano, mes, dia);
                dpd.show();
            }
        });

        botao_termino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clicado){
                    mostrarBotoes();
                }
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(AlugueisActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if(month+1 < 10) {
                            if(dayOfMonth < 10) {
                                data_ter = year + "-0" + (month+1) + "-0" + dayOfMonth;
                            }
                            else {
                                data_ter = year + "-0" + (month+1) + "-" + dayOfMonth;
                            }
                        }
                        else{
                            if(dayOfMonth < 10) {
                                data_ter = year + "-" + (month+1) + "-0" + dayOfMonth;
                            }
                            else{
                                data_ter = year + "-" + (month+1) + "-" + dayOfMonth;
                            }
                        }
                        aluguel_termino.setText(data_ter);
                        datas_termino[0] = year;
                        datas_termino[1] = month;
                        datas_termino[2] = dayOfMonth;
                    }
                }, ano, mes, dia);
                dpd.show();
            }
        });

        extras = findViewById(R.id.botao_extras);
        extras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaDialogo();
            }
        });

        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this, R.anim.to_bottrom_anim);

        botao_add_aluguel = findViewById(R.id.addAluguel);
        botao_relatorio_aluguel = findViewById(R.id.relatorioAluguel);
        texto_aluga = findViewById(R.id.texto_adicionar_aluguel);
        texto_relatorio =  findViewById(R.id.texto_relatorio_aluguel);

        botao_acao = findViewById(R.id.extended_alugueis);
        botao_acao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarBotoes();
            }
        });

    }




    public void mostrarBotoes() {
        mostrar(clicado);
        animar(clicado);
        clicado = !clicado;
    }


    public void mostrar(boolean clicado){
        if(!clicado){
            botao_add_aluguel.setVisibility(View.VISIBLE);
            botao_add_aluguel.setEnabled(true);
            botao_relatorio_aluguel.setVisibility(View.VISIBLE);
            botao_relatorio_aluguel.setEnabled(true);
            texto_aluga.setVisibility(View.VISIBLE);
            texto_relatorio.setVisibility(View.VISIBLE);
        }
        else{
            botao_add_aluguel.setVisibility(View.INVISIBLE);
            botao_add_aluguel.setEnabled(false);
            botao_relatorio_aluguel.setVisibility(View.INVISIBLE);
            botao_relatorio_aluguel.setEnabled(false);
            texto_aluga.setVisibility(View.INVISIBLE);
            texto_relatorio.setVisibility(View.INVISIBLE);
        }
    }

    public void animar(boolean clicado) {
        if(!clicado) {
            botao_acao.startAnimation(rotateClose);
            botao_add_aluguel.startAnimation(fromBottom);
            botao_relatorio_aluguel.startAnimation(fromBottom);
            texto_aluga.setAnimation(fromBottom);
            texto_relatorio.setAnimation(fromBottom);
        }
        else {
            botao_acao.startAnimation(rotateClose);
            botao_add_aluguel.startAnimation(toBottom);
            botao_relatorio_aluguel.startAnimation(toBottom);
            texto_aluga.setAnimation(toBottom);
            texto_relatorio.setAnimation(toBottom);
        }
    }

    public void criaDialogo() {
        if(clicado){
            mostrarBotoes();
        }
        lista_extras = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Extras");
        builder.setMultiChoiceItems(R.array.extras_aluguel, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String itens[] = getResources().getStringArray(R.array.extras_aluguel);
                if(isChecked){
                    lista_extras.add(itens[which]);
                }
                else if(lista_extras.contains(itens[which])) {
                    lista_extras.remove(itens[which]);
                }
            }
        });
        builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zeraExtra();
                for(String item: lista_extras) {
                    //dado = dado +item;
                    if(item.equals("Seguro")){
                        valores_extras[0] = 1;
                    }
                    else if(item.equals("Chave extra")) {
                        valores_extras[1] = 1;
                    }
                    else if(item.equals("Mobiliado")) {
                        valores_extras[2] = 1;
                    }
                }
                Toast.makeText(AlugueisActivity.this, (valores_extras[0] == 1? "Seguro: sim" : "Seguro: não") + "\n" + (valores_extras[1] == 1? "Chave extra: sim" : "Chave extra: não") + "\n" + (valores_extras[2] == 1? "Mobiliado: sim" : "Mobiliado: não"), Toast.LENGTH_SHORT).show();
            }
        });

        builder.create();
        builder.show();
    }
    public double meuTrunca(double num,  int c)
    {
        double pot = pow(10, c);
        double fatorM = num * pot;
        double res = round(fatorM) / pot;
        return res;
    }

//    public String inverteString(String s) {
//        StringBuilder sb = new StringBuilder(s);
//        sb.reverse();
//        return sb.toString();
//    }

    public boolean comparaDatas(int[] inicio, int[] termino) {
        for (int i = 0; i < 3; i++) {
            if(inicio[i] > termino[i]) {
                return false;
            }
        }
        return true;
    }

    public void alugaImovel(View view) {

        ClienteDAO clienteDAO = new ClienteDAO(this);
        if(clicado){
            mostrarBotoes();
        }
        if(alugado.equals("sim")) {
            Toast.makeText(this, "Imóvel já está alugado!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(clienteDAO.retornaImovelId(id_cliente) != 0) {
            Toast.makeText(this, "Cliente já é locatário de um imóvel!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(aluguel_inicio.getText().toString().equals("") || aluguel_termino.getText().toString().equals("")) {
            Toast.makeText(this, "Preencha as datas", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
               if(!comparaDatas(datas_inicio, datas_termino)) {
                   Toast.makeText(this, "A data de início deve ser menor que a de término!", Toast.LENGTH_SHORT).show();
                   return;
               }
               else {
                   //String inicio = SimpleDateFormat.getDateInstance().format("dd/mm/yy");
                   //String termino = SimpleDateFormat.getDateInstance().format("dd/mm/yy");

                   //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                   //Calendar cal = Calendar.getInstance();
                   //SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
                   //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                   double seguro = valores_extras[0] == 1? meuTrunca(aluguelDAO.custoImovel(id_imovel)*0.1, 2) : 0;
                   double chave = valores_extras[1] == 1? 200.00 : 0;
                   double mobilia = valores_extras[2] == 1? meuTrunca(aluguelDAO.custoImovel(id_imovel)*0.3, 2) : 0;

                   //Aluguel aluguel = new Aluguel(0, id_imovel, id_cliente, sdf.format(cal.getTime()) , sdf.format(cal.getTime()) , seguro, chave, mobilia);
                   Aluguel aluguel = new Aluguel(0, id_imovel, id_cliente, data_ini , data_ter , seguro, chave, mobilia);

                   //Toast.makeText(this, "Id do cliente: " + id_cliente, Toast.LENGTH_SHORT).show();
                   aluguelDAO.add(aluguel);
                   ImovelDAO imovelDAO = new ImovelDAO(this);
                   Imovel imovel = imovelDAO.get(id_imovel);
                   Cliente cliente = clienteDAO.get(id_cliente);
                   cliente.setImovel_id(id_imovel);
                   clienteDAO.update(cliente);
                   imovelDAO.alugaImovel(imovel);
                   adapter.update();
                   adapter.notifyDataSetChanged();
                   loadSpinners();
                   //zeraExtra();
               }
           }
    }

    public void geraRelatorio(View view){
        if(clicado){
            mostrarBotoes();
        }
        Intent intent = new Intent(this, RelatorioAlugueis.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
        loadSpinners();
        if(clicado){
            mostrarBotoes();
        }
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
        if(clicado){
            mostrarBotoes();
        }
        if(parent.getId() == R.id.spinnerImoveis) {
            id_imovel = pegaId(parent.getSelectedItem().toString());
            alugado = parent.getSelectedItem().toString().substring(parent.getSelectedItem().toString().length() -3);
            //Toast.makeText(this, "Id do imóvel: " + id_imovel, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Alugado: " + alugado, Toast.LENGTH_SHORT).show();

        }
        else if(parent.getId() == R.id.spinnerClientes) {
            id_cliente = pegaId(parent.getSelectedItem().toString());
            //Toast.makeText(this, "Id do cliente: " + id_cliente, Toast.LENGTH_SHORT).show();
        }

    }

    public void zeraExtra() {
        valores_extras[0] = 0;
        valores_extras[1] = 0;
        valores_extras[2] = 0;
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
        AluguelDAO adao = new AluguelDAO(context);
        //Toast.makeText(context, "Cliente: " + this.aluguel_id.getText().toString() + "\nId: " + this.imovel_id.getText().toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, adao.imprimeImovel(Integer.parseInt(this.aluguel_id.getText().toString())), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, EditActivityAluguel.class);
        intent.putExtra("aluguelId", Integer.parseInt(this.aluguel_id.getText().toString()));
        context.startActivity(intent);
    }

}