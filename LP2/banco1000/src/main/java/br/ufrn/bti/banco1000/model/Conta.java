/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;

public class Conta {
    private String nome;
    private Cliente cliente;
    private int agencia;
    private int numeroConta;
    private char tipo;
    private int senha;
    private double saldo;
    private ArrayList<Movimentacao> movimentacao = new ArrayList();

    public Conta(String nome, char tipo, int senha) {
        this.nome = nome;
        this.agencia = 1;
        double doubleRandomNumber = Math.random() * 99999;
        int randomNumber = (int)doubleRandomNumber; // 1 - 99999
        this.numeroConta = randomNumber;
        this.tipo = tipo;
        this.senha = senha;
        this.saldo = 0;
    }
    public int getNumConta(){
        return this.numeroConta;
    }

    public void depositar(double valor) {
        this.saldo = this.saldo + valor;
        this.movimentacao.add(new Movimentacao("FORMA", this.cliente, "DEPOSITO",
                valor));

    }

    public void sacar(double valor) {
        if (this.saldo - valor >= 0) {
            this.saldo = this.saldo - valor;
            this.movimentacao.add(new Movimentacao("FORMA", this.cliente, "SAQUE",
                    valor));
        } else {
            System.out.println("Saldo insuficiente");
        }
    }

    public void transferir(int agencia, int numeroConta, double valor) {
        if (this.saldo - valor >= 0) {
            this.saldo = this.saldo - valor;
        }
    }

    public void transferir(Conta conta, double valor) {

        if (this.saldo - valor >= 0) {
            this.saldo = this.saldo - valor;
            conta.saldo = conta.saldo + valor;
            conta.movimentacao.add(new Movimentacao("FORMA", this.cliente,
                    "ENTRADA POR TRANSFERENCIA", valor));
            this.movimentacao.add(new Movimentacao("FORMA", this.cliente,
                    "SAIDA POR TRANSFERENCIA", valor));
        } else {
            System.out.println("Saldo insuficiente");
        }

    }

    public String getNome() {
        return this.nome;
    }

    public double getSaldo() {
        return this.saldo;
    }

    @Override
    public boolean equals(Object o){
        return false;
    }

    @Override
    public String toString()
    {
        return "";
    }

    public int getAgencia() {
        return agencia;
    }

    public void exibirMovimentacao() {
        System.out.println("Data\tDescrição\tValor");
        for (Movimentacao movimentacao : this.movimentacao) {
            System.out.println(movimentacao.getData() + "\t" + movimentacao.getDescricao() + "\t" + movimentacao.getValor());
        }
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
}