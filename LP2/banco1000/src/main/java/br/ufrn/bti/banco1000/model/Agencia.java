package br.ufrn.bti.banco1000.model;
import java.util.ArrayList;
import java.util.Scanner;


public class Agencia {
    private int codigo;
    private ArrayList<Conta> contas = new ArrayList();

    public Agencia(int codigo) {
        Agencia.this.codigo = codigo;
    }

    public void addConta(Conta conta) {
        Agencia.this.contas.add(conta);
    }

    public ArrayList<Conta> getContas() {
        return this.contas;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public Conta getContaPorNumero(int numeroConta) {
        for (Conta conta : contas) {
            if(conta.getNumConta() == numeroConta) {
                return conta;
            }
        }
        System.out.println("Conta inexistente na agência!");
        return null;
    }

    public void listarContasCliente(Cliente cliente) {
        for(int i = 0; i < contas.size(); i++){
            if(contas.get(i).getCliente().getCpf().equals(cliente.getCpf())){}
            System.out.println("Conta " + (i+1) + ":");
            System.out.println("Nome: " + contas.get(i).getNome());
            System.out.println("Agência: " + contas.get(i).getAgencia());
            System.out.println("Numero da conta: " + contas.get(i).getNumConta());
            System.out.println("Tipo: " + contas.get(i).getAgencia());
        }
    }

}
