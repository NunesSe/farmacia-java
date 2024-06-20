package com.farmacia.views;

import com.farmacia.controllers.MedicamentoController;
import com.farmacia.models.Medicamento;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MedicamentoView {
    public static void iniciar(Scanner scan) throws IOException {
        int escolha;
        do {
            exibirMenu();
            escolha = Integer.parseInt(scan.nextLine());
            tratarEscolha(scan, escolha);
        } while (escolha != 0);
    }

    private static void exibirMenu() {
        System.out.println("##################################");
        System.out.println("         Menu de Medicamentos      ");
        System.out.println("##################################");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar Medicamento");
        System.out.println("2 - Atualizar Medicamento");
        System.out.println("3 - Remover Medicamento");
        System.out.println("4 - Listar Medicamentos");
    }

    private static void tratarEscolha(Scanner scan, int escolha) throws IOException {
        switch (escolha) {
            case 0:
                System.out.println("Encerrando!");
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
                MedicamentoController.listar();
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private static void cadastrar(Scanner scan) throws IOException {
        System.out.println("Digite o nome do medicamento");
        var nome = scan.nextLine();
        System.out.println("Digite o preço do medicamento");
        var preco = Double.parseDouble(scan.nextLine());
        System.out.println("Digite a função do medicamento");
        var funcao = scan.nextLine();

        Medicamento medicamento = new Medicamento(nome, preco, funcao);

        MedicamentoController.cadastrar(medicamento);
    }

    private static void alterar(Scanner scan) throws IOException {
        MedicamentoController.listar();
        System.out.println("Digite o ID do medicamento a ser alterado");
        var id = UUID.fromString(scan.nextLine());
        System.out.println("Digite o novo nome do medicamento");
        var nome = scan.nextLine();
        System.out.println("Digite o novo preço do medicamento");
        var preco = Double.parseDouble(scan.nextLine());
        System.out.println("Digite a nova função do medicamento");
        var funcao = scan.nextLine();

        Medicamento medicamento = new Medicamento(nome, preco, funcao);
        medicamento.setId(id);

        MedicamentoController.atualizar(medicamento);
    }

    private static void remover(Scanner scan) throws IOException {
        MedicamentoController.listar();
        System.out.println("Digite o ID do medicamento a ser removido");
        var id = UUID.fromString(scan.nextLine());
        MedicamentoController.deletar(id);
    }

    public static void listar(List<Medicamento> medicamentos) {
        for (Medicamento medicamento : medicamentos) {
            System.out.println("Id: " + medicamento.getId());
            System.out.println("Nome: " + medicamento.getNome());
            System.out.println("Preço: " + medicamento.getPreco());
            System.out.println("Função: " + medicamento.getFuncao());
            System.out.println("------------------------");
        }
        
    }
}
