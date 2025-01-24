package br.ufrn.bti.banco1000.model;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Agencias {
    private ArrayList<Agencia> agencias = new ArrayList<>();
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Agencia getAgencia(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código da agência deve ser positivo.");
        }
        for (Agencia agencia : agencias) {
            if (agencia.getCodigo() == codigo) {
                return agencia;
            }
        }
        throw new IllegalStateException("Agência com o código " + codigo + " não encontrada.");
    }

    public Conta getConta(int codigo, int conta) {
        Agencia agencia = getAgencia(codigo);
        if (agencia == null) {
            throw new IllegalStateException("Agência inexistente.");
        }
        Conta contaEncontrada = agencia.getContaPorNumero(conta);
        if (contaEncontrada == null) {
            throw new IllegalStateException("Conta " + conta + " não encontrada na agência " + codigo + ".");
        }
        return contaEncontrada;
    }

    public void getContas(Cliente cliente){
        Scanner scan = new Scanner(System.in);
        System.out.println("Listar contas de qual agência? 1 - 2 - 3:");
        int agencia = scan.nextInt();
        this.getAgencia(agencia).listarContasCliente(cliente);
    }

    public int getLenght(){
        return agencias.size();
    }

    public void add(Agencia agencia) {
        if (agencia == null) {
            throw new IllegalArgumentException("Agência não pode ser nula.");
        }
        agencias.add(agencia);
    }

    public Conta addConta(String nome, char tipo, int senha, Cliente cliente, int agenciaCodigo) {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da conta não pode ser vazio.");
        }
        if (senha <= 0) {
            throw new IllegalArgumentException("A senha deve ser positiva.");
        }

        Conta contaNova;
        switch (tipo) {
            case 'C':
                contaNova = new ContaCorrente(nome, tipo, senha, cliente, agenciaCodigo);
                break;
            case 'P':
                contaNova = new ContaPoupanca(nome, tipo, senha, cliente, agenciaCodigo);
                break;
            case 'S':
                try (Scanner scan = new Scanner(System.in)) {
                    System.out.println("Qual o CPF do seu empregador?");
                    String cpfEmpregador = scan.nextLine();
                    contaNova = new ContaSalario(nome, tipo, senha, cliente, agenciaCodigo, cpfEmpregador);
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de conta inválido: " + tipo);
        }

        Agencia agencia = getAgencia(agenciaCodigo);
        agencia.addConta(contaNova);
        return contaNova;
    }

    public void salvarAgencias() {
        File arquivo = new File("./LP2/banco1000/src/main/java/br/ufrn/bti/banco1000/cache/agencias.txt");
        try {
            if (!arquivo.exists() && !arquivo.createNewFile()) {
                throw new IOException("Não foi possível criar o arquivo de salvamento.");
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                for (Agencia agencia : agencias) {
                    writer.write(String.format("Agencia:%d\n", agencia.getCodigo()));
                    for (Conta conta : agencia.getContas()) {
                        writer.write(String.format(Locale.US, "Conta:%s,%c,%d,%.2f,%s,%d",
                                conta.getNome(),
                                conta.getTipo(),
                                conta.getSenha(),
                                conta.getSaldo(),
                                conta.getCliente().getCpf(),
                                conta.getNumConta()));
                        if (conta instanceof ContaSalario) {
                            ContaSalario salario = (ContaSalario) conta;
                            writer.write(String.format(Locale.US, ",%s,%d",
                                    salario.getCpfEmpregador(),
                                    salario.getNumSaques()));
                        }
                        writer.write("\n");
                        for (Movimentacao movimentacao : conta.getMovimentacao()) {
                            writer.write(String.format("Movimentacao:%s,%s,%s,%.2f\n",
                                    formatter.format(movimentacao.getData()),
                                    movimentacao.getTipo(),
                                    movimentacao.getDescricao(),
                                    movimentacao.getValor()));
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar agências: " + e.getMessage());
        }
    }

    public Agencias(ArrayList<Cliente> clientes) throws IOException {
        Agencia agenciaAtual = null;

        try (BufferedReader reader = new BufferedReader(new FileReader("./LP2/banco1000/src/main/java/br/ufrn/bti/banco1000/cache/agencias.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Agencia:")) {
                    int numero = Integer.parseInt(linha.split(":")[1]);
                    agenciaAtual = new Agencia(numero);
                    this.add(agenciaAtual);
                } else if (linha.startsWith("Conta:") && agenciaAtual != null) {
                    String[] dados = linha.split(":")[1].split(",");
                    if (dados.length >= 6) {
                        Cliente cliente = clientes.stream()
                                .filter(c -> c.getCpf().equals(dados[4]))
                                .findFirst()
                                .orElse(null);

                        if (cliente != null) {
                            if (dados[1].charAt(0) == 'C') {
                                agenciaAtual.addConta(new ContaCorrente(
                                        dados[0], // Nome
                                        dados[1].charAt(0), // Tipo
                                        Integer.parseInt(dados[2]), // Senha
                                        cliente, // Cliente
                                        agenciaAtual.getCodigo(), // Código da agência
                                        Double.parseDouble(dados[3]), // Saldo
                                        Integer.parseInt(dados[5]) // Numero da conta
                                ));
                            } else if (dados[1].charAt(0) == 'P'){
                                agenciaAtual.addConta(new ContaPoupanca(
                                        dados[0], // Nome
                                        dados[1].charAt(0), // Tipo
                                        Integer.parseInt(dados[2]), // Senha
                                        cliente, // Cliente
                                        agenciaAtual.getCodigo(), // Código da agência
                                        Double.parseDouble(dados[3]), // Saldo
                                        Integer.parseInt(dados[5]) // Numero da conta
                                ));
                            } else if (dados[1].charAt(0) == 'S'){
                                agenciaAtual.addConta(new ContaSalario(
                                        dados[0], // Nome
                                        dados[1].charAt(0), // Tipo
                                        Integer.parseInt(dados[2]), // Senha
                                        cliente, // Cliente
                                        agenciaAtual.getCodigo(), // Código da agência
                                        Double.parseDouble(dados[3]), // Saldo
                                        Integer.parseInt(dados[5]), // Numero da conta
                                        dados[6], // CPF empregador
                                        Integer.parseInt(dados[7]) // numSaques
                                ));
                            }

                        }
                    }
                } else if (linha.startsWith("Movimentacao:") && agenciaAtual != null) {
                    String[] dados = linha.split(":")[1].split(",");
                    if (dados.length == 4) {
                        try {
                            Date data = formatter.parse(dados[0]);
                            String tipo = dados[1];
                            String descricao = dados[2];
                            double valor = Double.parseDouble(dados[3]);

                            agenciaAtual.getContas().get(agenciaAtual.getContas().size() - 1).addMovimentacao(tipo, descricao, valor, data); // Última conta criada
                        } catch (ParseException e) {
                            System.err.println("Erro ao interpretar a data: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo de agencias não encontrado
            // Código segue retornando uma array vazia
        }
    }
}
