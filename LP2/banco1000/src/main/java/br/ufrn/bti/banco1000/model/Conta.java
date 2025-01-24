/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;
import java.util.Date;

public abstract class Conta {
    private String nome;
    private Cliente cliente;
    private int numeroConta;
    private int agencia;
    private char tipo;
    private int senha;
    private double saldo;
    private ArrayList<Movimentacao> movimentacao = new ArrayList();

    public abstract void calcularTaxas();

    public Conta(String nome, char tipo, int senha, Cliente cliente, int agencia) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da conta não pode ser nulo ou vazio.");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente associado à conta não pode ser nulo.");
        }
        if (senha <= 0) {
            throw new IllegalArgumentException("A senha deve ser um número positivo.");
        }
        if (agencia <= 0) {
            throw new IllegalArgumentException("O número da agência deve ser positivo.");
        }

        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
        this.cliente = cliente;
        this.agencia = agencia;
        this.saldo = 0;
        this.numeroConta = (int) (Math.random() * 99999);
    }

    // Construtor para contas já prontas, espera-se que estejam tratadas
    public Conta(String nome, char tipo, int senha, Cliente cliente, int agencia, double saldo, int numeroConta) {
        this.nome = nome;
        this.numeroConta = numeroConta;
        this.tipo = tipo;
        this.senha = senha;
        this.saldo = saldo;
        this.cliente = cliente;
        this.agencia = agencia;
    }

    public int getNumConta() {
        return this.numeroConta;
    }

    public int getAgencia() {
        return this.agencia;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
        this.saldo += valor;
        this.movimentacao.add(new Movimentacao("ENTRADA", "DEPÓSITO", valor));
    }

    public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }
        if (this.saldo < valor) {
            throw new IllegalStateException("Saldo insuficiente para realizar o saque.");
        }
        this.saldo -= valor;
        this.movimentacao.add(new Movimentacao("SAÍDA", "SAQUE", valor));
    }

    public void transferir(Conta conta, double valor) {
        if (conta == null) {
            throw new IllegalArgumentException("A conta de destino não pode ser nula.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }
        if (this.saldo < valor) {
            throw new IllegalStateException("Saldo insuficiente para realizar a transferência.");
        }

        if (conta.getTipo() == 'S') {
            ContaSalario salario = (ContaSalario) conta;
            if (!salario.getCpfEmpregador().equals(this.cliente.getCpf())) {
                throw new IllegalArgumentException("Conta salário só pode receber de uma conta do empregador.");
            }
        }

        this.saldo -= valor;
        conta.saldo += valor;
        this.movimentacao.add(new Movimentacao("SAÍDA", "TRANSFERÊNCIA", valor));
        conta.movimentacao.add(new Movimentacao("ENTRADA", "TRANSFERÊNCIA", valor));
    }

    public String getNome() {
        return this.nome;
    }

    public double getSaldo() {
        return this.saldo;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public String toString() {
        return "";
    }

    public void exibirConta() {
        System.out.println("Nome: " + this.getNome());
        System.out.println("Agência: " + this.getAgencia());
        System.out.println("Numero da conta: " + this.getNumConta());
        System.out.println("Tipo: " + this.getTipo());
    }

    public void exibirMovimentacao() {
        System.out.println("Data\tDescrição\tValor");
        for (Movimentacao movimentacao : this.movimentacao) {
            System.out.println(movimentacao.getData() + "\t" + movimentacao.getDescricao() + "\t" + movimentacao.getValor());
        }
    }

    public char getTipo() {
        return tipo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getSenha() {
        return this.senha;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public ArrayList<Movimentacao> getMovimentacao() {
        return this.movimentacao;
    }

    public void addMovimentacao(String tipo, String descricao, double valor, Date data) {
        this.movimentacao.add(new Movimentacao(tipo, descricao, valor, data));
    }

}