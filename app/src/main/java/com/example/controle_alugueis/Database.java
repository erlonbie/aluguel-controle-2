package com.example.controle_alugueis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Alugueis.db";
    public static final String SQL_CREATE_PASS = "CREATE TABLE imoveis (id INTEGER PRIMARY KEY AUTOINCREMENT, categoria TEXT, endereco TEXT, area DOUBLE, custo DOUBLE, qntQuartos INT, qntSuites INT, qntVagasEstacionamento INT, piscina INT, churrasqueira INT, playground INT, alugado INT)";
    public static final String SQL_POPULATE_PASS = "INSERT INTO imoveis VALUES (NULL, 'Kitchenette', 'Rua 1', '22.5', '10.2', '1', '1', '1', '0', '0', '0', '0')";
    public static final String SQL_DELETE_PASS = "DROP TABLE IF EXISTS passwords";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PASS);
        db.execSQL(SQL_POPULATE_PASS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_PASS);
        onCreate(db);
    }
}
