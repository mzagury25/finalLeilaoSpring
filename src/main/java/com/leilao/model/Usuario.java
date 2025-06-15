package com.leilao.model;

import java.util.Objects;

public class Usuario {
    private final String nomeUsuario;
    private final String cpf;
    private final String email;
    private final String dataNascimento;

    public Usuario(String nomeUsuario, String cpf, String email, String dataNascimento) {
        this.nomeUsuario = nomeUsuario;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
    }

    public Usuario(String nomeUsuario) {
        this(nomeUsuario, "", "", "");
    }

    public String getNomeUsuario() { return nomeUsuario; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public String getDataNascimento() { return dataNascimento; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return cpf.equals(usuario.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}