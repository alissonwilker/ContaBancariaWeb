
/**
 * Classe que representa uma pessoa juridica.
 *
 */
public class PessoaJuridica extends Cliente {
    /**
     * O CNPJ da pessoa juridica.
     */
    private String cnpj;

    /**
     * Construtor que recebe o nome e o CNPJ da pessoa juridica.
     * 
     * @param nome
     *            o nome da pessoa juridica.
     * @param cnpj
     *            o CNPJ da pessoa juridica.
     */
    public PessoaJuridica(String nome, String cnpj) {
        super(nome);
        this.cnpj = cnpj;
    }

    /**
     * Recupera o CNPJ da pessoa juridica.
     * 
     * @return o CNPJ da pessoa juridica.
     */
    public String getCnpj() {
        return cnpj;
    }

}
