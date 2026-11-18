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
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Cliente: ").append(nome).append(" ").append(sobrenome)
                .append("\nRegistro: ").append(numeroRegistro)
                .append("\nQuantidade de bilhetes: ").append(bilhetes.size()).append("\n");
        for (Bilhete bilhete : bilhetes) {
            relatorio.append(bilhete).append("\n");
        }
        relatorio.append("Total gasto: R$ ").append(String.format(
                Locale.forLanguageTag("pt-BR"), "%.2f", calcularTotalGasto()));
        return relatorio.toString();
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome + " - Registro: " + numeroRegistro;
    }
}
