package com.example.controle_alugueis;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AluguelDAO {
    private DecimalFormat dec = new DecimalFormat("#0.00");
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
            //Toast.makeText(context, "Aluguel Salvo!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao adicionar um cliente!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean update(Aluguel aluguel) {

        String sql = "UPDATE alugueis SET "
                + "imovel_id='" + aluguel.getImovel_id() + "', "
                + "cliente_id='" + aluguel.getCliente_id() +  "', "
                + "inicio='" + aluguel.getInicio() + "', "
                + "termino='" + aluguel.getTermino() + "', "
                + "seguro='" + aluguel.getSeguro() + "', "
                + "chaveExtra='" + aluguel.getChaveExtra() + "', "
                + "mobiliado='" + aluguel.getMobiliado() + "' "
                + "WHERE id=" + aluguel.getId();

        try {
            database.execSQL(sql);
            //Toast.makeText(context, "Aluguel Salvo!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao adicionar um cliente!" + e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM alugueis WHERE id=" +id;
        try {
            database.execSQL(sql);
            Toast.makeText(context, "Aluguel apagado!", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (SQLException e) {
            Toast.makeText(context, "Erro ao deletar um imóvel!" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public boolean temCliente(int id) {

        String sql = "SELECT * FROM alugueis WHERE cliente_id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToNext()) {
            return true;
        }
        return false;
    }

    public boolean temImovel(int id) {

        String sql = "SELECT * FROM alugueis WHERE imovel_id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToNext()) {
            return true;
        }
        return false;
    }

    public String nomeCliente(int id) {
        String nome = "123";
        String sql = "SELECT * FROM alugueis WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            nome = "Cliente";
            int cliente_id = cursor.getInt(2);
            String sql2 = "SELECT * FROM clientes WHERE id=" + cliente_id;
            Cursor cursor2 = database.rawQuery(sql2, null);
            if (cursor2.moveToNext()) {
                nome = cursor2.getString(2);
            }
        }

        return nome;
    }

    public String categoriaImovel(int id) {
        String cat = "123";
        String sql = "SELECT * FROM alugueis WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            cat = "Categoria";
            int imovel_id = cursor.getInt(1);
            String sql2 = "SELECT * FROM imoveis WHERE id=" + imovel_id;
            Cursor cursor2 = database.rawQuery(sql2, null);
            if (cursor2.moveToNext()) {
                cat = cursor2.getString(1);
            }
        }

        return cat;
    }

    public double custoImovel(int id) {
        double res = 0.00;
        String sql = "SELECT * FROM imoveis WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if (cursor.moveToNext()) {
            res = cursor.getDouble(4);
        }

        return res;
    }

    public String imprimeImovel(int id) {
        String tmp = "";
        String sql = "SELECT * FROM alugueis WHERE id=" + id;
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToNext()) {
            int imovel_id = cursor.getInt(1);
            int cliente_id = cursor.getInt(2);
            String inicio = cursor.getString(3);
            String termino = cursor.getString(4);
            double seguro = cursor.getDouble(5);
            double chaveExtra = cursor.getDouble(6);
            double mobiliado = cursor.getDouble(7);
            tmp = "Imovel_id: " + imovel_id + "\nCliente_id: " + cliente_id + "\nInicio: " + inicio + "\nTermino: " + termino + "\nSeguro: " + seguro + "\nChave: " + chaveExtra + "\nMobilia: " + mobiliado;
        }
        return tmp;
    }

    public String mudaFormatoData(String data) {
        String tmp = "";
        for (int i = 0; i < data.length(); i++){
            if(data.charAt(i) != '-') {
                tmp += data.charAt(i);
            }
        }
        return tmp;
    }

