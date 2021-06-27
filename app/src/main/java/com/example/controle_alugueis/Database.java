package com.example.controle_alugueis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Alugueis.db";

    public static final String SQL_CREATE_IMOVEIS = "CREATE TABLE imoveis (id INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT, endereco TEXT, area DOUBLE, custo DOUBLE, qntQuartos INT, qntSuites INT, qntVagasEstacionamento INT, piscina INT, churrasqueira INT, playground INT, alugado INT)";
    public static final String SQL_CREATE_CLIENES = "CREATE TABLE clientes (id INTEGER PRIMARY KEY AUTOINCREMENT, imovel_id INT, nome TEXT, idade INT)";
    public static final String SQL_CREATE_ALUGUEIS = "CREATE TABLE alugueis (id INTEGER PRIMARY KEY AUTOINCREMENT, imovel_id INT, cliente_id INT, inicio TEXT, termino TEXT, seguro DOUBLE, chaveExtra DOUBLE, mobiliado DOUBLE, FOREIGN KEY (imovel_id) REFERENCES imoveis (id), FOREIGN KEY (cliente_id) REFERENCES clientes (id))";

    public static final String SQL_POPULATE_IMOVEIS = "INSERT INTO imoveis VALUES (NULL, 'Kitchenette', 'Rua 1', '22.5', '10.2', '1', '1', '1', '0', '0', '0', '0')";
    public static final String SQL_POPULATE_IMOVEIS2 = "INSERT INTO imoveis VALUES (NULL, 'Casa Padrao', 'Rua 2', '19.5', '1.2', '2', '1', '2', '0', '1', '0', '0')";
    public static final String SQL_POPULATE_CLIENTES = "INSERT INTO clientes VALUES (NULL, '0', 'Erlon Bie', '29')";
    public static final String SQL_POPULATE_CLIENTES2 = "INSERT INTO clientes VALUES (NULL, '0', 'Agner Bie', '31')";


    public static final String SQL_DELETE_IMOVEIS = "DROP TABLE IF EXISTS imoveis";
    public static final String SQL_DELETE_CLIENTES = "DROP TABLE IF EXISTS clientes";
    public static final String SQL_DELETE_ALUGUEIS = "DROP TABLE IF EXISTS alugueis";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_IMOVEIS);
        db.execSQL(SQL_CREATE_CLIENES);
        db.execSQL(SQL_CREATE_ALUGUEIS);

        db.execSQL(SQL_POPULATE_IMOVEIS);
        db.execSQL(SQL_POPULATE_IMOVEIS2);

        db.execSQL(SQL_POPULATE_CLIENTES);
        db.execSQL(SQL_POPULATE_CLIENTES2);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALUGUEIS);
        onCreate(db);
        db.execSQL(SQL_DELETE_IMOVEIS);
        onCreate(db);
        db.execSQL(SQL_DELETE_CLIENTES);
        onCreate(db);
    }
}
