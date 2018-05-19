
/**
 * Classe abstrata que representa um cliente (pessoa física ou pessoa jurídica).
 *
 */
public abstract class Cliente {
    /**
     * O nome do cliente.
     */
    private String nome; 

    /**
     * Construtor que recebe o nome do cliente.
     * 
     * @param nome
     *            o nome do cliente.
     */
    protected Cliente(String nome) {
        this.nome = nome;
    }

    /**
     * Recupera o nome do cliente.
     * 
     * @return o nome do cliente.
     */
    public String getNome() {
        return nome;
    }

}
