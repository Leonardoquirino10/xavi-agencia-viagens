package xavi;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ClienteTest {
    private final Cliente cliente = new Cliente("Ana", "Silva", 1);
    private final LocalDate data = LocalDate.of(2026, 11, 18);

    private Bilhete criarBilhete(Cliente titular, double preco) {
        return new Bilhete(titular, new Voo(new Rota(Cidade.BELO_HORIZONTE, Cidade.RECIFE), data, preco));
    }

    @Test
    void criaClienteSemCompras() {
        assertEquals("Ana", cliente.getNome());
        assertEquals(0, cliente.calcularTotalGasto(), 0.001);
        assertEquals("Ana Silva - Registro: 1", cliente.toString());
    }

    @Test
    void removeEspacosNasExtremidadesDoNomeESobrenome() {
        Cliente ana = new Cliente("  Ana  ", "  Silva  ", 1);
        assertEquals("Ana", ana.getNome());
        assertEquals("Ana Silva - Registro: 1", ana.toString());
    }

    @Test
    void rejeitaNomeOuSobrenomeInvalidos() {
        for (String valor : new String[]{null, "", "   "}) {
            assertThrows(IllegalArgumentException.class, () -> new Cliente(valor, "Silva", 1));
            assertThrows(IllegalArgumentException.class, () -> new Cliente("Ana", valor, 1));
        }
    }

    @Test
    void rejeitaRegistroNaoPositivo() {
        assertThrows(IllegalArgumentException.class, () -> new Cliente("Ana", "Silva", 0));
        assertThrows(IllegalArgumentException.class, () -> new Cliente("Ana", "Silva", -1));
    }

    @Test
    void registraCompraDeUmBilhete() {
        Bilhete bilhete = criarBilhete(cliente, 500);
        cliente.comprarBilhete(bilhete);
        assertEquals(500, cliente.calcularTotalGasto(), 0.001);
        assertTrue(cliente.gerarRelatorio().contains(bilhete.toString()));
    }

    @Test
    void somaGastosDeVariosBilhetes() {
        cliente.comprarBilhete(criarBilhete(cliente, 500));
        cliente.comprarBilhete(criarBilhete(cliente, 300));
        assertEquals(800, cliente.calcularTotalGasto(), 0.001);
    }

    @Test
    void usaPrecoFinalDoBilheteComConexao() {
        Bilhete bilhete = criarBilhete(cliente, 500);
        assertTrue(bilhete.adicionarVoo(new Voo(new Rota(Cidade.RECIFE, Cidade.NATAL), data, 400)));
        cliente.comprarBilhete(bilhete);
        assertEquals(460, cliente.calcularTotalGasto(), 0.001);
    }

    @Test
    void rejeitaBilheteNuloSemAlterarTotal() {
        assertThrows(IllegalArgumentException.class, () -> cliente.comprarBilhete(null));
        assertEquals(0, cliente.calcularTotalGasto(), 0.001);
    }

    @Test
    void rejeitaBilheteDeOutroCliente() {
        Bilhete bilhete = criarBilhete(new Cliente("Pedro", "Souza", 2), 500);
        assertThrows(IllegalArgumentException.class, () -> cliente.comprarBilhete(bilhete));
        assertEquals(0, cliente.calcularTotalGasto(), 0.001);
    }

    @Test
    void impedeCompraDuplicadaDoMesmoBilhete() {
        Bilhete bilhete = criarBilhete(cliente, 500);
        cliente.comprarBilhete(bilhete);
        assertThrows(IllegalArgumentException.class, () -> cliente.comprarBilhete(bilhete));
        assertEquals(500, cliente.calcularTotalGasto(), 0.001);
        assertTrue(cliente.gerarRelatorio().contains("Quantidade de bilhetes: 1"));
    }

    @Test
    void geraRelatorioSemCompras() {
        String relatorio = cliente.gerarRelatorio();
        assertTrue(relatorio.contains("Cliente: Ana Silva"));
        assertTrue(relatorio.contains("Registro: 1"));
        assertTrue(relatorio.contains("Quantidade de bilhetes: 0"));
        assertTrue(relatorio.contains("Total gasto: R$ 0,00"));
    }

    @Test
    void geraRelatorioComTodosOsBilhetesETotal() {
        Bilhete primeiro = criarBilhete(cliente, 500);
        Bilhete segundo = criarBilhete(cliente, 300);
        cliente.comprarBilhete(primeiro);
        cliente.comprarBilhete(segundo);
        String relatorio = cliente.gerarRelatorio();
        assertTrue(relatorio.contains("Quantidade de bilhetes: 2"));
        assertTrue(relatorio.contains(primeiro.toString()));
        assertTrue(relatorio.contains(segundo.toString()));
        assertTrue(relatorio.contains("Total gasto: R$ 800,00"));
    }
}
