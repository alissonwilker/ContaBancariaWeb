/**
 * Classe que representa uma conta bancaria.
 *
 */
public class ContaBancaria {
    /**
     * Contador de instancias da classe ContaBancaria.
     */
    private static int contador;

    /**
     * Identificador da conta. Representa o numero da instancia de ContaBancaria.
     */
    private int id;

    /**
     * Saldo da conta bancaria.
     */
    private int saldo = 0;

    /**
     * O cliente dono da conta bancaria.
     */
    private Cliente cliente;

    /**
     * Construtor que recebe o cliente dono da nova conta bancaria. Tambem configura o id da conta de
     * acordo com o contador de instancias, que eh incrementado por este construtor.
     * 
     * @param cliente
     *            o cliente dono da conta bancaria.
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
     * Recupera o cliente dono da conta bancaria.
     * 
     * @return o cliente dono da conta bancaria.
     */
    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Recupera o saldo da conta bancaria.
     * 
     * @return o saldo da conta bancaria.
     */
    public int getSaldo() {
        return saldo;
    }

    /**
     * Deposita um valor na conta bancaria, desde que esse valor seja positivo.
     * 
     * @param valor
     *            o valor a ser depositado na conta bancaria. O valor a ser depositado deve ser
     *            positivo.
     * @return o novo saldo da conta bancaria, após o deposito.
     */
    public int depositar(int valor) {
        if (valor > 0) {
            saldo = saldo + valor;
        }
        return saldo;
    }

    /**
     * Saca um valor da conta bancaria, se esse valor for positivo e menor ou igual ao saldo.
     * 
     * @param valor
     *            o valor a ser sacado da conta bancaria. O valor a ser sacado deve ser positivo e menor
     *            ou igual ao saldo da conta bancaria.
     * @return o novo saldo da conta bancaria, apos o saque.
     */
    public int sacar(int valor) {
        if (valor > 0 && valor <= saldo) {
            saldo = saldo - valor;
        }
        return saldo;
    }

}
