package com.example.controle_alugueis;

public class Cliente {
    private int id;
    private int imovel_id;
    private String nome;
    private int idade;

    public Cliente(int id, int imovel_id, String nome, int idade) {
        this.id = id;
        this.imovel_id = imovel_id;
        this.nome = nome;
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImovel_id() {
        return imovel_id;
    }

    public void setImovel_id(int imovel_id) {
        this.imovel_id = imovel_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
