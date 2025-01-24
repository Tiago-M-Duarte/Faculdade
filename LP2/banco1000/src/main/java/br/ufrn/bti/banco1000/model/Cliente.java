package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;
    private int agenciaLogada = -1; // Inicializa como não logado
    private int contaLogada = -1; // Inicializa como não logado

    public Cliente(String nome, String cpf, String email, String telefone, String senha) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser nulo ou vazio.");
        }
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
            throw new IllegalArgumentException("O CPF deve conter 11 dígitos numéricos.");
        }
        if (telefone == null || telefone.isEmpty()) {
            throw new IllegalArgumentException("O telefone não pode ser nulo ou vazio.");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia.");
        }

        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
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
