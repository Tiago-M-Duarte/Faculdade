/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufrn.bti.banco1000.model;

import java.util.Date;

/**
 * @author vinicius
 */
public class Movimentacao {

    public Date getData() {
        return data;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    private Date data;
    private String tipo;
    private String descricao;
    private double valor;

    public Movimentacao(String tipo, String descricao, double valor) {
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("Tipo de movimentação não pode ser nulo ou vazio.");
        }
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição da movimentação não pode ser nula ou vazia.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor da movimentação deve ser maior que zero.");
        }

        this.data = new Date();
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Movimentacao(String tipo, String descricao, double valor, Date data) {
        if (tipo == null || tipo.isEmpty()) {
            throw new IllegalArgumentException("Tipo de movimentação não pode ser nulo ou vazio.");
        }
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição da movimentação não pode ser nula ou vazia.");
        }
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor da movimentação deve ser maior que zero.");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data da movimentação não pode ser nula.");
        }

        this.data = data;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
    }

}
