/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;
import java.util.Date;

public class Conta {
    private String nome;
    private Cliente cliente;
    private int numeroConta;
    private int agencia;
    private char tipo;
    private int senha;
    private double saldo;
    private ArrayList<Movimentacao> movimentacao = new ArrayList();

    public Conta(String nome, char tipo, int senha, Cliente cliente, int agencia) {
        this.nome = nome;
        double doubleRandomNumber = Math.random() * 99999;
        this.numeroConta = (int)doubleRandomNumber;
        this.tipo = tipo;
        this.senha = senha;
        this.saldo = 0;
        this.cliente = cliente;
        this.agencia = agencia;
    }

    public Conta(String nome, char tipo, int senha, Cliente cliente, int agencia, double saldo) {
        this.nome = nome;
        double doubleRandomNumber = Math.random() * 99999;
        this.numeroConta = (int)doubleRandomNumber;
        this.tipo = tipo;
        this.senha = senha;
        this.saldo = saldo;
        this.cliente = cliente;
        this.agencia = agencia;
    }

    public int getNumConta(){
        return this.numeroConta;
    }

    public int getAgencia(){
        return this.agencia;
    }

    public void depositar(double valor) {
        this.saldo = this.saldo + valor;
        this.movimentacao.add(new Movimentacao("FORMA", "DEPOSITO",
                valor));

    }

    public void sacar(double valor) {
        if (this.saldo - valor >= 0) {
            this.saldo = this.saldo - valor;
            this.movimentacao.add(new Movimentacao("FORMA", "SAQUE",
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
            conta.movimentacao.add(new Movimentacao("FORMA",
                    "ENTRADA POR TRANSFERENCIA", valor));
            this.movimentacao.add(new Movimentacao("FORMA",
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

    public void exibirMovimentacao() {
        System.out.println("Data\tDescrição\tValor");
        for (Movimentacao movimentacao : this.movimentacao) {
            System.out.println(movimentacao.getData() + "\t" + movimentacao.getDescricao() + "\t" + movimentacao.getValor());
        }
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
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