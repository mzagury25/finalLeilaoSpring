import com.leilao.model.EstadoLeilao;
import com.leilao.model.Lance;
import com.leilao.model.Leilao;
import com.leilao.model.Usuario;
import com.leilao.app.LeilaoApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

public class LeilaoTest {

    private Usuario joao;
    private Usuario maria;
    private Leilao leilao;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private LocalDate toLocalDate(int dataFormatoInt) {
        return LocalDate.parse(String.valueOf(dataFormatoInt), formatter);
    }

    @BeforeEach
    public void setUp() {
        joao = new Usuario("João", "12345678900", "joao@email.com", "1980-01-01");
        maria = new Usuario("Maria", "98765432100", "maria@email.com", "1985-02-02");

        leilao = new Leilao.Builder()
                .setNomeObjeto("Escultura Neoclassica")
                .setLanceMinimo(100000)
                .setDataInicio(toLocalDate(20250605))
                .setDataTermino(toLocalDate(20250606))
                .build();
    }

    @Test
    public void testLeilaoComecaInativo() {
        assertEquals(EstadoLeilao.INATIVO, leilao.getEstadoLeilao());
    }

    @Test
    public void testLeilaoAbreNaDataCerta() {
        leilao.atualizarEstado(toLocalDate(20250605));
        assertEquals(EstadoLeilao.ABERTO, leilao.getEstadoLeilao());
    }

    @Test
    public void testRegistrarLanceValido() {
        leilao.atualizarEstado(toLocalDate(20250605));
        assertTrue(leilao.registrarLance(new Lance(joao, 150000, toLocalDate(20250606))));
    }

    @Test
    public void testNaoAceitaLanceSeNaoAberto() {
        assertFalse(leilao.registrarLance(new Lance(joao, 150000, toLocalDate(20250606))));
    }

    @Test
    public void testNaoAceitaLanceExpirado() {
        leilao.atualizarEstado(toLocalDate(20250605));
        leilao.atualizarEstado(toLocalDate(20250607));
        assertEquals(EstadoLeilao.EXPIRADO, leilao.getEstadoLeilao());
        assertFalse(leilao.registrarLance(new Lance(maria, 160000, toLocalDate(20250608))));
    }

    @Test
    public void testNaoAceitaAlteracaoLance() {
        Lance lance = new Lance(joao, 150000, toLocalDate(20250606));
        assertEquals(150000, lance.getValor());
        assertEquals(toLocalDate(20250606), lance.getData());
        assertEquals("João", lance.getParticipante().getNomeUsuario());
    }

    @Test
    public void testDoisLancesSeguidosMesmoUsuarioSaoRejeitados() {
        leilao.atualizarEstado(toLocalDate(20250605));
        assertTrue(leilao.registrarLance(new Lance(joao, 150000, toLocalDate(20250606))));
        assertFalse(leilao.registrarLance(new Lance(joao, 200000, toLocalDate(20250607))));
    }

    @Test
    public void testLanceNaoPodeSerMenorOuIgualAoAnterior() {
        leilao.atualizarEstado(toLocalDate(20250610));
        assertTrue(leilao.registrarLance(new Lance(joao, 150000, toLocalDate(20250611))));
        assertFalse(leilao.registrarLance(new Lance(maria, 17000, toLocalDate(20250612))));
        assertFalse(leilao.registrarLance(new Lance(maria, 18000, toLocalDate(20250613))));
    }

    @Test
    public void testFinalizaSemLances() {
        leilao.atualizarEstado(toLocalDate(20250605));
        leilao.atualizarEstado(toLocalDate(20250607));
        assertEquals(EstadoLeilao.EXPIRADO, leilao.getEstadoLeilao());
    }

    @Test
    public void testGanhador() {
        leilao.atualizarEstado(toLocalDate(20250610));
        assertTrue(leilao.registrarLance(new Lance(joao, 150000, toLocalDate(20250611))));
        assertTrue(leilao.registrarLance(new Lance(maria, 170000, toLocalDate(20250612))));
        leilao.atualizarEstado(toLocalDate(20250613));

        Optional<Usuario> ganhador = leilao.getGanhador();
        assertTrue(ganhador.isPresent());
        assertEquals("Maria", ganhador.get().getNomeUsuario());
    }

    @Test
    public void testMaiorEMenorLance() {
        leilao.atualizarEstado(toLocalDate(20250610));
        leilao.registrarLance(new Lance(joao, 120000, toLocalDate(20250611)));
        leilao.registrarLance(new Lance(maria, 200000, toLocalDate(20250612)));

        OptionalInt maior = leilao.getMaiorLance();
        OptionalInt menor = leilao.getMenorLance();

        assertTrue(maior.isPresent());
        assertEquals(200000, maior.getAsInt());

        assertTrue(menor.isPresent());
        assertEquals(120000, menor.getAsInt());
    }

    @Test
    public void testParticipanteComLanceNaoPodeSerRemovido() {
        leilao.atualizarEstado(toLocalDate(20250610));
        leilao.registrarLance(new Lance(joao, 150000, toLocalDate(20250611)));
        assertFalse(leilao.podeRemoverParticipante(joao));
    }

    @Test
    public void testParticipanteSemLancePodeSerRemovido() {
        assertTrue(leilao.podeRemoverParticipante(joao));
    }

    @Test
    public void testLanceMinimoOuAbaixo() {
        leilao.atualizarEstado(toLocalDate(20250605));

        assertTrue(leilao.registrarLance(new Lance(joao, 100000, toLocalDate(20250605))));
        assertFalse(leilao.registrarLance(new Lance(maria, 99999, toLocalDate(20250605))));
    }

    // Novos após refatoração


    @Test
    public void testPodeRemoverParticipanteInexistente() {
        Leilao leilao = new Leilao.Builder()
                .setNomeObjeto("Quadro")
                .setLanceMinimo(500)
                .setDataInicio(LocalDate.now())
                .setDataTermino(LocalDate.now().plusDays(5))
                .build();


        Usuario participante = new Usuario("Mariangela");

        boolean podeRemover = leilao.podeRemoverParticipante(participante);

        assertTrue(podeRemover, "Participante que não deu lances pode ser removido.");
    }

    @Test
    public void testCriarLeilaoComDatasInvalidas() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Leilao.Builder()
                    .setNomeObjeto("Violão")
                    .setLanceMinimo(500)
                    .setDataInicio(LocalDate.now().plusDays(5))
                    .setDataTermino(LocalDate.now())
                    .build();
        });


}}


