package xavi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

class BilheteTest {

    private final LocalDate hoje = LocalDate.of(2026, 11, 17);

    @Test
    void criaBilheteComUmVoo() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.RECIFE, hoje, 500));
        assertEquals(1, bilhete.getQuantidadeVoos());
        assertEquals(Cidade.BELO_HORIZONTE, bilhete.getOrigemInicial());
        assertEquals(Cidade.RECIFE, bilhete.getDestinoFinal());
    }

    @Test
    void precoComUmVoo() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.RECIFE, hoje, 500));
        assertEquals(500, bilhete.calcularPrecoFinal(), 0.001);
    }

    @Test
    void precoComDoisVoos() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        assertTrue(bilhete.adicionarVoo(voo(Cidade.FORTALEZA, Cidade.RECIFE, hoje, 400)));
        assertEquals(460, bilhete.calcularPrecoFinal(), 0.001);
    }

    @Test
    void precoComTresOuMaisVoos() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        assertTrue(bilhete.adicionarVoo(voo(Cidade.FORTALEZA, Cidade.RECIFE, hoje, 400)));
        assertTrue(bilhete.adicionarVoo(voo(Cidade.RECIFE, Cidade.JOAO_PESSOA, hoje, 300)));
        assertEquals(490, bilhete.calcularPrecoFinal(), 0.001);
    }

    @Test
    void adicionaVooComOrigemIgualAoDestinoAnterior() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        assertTrue(bilhete.adicionarVoo(voo(Cidade.FORTALEZA, Cidade.SAO_LUIS, hoje.plusDays(1), 200)));
        assertEquals(2, bilhete.getQuantidadeVoos());
        assertEquals(Cidade.SAO_LUIS, bilhete.getDestinoFinal());
    }

    @Test
    void rejeitaVooComOrigemDiferente() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        assertFalse(bilhete.adicionarVoo(voo(Cidade.RECIFE, Cidade.NATAL, hoje, 200)));
        assertEquals(1, bilhete.getQuantidadeVoos());
    }

    @Test
    void aceitaVooNoMesmoDia() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        assertTrue(bilhete.adicionarVoo(voo(Cidade.FORTALEZA, Cidade.RECIFE, hoje, 400)));
    }

    @Test
    void aceitaVooNoDiaSeguinte() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        assertTrue(bilhete.adicionarVoo(voo(Cidade.FORTALEZA, Cidade.RECIFE, hoje.plusDays(1), 400)));
    }

    @Test
    void rejeitaVooComMaisDeUmDiaDeIntervalo() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        assertFalse(bilhete.adicionarVoo(voo(Cidade.FORTALEZA, Cidade.RECIFE, hoje.plusDays(2), 400)));
        assertEquals(1, bilhete.getQuantidadeVoos());
    }

    @Test
    void rejeitaVooEmDataAnterior() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        assertFalse(bilhete.adicionarVoo(voo(Cidade.FORTALEZA, Cidade.RECIFE, hoje.minusDays(1), 400)));
        assertEquals(1, bilhete.getQuantidadeVoos());
    }

    @Test
    void informaOrigemInicialEDestinoFinal() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.FORTALEZA, hoje, 500));
        bilhete.adicionarVoo(voo(Cidade.FORTALEZA, Cidade.SAO_LUIS, hoje, 200));
        assertEquals(Cidade.BELO_HORIZONTE, bilhete.getOrigemInicial());
        assertEquals(Cidade.SAO_LUIS, bilhete.getDestinoFinal());
    }

    @Test
    void getVoosNaoExpoeListaInterna() {
        Bilhete bilhete = bilhete(voo(Cidade.BELO_HORIZONTE, Cidade.RECIFE, hoje, 500));
        List<Voo> copia = bilhete.getVoos();
        assertNotSame(copia, bilhete.getVoos());
        copia.clear();
        assertEquals(1, bilhete.getQuantidadeVoos());
    }

    private Bilhete bilhete(Voo primeiro) {
        return new Bilhete(new Cliente("Ana", "Silva", 1), primeiro);
    }

    private Voo voo(Cidade origem, Cidade destino, LocalDate data, double preco) {
        return new Voo(new Rota(origem, destino), data, preco);
    }
}
