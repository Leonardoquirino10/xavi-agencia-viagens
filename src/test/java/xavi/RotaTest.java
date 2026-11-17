package xavi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RotaTest {

    @Test
    void codigoDeBeloHorizonteParaRecife() {
        Rota rota = new Rota(Cidade.BELO_HORIZONTE, Cidade.RECIFE);
        assertEquals("1350", rota.getCodigo());
        assertEquals(Cidade.BELO_HORIZONTE, rota.getOrigem());
        assertEquals(Cidade.RECIFE, rota.getDestino());
    }

    @Test
    void rejeitaOrigemIgualAoDestino() {
        assertThrows(IllegalArgumentException.class,
                () -> new Rota(Cidade.NATAL, Cidade.NATAL));
    }

    @Test
    void informaSeLigaAsCidadesPedidas() {
        Rota rota = new Rota(Cidade.FORTALEZA, Cidade.SAO_LUIS);
        assertTrue(rota.liga(Cidade.FORTALEZA, Cidade.SAO_LUIS));
        assertFalse(rota.liga(Cidade.SAO_LUIS, Cidade.FORTALEZA));
    }
}
