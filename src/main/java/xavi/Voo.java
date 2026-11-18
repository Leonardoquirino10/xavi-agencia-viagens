package xavi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Voo {
    public static final int CAPACIDADE = 150;
    private static final String PREFIXO = "XAV";
    private static final long MAX_DIAS_ENTRE_CONEXOES = 1;
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Rota rota;
    private final LocalDate data;
    private final double precoBase;
    private int assentosOcupados;

    public Voo(Rota rota, LocalDate data, double precoBase) {
        if (rota == null || data == null) {
            throw new IllegalArgumentException("Rota e data são obrigatórias.");
        }
        if (!Double.isFinite(precoBase) || precoBase <= 0) {
            throw new IllegalArgumentException("O preço base deve ser um número positivo.");
        }
        this.rota = rota;
        this.data = data;
        this.precoBase = precoBase;
        this.assentosOcupados = 0;
    }

    public String getCodigo() {
        return PREFIXO + rota.getCodigo();
    }

    public Rota getRota() {
        return rota;
    }

    public Cidade getOrigem() {
        return rota.getOrigem();
    }

    public Cidade getDestino() {
        return rota.getDestino();
    }

    public LocalDate getData() {
        return data;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public int getAssentosOcupados() {
        return assentosOcupados;
    }

    public int vagasDisponiveis() {
        return CAPACIDADE - assentosOcupados;
    }

    public boolean temVaga() {
        return vagasDisponiveis() > 0;
    }

    public void ocuparAssento() {
        if (!temVaga()) {
            throw new IllegalStateException("O voo está lotado.");
        }
        assentosOcupados++;
    }

    public boolean podeSerSeguidoPor(Voo proximo) {
        if (proximo == null || getDestino() != proximo.getOrigem()) {
            return false;
        }
        long dias = ChronoUnit.DAYS.between(data, proximo.getData());
        return dias >= 0 && dias <= MAX_DIAS_ENTRE_CONEXOES;
    }

    public boolean mesmoVooEData(Voo outro) {
        return outro != null && getCodigo().equals(outro.getCodigo()) && data.equals(outro.getData());
    }

    @Override
    public String toString() {
        return getCodigo() + " - " + getOrigem().getNome() + " → " + getDestino().getNome()
                + " - " + data.format(FORMATO_DATA)
                + " - R$ " + String.format(Locale.forLanguageTag("pt-BR"), "%.2f", precoBase)
                + " - Vagas: " + vagasDisponiveis();
    }
}
