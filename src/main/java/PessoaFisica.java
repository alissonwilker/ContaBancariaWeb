
/**
 * Classe que representa uma pessoa fisica.
 *
 */
public class PessoaFisica extends Cliente {
    /**
     * O CPF da pessoa fisica.
     */
    private String cpf;

    /**
     * Construtor que recebe nome e CPF da pessoa fisica.
     * 
     * @param nome
     *            o nome da pessoa fisica.
     * @param cpf
     *            o CPF da pessoa fisica.
     */
    public PessoaFisica(String nome, String cpf) {
        super(nome);
        this.cpf = cpf;
    }

    /**
     * Recupera o CPF da pessoa fisica.
     * 
     * @return o CPF da pessoa fisica.
     */
    public String getCpf() {
        return cpf;
    }

}
