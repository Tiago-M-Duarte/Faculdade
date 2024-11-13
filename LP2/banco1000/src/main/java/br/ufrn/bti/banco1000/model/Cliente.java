/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufrn.bti.banco1000.model;
import java.util.ArrayList;

/**
 *
 * @author Tiago
 */

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
    private int contaLogada;
    private ArrayList<Conta> contas = new ArrayList();

    public Cliente(String nome, String cpf, String email, String telefone, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Conta getConta(int conta) {
        return contas.get(conta);
    }

    public Conta getContaPorNumero(int numeroConta) {
        for (Conta conta : contas) {
            if(conta.getNumConta() == numeroConta) {
                return conta;
            }
        }
        System.out.println("Conta inexistente!");
        return null;
    }

    public void listarContas() {
        for(int i = 0; i < contas.size(); i++){
            System.out.println("Conta " + (i+1) + ":");
            System.out.println("Nome: " + contas.get(i).getNome());
            System.out.println("Agência: " + contas.get(i).getAgencia());
            System.out.println("Numero da conta: " + contas.get(i).getNumConta());
            System.out.println("Tipo: " + contas.get(i).getAgencia());
        }
    }

    public void addContas(Conta conta) {
        this.contas.add(conta);
    }

    public int getContaLogada() {
        return contaLogada;
    }

    public void setContaLogada(int contaLogada) {
        this.contaLogada = contaLogada;
    }

    public String getSenha() {
        return senha;
    }
}
