package com.farmacia.views;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.farmacia.controllers.ClienteController;
import com.farmacia.models.Cliente;

public class ClienteView {
    private static final Logger logger = LogManager.getLogger(ClienteView.class);

    public static void iniciar(Scanner scan) {
        try {
            int escolha;
            scan = new Scanner(System.in);
            do {
                exibirMenu();
                escolha = Integer.parseInt(scan.nextLine());
                tratarEscolha(scan, escolha);
            } while (escolha != 0);
        } catch (NumberFormatException e) {
            System.out.println("Digite um número valido!");
            logger.error("Entrada invalida (inteiro esperado)");
            iniciar(scan);
        } 
    }

    private static void exibirMenu() {
        System.out.println("###########################");
        System.out.println("#        Cliente Menu     #");
        System.out.println("###########################");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Alterar");
        System.out.println("3 - Remover");
        System.out.println("4 - Listar");
    }

    private static void tratarEscolha(Scanner scan, int escolha) {
        switch (escolha) {
            case 0:
                System.out.println("Saindo do menu!");
                break;
            case 1:
                cadastrar(scan);
                break;
            case 2:
                alterar(scan);
                break;
            case 3:
                remover(scan);
                break;
            case 4:
                ClienteController.listar(); 
                break;
            default:
                System.out.println("Opção invalida!");
                break;
        }
    }

    private static void cadastrar(Scanner scan) {
        System.out.println("Digite o nome");
        var nome = scan.nextLine();

        if (!nome.matches("[a-zA-Z\\s]+")) {
            System.out.println("Nome deve conter apenas letras e espaços");
            logger.warn("Nome invalido inserido em ClienteView.");
            return;
        }
        
        if(nome.length() == 0) {
            System.out.println("Digite um nome válido!");
            logger.warn("Nome invalido inserido em clienteView.");
            return;
        }

        System.out.println("Digite o CPF");
        var cpf = scan.nextLine();

        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            System.out.println("CPF deve estar no formato 999.999.999-99");
            logger.warn("CPF invalido em ClienteView");
            return;
        }
        
        if(ClienteController.clienteJaExiste(cpf) == true) {
            System.out.println("Cliente com esse CPF já cadastrado!");
            logger.warn("Cliente com CPF duplicado recusado em ClienteView!");
            return;
        }

        System.out.println("Digite a idade");
        var idade = Integer.parseInt(scan.nextLine());

        if(idade < 0 || idade > 110) {
            System.out.println("Digite uma idade valida.");
            logger.warn("Cadastro de idade recusado em ClienteView");
            return;
        }
        
        Cliente cliente = new Cliente(nome, cpf, idade);

        ClienteController.cadastrar(cliente);
    }

    private static void alterar(Scanner scan) {
        try {
            ClienteController.listar();
            System.out.println("Digite o ID do cliente a ser alterado");

            var id = UUID.fromString(scan.nextLine());
            if(ClienteController.buscarClientePorUuid(id) == null) {
                System.out.println("Cliente não encontrado com esse id!");
                logger.debug("Cliente não encontrado em ClienteView!");
                return;
            }

            System.out.println("Digite o novo nome");
            var nome = scan.nextLine();

            if(nome.length() == 0) {
                System.out.println("Digite um nome válido!");
                logger.warn("Nome invalido inserido em ClienteView.");
                return;
            }

            System.out.println("Digite o novo CPF");
            var cpf = scan.nextLine();

            if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
                System.out.println("CPF deve estar no formato 999.999.999-99");
                logger.warn("CPF invalido em ClienteView");
                return;
            }

            if(ClienteController.clienteJaExiste(cpf) == true) {
                System.out.println("Cliente com esse CPF já cadastrado!");
                logger.warn("Cliente com CPF duplicado recusado em ClienteView!");
                return;
            }

            System.out.println("Digite a nova idade");
            var idade = Integer.parseInt(scan.nextLine());

            if(idade < 0 || idade > 110) {
                System.out.println("Digite uma idade valida.");
                logger.warn("Cadastro de idade recusado em ClienteView");
                return;
            }

            Cliente cliente = new Cliente(nome, cpf, idade);
            cliente.setId(id);

            ClienteController.atualizar(cliente);
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
        } 
    }

    private static void remover(Scanner scan)  {
        try {
            ClienteController.listar(); 
            System.out.println("Digite o ID do cliente a ser removido");
            var id = UUID.fromString(scan.nextLine());

            if(ClienteController.buscarClientePorUuid(id) == null) {
                System.out.println("Cliente não encontrado com esse id!");
                logger.debug("Cliente não encontrado em ClienteView!");
                return;
            }

            ClienteController.deletar(id);
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
        }
    }

    public static void listar(List<Cliente> clientes) {
        if(clientes.size() == 0) {
            System.out.println("Nenhum cliente encontrado!");
            return;
        }
        
        for (Cliente cliente : clientes) {
            System.out.println("=============================");
            System.out.println("Id: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Idade: " + cliente.getIdade());
            System.out.println("=============================");
        }
    }
}
