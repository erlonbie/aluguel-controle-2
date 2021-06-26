package com.example.controle_alugueis;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AluguelDAO {
    private Context context;
    private SQLiteDatabase database;

    public AluguelDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    public ArrayList<Aluguel> getList() {
        ArrayList<Aluguel> alugueisList = new ArrayList<>();
        String sql = "SELECT * FROM alugueis ORDER BY id";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int imovel_id = cursor.getInt(1);
            int cliente_id = cursor.getInt(2);
            String inicio = cursor.getString(3);
            String termino = cursor.getString(4);
            double seguro = cursor.getDouble(5);
            double chaveEstra = cursor.getDouble(6);
            double mobiliado = cursor.getDouble(7);

//            SimpleDateFormat inicioFormatado = new SimpleDateFormat(inicio);
//            SimpleDateFormat terminoFormatado = new SimpleDateFormat(termino);

            alugueisList.add(new Aluguel(id, imovel_id, cliente_id, inicio, termino, seguro, chaveEstra, mobiliado));
        }

        return alugueisList;
    }

    public boolean add(Aluguel aluguel) {

        String sql = "INSERT INTO alugueis VALUES (NULL, "
                + "'" + aluguel.getImovel_id() + "', "
                + "'" + aluguel.getCliente_id() +  "', "
                + "'" + aluguel.getInicio().toString() + "', "
                + "'" + aluguel.getTermino().toString() + "', "
                + "'" + aluguel.getSeguro() + "', "
                + "'" + aluguel.getChaveExtra() + "', "
                + "'" + aluguel.getMobiliado() + "')";

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Aluguel Salvo!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao adicionar um cliente!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Aluguel get(int id) {

        String sql = "SELECT * FROM alugueis WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToNext()) {
            int imovel_id = cursor.getInt(1);
            int cliente_id = cursor.getInt(2);
            String inicio = cursor.getString(3);
            String termino = cursor.getString(4);
            double seguro = cursor.getDouble(5);
            double chaveEstra = cursor.getDouble(6);
            double mobiliado = cursor.getDouble(7);

//            SimpleDateFormat inicioFormatado = new SimpleDateFormat(inicio);
//            SimpleDateFormat terminoFormatado = new SimpleDateFormat(termino);

           return new Aluguel(id, imovel_id, cliente_id, inicio, termino, seguro, chaveEstra, mobiliado);
        }
        return null;
    }
}