package br.ufrn.bti.banco1000.model;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import br.ufrn.bti.banco1000.model.Agencia;

public class Agencias {
    private ArrayList<Agencia> agencias = new ArrayList();

    public Agencias(){};

    public Agencia getAgencia(int codigo){
        for (Agencia agencia : agencias) {
            if(agencia.getCodigo() == codigo) {
                return agencia;
            }
        }
        System.out.println("Conta inexistente na agência!");
        return null;
    }

    public Conta getConta(int codigo, int conta) {
        Agencia agencia = this.getAgencia(codigo);
        if (agencia != null) {
            return agencia.getContaPorNumero(conta);
        }
        return null;
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
        agencias.add(agencia);
    }

    public Conta addConta(String nome, char tipo, int senha, Cliente cliente, int agencia){
        Conta contaNova = new Conta(nome, tipo, senha, cliente, agencia);
        this.getAgencia(agencia).addConta(contaNova);
        return contaNova;
    }

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void salvarAgencias() throws IOException {
        File arquivo = new File("./LP2/banco1000/src/main/java/br/ufrn/bti/banco1000/cache/agencias.txt");
        if (!arquivo.exists()) {
            arquivo.createNewFile();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Agencia agencia : this.agencias) {
                writer.write(String.format("Agencia:%d\n", agencia.getCodigo()));
                for (Conta conta : agencia.getContas()) {
                    writer.write(String.format(Locale.US, "Conta:%s,%c,%d,%.2f,%s\n",
                            conta.getNome(),
                            conta.getTipo(),
                            conta.getSenha(),
                            conta.getSaldo(),
                            conta.getCliente().getCpf()));
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
                    if (dados.length == 5) {
                        Cliente cliente = clientes.stream()
                                .filter(c -> c.getCpf().equals(dados[4]))
                                .findFirst()
                                .orElse(null);

                        if (cliente != null) {
                            agenciaAtual.addConta(new Conta(
                                    dados[0], // Nome
                                    dados[1].charAt(0), // Tipo
                                    Integer.parseInt(dados[2]), // Senha
                                    cliente, // Cliente
                                    agenciaAtual.getCodigo(), // Código da agência
                                    Double.parseDouble(dados[3]) // Saldo
                            ));
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

                            agenciaAtual.getContas().getLast().addMovimentacao(tipo, descricao, valor, data); // Última conta criada
                        } catch (ParseException e) {
                            System.err.println("Erro ao interpretar a data: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Arquivo de agencias não encontrado
        }
    }
}
