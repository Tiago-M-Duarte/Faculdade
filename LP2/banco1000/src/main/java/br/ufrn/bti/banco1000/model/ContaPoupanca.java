package br.ufrn.bti.banco1000.model;

public class ContaPoupanca extends Conta {
    private static final double taxa = 0.02;

    public ContaPoupanca(String nome, char tipo, int senha, Cliente cliente, int agencia) {
        super(nome, tipo, senha, cliente, agencia);
    }

    public ContaPoupanca(String nome, char tipo, int senha, Cliente cliente, int agencia, double saldo, int numeroConta) {
        super(nome, tipo, senha, cliente, agencia, saldo, numeroConta);
    }

    @Override
    public void calcularTaxas() {
        double ultimoDeposito = this.getMovimentacao().get(this.getMovimentacao().size() - 1).getValor();
        double rendimento = ultimoDeposito * taxa;
        this.setSaldo(getSaldo() + rendimento);
        this.getMovimentacao().add(new Movimentacao("ENTRADA", "RENDIMENTO POUPANÇA", rendimento));
    }

    @Override
    public void depositar(double valor) {
        super.depositar(valor);
        calcularTaxas();
    }
}
