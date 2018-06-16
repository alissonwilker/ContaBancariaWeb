public class BancoBusiness {
    public static enum TipoCliente {
        pessoaFisica, pessoaJuridica
    }

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
            contaBancaria.depositar(valorDeposito);
            return contaBancaria.getSaldo();
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

    public ContaBancaria criarContaBancaria(String nomeCliente, String cpfCnpj, TipoCliente tipoCliente) {
        ContaBancaria contaBancaria;
        if (TipoCliente.pessoaFisica.equals(tipoCliente)) {
            contaBancaria = new ContaBancaria(new PessoaFisica(nomeCliente, cpfCnpj));
        } else {
            contaBancaria = new ContaBancaria(new PessoaJuridica(nomeCliente, cpfCnpj));
        }
        return contaBancaria;
    }
}
