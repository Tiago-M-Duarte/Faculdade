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
    private int agenciaLogada;
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

    public void addContas(Conta conta) {
        this.contas.add(conta);
    }

    public int getAgenciaLogada() {
        return agenciaLogada;
    }

    public int getContaLogada() {
        return contaLogada;
    }

    public void setContaLogada(int contaLogada, int agencia) {
        this.contaLogada = contaLogada;
        this.agenciaLogada = agencia;
    }

    public String getSenha() {
        return senha;
    }
}
