
/**
 * Classe que representa uma pessoa física.
 *
 */
public class PessoaFisica extends Cliente {
    /**
     * O CPF da pessoa física.
     */
    private String cpf;

    /**
     * Construtor que recebe nome e CPF da pessoa física.
     * 
     * @param nome
     *            o nome da pessoa física.
     * @param cpf
     *            o CPF da pessoa física.
     */
    public PessoaFisica(String nome, String cpf) {
        super(nome);
        this.cpf = cpf;
    }

    /**
     * Recupera o CPF da pessoa física.
     * 
     * @return o CPF da pessoa física.
     */
    public String getCpf() {
        return cpf;
    }

}
