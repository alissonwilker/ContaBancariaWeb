public class BancoBusiness {
    public Integer depositarSacarValor(ContaBancaria contaBancaria, int valorDeposito, int valorSaque) {
        if (contaBancaria != null) {
            contaBancaria.depositar(valorDeposito);
            int saldo = contaBancaria.sacar(valorSaque);
            return saldo;
        }
        return null;
    }

    public Integer sacarValor(ContaBancaria contaBancaria, int valorSaque) {
        if (contaBancaria != null) {
            int saldo = contaBancaria.sacar(valorSaque);
            return saldo;
        }
        return null;
    }

    public Integer depositarValor(ContaBancaria contaBancaria, int valorDeposito) {
        if (contaBancaria != null) {
            int saldo = contaBancaria.depositar(valorDeposito);
            return saldo;
        }
        return null;
    }

    public Integer recuperarSaldo(ContaBancaria contaBancaria) {
        if (contaBancaria != null) {
            int saldo = contaBancaria.getSaldo();
            return saldo;
        }
        return null;
    }

    public String recuperarCnpjCliente(ContaBancaria contaBancaria) {
        if (contaBancaria != null) {
            Cliente cliente = contaBancaria.getCliente();
            if (cliente instanceof PessoaJuridica) {
                return ((PessoaJuridica) cliente).getCnpj();
            }
        }
        return null;
    }

    public String recuperarCpfCliente(ContaBancaria contaBancaria) {
        if (contaBancaria != null) {
            Cliente cliente = contaBancaria.getCliente();
            if (cliente instanceof PessoaFisica) {
                return ((PessoaFisica) cliente).getCpf();
            }
        }
        return null;
    }

    public String recuperarNomeCliente(ContaBancaria contaBancaria) {
        if (contaBancaria != null) {
            return contaBancaria.getCliente().getNome();
        }
        return null;
    }

    public ContaBancaria criarContaBancaria(String nomeCliente, String cpfCnpj, Cliente.TipoCliente tipoCliente) {
        return ContaBancaria.criar(nomeCliente, cpfCnpj, tipoCliente);
    }
}