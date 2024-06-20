package com.farmacia.views;

import com.farmacia.controllers.VendedorController;
import com.farmacia.models.Vendedor;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class VendedorView {
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
        System.out.println("#       Vendedor Menu     #");
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
                VendedorController.listar();
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
        System.out.println("Digite o salário");
        var salario = Double.parseDouble(scan.nextLine());

        Vendedor vendedor = new Vendedor(nome, cpf, salario);

        VendedorController.cadastrar(vendedor);
    }

    private static void alterar(Scanner scan) throws IOException {
        try {
            VendedorController.listar();
            System.out.println("Digite o ID do vendedor a ser alterado");
            var id = UUID.fromString(scan.nextLine());
            System.out.println("Digite o novo nome");
            var nome = scan.nextLine();
            System.out.println("Digite o novo CPF");
            var cpf = scan.nextLine();
            System.out.println("Digite o novo salário");
            var salario = Double.parseDouble(scan.nextLine());

            Vendedor vendedor = new Vendedor(nome, cpf, salario);
            vendedor.setId(id);

            VendedorController.atualizar(vendedor);
        }  catch (Exception e) {
            System.out.println("Erro ao alterar vendedor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void remover(Scanner scan) throws IOException {
        try {
            VendedorController.listar();
            System.out.println("Digite o ID do vendedor a ser removido");
            var id = UUID.fromString(scan.nextLine());
            VendedorController.deletar(id);
        } 
        catch (Exception e) {
            System.out.println("Erro ao remover vendedor: " + e.getMessage());
        } 
    }

    public static void listar(List<Vendedor> vendedores) throws IOException {
        for (Vendedor vendedor : vendedores) {
            System.out.println("===============================");
            System.out.println("Id: " + vendedor.getId());
            System.out.println("Nome: " + vendedor.getNome());
            System.out.println("CPF: " + vendedor.getCpf());
            System.out.println("Salário: " + vendedor.getSalario());
            System.out.println("===============================");
        }
    }
}
