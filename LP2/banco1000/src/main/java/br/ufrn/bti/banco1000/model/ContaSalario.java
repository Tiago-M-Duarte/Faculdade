package br.ufrn.bti.banco1000.model;

public class ContaSalario extends Conta {
    private String cpfEmpregador;
    private int numSaques = 20;

    public ContaSalario(String nome, char tipo, int senha, Cliente cliente, int agencia, String cpfEmpregador) {
        super(nome, tipo, senha, cliente, agencia);
        setCpfEmpregador(cpfEmpregador);
    }

    public ContaSalario(String nome, char tipo, int senha, Cliente cliente, int agencia, double saldo, int numeroConta, String cpfEmpregador, int numSaques) {
        super(nome, tipo, senha, cliente, agencia, saldo, numeroConta);
        setCpfEmpregador(cpfEmpregador);
        setNumSaques(numSaques);
    }

    public void setCpfEmpregador(String cpfEmpregador) {
        this.cpfEmpregador = cpfEmpregador;
    }

    public String getCpfEmpregador() {
        return cpfEmpregador;
    }

    public void setNumSaques(int numSaques) {
        this.numSaques = numSaques;
    }

    public int getNumSaques() {
        return numSaques;
    }

    @Override
    public void exibirConta() {
        super.exibirConta();
        System.out.println("CPF do empregador: " + this.getCpfEmpregador());
        System.out.println("Número de saques restantes: " + this.getNumSaques());
    }

    @Override
    public void calcularTaxas() {
        // Implementação específica para Conta Salário
        System.out.println("Conta Salário não possui taxas.");
    }

    @Override
    public void depositar(double valor) {
        System.out.println("Conta Salário pode receber depositos apenas da conta do empregador.");
    }

    @Override
    public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }
        if (numSaques <= 0) {
            throw new IllegalStateException("Número máximo de saques atingido.");
        }
        if (this.getSaldo() < valor) {
            throw new IllegalStateException("Saldo insuficiente para realizar o saque.");
        }
        this.setSaldo(getSaldo() - valor);
        this.getMovimentacao().add(new Movimentacao("SAÍDA", "SAQUE", valor));
        numSaques--;
    }

}
