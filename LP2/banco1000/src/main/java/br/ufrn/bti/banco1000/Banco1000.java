/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.ufrn.bti.banco1000;

import br.ufrn.bti.banco1000.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author Tiago
 */
public class Banco1000 {

    public static void salvarClientes(ArrayList<Cliente> clientes) throws IOException {
        File arquivo = new File("./LP2/banco1000/src/main/java/br/ufrn/bti/banco1000/cache/clientes.txt");

        if (!arquivo.exists()) {
            arquivo.createNewFile();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Cliente cliente : clientes) {
                writer.write(String.format("%s,%s,%s,%s,%s\n",
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getEmail(),
                        cliente.getTelefone(),
                        cliente.getSenha()));
            }
        }
    }

    public static ArrayList<Cliente> carregarClientes() throws IOException {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("./LP2/banco1000/src/main/java/br/ufrn/bti/banco1000/cache/clientes.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 5) {
                    clientes.add(new Cliente(dados[0], dados[1], dados[2], dados[3], dados[4]));
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo de clientes não encontrado
        }
        return clientes;
    }

    public static Cliente criarCadastro() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Crie uma cadastro no banco digital");
        System.out.println("Qual o seu nome completo?:");
        String nome = scan.nextLine();
        System.out.println("Qual o seu CPF? (Colocar apenas números):");
        String cpf = scan.nextLine();
        System.out.println("Qual o seu e-mail?:");
        String email = scan.nextLine();
        System.out.println("Qual o seu Telefone?:");
        String telefone = scan.nextLine();
        System.out.println("Qual o será sua senha?:");
        String senha = scan.nextLine();
        return new Cliente(nome, cpf, email, telefone, senha);
    }

    public static Conta criarConta(Agencias agencias, Cliente cliente) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Crie uma conta no banco digital");
        System.out.println("Em qual agencia deseja criar? 1 - 2 - 3:");
        int agencia = scan.nextInt();
        scan.nextLine();
        System.out.println("Qual o nome da nova conta?:");
        String nome = scan.nextLine();
        System.out.println("Qual o tipo da nova conta? (C para Corrente, P para poupança, S para Salario):");
        char tipo = scan.next().charAt(0);
        System.out.println("Qual a senha da nova conta?(Digite apenas números):");
        int senha = scan.nextInt();
        System.out.println("Conta nova criada!");
        return agencias.addConta(nome, tipo, senha, cliente, agencia);
    }

    public static void menuConta( Cliente cliente, ArrayList<Cliente> clientes, Agencias agencias) {
        Scanner scan = new Scanner(System.in);
        Conta contaAcessada = agencias.getConta(cliente.getAgenciaLogada(), cliente.getContaLogada());
        boolean logado = true;
        while (logado) {
            System.out.println("Bem vindo a conta " + contaAcessada.getNome() + ", digite qual opção deseja acessar!");
            System.out.println("1 - Realizar deposito");
            System.out.println("2 - Realizar saque");
            System.out.println("3 - Visualizar saldo");
            System.out.println("4 - Visualizar extrato");
            System.out.println("5 - Transferir para outra conta");
            System.out.println("9 - Sair da conta");
            int opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    System.out.println("Digite o valor do deposito:");
                    contaAcessada.depositar(scan.nextDouble());
                    break;
                case 2:
                    System.out.println("Digite o valor do saque:");
                    contaAcessada.sacar(scan.nextDouble());
                    break;
                case 3:
                    System.out.println("Saldo atual da conta: " +contaAcessada.getSaldo());
                    break;
                case 4:
                    System.out.println("Segue extrato da conta:");
                    contaAcessada.exibirMovimentacao();
                    break;
                case 5:
                    System.out.println("Digite o número da agencia da conta para a qual deseja transferir:");
                    int agencia = scan.nextInt();
                    System.out.println("Digite o número da conta para a qual deseja transferir:");
                    int contaNum = scan.nextInt();
                    Conta contaAlvo = agencias.getConta(agencia, contaNum);
                    if (contaAlvo != null) {
                        System.out.println("Digite o valor que deseja transferir:");
                        double valor = scan.nextDouble();
                        contaAcessada.transferir(contaAlvo, valor);
                        System.out.println("Transferência realizada com sucesso!");
                    } else {
                        System.out.println("Número da conta inválido.");
                    }
                    break;
                case 9:
                    cliente.setContaLogada(-1, -1);
                    logado = false;
                    break;
            }
        }
    }

    public static void menuCliente( Cliente cliente, ArrayList<Cliente> clientes, Agencias agencias) {
        Scanner scan = new Scanner(System.in);
        boolean logado = true;
        while (logado) {
            System.out.println("Bem vindo " + cliente.getNome() + ", digite qual opção deseja acessar!");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Listar contas");
            System.out.println("3 - Logar na conta");
            System.out.println("9 - Sair");
            int opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    criarConta(agencias, cliente);
                    break;
                case 2:
                    agencias.getContas(cliente);
                    break;
                case 3:
                    System.out.println("Digite o número da agência que deseja logar: (1 - 2 - 3)");
                    int agenciaLogada = scan.nextInt();
                    System.out.println("Digite o número da conta que deseja logar:");
                    int contaLogada = scan.nextInt();
                    cliente.setContaLogada(contaLogada, agenciaLogada);
                    menuConta(cliente, clientes, agencias);
                    break;
                case 9:
                    logado = false;
                    break;
            }
        }
    }

    public static void menuInicial(ArrayList<Cliente> clientes, Agencias agencias) {
        Scanner scan = new Scanner(System.in);
        boolean logado = false;
        while (!logado) {
            System.out.println("Bem vindo ao banco digital 1000, digite qual opção deseja acessar!");
            System.out.println("1 - Entrar com usuário existente");
            System.out.println("2 - Criar cadastro");
            System.out.println("3 - Listar cadastros");
            System.out.println("9 - Fechar aplicação");
            int opcao = scan.nextInt();
            switch (opcao) {
                case 1:
                    System.out.println("Digite seu CPF:");
                    String cpf = scan.next();
                    for (Cliente cliente : clientes) {
                        if (Objects.equals(cliente.getCpf(), cpf)) {
                            System.out.println("Qual a senha da conta?:");
                            String senha = scan.next();
                            if (Objects.equals(cliente.getSenha(), senha)) {
                                System.out.println("Login realizado com sucesso!");
                                menuCliente(cliente, clientes, agencias);
                            } else {
                                System.out.println("Senha invalida");
                            }
                            break;
                        }
                    }
                    System.out.println("CPF Inválido");
                    break;
                case 2:
                    clientes.add(criarCadastro());
                    break;
                case 3:
                    System.out.println("Seguem os clientes do banco:");
                    for(int i = 0; i < clientes.size(); i++){
                        System.out.println("Cliente " + (i+1) + ":");
                        System.out.println("Nome: " + clientes.get(i).getNome());
                        System.out.println("CPF: " + clientes.get(i).getCpf());
                    }
                    break;
                case 9:
                    logado = true;
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Cliente> clientes = carregarClientes();
        Agencias agencias = new Agencias(clientes);
        if (agencias.getLenght() < 1){
            System.out.println("Arquivo de agências não encontrado, criando-as");
            agencias.add(new Agencia(1));
            agencias.add(new Agencia(2));
            agencias.add(new Agencia(3));
        }
        if (clientes.isEmpty()){
            System.out.println("Arquivo de clientes não encontrado, então");
            Cliente novoCliente = criarCadastro();
            clientes.add(novoCliente);
            System.out.println("Como é seu primeiro acesso");
            Conta primeiraConta = criarConta(agencias, novoCliente);
            novoCliente.setContaLogada(primeiraConta.getNumConta(), primeiraConta.getAgencia());
            menuConta(novoCliente, clientes, agencias);
            menuCliente(novoCliente, clientes, agencias);
        }
        menuInicial(clientes, agencias);
        salvarClientes(clientes);
        agencias.salvarAgencias();
    }

}
