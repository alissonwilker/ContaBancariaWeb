
/**
 * Classe que representa uma pessoa jurídica.
 *
 */
public class PessoaJuridica extends Cliente {
    /**
     * O CNPJ da pessoa jurídica.
     */
    private String cnpj;

    /**
     * Construtor que recebe o nome e o CNPJ da pessoa jurídica.
     * 
     * @param nome
     *            o nome da pessoa jurídica.
     * @param cnpj
     *            o CNPJ da pessoa jurídica.
     */
    public PessoaJuridica(String nome, String cnpj) {
        super(nome);
        this.cnpj = cnpj;
    }

    /**
     * Recupera o CNPJ da pessoa jurídica.
     * 
     * @return o CNPJ da pessoa jurídica.
     */
    public String getCnpj() {
        return cnpj;
    }

}
