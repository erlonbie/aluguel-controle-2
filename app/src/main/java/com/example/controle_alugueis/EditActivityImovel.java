package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivityImovel extends AppCompatActivity {

    private ImovelDAO imovelDAO;
    private int imovelId;
    private TextView editEndereco, editCusto, editArea;
    private NumberPicker editQuartos, editSuites, editVagas;
    private RadioGroup group;
    private RadioButton buttonK, buttonCP, buttonA, buttonCC;
    private int alugado_status = 0;
    private Button buttonS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_imovel);

        editEndereco = findViewById(R.id.endereco);
        editCusto = findViewById(R.id.custo);
        editArea = findViewById(R.id.area);
        editQuartos = findViewById(R.id.qntQuartos);
        editQuartos.setMaxValue(5);
        editQuartos.setMinValue(1);
        editSuites = findViewById(R.id.qntSuites);
        editSuites.setMaxValue(5);
        editSuites.setMinValue(1);
        editVagas = findViewById(R.id.qntVagases);
        editVagas.setMaxValue(4);
        editVagas.setMinValue(1);
        //addListenerButton();
        group = findViewById(R.id.radioGrupo);
        //int selectedId = group.getCheckedRadioButtonId();
        buttonK = (RadioButton) findViewById(R.id.kitchenette);
        buttonCP = (RadioButton) findViewById(R.id.casa);
        buttonA = (RadioButton) findViewById(R.id.apartamento);
        buttonCC = (RadioButton) findViewById(R.id.casaCond);
        imovelDAO = new ImovelDAO(this);


        Intent intent = getIntent();
        imovelId = intent.getIntExtra("imovelId", -1);




        if(imovelId != -1) {
            alugado_status = imovelDAO.retornaAlugado(imovelId);
            Imovel imovel = imovelDAO.get(imovelId);
            Toast.makeText(this, imovelDAO.get(imovelId).getCategoria(), Toast.LENGTH_SHORT).show();
            editEndereco.setText(imovel.getEndereco());
            editCusto.setText(String.valueOf(imovel.getCusto()));
            editArea.setText(String.valueOf(imovel.getArea()));
            int x = imovel.getQntQuartos();
            editQuartos.setValue(x);
            editSuites.setValue(imovel.getQntSuites());
            editVagas.setValue(imovel.getQntVagasEstacionamento());
            if(imovel.getCategoria().equals("Kitchenette")){
                //buttonK.setSelected(true);
                buttonK.setChecked(true);
            }
            else if(imovel.getCategoria().equals("Casa Padrao")) {
                //buttonCP.setSelected(true);
                buttonCP.setChecked(true);
            }
            else if(imovel.getCategoria().equals("Apartamento")) {
                //buttonA.setSelected(true);
                buttonA.setChecked(true);
            }
            else if(imovel.getCategoria().equals("Casa Condominio")) {
                //buttonCC.setSelected(true);
                buttonCC.setChecked(true);
            }
        }
    }

//    public void addListenerButton() {
//        group = findViewById(R.id.radioGrupo);
//        //int selectedId = group.getCheckedRadioButtonId();
//        //button = (RadioButton) findViewById(selectedId);
//        buttonS = (Button) findViewById(R.id.salvaImovel);
//
//        buttonS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int selectedId = group.getCheckedRadioButtonId();
//                button = (RadioButton) findViewById(selectedId);
//                Toast.makeText(EditActivityImovel.this, button.getText(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void salvarClicado(View view) {

        Imovel imovel = null;
        if(buttonK.isChecked()) {
            if(editEndereco.getText().toString().equals("") || editArea.getText().toString().equals("") || editCusto.getText().toString().equals("")) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }
            else{
                imovel = new Imovel(imovelId, buttonK.getText().toString(),editEndereco.getText().toString(), Double.parseDouble(editArea.getText().toString()), Double.parseDouble(editCusto.getText().toString()), editQuartos.getValue(), editSuites.getValue(), editVagas.getValue(), 0, 0, 0, alugado_status);
            }
        }
        else if(buttonCP.isChecked()) {
            if(editEndereco.getText().toString().equals("") || editArea.getText().toString().equals("") || editCusto.getText().toString().equals("")) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                imovel = new Imovel(imovelId, buttonCP.getText().toString(),editEndereco.getText().toString(), Double.parseDouble(editArea.getText().toString()), Double.parseDouble(editCusto.getText().toString()), editQuartos.getValue(), editSuites.getValue(), editVagas.getValue(), 0, 1, 0, alugado_status);
            }
        }
        else if(buttonA.isChecked()) {
            if(editEndereco.getText().toString().equals("") || editArea.getText().toString().equals("") || editCusto.getText().toString().equals("")) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                imovel = new Imovel(imovelId, buttonA.getText().toString(),editEndereco.getText().toString(), Double.parseDouble(editArea.getText().toString()), Double.parseDouble(editCusto.getText().toString()), editQuartos.getValue(), editSuites.getValue(), editVagas.getValue(), 1, 0, 1, alugado_status);
            }
        }
        else if(buttonCC.isChecked()) {
            if(editEndereco.getText().toString().equals("") || editArea.getText().toString().equals("") || editCusto.getText().toString().equals("")) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                imovel = new Imovel(imovelId, buttonCC.getText().toString(),editEndereco.getText().toString(), Double.parseDouble(editArea.getText().toString()), Double.parseDouble(editCusto.getText().toString()), editQuartos.getValue(), editSuites.getValue(), editVagas.getValue(), 1, 1, 1, alugado_status);
            }
        }
        else {
            Toast.makeText(this, "Selecione um tipo de imóvel!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result;
        result = imovelId == -1 ? imovelDAO.add(imovel) : imovelDAO.update(imovel);

        if(result) finish();
    }

    public void apagarClicado(View view) {
        AluguelDAO aluguelDAO = new AluguelDAO(this);
        if(aluguelDAO.temImovel(imovelId)){
            Toast.makeText(this, "Esse imóvel está vinculado a um aluguel! Apague o aluguel antes", Toast.LENGTH_SHORT).show();
            return;
        }
        imovelDAO.delete(imovelId);
        finish();
    }



}