package com.leilao.app;

import com.leilao.model.Lance;
import com.leilao.model.Leilao;
import com.leilao.model.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@SpringBootApplication
public class LeilaoApplication implements CommandLineRunner {

    private final LeilaoService leilaoService;

    public LeilaoApplication(LeilaoService leilaoService) {
        this.leilaoService = leilaoService;
    }

    public static void main(String[] args) {
        // Define o fuso horário da aplicação (opcional, mas recomendado)
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

        // Imprime data e hora atual no momento da inicialização
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Aplicação iniciada em: " + agora.format(formatter));

        SpringApplication.run(LeilaoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        leilaoService.executarLeilao();
    }
}

@Service
class LeilaoService {

    public void executarLeilao() {
        // Data real do sistema
        LocalDate hoje = LocalDate.now();
        LocalDate termino = hoje.plusDays(10);

        Leilao leilao = new Leilao.Builder()
                .setNomeObjeto("Rolex")
                .setLanceMinimo(5000)
                .setDataInicio(hoje)
                .setDataTermino(termino)
                .build();

        // Criando usuários
        Usuario joao = new Usuario("João", "12345678900", "joao@email.com", "1990-01-01");
        Usuario maria = new Usuario("Maria", "98765432100", "maria@email.com", "1992-02-02");
        Usuario jose = new Usuario("José", "23478698822", "jose@gmail.com", "1992-02-04");
        Usuario morgana = new Usuario("Morgana", "04443295100", "zagury42@gmail.com", "2001-10-25");
        Usuario ana = new Usuario("Ana", "11122233344", "ana@email.com", "1991-03-15");
        Usuario lucas = new Usuario("Lucas", "55566677788", "lucas@email.com", "1989-07-20");
        Usuario pedro = new Usuario("Pedro", "99988877766", "pedro@email.com", "1988-11-11");

        // Atualiza o estado do leilão com data atual
        leilao.atualizarEstado(hoje);

        // Registrar lances simulando um a cada dia
        registrarLanceEImprimir(leilao, new Lance(joao, 5100, hoje), "Lance 1");
        registrarLanceEImprimir(leilao, new Lance(joao, 5200, hoje), "Lance 2");
        registrarLanceEImprimir(leilao, new Lance(maria, 5300, hoje.plusDays(1)), "Lance 3");
        registrarLanceEImprimir(leilao, new Lance(jose, 5400, hoje.plusDays(1)), "Lance 4");
        registrarLanceEImprimir(leilao, new Lance(morgana, 5500, hoje.plusDays(1)), "Lance 5");
        registrarLanceEImprimir(leilao, new Lance(ana, 5600, hoje.plusDays(2)), "Lance 6");
        registrarLanceEImprimir(leilao, new Lance(lucas, 5700, hoje.plusDays(2)), "Lance 7");
        registrarLanceEImprimir(leilao, new Lance(pedro, 5800, hoje.plusDays(3)), "Lance 8");
        registrarLanceEImprimir(leilao, new Lance(joao, 5900, hoje.plusDays(4)), "Lance 9");
        registrarLanceEImprimir(leilao, new Lance(maria, 6000, hoje.plusDays(4)), "Lance 10");

        // Finaliza o leilão passando uma data após o término
        leilao.atualizarEstado(termino.plusDays(1));
    }

    private void registrarLanceEImprimir(Leilao leilao, Lance lance, String descricao) {
        boolean registrado = leilao.registrarLance(lance);
        System.out.println(descricao + " registrado: " + registrado);
    }
}
