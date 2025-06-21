package com.leilao.model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Leilao {
    private String nomeObjeto;
    private int lanceMinimo;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private EstadoLeilao estadoLeilao;
    private List<Lance> lances = new ArrayList<>();
    private Set<Usuario> participantes = new HashSet<>();

    private Leilao(Builder builder) {
        this.nomeObjeto = builder.nomeObjeto;
        this.lanceMinimo = builder.lanceMinimo;
        this.dataInicio = builder.dataInicio;
        this.dataTermino = builder.dataTermino;
        this.estadoLeilao = EstadoLeilao.INATIVO;
    }
    public static class Builder {
        private String nomeObjeto;
        private int lanceMinimo;
        private LocalDate dataInicio;
        private LocalDate dataTermino;

        public Builder setNomeObjeto(String nomeObjeto) {
            this.nomeObjeto = nomeObjeto;
            return this;
        }

        public Builder setLanceMinimo(int lanceMinimo) {
            this.lanceMinimo = lanceMinimo;
            return this;
        }

        public Builder setDataInicio(LocalDate dataInicio) {
            this.dataInicio = dataInicio;
            return this;
        }

        public Builder setDataTermino(LocalDate dataTermino) {
            this.dataTermino = dataTermino;
            return this;
        }

        public Leilao build() {
            if (dataInicio.isAfter(dataTermino)) {
                throw new IllegalArgumentException("A data de início deve ser anterior ou igual à data de término.");
            }
            return new Leilao(this);
        }
    }

    public void atualizarEstado(LocalDate dataAtual) {
        if (estadoLeilao == EstadoLeilao.INATIVO && (dataAtual.isEqual(dataInicio) || dataAtual.isAfter(dataInicio))) {
            estadoLeilao = EstadoLeilao.ABERTO;
        } else if (estadoLeilao == EstadoLeilao.ABERTO && dataAtual.isAfter(dataTermino)) {
            estadoLeilao = lances.isEmpty() ? EstadoLeilao.EXPIRADO : EstadoLeilao.FINALIZADO;
        }
    }

    public boolean registrarLance(Lance novoLance) {
        if (estadoLeilao != EstadoLeilao.ABERTO) return false;
        if (novoLance.getValor() < lanceMinimo) return false;

        if (!lances.isEmpty()) {
            Lance ultimo = lances.get(lances.size() - 1);
            boolean mesmoUsuario = ultimo.getParticipante().equals(novoLance.getParticipante());
            boolean valorInvalido = novoLance.getValor() <= ultimo.getValor();
            if (mesmoUsuario || valorInvalido) return false;
        }

        lances.add(novoLance);
        participantes.add(novoLance.getParticipante());
        return true;
    }

    public Optional<Usuario> getGanhador() {
        if (estadoLeilao != EstadoLeilao.FINALIZADO) {
            return Optional.empty();
        }

        Optional<Lance> maiorLance = lances.stream()
                .max(Comparator.comparingInt(Lance::getValor));

        maiorLance.ifPresent(l -> System.out.println(
               // STR."Parabéns ao ganhador: \{l.getParticipante().getNomeUsuario()}! Com o lance de R$\{l.getValor()} no objeto \"\{nomeObjeto}\"."
        ));

        return maiorLance.map(Lance::getParticipante);
    }

    public OptionalInt getMaiorLance() {
        return lances.stream().mapToInt(Lance::getValor).max();
    }

    public OptionalInt getMenorLance() {
        return lances.stream().mapToInt(Lance::getValor).min();
    }

    public List<Lance> filtrarLancesPorData(LocalDate dataInicial, LocalDate dataFinal) {
        return lances.stream()
                .filter(l -> !l.getData().isBefore(dataInicial) && !l.getData().isAfter(dataFinal))
                .collect(Collectors.toList());
    }

    public boolean podeRemoverParticipante(Usuario u) {
        return lances.stream().noneMatch(l -> l.getParticipante().equals(u));
    }

    public List<Lance> getLancesOrdenados() {
        return lances.stream()
                .sorted(Comparator.comparingInt(Lance::getValor).reversed())
                .collect(Collectors.toList());
    }

    // Getters
    public EstadoLeilao getEstadoLeilao() {
        return estadoLeilao;
    }

    public String getNomeObjeto() {
        return nomeObjeto;
    }

    public int getLanceMinimo() {
        return lanceMinimo;
    }

    public Set<Usuario> getParticipantes() {
        return Collections.unmodifiableSet(participantes);
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }
}
