package com.example.controle_alugueis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class EditActivityImovel extends AppCompatActivity {

    private ImovelDAO imovelDAO;
    private int imovelId;
    private TextView editEndereco, editCusto, editArea;
    private NumberPicker editQuartos, editSuites, editVagas;
    private RadioGroup group;
    private RadioButton buttonK, buttonCP, buttonA, buttonCC;
    private int alugado_status = 0;
    private Button buttonS;

    private EditText endereco, custo, area, quartos, suites, vagas;
    private TextInputLayout layout_categoria;
    private AutoCompleteTextView autocomplete_imoveis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_imovel2);
        setTitle("Imóvel");

//        LAYOUT ANTIGO

//        editEndereco = findViewById(R.id.endereco);
//        editCusto = findViewById(R.id.custo);
//        editArea = findViewById(R.id.area);
//        editQuartos = findViewById(R.id.qntQuartos);
//        editQuartos.setMaxValue(5);
//        editQuartos.setMinValue(1);
//        editSuites = findViewById(R.id.qntSuites);
//        editSuites.setMaxValue(5);
//        editSuites.setMinValue(1);
//        editVagas = findViewById(R.id.qntVagases);
//        editVagas.setMaxValue(4);
//        editVagas.setMinValue(1);
//        //addListenerButton();
//        group = findViewById(R.id.radioGrupo);
//        //int selectedId = group.getCheckedRadioButtonId();
//        buttonK = (RadioButton) findViewById(R.id.kitchenette);
//        buttonCP = (RadioButton) findViewById(R.id.casa);
//        buttonA = (RadioButton) findViewById(R.id.apartamento);
//        buttonCC = (RadioButton) findViewById(R.id.casaCond);

