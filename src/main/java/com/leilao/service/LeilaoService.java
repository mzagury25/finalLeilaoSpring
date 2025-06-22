package com.leilao.service;

import com.leilao.model.Leilao;
import com.leilao.model.Lance;
import repository.LeilaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LeilaoService {

    @Autowired
    private LeilaoRepository leilaoRepository;

    public Leilao criarLeilao(Leilao leilao) {
        // Add validation logic here
        return leilaoRepository.save(leilao);
    }

    public Optional<Leilao> buscarLeilao(Long id) {
        return leilaoRepository.findById(id);
    }

    public boolean registrarLance(Long leilaoId, Lance lance) {
        return leilaoRepository.findById(leilaoId)
                .map(leilao -> leilao.registrarLance(lance))
                .orElse(false);
    }
}