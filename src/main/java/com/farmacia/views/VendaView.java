package com.farmacia.views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.farmacia.controllers.MedicamentoController;
import com.farmacia.controllers.VendaController;
import com.farmacia.controllers.ClienteController;
import com.farmacia.controllers.VendedorController;
import com.farmacia.daos.VendaDao;
import com.farmacia.exceptions.MedicamentoNaoEncontradoException;
import com.farmacia.exceptions.ClienteNaoEncontradoException;
import com.farmacia.exceptions.VendedorNaoEncontradoException;
import com.farmacia.models.Medicamento;
import com.farmacia.models.Venda;

public class VendaView {

    public static void iniciar(Scanner scan) throws IOException, ClienteNaoEncontradoException, VendedorNaoEncontradoException {
        int escolha;
        do {
            exibirMenu();
            escolha = Integer.parseInt(scan.nextLine());
            tratarEscolha(scan, escolha);
        } while (escolha != 0);
    }

    private static void exibirMenu() {
        System.out.println("###########################");
        System.out.println("#       Venda Menu        #");
        System.out.println("###########################");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar");
        System.out.println("2 - Remover");
        System.out.println("3 - Listar");
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
                remover(scan);
                break;
            case 3:
                VendaController.listar();
                break;
            default:
                break;
        }
    }

    public static void cadastrar(Scanner scan) throws IOException {
        List<Medicamento> medicamentos = new ArrayList<>();
        UUID clienteId = null;
        UUID vendedorId = null;
        int escolha = -1;
        do {
            try {
                ClienteController.listar();
                System.out.println("Escolha um cliente (UUID): ");
                clienteId = UUID.fromString(scan.nextLine());
                ClienteController.buscarClientePorUuid(clienteId);

                VendedorController.listar();
                System.out.println("Escolha um vendedor (UUID): ");
                vendedorId = UUID.fromString(scan.nextLine());
                VendedorController.buscarVendedorPorUuid(vendedorId);

                MedicamentoController.listar();
                System.out.println("Escolha um medicamento (UUID): ");
                UUID uuid = UUID.fromString(scan.nextLine());
                Medicamento medicamento = MedicamentoController.buscarMedicamentoPorUuid(uuid);
                System.out.println("Digite a quantidade que deseja desse medicamento: ");
                int quantidade = Integer.parseInt(scan.nextLine());

                if (quantidade <= 0) {
                    System.out.println("Valor inválido!");
                    return;
                }

                for (int i = 0; i < quantidade; i++) {
                    medicamentos.add(medicamento);
                }

                System.out.println("Pressione 0 para sair ou qualquer outro número para continuar: ");
                escolha = Integer.parseInt(scan.nextLine());
            } catch (MedicamentoNaoEncontradoException e) {
                System.out.println("Nenhum medicamento achado com esse ID!");
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println("UUID inválido!");
                e.printStackTrace();
            }
        } while (escolha != 0);

        Venda venda = new Venda(clienteId, vendedorId, medicamentos);
        VendaController.cadastrar(venda);
    }

    public static void remover(Scanner scan) {
        try {
            VendaController.listar();
            System.out.println("Digite o ID da venda a ser removido");
            var id = UUID.fromString(scan.nextLine());
            VendaController.deletar(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listar(List<Venda> vendas) {
        for (Venda venda : vendas) {
            System.out.println("===============================");
            System.out.println("Id: " + venda.getId());
            System.out.println("Cliente: " + ClienteController.buscarClientePorUuid(venda.getClienteId()).getNome());
            System.out.println("Vendedor: " + VendedorController.buscarVendedorPorUuid(venda.getVendedorId()).getNome());
            System.out.println("Preço Final: " + venda.getPrecoFinal());
            System.out.println("-------------------------------");
            for (Medicamento medicamento : venda.getMedicamentos()) {
                System.out.println("Nome Medicamento: " + medicamento.getNome());
                System.out.println("Preço Medicamento: " + medicamento.getPreco());
                System.out.println("Função Medicamento: " + medicamento.getFuncao());
                System.out.println("-------------------------------");
            }
            System.out.println("===============================");
        }
    }
}
