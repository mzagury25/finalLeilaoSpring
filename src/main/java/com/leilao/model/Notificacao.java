package com.leilao.model;

import java.time.LocalDate;

public class Notificacao {
    private Long id;
    private Usuario usuario;
    private String mensagem;
    private LocalDate dataEnvio;
    private boolean lida;
    private TipoNotificacao tipo; // (LANCE_SUPERADO, LEILAO_FINALIZADO, etc.)
    private Leilao leilaoRelacionado;
}