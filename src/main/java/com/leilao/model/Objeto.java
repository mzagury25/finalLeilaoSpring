package com.leilao.model;

public class Objeto {
    private int id;
    private String nome;
    private String descricao;
    private String foto;

    public Objeto(int id, String nome, String descricao, String foto, int leilaoId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.foto = foto;
    }
}
