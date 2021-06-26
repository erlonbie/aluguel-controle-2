package com.example.controle_alugueis;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Aluguel {
    private int id;
    private int cliente_id;
    private int imovel_id;
    private String inicio;
    private String termino;
    private double seguro;
    private double chaveExtra;
    private double mobiliado;

    public Aluguel(int id, int imovel_id, int cliente_id, String inicio, String termino, double seguro, double chaveExtra, double mobiliado) {
        this.id = id;
        this.imovel_id = imovel_id;
        this.cliente_id = cliente_id;
        this.inicio = inicio;
        this.termino = termino;
        this.seguro = seguro;
        this.chaveExtra = chaveExtra;
        this.mobiliado = mobiliado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public int getImovel_id() {
        return imovel_id;
    }

    public void setImovel_id(int clinte_imovel) {
        this.imovel_id = clinte_imovel;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public double getSeguro() {
        return seguro;
    }

    public void setSeguro(double seguro) {
        this.seguro = seguro;
    }

    public double getChaveExtra() {
        return chaveExtra;
    }

    public void setChaveExtra(double chaveExtra) {
        this.chaveExtra = chaveExtra;
    }

    public double getMobiliado() {
        return mobiliado;
    }

    public void setMobiliado(double mobiliado) {
        this.mobiliado = mobiliado;
    }
}