//    public double retornaSeguro(int id) {
//        double res = 0;
//        String sql = "SELECT * FROM alugueis WHERE id="+id;
//        Cursor cursor = database.rawQuery(sql, null);
//        if(cursor.moveToNext()) {
//            res =
//        }
//    }



    public String relatorioAlugueis(String data) {
        String s = "";
        double total = 0;
        double custo = 0;
        String dataModificado = mudaFormatoData(data);
//        String sql = "SELECT * FROM alugueis WHERE ("+mes+" BETWEEN month(inicio) AND month(termino)) AND ("+ano+" BETWEEN YEAR(inicio) AND YEAR(termino))";
//        String sql = "SELECT * FROM alugueis WHERE "+ano+"-"+mes+"-"+dia+" BETWEEN inicio AND termino";


        String sqlF = "SELECT * FROM alugueis";
        Cursor cursorF = database.rawQuery(sqlF, null);
        if (cursorF.moveToNext()){

            String ini = cursorF.getString(3);
            String ter = cursorF.getString(4);

            //String sql = "SELECT * FROM alugueis WHERE '"+ data + "' BETWEEN inicio AND termino";
            //String sql = "SELECT * FROM alugueis WHERE '"+ data + "' BETWEEN '"+ ini + "' AND '"+ ter +"'";
            //String sql = "SELECT * FROM alugueis WHERE '"+ ini + "' <= '"+data+"' AND '"+ ter +"' >= '"+data+"'";
            String sql = "SELECT * FROM alugueis WHERE  inicio <= '"+data+"' AND termino >= '"+data+"'";
            //String sql = "SELECT * FROM alugueis WHERE  CAST(strftime('%s', inicio) as integer) <= CAST(strftime('%s', '"+data+"') as integer) AND CAST(strftime('%s', termino) as integer) >= CAST(strftime('%s', '"+data+"') as integer)";
            //String sql = "SELECT * FROM alugueis WHERE  CAST(strftime('%s', inicio) as integer) <= CAST(strftime('%s', '"+data+"') as integer) AND CAST(strftime('%s', termino) as integer) >= CAST(strftime('%s', '"+data+"') as integer)";
            //String sql = "SELECT * FROM alugueis WHERE  CAST(strftime('%s', '"+ini+"') as integer) <= CAST(strftime('%s', '"+data+"') as integer) AND CAST(strftime('%s', '"+ter+"') as integer) >= CAST(strftime('%s', '"+data+"') as integer)";


            Cursor cursor = database.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                int id_imovel = cursor.getInt(1);
                int id_cliente = cursor.getInt(2);

                String sql2 = "SELECT * FROM imoveis WHERE id="+id_imovel;
                Cursor cursor2 = database.rawQuery(sql2, null);

                while (cursor2.moveToNext()) {
                    custo = cursor2.getDouble(4);

                    String sql3 = "SELECT * FROM clientes WHERE id="+id_cliente;
                    Cursor cursor3 = database.rawQuery(sql3, null);

                    while (cursor3.moveToNext()) {
                        s += "Cliente: " + cursor3.getString(2) +'\n';
                    }
                    s += "Imóvel_ID: " + cursor2.getString(0)+", Categoria: "+cursor2.getString(1)+'\n'+"Custo: R$ "+dec.format(custo)+" "+'\n';
                }
                double seg = Double.parseDouble(cursor.getString(5));
                double chv = Double.parseDouble(cursor.getString(6));
                double mob = Double.parseDouble(cursor.getString(7));
                total = seg + chv + mob;

                s += "Na data "+data+ ":"+'\n'+"    "+"Seguro: R$ "+dec.format(seg)+" "+'\n'+"    "+"Chave-extra: R$ "+dec.format(chv)+" "+'\n'+"    "+"Mobília: R$ "+dec.format(mob)+" "+'\n'+"    "+"\t\tSubtotal: R$ "+dec.format(total)+" "+'\n'+ "Total(Subtotal + custo): R$ "+dec.format(total+custo) + " " +'\n' +'\n' +"-----------------------------------------------"+'\n'+'\n';
            }

        }

        if (s.length() > 0) {
            return s;
        }
        return "Nenhum aluguel encontrado no mês selecionado";
    }

}
