package com.example.controle_alugueis;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class ImovelDAO {
    private Context context;
    private static ArrayList<Imovel> imoveisList = new ArrayList<>();

    public ImovelDAO(Context context) {
        this.context = context;
    }

    public ArrayList<Imovel> getList() {
        if(imoveisList.size() == 0) {
            imoveisList.add(new Imovel(0, "Kitchenette", "Rua do Kitchenette, 141", 10.5, 22.5, 1,1,1,0,0,0,0));
            imoveisList.add(new Imovel(1, "Casa Padrão", "Rua da Casa Padrão, 141", 10.5, 22.5, 2,1,2,0,1,0,0));
        }

        return imoveisList;
    }

    public boolean add(Imovel imovel) {
        imovel.setId(imoveisList.size());
        imoveisList.add(imovel);
        Toast.makeText(context, "Imovel Salvo!", Toast.LENGTH_SHORT).show();
        return true;
    }

    public boolean update(Imovel imovel) {
        imoveisList.set(imovel.getId(), imovel);
        Toast.makeText(context, "Imovel atualizado!", Toast.LENGTH_SHORT).show();
        return true;
    }

    public Imovel get(int id) {
        return imoveisList.get(id);
    }

    public String getCat(Imovel imovel) {
        return imovel.getCategoria();
    }
}
