package xavi;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class VooTest {
    private final LocalDate data = LocalDate.of(2026, 11, 18);
    private final Rota rota = new Rota(Cidade.BELO_HORIZONTE, Cidade.RECIFE);
    private final Voo voo = new Voo(rota, data, 500);

    private Voo criarConexao(LocalDate dataConexao) {
        return new Voo(new Rota(Cidade.RECIFE, Cidade.NATAL), dataConexao, 300);
    }

    @Test
    void criaVooComDadosInformados() {
        assertSame(rota, voo.getRota());
        assertEquals(Cidade.BELO_HORIZONTE, voo.getOrigem());
        assertEquals(Cidade.RECIFE, voo.getDestino());
        assertEquals(data, voo.getData());
        assertEquals(500, voo.getPrecoBase(), 0.001);
        assertEquals("XAV1350", voo.getCodigo());
        assertEquals(0, voo.getAssentosOcupados());
        assertEquals(150, voo.vagasDisponiveis());
        assertTrue(voo.temVaga());
    }

    @Test
    void rejeitaRotaOuDataNula() {
        assertThrows(IllegalArgumentException.class, () -> new Voo(null, data, 500));
        assertThrows(IllegalArgumentException.class, () -> new Voo(rota, null, 500));
    }

    @Test
    void rejeitaPrecosInvalidos() {
        for (double preco : new double[]{0, -1, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY}) {
            assertThrows(IllegalArgumentException.class, () -> new Voo(rota, data, preco));
        }
    }

    @Test
    void ocuparAssentoReduzVagasDisponiveis() {
        voo.ocuparAssento();
        assertEquals(1, voo.getAssentosOcupados());
        assertEquals(149, voo.vagasDisponiveis());
        assertTrue(voo.temVaga());
    }

    @Test
    void impedeOcupacaoAcimaDaCapacidade() {
        for (int i = 0; i < Voo.CAPACIDADE; i++) {
            voo.ocuparAssento();
        }
        assertFalse(voo.temVaga());
        assertThrows(IllegalStateException.class, voo::ocuparAssento);
        assertEquals(150, voo.getAssentosOcupados());
        assertEquals(0, voo.vagasDisponiveis());
    }

    @Test
    void aceitaConexaoNoMesmoDiaOuNoDiaSeguinte() {
        assertTrue(voo.podeSerSeguidoPor(criarConexao(data)));
        assertTrue(voo.podeSerSeguidoPor(criarConexao(data.plusDays(1))));
    }

    @Test
    void rejeitaConexaoAnteriorOuComMaisDeUmDiaDeIntervalo() {
        assertFalse(voo.podeSerSeguidoPor(criarConexao(data.minusDays(1))));
        assertFalse(voo.podeSerSeguidoPor(criarConexao(data.plusDays(2))));
    }

    @Test
    void rejeitaConexaoComOrigemIncompativelOuNula() {
        assertFalse(voo.podeSerSeguidoPor(new Voo(new Rota(Cidade.FORTALEZA, Cidade.NATAL), data, 300)));
        assertFalse(voo.podeSerSeguidoPor(null));
    }

    @Test
    void identificaMesmoVooEDataMesmoComOutraInstanciaDeRota() {
        Voo outro = new Voo(new Rota(Cidade.BELO_HORIZONTE, Cidade.RECIFE), data, 700);
        assertTrue(voo.mesmoVooEData(outro));
        assertTrue(outro.mesmoVooEData(voo));
        assertTrue(voo.mesmoVooEData(voo));
    }

    @Test
    void diferenciaVoosPorRotaOuData() {
        assertFalse(voo.mesmoVooEData(new Voo(rota, data.plusDays(1), 500)));
        assertFalse(voo.mesmoVooEData(new Voo(new Rota(Cidade.BELO_HORIZONTE, Cidade.NATAL), data, 500)));
        assertFalse(voo.mesmoVooEData(null));
    }

    @Test
    void toStringApresentaInformacoesDoVoo() {
        String texto = voo.toString();
        for (String trecho : new String[]{"XAV1350", "Belo Horizonte", "Recife", "18/11/2026", "500,00", "Vagas: 150"}) {
            assertTrue(texto.contains(trecho));
        }
    }
}
