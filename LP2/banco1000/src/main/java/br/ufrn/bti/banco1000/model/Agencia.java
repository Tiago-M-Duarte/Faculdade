package br.ufrn.bti.banco1000.model;

import java.util.ArrayList;

public class Agencia {
    private int codigo;
    private ArrayList<Conta> contas = new ArrayList<>();

    public Agencia(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código da agência deve ser um número positivo.");
        }
        this.codigo = codigo;
    }

    public void addConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("A conta não pode ser nula.");
        }
        this.contas.add(conta);
    }

    public ArrayList<Conta> getContas() {
        return this.contas;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public Conta getContaPorNumero(int numeroConta) {
        if (numeroConta <= 0) {
            throw new IllegalArgumentException("O número da conta deve ser um valor positivo.");
        }
        for (Conta conta : contas) {
            if (conta.getNumConta() == numeroConta) {
                return conta;
            }
        }
        throw new IllegalStateException("Conta inexistente na agência com o número: " + numeroConta);
    }

    public void listarContasCliente(Cliente cliente) {
        for(int i = 0; i < contas.size(); i++){
            if(contas.get(i).getCliente().getCpf().equals(cliente.getCpf())){}
            System.out.println("Conta " + (i+1) + ":");
            contas.get(i).exibirConta();
        }
    }
}
