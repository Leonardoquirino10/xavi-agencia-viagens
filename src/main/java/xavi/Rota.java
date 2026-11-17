package xavi;

/**
 * Ligação fixa entre duas cidades atendidas pela agência.
 * O código tem quatro dígitos: origem + destino.
 */
public class Rota {
    private final Cidade origem;
    private final Cidade destino;

    public Rota(Cidade origem, Cidade destino) {
        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Origem e destino são obrigatórios.");
        }
        if (origem == destino) {
            throw new IllegalArgumentException("Origem e destino devem ser cidades diferentes.");
        }
        this.origem = origem;
        this.destino = destino;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public String getCodigo() {
        return origem.getCodigo() + destino.getCodigo();
    }

    public boolean liga(Cidade origem, Cidade destino) {
        return this.origem == origem && this.destino == destino;
    }

    @Override
    public String toString() {
        return origem.getNome() + " – " + destino.getNome() + " (" + getCodigo() + ")";
    }
}
