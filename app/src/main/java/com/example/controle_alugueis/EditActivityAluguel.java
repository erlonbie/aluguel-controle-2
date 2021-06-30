package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.Math.pow;
import static java.lang.Math.round;

public class EditActivityAluguel extends AppCompatActivity {

    private AluguelDAO aluguelDAO;
    private ImovelDAO imovelDAO;
    private EditText aluguel_inicio, aluguel_termino;
    private Switch seguro, chave, mobilia;
    private int aluguelId;
    private double valor_seguro, valor_chave, valor_mobilia;
    private Calendar calendar;
    private DatePickerDialog dpd;
    private Button botao_inicio, botao_termino;
    private int[] datas_inicio = new int[3];
    private int[] datas_termino = new int[3];
    private String data_ini, data_ter;
    private int id_imovel, id_cliente;
    private Aluguel aluguel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_aluguel);

        Intent intent = getIntent();
        aluguelId = intent.getIntExtra("aluguelId", -1);
        aluguelDAO = new AluguelDAO(this);
        imovelDAO = new ImovelDAO(this);
        aluguel = aluguelDAO.get(aluguelId);

        aluguel_inicio = findViewById(R.id.edit_inicio_aluguel);
        aluguel_termino = findViewById(R.id.edit_termino_aluguel);

        botao_inicio = findViewById(R.id.edit_botao_inicio);
        botao_termino = findViewById(R.id.edit_botao_termino);

        botao_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(EditActivityAluguel.this, new DatePickerDialog.OnDateSetListener() {
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
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(EditActivityAluguel.this, new DatePickerDialog.OnDateSetListener() {
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

        seguro = findViewById(R.id.switch_seguro);
        seguro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    valor_seguro = meuTrunca(aluguelDAO.custoImovel(aluguel.getImovel_id())*0.1, 2);
                }
                else {
                    valor_seguro = 0;
                }
            }
        });

        chave = findViewById(R.id.switch_chave);
        chave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    valor_chave = 200.00;
                }
                else {
                    valor_chave = 0;
                }
            }
        });

        mobilia = findViewById(R.id.switch_mobiliado);
        mobilia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    valor_mobilia = meuTrunca(aluguelDAO.custoImovel(aluguel.getImovel_id())*0.3, 2);
                }
                else {
                    valor_mobilia = 0;
                }
            }
        });

        aluguel_inicio.setText(aluguel.getInicio());
        data_ini = aluguel.getInicio();
        aluguel_termino.setText(aluguel.getTermino());
        data_ter = aluguel.getTermino();

        valor_seguro = aluguel.getSeguro();
        valor_chave = aluguel.getChaveExtra();
        valor_mobilia = aluguel.getMobiliado();

        id_imovel = aluguel.getImovel_id();
        id_cliente = aluguel.getCliente_id();

        if (aluguel.getSeguro() > 0) {
            seguro.setChecked(true);
        }
        if (aluguel.getChaveExtra() > 0) {
            chave.setChecked(true);
        }
        if (aluguel.getMobiliado() > 0) {
            mobilia.setChecked(true);
        }

    }

    public double meuTrunca(double num,  int casas)
    {
        double pot = pow(10, casas);
        double fatorM = num * pot;
        double res = round(fatorM) / pot;
        return res;
    }

    public boolean comparaDatas(int[] inicio, int[] termino) {
        for (int i = 0; i < 3; i++) {
            if(inicio[i] > termino[i]) {
                return false;
            }
        }
        return true;
    }

    public void salvarClicado(View view) {
        if(!comparaDatas(datas_inicio, datas_termino)) {
            Toast.makeText(this, "A data de início deve ser menor que a de término!", Toast.LENGTH_SHORT).show();
            return;
        }

        Aluguel aluguel = new Aluguel(aluguelId, id_imovel, id_cliente, data_ini , data_ter , valor_seguro, valor_chave, valor_mobilia);
        aluguelDAO.update(aluguel);
        finish();
    }

    public void apagarClicado(View view) {
        aluguelDAO.delete(aluguelId);
        Imovel imovel = imovelDAO.get(aluguel.getImovel_id());
        imovelDAO.desocupaImovel(imovel);
        finish();
    }
}