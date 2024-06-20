package com.farmacia.views;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.farmacia.controllers.ClienteController;
import com.farmacia.models.Cliente;

public class ClienteView {
    public static void iniciar(Scanner scan) throws IOException {
        int escolha;
        do {
            exibirMenu();
            escolha = Integer.parseInt(scan.nextLine());
            tratarEscolha(scan, escolha);
        } while (escolha != 0);
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

    private static void tratarEscolha(Scanner scan, int escolha) throws IOException {
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
                break;
        }
    }

    private static void cadastrar(Scanner scan) throws IOException {
        System.out.println("Digite o nome");
        var nome = scan.nextLine();
        System.out.println("Digite o CPF");
        var cpf = scan.nextLine();
        System.out.println("Digite a idade");
        var idade = Integer.parseInt(scan.nextLine());

        Cliente cliente = new Cliente(nome, cpf, idade);

        ClienteController.cadastrar(cliente);
    }

    private static void alterar(Scanner scan) throws IOException {
        try {
            ClienteController.listar(); 
            System.out.println("Digite o ID do cliente a ser alterado");
            var id = UUID.fromString(scan.nextLine());
            System.out.println("Digite o novo nome");
            var nome = scan.nextLine();
            System.out.println("Digite o novo CPF");
            var cpf = scan.nextLine();
            System.out.println("Digite a nova idade");
            var idade = Integer.parseInt(scan.nextLine());

            Cliente cliente = new Cliente(nome, cpf, idade);
            cliente.setId(id);

            ClienteController.atualizar(cliente);
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
        } catch (Exception e) {
            System.out.println("Erro ao alterar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void remover(Scanner scan) throws IOException {
        try {
            ClienteController.listar(); 
            System.out.println("Digite o ID do cliente a ser removido");
            var id = UUID.fromString(scan.nextLine());
            ClienteController.deletar(id);
        } catch (IllegalArgumentException e) {
            System.out.println("ID inválido. Por favor, insira um UUID válido.");
        } catch (Exception e) {
            System.out.println("Erro ao remover cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void listar(List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            System.out.println("Id: " + cliente.getId());
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Idade: " + cliente.getIdade());
            System.out.println("=============================");
        }
    }
}
