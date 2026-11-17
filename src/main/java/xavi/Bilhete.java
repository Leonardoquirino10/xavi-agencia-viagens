package xavi;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Bilhete vendido a um cliente. Encadeia um ou mais voos e calcula o preço de venda.
 */
public class Bilhete {
    private static int proximoId = 1;
    private static final double PERC_MAIS_CARO = 0.60;
    private static final double PERC_SEGUNDO_MAIS_CARO = 0.40;
    private static final double PERC_ADICIONAIS = 0.10;

    private final String idBilhete;
    private final List<Voo> voos;
    private final Cliente cliente;
    private final LocalDate dataEmissao;

    public Bilhete(Cliente cliente, Voo primeiroVoo) {
        if (cliente == null || primeiroVoo == null) {
            throw new IllegalArgumentException("Cliente e primeiro voo são obrigatórios.");
        }
        this.idBilhete = String.valueOf(proximoId++);
        this.cliente = cliente;
        this.dataEmissao = LocalDate.now();
        this.voos = new ArrayList<>();
        this.voos.add(primeiroVoo);
    }

    public boolean adicionarVoo(Voo voo) {
        if (voo == null || !validarSequenciaVoo(voo)) {
            return false;
        }
        voos.add(voo);
        return true;
    }

    /**
     * Um voo vale o preço base. Com dois ou mais, 60% do mais caro,
     * 40% do segundo mais caro e 10% de cada voo restante.
     */
    public double calcularPrecoFinal() {
        List<Double> precos = new ArrayList<>();
        for (Voo voo : voos) {
            precos.add(voo.getPrecoBase());
        }
        precos.sort(Comparator.reverseOrder());
        if (precos.size() == 1) {
            return precos.get(0);
        }
        double total = PERC_MAIS_CARO * precos.get(0) + PERC_SEGUNDO_MAIS_CARO * precos.get(1);
        for (int i = 2; i < precos.size(); i++) {
            total += PERC_ADICIONAIS * precos.get(i);
        }
        return total;
    }

    public Cidade getOrigemInicial() {
        return voos.get(0).getOrigem();
    }

    public Cidade getDestinoFinal() {
        return voos.get(voos.size() - 1).getDestino();
    }

    public int getQuantidadeVoos() {
        return voos.size();
    }

    public List<Voo> getVoos() {
        return new ArrayList<>(voos);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getIdBilhete() {
        return idBilhete;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    private boolean validarSequenciaVoo(Voo novoVoo) {
        return origemCompativel(novoVoo) && dataCompativel(novoVoo);
    }

    private boolean origemCompativel(Voo novoVoo) {
        Voo anterior = voos.get(voos.size() - 1);
        return novoVoo.getOrigem() == anterior.getDestino();
    }

    private boolean dataCompativel(Voo novoVoo) {
        Voo anterior = voos.get(voos.size() - 1);
        long dias = ChronoUnit.DAYS.between(anterior.getData(), novoVoo.getData());
        return dias == 0 || dias == 1;
    }

    @Override
    public String toString() {
        return "Bilhete " + idBilhete + " - " + cliente.getNome()
                + " - " + getOrigemInicial() + " → " + getDestinoFinal()
                + " - R$ " + String.format("%.2f", calcularPrecoFinal());
    }
}
