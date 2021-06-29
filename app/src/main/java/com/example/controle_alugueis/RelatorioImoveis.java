package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class RelatorioImoveis extends AppCompatActivity {

    private TextView texto_relatorio;
    private ImovelDAO imovelDAO;
    //private ArrayList<String> imoveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_imoveis);

        imovelDAO = new ImovelDAO(this);
        //imoveis = new ArrayList<String>();
        //imoveis = imovelDAO.getListNames();
        texto_relatorio = findViewById(R.id.texto_relatorio_imoveis);
        texto_relatorio.setText(imovelDAO.getImoveisRelatorio());
    }
}