package com.example.controle_alugueis;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class ClienteDAO {
    private Context context;
    private SQLiteDatabase database;

    public ClienteDAO(Context context) {
        this.context = context;
        this.database = (new Database(context)).getWritableDatabase();
    }

    public ArrayList<Cliente> getList() {
        ArrayList<Cliente> clientesList = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY id";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int imovel_id = cursor.getInt(1);
            String nome = cursor.getString(2);
            int idade = cursor.getInt(3);
            clientesList.add(new Cliente(id, imovel_id, nome, idade));
        }

        return clientesList;
    }

    public ArrayList<String> getListNames() {
        String tmp = "";
        ArrayList<String> clientesList = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY id";
        Cursor cursor = database.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int imovel_id = cursor.getInt(1);
            String nome = cursor.getString(2);
            int idade = cursor.getInt(3);
            tmp += "Id: " + id + "|Imovel_id: " + imovel_id + "\nNome: " + nome + "|Idade: " + idade;
            clientesList.add(tmp);
            tmp = "";
        }

        return clientesList;
    }

    public boolean add(Cliente cliente) {

        String sql = "INSERT INTO clientes VALUES (NULL, "
                + "'" + cliente.getImovel_id() + "', "
                + "'" + cliente.getNome() +  "', "
                + "'" + cliente.getIdade() + "') ";

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Cliente Salvo!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao adicionar um cliente!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean update(Cliente cliente) {

        String sql = "UPDATE clientes SET "
                + "imovel_id='" + cliente.getImovel_id() + "', "
                + "nome='" + cliente.getNome() +  "', "
                + "idade='" + cliente.getIdade() + "' "
                + "WHERE id=" +cliente.getId();

        try {
            database.execSQL(sql);
            Toast.makeText(context, "Cliente atualizado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao atualizar um cliente!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM clientes WHERE id=" +id;
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Cliente apagado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar um cliente!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public Cliente get(int id) {

        String sql = "SELECT * FROM clientes WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToNext()) {
            int imovel_id = cursor.getInt(1);
            String nome = cursor.getString(2);
            int idade = cursor.getInt(3);

            return new Cliente(id, imovel_id, nome, idade);
        }
        return null;
    }
}
