package xavi;

/**
 * Cidades atendidas pela XAVi.
 * O código de dois dígitos entra na rota: os dois primeiros são a origem
 * e os dois últimos, o destino. Belo Horizonte (13) e Recife (50) formam
 * a rota 1350 do enunciado.
 */
public enum Cidade {
    BELO_HORIZONTE("13", "Belo Horizonte"),
    FORTALEZA("21", "Fortaleza"),
    RECIFE("50", "Recife"),
    JOAO_PESSOA("31", "João Pessoa"),
    SAO_LUIS("41", "São Luís"),
    NATAL("61", "Natal");

    private final String codigo;
    private final String nome;

    Cidade(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
