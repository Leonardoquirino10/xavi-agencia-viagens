package xavi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cliente {
    private final String nome;
    private final String sobrenome;
    private final int numeroRegistro;
    private final List<Bilhete> bilhetes;

    public Cliente(String nome, String sobrenome, int numeroRegistro) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (sobrenome == null || sobrenome.isBlank()) {
            throw new IllegalArgumentException("O sobrenome é obrigatório.");
        }
        if (numeroRegistro <= 0) {
            throw new IllegalArgumentException("O número de registro deve ser positivo.");
        }
        this.nome = nome.trim();
        this.sobrenome = sobrenome.trim();
        this.numeroRegistro = numeroRegistro;
        this.bilhetes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    // Permite montar o itinerário e registrar a compra em uma única chamada.
    public Bilhete emitirEComprarBilhete(Voo primeiroVoo, Voo... conexoes) {
        if (conexoes == null) {
            throw new IllegalArgumentException("A lista de conexões é obrigatória.");
        }
        Bilhete bilhete = new Bilhete(this, primeiroVoo);
        for (Voo conexao : conexoes) {
            if (!bilhete.adicionarVoo(conexao)) {
                throw new IllegalArgumentException("Conexão inválida para este itinerário.");
            }
        }
        comprarBilhete(bilhete);
        return bilhete;
    }

    // Ponto único de registro, inclusive para bilhetes montados pelo código anterior.
    public void comprarBilhete(Bilhete bilhete) {
        if (bilhete == null) {
            throw new IllegalArgumentException("O bilhete é obrigatório.");
        }
        if (bilhete.getCliente() != this) {
            throw new IllegalArgumentException("O bilhete pertence a outro cliente.");
        }
        if (bilhetes.contains(bilhete)) {
            throw new IllegalArgumentException("Este bilhete já foi comprado.");
        }
        // Só inclui a compra no relatório e no total depois de garantir todos os assentos.
        Voo.reservarAssentos(bilhete.getVoos());
        bilhetes.add(bilhete);
    }

    public double calcularTotalGasto() {
        double total = 0;
        for (Bilhete bilhete : bilhetes) {
            total += bilhete.calcularPrecoFinal();
        }
        return total;
    }

    public String gerarRelatorio() {
        String relatorio = "Cliente: " + nome + " " + sobrenome
                + "\nRegistro: " + numeroRegistro
                + "\nQuantidade de bilhetes: " + bilhetes.size() + "\n";
        for (Bilhete bilhete : bilhetes) {
            relatorio += bilhete + "\n";
        }
        relatorio += "Total gasto: R$ " + String.format(
                Locale.forLanguageTag("pt-BR"), "%.2f", calcularTotalGasto());
        return relatorio;
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome + " - Registro: " + numeroRegistro;
    }
}
