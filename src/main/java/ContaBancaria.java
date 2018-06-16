import java.io.Serializable;

/**
 * Classe que representa uma conta bancária.
 *
 */
public class ContaBancaria implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contador;

    /**
     * Identificador da conta.
     */
    private int id;

    /**
     * Saldo da conta bancária.
     */
    private int saldo = 0;

    /**
     * O cliente dono da conta bancária.
     */
    private Cliente cliente;

    /**
     * Construtor que recebe o cliente dono da nova conta bancária.
     * 
     * @param cliente
     *            o cliente dono da conta bancária.
     */
    public ContaBancaria(Cliente cliente) {
        this.id = ++contador;
        this.cliente = cliente;
    }

    /**
     * Recupera o identificador da conta.
     * 
     * @return o id da conta.
     */
    public int getId() {
        return id;
    }

    /**
     * Recupera o cliente dono da conta bancária.
     * 
     * @return o cliente dono da conta bancária.
     */
    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Recupera o saldo da conta bancária.
     * 
     * @return o saldo da conta bancária.
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     * Deposita um valor na conta bancária, desde que esse valor seja positivo.
     * 
     * @param valor
     *            o valor a ser depositado na conta bancária. O valor a ser depositado deve ser
     *            positivo.
     * @return o novo saldo da conta bancária, após o depósito.
     */
    public int depositar(int valor) {
        if (valor > 0) {
            saldo = saldo + valor;
        }
        return saldo;
    }

    /**
     * Saca um valor da conta bancária, se esse valor for positivo e menor ou igual ao saldo.
     * 
     * @param valor
     *            o valor a ser sacado da conta bancária. O valor a ser sacado deve ser positivo e menor
     *            ou igual ao saldo da conta bancária.
     * @return o novo saldo da conta bancária, após o saque.
     */
    public int sacar(int valor) {
        if (valor > 0 && valor <= saldo) {
            saldo = saldo - valor;
        }
        return saldo;
    }
    
}
