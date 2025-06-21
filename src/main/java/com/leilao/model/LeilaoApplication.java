package com.leilao.model;

import com.leilao.model.Lance;
import com.leilao.model.Leilao;
import com.leilao.model.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@SpringBootApplication
public class LeilaoApplication implements CommandLineRunner {

    private final LeilaoService leilaoService;

    public LeilaoApplication(LeilaoService leilaoService) {
        this.leilaoService = leilaoService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LeilaoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        leilaoService.executarLeilao();
    }
}

@Service
class LeilaoService {

    public void executarLeilao() {
        Leilao leilao = new Leilao.Builder()
                .setNomeObjeto("Rolex")
                .setLanceMinimo(5000)
                .setDataInicio(LocalDate.of(2025, 6, 1))
                .setDataTermino(LocalDate.of(2025, 6, 10))
                .build();

        Usuario joao = new Usuario("João", "12345678900", "joao@email.com", "1990-01-01");
        Usuario maria = new Usuario("Maria", "98765432100", "maria@email.com", "1992-02-02");
        Usuario jose = new Usuario("José", "23478698822", "jose@gmail.com", "1992-02-04");
        Usuario morgana = new Usuario("Morgana", "04443295100", "zagury42@gmail.com", "2001-10-25");
        Usuario ana = new Usuario("Ana", "11122233344", "ana@email.com", "1991-03-15");
        Usuario lucas = new Usuario("Lucas", "55566677788", "lucas@email.com", "1989-07-20");
        Usuario pedro = new Usuario("Pedro", "99988877766", "pedro@email.com", "1988-11-11");

        leilao.atualizarEstado(LocalDate.of(2025, 6, 1));

        registrarLanceEImprimir(leilao, new Lance(joao, 5100, LocalDate.of(2025, 6, 1)), "Lance 1");
        registrarLanceEImprimir(leilao, new Lance(joao, 5200, LocalDate.of(2025, 6, 1)), "Lance 2");
        registrarLanceEImprimir(leilao, new Lance(maria, 5300, LocalDate.of(2025, 6, 2)), "Lance 3");
        registrarLanceEImprimir(leilao, new Lance(jose, 5400, LocalDate.of(2025, 6, 2)), "Lance 4");
        registrarLanceEImprimir(leilao, new Lance(morgana, 5500, LocalDate.of(2025, 6, 2)), "Lance 5");
        registrarLanceEImprimir(leilao, new Lance(ana, 5600, LocalDate.of(2025, 6, 3)), "Lance 6");
        registrarLanceEImprimir(leilao, new Lance(lucas, 5700, LocalDate.of(2025, 6, 3)), "Lance 7");
        registrarLanceEImprimir(leilao, new Lance(pedro, 5800, LocalDate.of(2025, 6, 4)), "Lance 8");
        registrarLanceEImprimir(leilao, new Lance(joao, 5900, LocalDate.of(2025, 6, 5)), "Lance 9");
        registrarLanceEImprimir(leilao, new Lance(maria, 6000, LocalDate.of(2025, 6, 5)), "Lance 10");

        leilao.atualizarEstado(LocalDate.of(2025, 6, 11));
    }

    private void registrarLanceEImprimir(Leilao leilao, Lance lance, String descricao) {
        boolean registrado = leilao.registrarLance(lance);
        System.out.println(descricao + " registrado: " + registrado);
    }

}