//        imovelDAO = new ImovelDAO(this);
//        Intent intent = getIntent();
//        imovelId = intent.getIntExtra("imovelId", -1);
//
//        if(imovelId != -1) {
//            alugado_status = imovelDAO.retornaAlugado(imovelId);
//            Imovel imovel = imovelDAO.get(imovelId);
//            Toast.makeText(this, imovelDAO.get(imovelId).getCategoria(), Toast.LENGTH_SHORT).show();
//            editEndereco.setText(imovel.getEndereco());
//            editCusto.setText(String.valueOf(imovel.getCusto()));
//            editArea.setText(String.valueOf(imovel.getArea()));
//            int x = imovel.getQntQuartos();
//            editQuartos.setValue(x);
//            editSuites.setValue(imovel.getQntSuites());
//            editVagas.setValue(imovel.getQntVagasEstacionamento());
//            if(imovel.getCategoria().equals("Kitchenette")){
//                //buttonK.setSelected(true);
//                buttonK.setChecked(true);
//            }
//            else if(imovel.getCategoria().equals("Casa Padrao")) {
//                //buttonCP.setSelected(true);
//                buttonCP.setChecked(true);
//            }
//            else if(imovel.getCategoria().equals("Apartamento")) {
//                //buttonA.setSelected(true);
//                buttonA.setChecked(true);
//            }
//            else if(imovel.getCategoria().equals("Casa Condominio")) {
//                //buttonCC.setSelected(true);
//                buttonCC.setChecked(true);
//            }
//        }

        layout_categoria = findViewById(R.id.layout_categoria);
        autocomplete_imoveis = findViewById(R.id.autocomplete_imoveis);
        String[] categorias = new String[] {
                "Kitchenette", "Casa Padrao", "Apartamento", "Casa Condominio"
        };
        ArrayAdapter<String> adapter_categoria = new ArrayAdapter<>(
                this, R.layout.dropdown_item_imoveis, categorias
        );
        autocomplete_imoveis.setAdapter(adapter_categoria);

        endereco = findViewById(R.id.endereco);
        custo = findViewById(R.id.custo);
        area = findViewById(R.id.area);

        quartos = findViewById(R.id.qntQuartos);
        quartos.setFilters(new InputFilter[] { new MinMaxFilter("1", "5")});
        suites = findViewById(R.id.qntSuites);
        suites.setFilters(new InputFilter[] { new MinMaxFilter("1", "5")});
        vagas = findViewById(R.id.qntVagases);
        vagas.setFilters(new InputFilter[] { new MinMaxFilter("1", "3")});

        autocomplete_imoveis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(autocomplete_imoveis.getText().toString().equals("Kitchenette")) {
                    quartos.setText("1");
                    quartos.setEnabled(false);
                    suites.setText("1");
                    suites.setEnabled(false);
                    vagas.setText("1");
                    vagas.setEnabled(false);
                }
                else {
                    quartos.setEnabled(true);
                    suites.setEnabled(true);
                    vagas.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(autocomplete_imoveis.getText().toString().equals("Kitchenette")) {
                    quartos.setText("1");
                    quartos.setEnabled(false);
                    suites.setText("1");
                    suites.setEnabled(false);
                    vagas.setText("1");
                    vagas.setEnabled(false);
                }
                else {
                    quartos.setEnabled(true);
                    suites.setEnabled(true);
                    vagas.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(autocomplete_imoveis.getText().toString().equals("Kitchenette")) {
                    quartos.setText("1");
                    quartos.setEnabled(false);
                    suites.setText("1");
                    suites.setEnabled(false);
                    vagas.setText("1");
                    vagas.setEnabled(false);
                }
                else {
                    quartos.setEnabled(true);
                    suites.setEnabled(true);
                    vagas.setEnabled(true);
                }
            }
        });


        imovelDAO = new ImovelDAO(this);
        Intent intent = getIntent();
        imovelId = intent.getIntExtra("imovelId", -1);

        if(imovelId != -1) {
            alugado_status = imovelDAO.retornaAlugado(imovelId);
            Imovel imovel = imovelDAO.get(imovelId);
            Toast.makeText(this, imovelDAO.get(imovelId).getCategoria(), Toast.LENGTH_SHORT).show();
            endereco.setText(imovel.getEndereco());
            custo.setText(String.valueOf(imovel.getCusto()));
            area.setText(String.valueOf(imovel.getArea()));
            quartos.setText(String.valueOf(imovel.getQntQuartos()));
            suites.setText(String.valueOf(imovel.getQntSuites()));
            vagas.setText(String.valueOf(imovel.getQntVagasEstacionamento()));
            autocomplete_imoveis.setText(imovel.getCategoria());
        }
    }

    public boolean verificaSuitesMaiorQueQuarto() {
        if(editQuartos.getValue() >= editSuites.getValue()) {
            return true;
        }
        return false;
    }

    public void salvarClicado2(View view) {

//        Imovel imovel = null;
//        if(buttonK.isChecked()) {
//            if(editEndereco.getText().toString().equals("") || editArea.getText().toString().equals("") || editCusto.getText().toString().equals("")) {
//                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            else{
//                if(!verificaSuitesMaiorQueQuarto()){
//                    Toast.makeText(this, "Quantidade de suites não pode ser maior que a de quartos!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                imovel = new Imovel(imovelId, buttonK.getText().toString(),editEndereco.getText().toString(), Double.parseDouble(editArea.getText().toString()), Double.parseDouble(editCusto.getText().toString()), editQuartos.getValue(), editSuites.getValue(), editVagas.getValue(), 0, 0, 0, alugado_status);
//            }
//        }
//        else if(buttonCP.isChecked()) {
//            if(editEndereco.getText().toString().equals("") || editArea.getText().toString().equals("") || editCusto.getText().toString().equals("")) {
//                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            else {
//                if(!verificaSuitesMaiorQueQuarto()){
//                    Toast.makeText(this, "Quantidade de suites não pode ser maior que a de quartos!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                imovel = new Imovel(imovelId, buttonCP.getText().toString(),editEndereco.getText().toString(), Double.parseDouble(editArea.getText().toString()), Double.parseDouble(editCusto.getText().toString()), editQuartos.getValue(), editSuites.getValue(), editVagas.getValue(), 0, 1, 0, alugado_status);
//            }
//        }
//        else if(buttonA.isChecked()) {
//            if(editEndereco.getText().toString().equals("") || editArea.getText().toString().equals("") || editCusto.getText().toString().equals("")) {
//                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            else {
//                if(!verificaSuitesMaiorQueQuarto()){
//                    Toast.makeText(this, "Quantidade de suites não pode ser maior que a de quartos!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                imovel = new Imovel(imovelId, buttonA.getText().toString(),editEndereco.getText().toString(), Double.parseDouble(editArea.getText().toString()), Double.parseDouble(editCusto.getText().toString()), editQuartos.getValue(), editSuites.getValue(), editVagas.getValue(), 1, 0, 1, alugado_status);
//            }
//        }
//        else if(buttonCC.isChecked()) {
//            if(editEndereco.getText().toString().equals("") || editArea.getText().toString().equals("") || editCusto.getText().toString().equals("")) {
//                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            else {
//                if(!verificaSuitesMaiorQueQuarto()){
//                    Toast.makeText(this, "Quantidade de suítes não pode ser maior que a de quartos!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                imovel = new Imovel(imovelId, buttonCC.getText().toString(),editEndereco.getText().toString(), Double.parseDouble(editArea.getText().toString()), Double.parseDouble(editCusto.getText().toString()), editQuartos.getValue(), editSuites.getValue(), editVagas.getValue(), 1, 1, 1, alugado_status);
//            }
//        }
//        else {
//            Toast.makeText(this, "Selecione um tipo de imóvel!", Toast.LENGTH_SHORT).show();
//            return;
//        }


        if(!autocomplete_imoveis.getText().toString().equals("Kitchenette") && !autocomplete_imoveis.getText().toString().equals("Casa Padrao") && !autocomplete_imoveis.getText().toString().equals("Apartamento") && !autocomplete_imoveis.getText().toString().equals("Casa Condominio")) {
            Toast.makeText(this, "Categoria Inválida!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(endereco.getText().toString().equals("") || custo.getText().toString().equals("") || area.getText().toString().equals("") || quartos.getText().toString().equals("") || suites.getText().toString().equals("") || vagas.getText().toString().equals("")){
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(Double.parseDouble(quartos.getText().toString()) < Double.parseDouble(suites.getText().toString())) {
            Toast.makeText(this, "Quantidade de suítes não pode ser maior que a de quartos!", Toast.LENGTH_SHORT).show();
            return;
        }
        int piscina=0, churrasqueira=0, playground=0;
        if(autocomplete_imoveis.getText().toString().equals("Kitchenette")) {
            piscina = 0; churrasqueira = 0; playground = 0;
        }
        else if(autocomplete_imoveis.getText().toString().equals("Casa Padrao")) {
            piscina = 0; churrasqueira = 1; playground = 0;
        }
        else if (autocomplete_imoveis.getText().toString().equals("Apartamento")) {
            piscina = 1; churrasqueira = 0; playground = 1;
        }
        else {
            piscina = 1; churrasqueira = 1; playground = 1;
        }

        Imovel imovel = new Imovel(imovelId, autocomplete_imoveis.getText().toString(), endereco.getText().toString(),
                Double.parseDouble(area.getText().toString()), Double.parseDouble(custo.getText().toString()),
                Integer.parseInt(quartos.getText().toString()), Integer.parseInt(suites.getText().toString()),
                Integer.parseInt(vagas.getText().toString()), piscina, churrasqueira, playground, alugado_status);

        boolean result;
        result = imovelId == -1 ? imovelDAO.add(imovel) : imovelDAO.update(imovel);
        Toast.makeText(this, "Imóvel salvo!", Toast.LENGTH_SHORT).show();

        if(result) finish();
    }

    public void apagarClicado2(View view) {
        AluguelDAO aluguelDAO = new AluguelDAO(this);
        if(aluguelDAO.temImovel(imovelId)){
            Toast.makeText(this, "Esse imóvel está vinculado a um aluguel! Apague o aluguel antes", Toast.LENGTH_SHORT).show();
            return;
        }
        imovelDAO.delete(imovelId);
        finish();
    }
}