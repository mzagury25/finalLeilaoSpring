package com.leilao.model;

import java.time.LocalDate;

public class Lance {
    private final Usuario participante;
    private final int valor;
    private final LocalDate data;

    public Lance(Usuario participante, int valor, LocalDate data) {
        this.participante = participante;
        this.valor = valor;
        this.data = data;
    }

    public Usuario getParticipante() {
        return participante;
    }
    public int getValor() { return valor; }
    public LocalDate getData() { return data; }
}