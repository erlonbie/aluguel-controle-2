package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class RelatorioAlugueis extends AppCompatActivity {

    private TextView texto;
    private EditText data_relatorio;
    private AluguelDAO aluguelDAO;
    private Button botao_relatorio_data;
    private Calendar calendar;
    private DatePickerDialog dpd;
    private String mesR, anoR, diaR, data;
    private Button gerar_relatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_alugueis);

        aluguelDAO = new AluguelDAO(this);

        texto = findViewById(R.id.texto_relatorio);
        botao_relatorio_data = findViewById(R.id.botao_data_relatorio);
        data_relatorio = findViewById(R.id.data_relatorio);
        gerar_relatorio = findViewById(R.id.botao_gera_relatorio);

        botao_relatorio_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH);
                int ano = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(RelatorioAlugueis.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        data_relatorio.setText(year + "-" + (month+1) + "-" + dayOfMonth);
//                        mesR = (year + "-" + (month+1) + "-" + dayOfMonth).substring(5, (month+1 > 9? 7 : 6));
//                        anoR = (year + "-" + (month+1) + "-" + dayOfMonth).substring(0, 4);
//                        diaR = (year + "-" + (month+1) + "-" + dayOfMonth).substring((month+1 > 9? 7 : 6));
                        data = year + "-" + (month+1) + "-" + dayOfMonth;
                    }
                }, ano, mes, dia);
                dpd.show();
            }
        });

        gerar_relatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data_relatorio.getText().toString().length() == 0){
                    Toast.makeText(RelatorioAlugueis.this, "Selecione uma data", Toast.LENGTH_SHORT).show();
                    return;
                }
                texto.setText(aluguelDAO.relatorioAlugueis(data));
            }
        });

    }
}