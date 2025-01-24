package br.ufrn.bti.banco1000.model;

public class ContaCorrente extends Conta {
    private static final double taxa = 0.005;

    public ContaCorrente(String nome, char tipo, int senha, Cliente cliente, int agencia) {
        super(nome, tipo, senha, cliente, agencia);
    }

    public ContaCorrente(String nome, char tipo, int senha, Cliente cliente, int agencia, double saldo, int numeroConta) {
        super(nome, tipo, senha, cliente, agencia, saldo, numeroConta);
    }

    @Override
    public void calcularTaxas() {
        double custo = this.getMovimentacao().getLast().getValor() * taxa;
        if (custo > getSaldo()) {
            throw new IllegalStateException("Saldo insuficiente para aplicar a taxa.");
        }
        this.setSaldo(getSaldo() - custo);
        this.getMovimentacao().add(new Movimentacao("SAÍDA", "CUSTO DE MANUTENÇÃO", custo));
    }

    @Override
    public void depositar(double valor) {
        super.depositar(valor);
        calcularTaxas();
    }
}
