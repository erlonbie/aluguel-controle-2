package com.example.controle_alugueis;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class ImovelDAO {
    private Context context;
    private SQLiteDatabase database;
    //private static ArrayList<Imovel> imoveisList = new ArrayList<>();

    public ImovelDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    public ArrayList<Imovel> getList() {
          ArrayList<Imovel> imoveisList = new ArrayList<>();
          String sql = "SELECT * FROM imoveis ORDER BY id";
          Cursor cursor = database.rawQuery(sql, null);
//        if(imoveisList.size() == 0) {
//            imoveisList.add(new Imovel(0, "Kitchenette", "Rua do Kitchenette, 141", 10.5, 22.5, 1,1,1,0,0,0,0));
//            imoveisList.add(new Imovel(1, "Casa Padrão", "Rua da Casa Padrão, 141", 10.5, 22.5, 2,1,2,0,1,0,0));
//        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String cat = cursor.getString(1);
            String end = cursor.getString(2);
            double area = cursor.getDouble(3);
            double custo = cursor.getDouble(4);
            int quarto = cursor.getInt(5);
            int suite = cursor.getInt(6);
            int vaga = cursor.getInt(7);
            int piscina = cursor.getInt(8);
            int churra = cursor.getInt(9);
            int play = cursor.getInt(10);
            int alugado = cursor.getInt(11);
            imoveisList.add(new Imovel(id, cat, end, area, custo, quarto, suite, vaga, piscina, churra, play, alugado));
        }

        return imoveisList;
    }

    public ArrayList<String> getListNames() {
        String tmp = "";
        String[] opcoes = {"não","sim"};
        ArrayList<String> imoveisList = new ArrayList<>();
        String sql = "SELECT * FROM imoveis ORDER BY id";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String cat = cursor.getString(1);
            String end = cursor.getString(2);
            double area = cursor.getDouble(3);
            double custo = cursor.getDouble(4);
            int quarto = cursor.getInt(5);
            int suite = cursor.getInt(6);
            int vaga = cursor.getInt(7);
            int piscina = cursor.getInt(8);
            int churra = cursor.getInt(9);
            int play = cursor.getInt(10);
            int alugado = cursor.getInt(11);
            tmp += "Id: " + id + "|" + cat + "\nArea: " + area + " m2|Custo: R$" + custo + "\nQuartos: " + quarto + "|Suítes: " + suite + "|Vagas: " + vaga + "\nPiscina: " + opcoes[piscina] + "|Churrasqueira: " + opcoes[churra] + "|Playground: " + opcoes[play] + "\nAlugado: " + opcoes[alugado];
            imoveisList.add(tmp);
            tmp = "";
        }

        return imoveisList;
    }

    public String getImoveisRelatorio() {
        String tmp = "";
        String[] opcoes = {"não","sim"};
        String sql = "SELECT * FROM imoveis ORDER BY id";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String cat = cursor.getString(1);
            String end = cursor.getString(2);
            double area = cursor.getDouble(3);
            double custo = cursor.getDouble(4);
            int quarto = cursor.getInt(5);
            int suite = cursor.getInt(6);
            int vaga = cursor.getInt(7);
            int piscina = cursor.getInt(8);
            int churra = cursor.getInt(9);
            int play = cursor.getInt(10);
            int alugado = cursor.getInt(11);
            tmp += "\nId: " + id + "|" + cat + "\nArea: " + area + " m2|Custo: R$" + custo + "\nQuartos: " + quarto + "|Suítes: " + suite + "|Vagas: " + vaga + "\nPiscina: " + opcoes[piscina] + "|Churrasqueira: " + opcoes[churra] + "|Playground: " + opcoes[play] + "\nAlugado: " + opcoes[alugado] +'\n';
        }

        return tmp;
    }

    public boolean add(Imovel imovel) {
//        imovel.setId(imoveisList.size());
//        imoveisList.add(imovel);
//        Toast.makeText(context, "Imovel Salvo!", Toast.LENGTH_SHORT).show();

        String sql = "INSERT INTO imoveis VALUES (NULL, "
                + "'" + imovel.getCategoria() + "', "
                + "'" + imovel.getEndereco() +  "', "
                + "'" + imovel.getArea() + "', "
                + "'" + imovel.getCusto() + "', "
                + "'" + imovel.getQntQuartos() + "', "
                + "'" + imovel.getQntSuites() + "', "
                + "'" + imovel.getQntVagasEstacionamento() + "', "
                + "'" + imovel.getPiscina() + "', "
                + "'" + imovel.getChurrasqueira() + "', "
                + "'" + imovel.getPlayground() + "', "
                + "'" + imovel.getAlugado() + "')";

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Imóvel Salvo!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao adicionar um imóvel!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean update(Imovel imovel) {
//        imoveisList.set(imovel.getId(), imovel);
//        Toast.makeText(context, "Imovel atualizado!", Toast.LENGTH_SHORT).show();
//        return true;

        String sql = "UPDATE imoveis SET "
                + "categoria='" + imovel.getCategoria() + "', "
                + "endereco='" + imovel.getEndereco() +  "', "
                + "area='" + imovel.getArea() + "', "
                + "custo='" + imovel.getCusto() + "', "
                + "qntQuartos='" + imovel.getQntQuartos() + "', "
                + "qntSuites='" + imovel.getQntSuites() + "', "
                + "qntVagasEstacionamento='" + imovel.getQntVagasEstacionamento() + "', "
                + "piscina='" + imovel.getPiscina() + "', "
                + "churrasqueira='" + imovel.getChurrasqueira() + "', "
                + "playground='" + imovel.getPlayground() + "', "
                + "alugado='" + imovel.getAlugado() + "' "
                + "WHERE id=" +imovel.getId();

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Imóvel atualizado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao atualizar um imóvel!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM imoveis WHERE id=" +id;
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Imóvel apagado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar um imóvel!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Imovel get(int id) {
        //return imoveisList.get(id);

        String sql = "SELECT * FROM imoveis WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToNext()) {
            String cat = cursor.getString(1);
            String end = cursor.getString(2);
            double area = cursor.getDouble(3);
            double custo = cursor.getDouble(4);
            int quarto = cursor.getInt(5);
            int suite = cursor.getInt(6);
            int vaga = cursor.getInt(7);
            int piscina = cursor.getInt(8);
            int churra = cursor.getInt(9);
            int play = cursor.getInt(10);
            int alugado = cursor.getInt(11);

            return new Imovel(id, cat, end, area, custo, quarto, suite, vaga, piscina, churra, play, alugado);
        }
        return null;
    }

    public boolean alugaImovel(Imovel imovel) {
//        imoveisList.set(imovel.getId(), imovel);
//        Toast.makeText(context, "Imovel atualizado!", Toast.LENGTH_SHORT).show();
//        return true;

        String sql = "UPDATE imoveis SET "
                + "categoria='" + imovel.getCategoria() + "', "
                + "endereco='" + imovel.getEndereco() +  "', "
                + "area='" + imovel.getArea() + "', "
                + "custo='" + imovel.getCusto() + "', "
                + "qntQuartos='" + imovel.getQntQuartos() + "', "
                + "qntSuites='" + imovel.getQntSuites() + "', "
                + "qntVagasEstacionamento='" + imovel.getQntVagasEstacionamento() + "', "
                + "piscina='" + imovel.getPiscina() + "', "
                + "churrasqueira='" + imovel.getChurrasqueira() + "', "
                + "playground='" + imovel.getPlayground() + "', "
                + "alugado='" + 1 + "' "
                + "WHERE id=" +imovel.getId();

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Imóvel alugado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao alugar um imóvel!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean desocupaImovel(Imovel imovel) {
//        imoveisList.set(imovel.getId(), imovel);
//        Toast.makeText(context, "Imovel atualizado!", Toast.LENGTH_SHORT).show();
//        return true;

        String sql = "UPDATE imoveis SET "
                + "categoria='" + imovel.getCategoria() + "', "
                + "endereco='" + imovel.getEndereco() +  "', "
                + "area='" + imovel.getArea() + "', "
                + "custo='" + imovel.getCusto() + "', "
                + "qntQuartos='" + imovel.getQntQuartos() + "', "
                + "qntSuites='" + imovel.getQntSuites() + "', "
                + "qntVagasEstacionamento='" + imovel.getQntVagasEstacionamento() + "', "
                + "piscina='" + imovel.getPiscina() + "', "
                + "churrasqueira='" + imovel.getChurrasqueira() + "', "
                + "playground='" + imovel.getPlayground() + "', "
                + "alugado='" + 0 + "' "
                + "WHERE id=" +imovel.getId();

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Imóvel alugado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao alugar um imóvel!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean desocupaIomvel(Imovel imovel) {
//        imoveisList.set(imovel.getId(), imovel);
//        Toast.makeText(context, "Imovel atualizado!", Toast.LENGTH_SHORT).show();
//        return true;

        String sql = "UPDATE imoveis SET "
                + "categoria='" + imovel.getCategoria() + "', "
                + "endereco='" + imovel.getEndereco() +  "', "
                + "area='" + imovel.getArea() + "', "
                + "custo='" + imovel.getCusto() + "', "
                + "qntQuartos='" + imovel.getQntQuartos() + "', "
                + "qntSuites='" + imovel.getQntSuites() + "', "
                + "qntVagasEstacionamento='" + imovel.getQntVagasEstacionamento() + "', "
                + "piscina='" + imovel.getPiscina() + "', "
                + "churrasqueira='" + imovel.getChurrasqueira() + "', "
                + "playground='" + imovel.getPlayground() + "', "
                + "alugado='" + 0 + "' "
                + "WHERE id=" +imovel.getId();

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Imóvel alugado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao alugar um imóvel!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public int retornaAlugado(int id) {
        int res = 0;
        String sql = "SELECT * FROM imoveis WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            res = cursor.getInt(11);
        }
        return res;
    }


    public String getCat(Imovel imovel) {
        return imovel.getCategoria();
    }
}
