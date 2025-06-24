package com.leilao.model;

import java.time.LocalDate;

public class Avaliacao {
    private Long id;
    private Usuario avaliador;
    private Usuario avaliado;
    private int pontuacao; // 1-5 stars
    private String comentario;
    private LocalDate dataAvaliacao;
    private Leilao leilao; // Related auction
}